package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def healthcheck() = Action { implicit request: Request[AnyContent] =>
    Ok("Status: OK")
  }
}
