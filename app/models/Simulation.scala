package models

import language.implicitConversions
import scala.actors.Actor
import Actor._
import play.api.libs.iteratee.Concurrent.Channel
import scala.actors.TIMEOUT
import gof._
import play.api.libs.functional._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.util._

case class SimRequest(startGen: Generation, steps: Int, pause: Int)

class SimulationController(channel: Channel[JsValue]) extends Actor {

	private var simulation: Option[Simulation] = None

	def genToJs(gen: Generation) = Json.obj(
										"life" -> gen.cells.map{cell => 
											Json.obj(
												"x" -> cell.x,
												"y" -> cell.y
											)
										}
									)
	
	def lifeOutput(gen: Generation) = channel.push(genToJs(gen))
	
	implicit val cellReads = (
	    (__ \ "x").read[Int] and (__ \ "y").read[Int]
	)(Live)
	
	implicit val simRequestReads = (
	    (__ \ "startGen").read[List[Live]].map(new Generation(_)) and
	    (__ \ "steps").read[Int] and
	    (__ \ "pause").read[Int]
	)(SimRequest)
	
	override def act = {
	  	loop{
	  		receive{
		  	  	case js: JsValue =>
		  	  		js.asOpt[SimRequest] match{
		  	  		  	case Some(simReq) =>
		  	  		  		stopSimulation()
		  	  		  		startNewSimulation(simReq)
		  	  		  	case None => stopSimulation()
		  	  		} 
		  	  	case _ => stopSimulation()
	  		}
	  	}
	}
	
	private def stopSimulation() = {
		simulation.foreach(_ ! "exit")
		simulation = None
	}
	private def startNewSimulation(simReq: SimRequest){
	  	val sim = new Simulation(lifeOutput, simReq.startGen, simReq.steps, simReq.pause)
		sim.start()
		simulation = Some(sim)
	}
}

class Simulation(output: Generation => Unit, startGen: Generation, steps: Int, pause: Int) extends Actor{

	private var life = startGen

	override def act = {
		for(_ <- 1 to steps){
			receiveWithin(pause){
				case "exit" => exit()
				case TIMEOUT =>
					life = life.tick
					output(life)
					if(life.cells.isEmpty) exit()
			}
		}
	}
}