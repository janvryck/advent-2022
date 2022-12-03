package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day03Test {
    private val day03 = Day03()

    @Test
    fun partOne() {
        assertThat(day03.partOne()).isEqualTo(157)
    }

    @Test
    fun partTwo() {
        assertThat(day03.partTwo()).isEqualTo(70)
    }

}