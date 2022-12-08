package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day08Test {
    private val day08 = Day08()

    @Test
    fun partOne() {
        assertThat(day08.partOne()).isEqualTo(21)
    }

    @Test
    fun partTwo() {
        assertThat(day08.partTwo()).isEqualTo(8)
    }
}