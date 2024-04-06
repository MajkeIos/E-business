package controllers

import models.Cart
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import services.CartsService

import javax.inject.Inject

class CartsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listCarts: Action[AnyContent] = Action {
    val carts = CartsService.findAll()
    Ok(Json.toJson(carts))
  }
  
  def getCart(id: Long): Action[AnyContent] = Action {
    CartsService.findById(id) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound(Json.obj("error" -> "Cart not found"))
    }
  }

  def createCart:Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      cart => {
        CartsService.create(cart)
        Created(Json.toJson(cart))
      }
    )
  }

  def updateCart(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      cart => {
        if (cart.id == id) {
          CartsService.update(cart) match {
            case Some(updatedCategory) => Ok(Json.toJson(updatedCategory))
            case None => NotFound(Json.obj("error" -> "Cart not found"))
          }
        } else {
          BadRequest(Json.obj("error" -> "Cart ID mismatch"))
        }
      }
    )
  }

  def deleteCart(id: Long): Action[AnyContent] = Action {
    CartsService.delete(id) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound(Json.obj("error" -> "Cart not found"))
    }
  }
}