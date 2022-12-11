package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.days.Day11.Monkey.Companion.parse
import be.tabs_spaces.advent2022.util.lowestCommonMultiple

class Day11 : Day(11) {

    private val monkeys
        get() = inputString
            .split("\n\n")
            .map { parse(it) }

    override fun partOne() = monkeys.runTurns(20) { it / 3 }

    override fun partTwo() = monkeys.runTurns(10_000) { it % monkeys.LCM() }

    private fun List<Monkey>.LCM() = map { it.divisor }.reduce { lcm, divisor -> lowestCommonMultiple(divisor, lcm) }

    private fun List<Monkey>.runTurns(
        times: Int,
        worryModifier: (Long) -> Long
    ) = apply { repeat(times) { runTurn(worryModifier) } }
        .map { it.inspections }
        .sortedDescending()
        .take(2)
        .let { (first, second) -> first * second }

    private fun List<Monkey>.runTurn(worryModifier: (Long) -> Long) {
        forEach { monkey ->
            monkey.takeTurn(worryModifier)
                .forEach { (target, item) -> this[target].catch(item) }
        }
    }

    class Monkey(
        private val items: MutableList<Long>,
        private val operation: (Long) -> Long,
        val divisor: Int,
        private val divisibleTarget: Int,
        private val nonDivisibleTarget: Int,
    ) {
        var inspections = 0L
            private set

        companion object {
            fun parse(raw: String): Monkey {
                return raw.lines()
                    .drop(1)
                    .let { (items, operation, divisor, divisible, nonDivisible) ->
                        Monkey(
                            parseItems(items),
                            parseOperation(operation),
                            divisor.trailingNumber(),
                            divisible.trailingNumber(),
                            nonDivisible.trailingNumber(),
                        )
                    }
            }

            private fun String.trailingNumber() = split(" ").last().toInt()

            private fun parseItems(items: String) = items
                .substringAfter(": ")
                .split(", ")
                .map { it.toLong() }
                .toMutableList()

            private fun parseOperation(operation: String): (Long) -> Long = operation
                .substringAfter("new = ")
                .split(" ")
                .let { raw ->
                    when (raw[1]) {
                        "+" -> { old -> old + if (raw.last() == "old") old else raw.last().toLong() }
                        else -> { old -> old * if (raw.last() == "old") old else raw.last().toLong() }
                    }
                }
        }

        fun takeTurn(worryModifier: (Long) -> Long) = items.map { item ->
            inspections++
            val worryLevel = worryModifier(operation(item))
            when (worryLevel % divisor) {
                0L -> divisibleTarget to worryLevel
                else -> nonDivisibleTarget to worryLevel
            }
        }.also { items.clear() }

        fun catch(item: Long) {
            items.add(item)
        }
    }
}