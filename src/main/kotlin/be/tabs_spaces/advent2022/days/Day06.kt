package be.tabs_spaces.advent2022.days

class Day06: Day(6) {
    override fun partOne() = startOfPacket(inputString)

    override fun partTwo() = startOfMessage(inputString)

    fun startOfPacket(input: String) = packetMarker(
        input = input,
        markerSize = 4
    )

    fun startOfMessage(input: String) = packetMarker(
        input = input,
        markerSize = 14
    )

    private fun packetMarker(input: String, markerSize: Int) = markerSize + input
        .windowed(markerSize)
        .indexOfFirst { it.toSet().size == it.length }
}