package ebusiness.services

import ebusiness.models.DiscordMessage
import ebusiness.models.ShopMethod
import ebusiness.models.ShopRequest
import ebusiness.utils.httpClient
import io.ktor.client.request.*
import io.ktor.http.*

class DiscordService {
    private val webhookUrl: String? = System.getenv("DISCORD_WEBHOOK_URL")

    suspend fun sendDiscordMessage(discordMessage: DiscordMessage) {
        val url = webhookUrl ?: throw IllegalArgumentException("Webhook URL is not set.")
        httpClient.post(url) {
            setBody(discordMessage)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun handleDiscordMessage(message: String) {
        try {
            val shopRequest: ShopRequest = parseBotMessageToShopRequest(message)
            val shopResponse: String = ShopService().sendShopRequest(shopRequest)
            DiscordService().sendDiscordMessage(DiscordMessage(shopResponse))
        } catch (e: Exception) {
            DiscordService().sendDiscordMessage(DiscordMessage(e.message ?: "Unknown error"))
        }
    }

    private fun parseBotMessageToShopRequest(message: String): ShopRequest {
        val parts = message.split(" ")
        require(parts.size == 2) {
            "Invalid message format. Expected 'METHOD endpoint', got '$message'"
        }
        require(ShopMethod.entries.map { it.name }.contains(parts[0].uppercase())) {
            "Invalid method '${parts[0]}'. Allowed methods are ${ShopMethod.entries.map { it.name }}"
        }
        val shopMethod: ShopMethod = ShopMethod.valueOf(parts[0].uppercase())
        val shopEndpoint: String = parts[1]
        return ShopRequest(shopMethod, shopEndpoint)
    }
}