package controllers

import play.api.libs.iteratee._
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import scala.concurrent.Future

object MySocket extends Controller {
  
	def sockets = WebSocket.using[String]{ req =>
	  	val out = Enumerator[String]()
	  	val in = Iteratee.foreach[String]{
	  		s => {
	  			println("Got " + s)
	  			Enumerator(s + " to you!") >>> out
	  		}
	  	}
	  	(in, out)
	}
	
}

class Echo extends Iteratee[String, Unit]{
  
	override def fold[B](folder: Step[String, Unit] => Future[B]): Future[B] = {
	  
	}
	
	def enum = new Enumerator[String]{
		override def apply[A](i: Iteratee[String, A]): Future[Iteratee[String, A]] = {
			
		}
	}
}
