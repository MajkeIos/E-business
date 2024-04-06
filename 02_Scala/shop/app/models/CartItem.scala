package models

import play.api.libs.json.{Json, OFormat}

case class CartItem(product: Product, quantity: Int)

object CartItem {
  implicit val cartItemFormat: OFormat[CartItem] = Json.format[CartItem]
}