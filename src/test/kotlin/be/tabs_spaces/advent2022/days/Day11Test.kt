package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    private val day11 = Day11()

    @Test
    fun partOne() {
        assertThat(day11.partOne()).isEqualTo(10_605L)
    }

    @Test
    fun partTwo() {
        assertThat(day11.partTwo()).isEqualTo(2_713_310_158)
    }
}