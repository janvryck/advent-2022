package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.util.InputReader

abstract class Day(dayNumber: Int) {

    protected val inputList: List<String> by lazy { InputReader.getInputAsList(dayNumber) }
    protected val inputString: String by lazy { InputReader.getInputAsString(dayNumber) }

    abstract fun partOne(): Any

    abstract fun partTwo(): Any
}