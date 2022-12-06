package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class Day06Test {
    private val day06 = Day06()

    @ParameterizedTest
    @MethodSource("startOfPacketCases")
    fun partOne(input: String, expected: Int) {
        assertThat(day06.startOfPacket(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("startOfMessageCases")
    fun partTwo(input: String, expected: Int) {
        assertThat(day06.startOfMessage(input)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun startOfPacketCases() = listOf(
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
            "nppdvjthqldpwncqszvftbrmjlhg" to 6,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11,
        ).map { (input, expected) -> arguments(input, expected) }.stream()

        @JvmStatic
        fun startOfMessageCases() = listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
            "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
            "nppdvjthqldpwncqszvftbrmjlhg" to 23,
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26,
        ).map { (input, expected) -> arguments(input, expected) }.stream()
    }
}