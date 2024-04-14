package ebusiness.models

import kotlinx.serialization.Serializable

@Serializable
data class DiscordMessage(
    val content: String,
    val username: String? = "Ktor Shop",
    val avatarUrl: String? = null
)