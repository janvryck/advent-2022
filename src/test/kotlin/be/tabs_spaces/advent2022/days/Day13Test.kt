package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13Test {

    private val day13 = Day13()

    @Test
    fun partOne() {
        assertThat(day13.partOne()).isEqualTo(13)
    }

    @Test
    fun partTwo() {
        assertThat(day13.partTwo()).isEqualTo(140)
    }
}