package be.tabs_spaces.advent2022.days

private const val TOTAL_DISK_SPACE = 70_000_000
private const val REQUIRED_DISK_SPACE = 30_000_000

class Day07 : Day(7) {

    private val fileSystem = Shell.apply { restoreFrom(inputString) }.fs

    override fun partOne() = (listOf(fileSystem) + fileSystem.nestedDirectories())
        .filter { it.totalSize() < 100_000 }
        .sumOf { it.totalSize() }

    override fun partTwo() = (REQUIRED_DISK_SPACE - (TOTAL_DISK_SPACE - fileSystem.totalSize()))
        .let { minimalSpaceToFree ->
            fileSystem.nestedDirectories()
                .filter { it.totalSize() >= minimalSpaceToFree }
                .minBy { it.totalSize() }
                .totalSize()
        }

    object Shell {
        val fs: FileSystem = FileSystem()
        private var currentDirectory: Directory = fs

        fun restoreFrom(output: String) {
            output.toCommands()
                .forEach { (command, output) -> parseCommand(command, output) }
        }

        private fun String.toCommands() = split("\n$ ")
            .map { it.lines() }
            .map {
                ShellCommand(it.first(), it.drop(1))
            }.drop(1)   // Drop `cd /`

        private fun parseCommand(command: String, output: List<String>) {
            when {
                command == "ls" -> parseListing(output)
                command.startsWith("cd ") -> changeDirectory(command.substringAfter("cd "))
            }
        }

        private fun parseListing(listing: List<String>) {
            listing
                .map { it.split(" ") }
                .forEach { (meta, name) ->
                    if (meta == "dir") {
                        currentDirectory.addDirectory(name)
                    } else {
                        currentDirectory.addFile(name, meta)
                    }
                }
        }

        private fun changeDirectory(destination: String) {
            currentDirectory = when (destination) {
                ".." -> moveUp()
                else -> moveDown(destination)
            }
        }

        private fun moveUp() = currentDirectory.parent ?: throw IllegalArgumentException("Can not move up from root")

        private fun moveDown(destination: String) = currentDirectory.findDirectory(destination)

        data class ShellCommand(
            val command: String,
            val output: List<String>,
        )
    }

    class FileSystem : Directory("/")

    open class Directory(
        val name: String,
        val parent: Directory? = null
    ) {
        private val dirs = mutableListOf<Directory>()
        private val files = mutableListOf<File>()

        fun findDirectory(destination: String) = dirs
            .firstOrNull {
                it.name == destination
            } ?: throw IllegalArgumentException("Can not find directory '$destination' in '$name'")

        fun addDirectory(name: String) {
            dirs.add(Directory(name, this))
        }

        fun addFile(name: String, meta: String) {
            files.add(File(name, meta.toInt()))
        }

        fun nestedDirectories(): List<Directory> = dirs + dirs.flatMap { it.nestedDirectories() }

        fun totalSize(): Int = files.sumOf { it.size } + dirs.sumOf { it.totalSize() }
    }

    data class File(
        val name: String,
        val size: Int,
    )

}