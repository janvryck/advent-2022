package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    private val day14 = Day14()

    @Test
    fun partOne() {
        assertThat(day14.partOne()).isEqualTo(24)
    }

    @Test
    fun partTwo() {
        assertThat(day14.partTwo()).isEqualTo(93)
    }
}