package controllers

import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import models.Product
import services.ProductsService

import javax.inject.Inject

class ProductsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listProducts: Action[AnyContent] = Action {
    val products = ProductsService.findAll
    Ok(Json.toJson(products))
  }

  def getProduct(id: Long): Action[AnyContent] = Action {
    ProductsService.findById(id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None          => NotFound(Json.obj("error" -> "Product not found"))
    }
  }

  def createProduct: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      product => {
        ProductsService.create(product)
        Created(Json.toJson(product))
      }
    )
  }

  def updateProduct(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      product =>
        if (product.id == id) {
          ProductsService.update(product) match {
            case Some(updatedProduct) => Ok(Json.toJson(updatedProduct))
            case None                 => NotFound(Json.obj("error" -> "Product not found"))
          }
        } else {
          BadRequest(Json.obj("error" -> "Product ID mismatch"))
        }
    )
  }

  def deleteProduct(id: Long): Action[AnyContent] = Action {
    ProductsService.delete(id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None          => NotFound(Json.obj("error" -> "Product not found"))
    }
  }
}