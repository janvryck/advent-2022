package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day07Test {
    private val day07 = Day07()

    @Test
    fun partOne() {
        assertThat(day07.partOne()).isEqualTo(95437)
    }

    @Test
    fun partTwo() {
        assertThat(day07.partTwo()).isEqualTo(24933642)
    }
}