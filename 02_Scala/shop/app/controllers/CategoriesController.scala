package controllers

import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import models.Category
import services.CategoriesService

import javax.inject.Inject

class CategoriesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def listCategories: Action[AnyContent] = Action {
    val categories = CategoriesService.findAll
    Ok(Json.toJson(categories))
  }

  def getCategory(id: Long): Action[AnyContent] = Action {
    CategoriesService.findById(id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound(Json.obj("error" -> "Category not found"))
    }
  }

  def createCategory: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      category => {
        CategoriesService.create(category)
        Created(Json.toJson(category))
      }
    )
  }

  def updateCategory(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid JSON")),
      category =>
        if (category.id == id) {
          CategoriesService.update(category) match {
            case Some(updatedCategory) => Ok(Json.toJson(updatedCategory))
            case None => NotFound(Json.obj("error" -> "Category not found"))
          }
        } else {
          BadRequest(Json.obj("error" -> "Category ID mismatch"))
        }
    )
  }

  def deleteCategory(id: Long): Action[AnyContent] = Action {
    CategoriesService.delete(id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound(Json.obj("error" -> "Category not found"))
    }
  }
}