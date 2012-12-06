package models

import scala.actors.Actor._
import play.api.libs.iteratee.Concurrent.Channel
import scala.actors.TIMEOUT

class Simulation(size: Int, channel: Channel[String]) {

	var pause = 500
	
	private val life = new LifeField(size)
	life.flip(Seq((4,4),(4,5),(4,6)))
	
	val runner = actor{
		for(i <- 1 to 10){
			receiveWithin(pause){
				case "exit" => exit()
				case TIMEOUT =>
					life.tick()
					channel.push(life.print)
			}
		}
	}
  
}