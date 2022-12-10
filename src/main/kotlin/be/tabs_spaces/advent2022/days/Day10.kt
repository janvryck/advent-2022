package be.tabs_spaces.advent2022.days

class Day10 : Day(10) {

    private val program = inputList.map {
        when {
            it == "noop" -> Noop
            it.startsWith("addx") -> it.split(" ").let { (_, toAdd) ->
                AddX(toAdd.toInt())
            }

            else -> throw IllegalArgumentException("Unrecognized instructin: $it")
        }
    }
    private val handheld = Handheld().apply { execute(program) }

    override fun partOne() = handheld
        .signalStrengths()
        .filterKeys { it % 40 == 20 }
        .values
        .sum()

    override fun partTwo() = handheld.drawSprites()

    class Handheld {
        private var cycles = List(1) { 1 }
        private val screenWidth = 40
        private val screenHeight
            get() = (cycles.size - 1) / 40

        fun execute(program: List<Instruction>) {
            program.forEach {
                cycles = cycles + it.run(cycles.lastOrNull() ?: 1)
            }
        }

        fun signalStrengths(): Map<Int, Int> {
            return cycles.mapIndexed { index, x ->
                (index + 1) to (index + 1) * x
            }.toMap()
        }

        fun drawSprites() = with(CRT(screenWidth, screenHeight)) {
            cycles.mapIndexed { index, x -> index to x }
                .dropLast(1)
                .forEach { (index, x) ->
                    val row = (index) / width
                    val column = (index) % width
                    pixel(row, column, column in x - 1..x + 1)
                }
            draw()
        }
    }

    class CRT(val width: Int, height: Int) {
        private val pixels = MutableList(height) {
            MutableList(width) { " " }
        }

        fun pixel(row: Int, column: Int, on: Boolean) {
            pixels[row][column] = if (on) "█" else " "
        }

        fun draw() = pixels
            .joinToString("\n", "\n", "\n") {
                it.joinToString(separator = "")
            }
    }

    fun interface Instruction {
        fun run(x: Int): List<Int>
    }

    object Noop : Instruction {
        override fun run(x: Int) = listOf(x)
    }

    class AddX(private val toAdd: Int) : Instruction {
        override fun run(x: Int) = listOf(x, x + toAdd)

    }
}
