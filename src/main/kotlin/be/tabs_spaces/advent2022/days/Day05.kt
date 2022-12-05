package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.days.Instruction.Companion.toInstruction

class Day05 : Day(5) {

    private val procedure = inputList
        .takeLastWhile { it.isNotBlank() }
        .map { it.toInstruction() }

    override fun partOne() = CrateMover9000
        .moveCrates(stacks(), procedure)
        .topLevelCargo()

    override fun partTwo() = CrateMover9001
        .moveCrates(stacks(), procedure)
        .topLevelCargo()

    private fun stacks() = inputList
        .takeWhile { it.isNotBlank() }
        .reversed()
        .parseStacks()
}

fun interface CrateMover {
    fun moveCrates(cargo: Cargo, procedure: List<Instruction>): Cargo
}

val CrateMover9000 = CrateMover { cargo, procedure ->
    procedure.forEach { (times, from, to) ->
        repeat(times) {
            cargo[to].add(cargo[from].removeLast())
        }
    }
    return@CrateMover cargo
}

val CrateMover9001 = CrateMover { cargo, procedure ->
    procedure.forEach { (times, from, to) ->
        cargo[to].addAll(cargo[from].takeLast(times))
        repeat(times) { cargo[from].removeLast() }
    }
    return@CrateMover cargo
}

data class Instruction(
    val times: Int,
    val from: Int,
    val to: Int,
) {
    companion object {
        private val instructionPattern = Regex("move (\\d+) from (\\d+) to (\\d+)")
        fun String.toInstruction(): Instruction {
            return instructionPattern.find(this)!!.destructured.let { (times, from, to) ->
                Instruction(
                    times.toInt(),
                    from.toInt() - 1,
                    to.toInt() - 1
                )
            }
        }
    }
}

typealias Cargo = List<MutableList<String>>
fun Cargo.topLevelCargo() = joinToString(separator = "") { it.last() }

private fun List<String>.parseStacks() = emptyStacks(first())
    .apply {
        this@parseStacks
            .drop(1)
            .forEach { fillRow(it) }
    }

private fun emptyStacks(first: String) = first.split(" ")
    .filter { it.isNotBlank() }
    .map { it.toInt() }
    .last()
    .let { List(it) { mutableListOf<String>() } }

private fun List<MutableList<String>>.fillRow(it: String) {
    it.chunked(4).forEachIndexed { stack, content ->
        if (content.isNotBlank()) {
            this[stack].add(content.substringAfter("[").substringBefore("]"))
        }
    }
}