package be.tabs_spaces.advent2022.days

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import toJson

class Day13 : Day(13) {

    private val packets = inputString
        .split("\n\n")
        .map { packets ->
            packets.lines()
                .let { (first, second) -> Packet(first, second) }
        }

    override fun partOne() = packets
        .mapIndexed { idx, packet -> idx + 1 to packet.inOrder() }
        .filter { (_, inOrder) -> inOrder }
        .sumOf { (idx, _) -> idx }

    override fun partTwo(): Any {
        val dividerPackets = listOf(
            ListValue(listOf(ListValue(listOf(LiteralValue(2))))),
            ListValue(listOf(ListValue(listOf(LiteralValue(6))))),
        )
        return packets
            .flatMap { listOf(it.first, it.second) }
            .toMutableList()
            .apply { addAll(dividerPackets) }
            .sorted()
            .mapIndexed { idx, packet -> idx + 1 to packet }
            .toMap()
            .filterValues { it in dividerPackets }
            .keys.toList()
            .let { (first, second) -> first * second }
    }

    class Packet(left: String, right: String) {
        val first = left.toValue()
        val second = right.toValue()

        private fun String.toValue(): Value = this.toJson()
            .jsonArray
            .toListValue()

        private fun JsonArray.toListValue(): ListValue = this.map {
            when (it) {
                is JsonArray -> it.toListValue()
                is JsonPrimitive -> LiteralValue(it.int)
                else -> throw IllegalArgumentException("Unsupported JSON type")
            }
        }.let { ListValue(it) }

        fun inOrder() = first < second
    }

    interface Value : Comparable<Value>

    @JvmInline
    value class ListValue(private val values: List<Value>) : Value {
        override fun compareTo(other: Value) = when (other) {
            is ListValue -> compareTo(other)
            else -> compareTo(ListValue(listOf(other)))
        }

        private fun compareTo(other: ListValue): Int {
            values.zip(other.values).forEach { (ours, theirs) ->
                if (ours != theirs) {
                    return ours.compareTo(theirs)
                }
            }
            return values.size.compareTo(other.values.size)
        }

        override fun toString() = "$values"
    }

    @JvmInline
    value class LiteralValue(val value: Int) : Value {
        override fun compareTo(other: Value) = when (other) {
            is LiteralValue -> this.value.compareTo(other.value)
            else -> ListValue(listOf(this)).compareTo(other)
        }

        override fun toString() = "$value"
    }
}