package test.gof

import org.scalatest.FunSuite
import gof._

class LifeTest extends FunSuite{
  
  test("live cell returns dead when flipped"){
    val live = Live(1,1)
    assert(live.flip.isInstanceOf[Dead])
  }
  		
  test("cell with 4 neighbours gets 4 as reply from generation.getNumberOfLiveNeighbours"){
    val generation = new Generation(Seq(Live(0,0), Live(0,1), Live(2,0), Live(2,1)))
    assert(generation.getNumberOfLiveNeighbours(Live(1,0)) === 4)
  }
  		
  test("cell with 4 neighbours and one more gets 4 as reply from generation.getNumberOfLiveNeighbours"){
    val generation = new Generation(Seq(Live(0,0), Live(0,1), Live(2,0), Live(2,1), Live(3,0)))
    assert(generation.getNumberOfLiveNeighbours(Live(1,0)) === 4)
  }
  
  test("Any live cell with fewer than two live neighbours dies, as if caused by under-population"){
	  val live = Live(0,0)
	  assert(live.tick(0).isInstanceOf[Dead])
	  assert(live.tick(1).isInstanceOf[Dead])
  }
  
  test("Any live cell with two or three live neighbours lives on to the next generation"){
	  val live = Live(0,0)
	  assert(live.tick(2).isInstanceOf[Live])
	  assert(live.tick(3).isInstanceOf[Live])
  }
  
  test("Any live cell with more than three live neighbours dies, as if by overcrowding."){
	  val live = Live(0,0)
	  assert(live.tick(4).isInstanceOf[Dead])
	  assert(live.tick(5).isInstanceOf[Dead])
  }
  
  test("Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction"){
	  val dead = Dead(0,0)
	  assert(dead.tick(3).isInstanceOf[Live])
  }
  
}