package ebusiness.routings

import ebusiness.models.DiscordMessage
import ebusiness.models.ShopRequest
import ebusiness.services.DiscordService
import ebusiness.services.ShopService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch

fun Route.configureDiscordRoutes() {
    val discordService = DiscordService()

    post("/send-discord-message") {
        val message = call.receiveText()

        launch {
            try {
                discordService.sendDiscordMessage(DiscordMessage(message, "Ktor"))
            } catch (e: Exception) {
                discordService.sendDiscordMessage(DiscordMessage(e.message ?: "Unknown error"))
            }
        }

        call.respond(HttpStatusCode.OK, "Message sent")
    }
}