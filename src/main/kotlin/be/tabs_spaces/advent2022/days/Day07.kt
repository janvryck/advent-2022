package be.tabs_spaces.advent2022.days

class Day07 : Day(7) {

    private val fileSystem = parseShell(inputString)

    override fun partOne(): Any {
        return -1
    }

    override fun partTwo(): Any {
        return -1
    }

    private fun parseShell(shellOutput: String): FileSystem {
        val shell = Shell()
        shellOutput.toCommands()
            .forEach { (command, output) -> shell.parseCommand(command, output) }
        return shell.fs.also { println(it) }
    }

    private fun String.toCommands() = split("\n$ ")
        .map { it.lines() }
        .map {
            ShellCommand(it.first(), it.drop(1))
        }.drop(1)   // Drop `cd /`

    class Shell(val fs: FileSystem = FileSystem()) {
        private var currentDirectory: Directory = fs

        fun parseCommand(command: String, output: List<String>) {
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
    }

    data class ShellCommand(
        val command: String,
        val output: List<String>,
    )

    class FileSystem : Directory("/")

    open class Directory(
        val name: String,
        val parent: Directory? = null
    ) {
        val dirs: MutableList<Directory> = mutableListOf()
        val files: MutableList<File> = mutableListOf()

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
    }

    data class File(
        val name: String,
        val size: Int,
    )

}