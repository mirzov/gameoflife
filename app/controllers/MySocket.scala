package controllers

import play.api.libs.iteratee._
import play.api.mvc.Controller
import play.api.mvc.WebSocket
import scala.concurrent.Future
import models.LifeField

object MySocket extends Controller {
  
	def sockets = WebSocket.using[String]{ req =>
	  	val (out,channel) = Concurrent.broadcast[String]
	  	val life = new LifeField(10)
	  	life.flip(Seq((4,4),(4,5),(4,6)))
	  	val in = Iteratee.foreach[String]{
	  		s => {
	  			channel.push(life.print)
	  			life.tick()
	  		}
	  	}
	  	(in, out)
	}
	
}
//	
//	def enum = new Enumerator[String]{
//		override def apply[A](i: Iteratee[String, A]): Future[Iteratee[String, A]] = {
//			
//		}
//	}
//}
