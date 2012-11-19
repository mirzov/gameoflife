package controllers

import play.api.libs.iteratee._
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import scala.concurrent.Future
import models.LifeField
import models.Simulation

object MySocket extends Controller {
  
	def sockets = WebSocket.using[String]{ req =>
	  	val (out,channel) = Concurrent.broadcast[String]
	  	val simulation = new Simulation(10, channel)
	  	val in = Iteratee.foreach[String]{s =>
	  	  	simulation.pause = try{s.toInt} catch{case _ => 500}
	  	}
	  	(in, out)
	}
	
}
