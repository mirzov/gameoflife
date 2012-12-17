package controllers

import play.api.libs.iteratee._
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import scala.concurrent.Future
import models.Simulation
import play.api.libs.json.JsValue

object MySocket extends Controller {
  
	def sockets = WebSocket.using[JsValue]{ req =>
	  	val (out,channel) = Concurrent.broadcast[JsValue]
	  	val simulation = new Simulation(channel)
	  	val in = Iteratee.foreach[JsValue]{s =>
	  	}
	  	(in, out)
	}
	
}
