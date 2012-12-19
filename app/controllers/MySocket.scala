package controllers

import play.api.libs.iteratee._
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import scala.concurrent.Future
import models.SimulationController
import play.api.libs.json.JsValue

object MySocket extends Controller {
  
	def sockets = WebSocket.using[JsValue]{ req =>
	  	val (out,channel) = Concurrent.broadcast[JsValue]
	  	val simulation = new SimulationController(channel)
	  	simulation.start()
	  	val in = Iteratee.foreach[JsValue]{js =>
	  		simulation ! js
	  	}
	  	(in, out)
	}
	
}
