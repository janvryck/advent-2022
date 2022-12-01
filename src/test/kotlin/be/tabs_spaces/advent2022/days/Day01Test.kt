package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day01Test {
    private val day01 = Day01()

    @Test
    fun partOne() {
        assertThat(day01.partOne()).isEqualTo(24000)
    }

    @Test
    fun partTwo() {
        assertThat(day01.partTwo()).isEqualTo(45000)
    }

}