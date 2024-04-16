package ebusiness.utils

import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import ebusiness.services.DiscordService

class DiscordBot(private val token: String) {

    private lateinit var client: Kord

    suspend fun start() {
        client = Kord(token)

        client.on<MessageCreateEvent> {
            if (message.author?.isBot != false) return@on
            if (!message.content.startsWith("!")) return@on
            val discordMessage = message.content.removePrefix("!");
            DiscordService().handleDiscordMessage(discordMessage)
        }

        client.login {
            @OptIn(PrivilegedIntent::class)
            intents += Intent.MessageContent
        }
    }
}