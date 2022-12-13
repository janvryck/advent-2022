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
        return -1
    }

    class Packet(left: String, right: String) {
        private val first = left.toValue()
        private val second = right.toValue()

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

    interface Value {
        operator fun compareTo(other: Value): Int
    }

    @JvmInline
    value class ListValue(val values: List<Value>) : Value {
        override fun compareTo(other: Value): Int {
            return when (other) {
                is ListValue -> compareTo(other)
                else -> compareTo(ListValue(listOf(other)))
            }
        }

        private fun compareTo(other: ListValue): Int {
            values.zip(other.values).forEach { (ours, theirs) ->
                if (ours != theirs) {
                    return ours.compareTo(theirs)
                }
            }
            return values.size.compareTo(other.values.size)
        }
    }

    @JvmInline
    value class LiteralValue(val value: Int) : Value {
        override fun compareTo(other: Value): Int {
            return when (other) {
                is LiteralValue -> this.value.compareTo(other.value)
                else -> ListValue(listOf(this)).compareTo(other)
            }
        }
    }
}