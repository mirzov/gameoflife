package models

import scala.actors.Actor._
import play.api.libs.iteratee.Concurrent.Channel
import scala.actors.TIMEOUT
import gof._
import play.api.libs.json._

class Simulation(channel: Channel[JsValue]) {

	val pause = 500
	
	private var life = new Generation(Seq(Live(5,4), Live(5,5), Live(5,6)))
	
	private def toJsValue: JsValue = Json.obj(
	    "life" -> life.cells.map{cell => 
        	Json.obj(
        	    "x" -> cell.x,
        	    "y" -> cell.y
        	)
        }
	)
	
	val runner = actor{
		for(i <- 1 to 100){
			receiveWithin(pause){
				case "exit" => exit()
				case TIMEOUT =>
					life = life.tick
					channel.push(toJsValue)
			}
		}
	}
  
}