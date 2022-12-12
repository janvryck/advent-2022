package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {
    private val day12 = Day12()

    @Test
    fun partOne() {
        assertThat(day12.partOne()).isEqualTo(31)
    }

    @Test
    fun partTwo() {
        assertThat(day12.partTwo()).isEqualTo(29)
    }
}