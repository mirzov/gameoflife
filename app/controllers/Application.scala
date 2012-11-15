package controllers

import play.api.mvc._

object Application extends Controller {
  
	def index = Action { req =>
		Ok(views.html.index(req))
	}

  
}
