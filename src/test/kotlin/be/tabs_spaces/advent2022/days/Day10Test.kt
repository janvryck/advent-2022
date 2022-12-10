package be.tabs_spaces.advent2022.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10Test {
    private val day10 = Day10()

    @Test
    fun partOne() {
        assertThat(day10.partOne()).isEqualTo(13140)
    }

    @Test
    fun partTwo() {
        assertThat(day10.partTwo()).isEqualTo(
            """
            
            ██  ██  ██  ██  ██  ██  ██  ██  ██  ██  
            ███   ███   ███   ███   ███   ███   ███ 
            ████    ████    ████    ████    ████    
            █████     █████     █████     █████     
            ██████      ██████      ██████      ████
            ███████       ███████       ███████     
            
        """.trimIndent()
        )
    }

}