package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.days.Day02.Move.*
import be.tabs_spaces.advent2022.days.Day02.Result.*

class Day02 : Day(2) {

    override fun partOne(): Any {
        return inputList
            .map { it.split(" ") }
            .map { it[0].toMove() to it[1].toMove() }
            .sumOf { it.play() }
    }

    override fun partTwo(): Any {
        return inputList
            .map { it.split(" ") }
            .map { it[0].toMove() to it[1].toResult().moveFor(it[0].toMove()) }
            .sumOf { it.play() }
    }

    enum class Move(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3)
        ;

        fun play(other: Move): Result {
            return when (this) {
                ROCK -> when (other) {
                    PAPER -> LOSS
                    ROCK -> DRAW
                    SCISSORS -> WIN
                }
                PAPER -> when (other) {
                    SCISSORS -> LOSS
                    PAPER -> DRAW
                    ROCK -> WIN
                }
                SCISSORS -> when (other) {
                    ROCK -> LOSS
                    SCISSORS -> DRAW
                    PAPER -> WIN
                }
            }
        }
    }

    enum class Result(val score: Int) {
        LOSS(0),
        DRAW(3),
        WIN(6)
        ;

        fun moveFor(other: Move): Move {
            return when (this) {
                LOSS -> when (other) {
                    ROCK -> SCISSORS
                    PAPER -> ROCK
                    SCISSORS -> PAPER
                }
                DRAW -> other
                WIN -> when (other) {
                    ROCK -> PAPER
                    PAPER -> SCISSORS
                    SCISSORS -> ROCK
                }
            }
        }
    }

    private fun String.toMove(): Move = when (this) {
        "A", "X" -> ROCK
        "B", "Y" -> PAPER
        "C", "Z" -> SCISSORS
        else -> throw IllegalArgumentException("Unknown move")
    }

    private fun String.toResult(): Result = when (this) {
        "X" -> LOSS
        "Y" -> DRAW
        "Z" -> WIN
        else -> throw IllegalArgumentException("Unknown move")
    }

    private fun Pair<Move, Move>.play(): Int {
        return second.score + second.play(first).score
    }
}
