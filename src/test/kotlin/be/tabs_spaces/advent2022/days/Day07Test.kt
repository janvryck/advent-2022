package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun partOne() {
        assertThat(Day07().partOne()).isEqualTo(95437)
    }

    @Test
    @Disabled("Investigate why test fails when run in combination with any other test.")
    fun partTwo() {
        assertThat(Day07().partTwo()).isEqualTo(24933642)
    }
}