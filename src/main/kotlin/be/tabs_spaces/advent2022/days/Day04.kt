package be.tabs_spaces.advent2022.days

import be.tabs_spaces.advent2022.days.Assignment.Companion.toAssignment

class Day04 : Day(4) {

    private val assignments = inputList
        .map { it.split(",") }
        .map { (left, right) -> left.toAssignment() to right.toAssignment() }

    override fun partOne() = assignments.count { it.hasFullDuplicate() }

    override fun partTwo() = assignments.count { (left, right) -> left.overlaps(right) }
}

private typealias AssignmentPair = Pair<Assignment, Assignment>
private fun AssignmentPair.hasFullDuplicate() = first.contains(second) || second.contains(first)

private class Assignment(val from: Int, val to: Int) {

    companion object {
        fun String.toAssignment() = split("-")
            .map { it.toInt() }
            .let { (from, to) -> Assignment(from, to) }
    }

    fun contains(other: Assignment) = from <= other.from && to >= other.to
    fun overlaps(other: Assignment) = this.from <= other.to && this.to >= other.from
}
