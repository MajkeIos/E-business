package ebusiness.models

data class ShopRequest(
    val method: ShopMethod,
    val endpoint: String
)