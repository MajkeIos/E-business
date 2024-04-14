package ebusiness.services

import ebusiness.models.ShopMethod
import ebusiness.models.ShopRequest
import ebusiness.utils.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ShopService {
    private val shopBaseUrl: String = System.getenv("SHOP_BASE_URL") ?: "http://localhost:9000"

    suspend fun sendShopRequest(shopRequest: ShopRequest): String {
        if (shopRequest.method == ShopMethod.GET) {
            val httpResponse: HttpResponse
            try {
                httpResponse = httpClient.get(shopBaseUrl + shopRequest.endpoint)
            } catch (e: Exception) {
                throw Exception("Error while sending request to Shop")
            }
            if (httpResponse.status.value != 200) {
                throw Exception("Received status ${httpResponse.status.value} from Shop")
            }
            return httpResponse.body<String>()
        } else {
            throw IllegalArgumentException("Method ${shopRequest.method} not implemented/allowed")
        }
    }
}