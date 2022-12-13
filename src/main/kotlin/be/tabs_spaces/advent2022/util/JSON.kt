import kotlinx.serialization.json.Json

fun String.toJson() = Json.parseToJsonElement(this)