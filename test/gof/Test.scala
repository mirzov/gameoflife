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
  
  test("two adjacent live cells have 10 dead neighbours"){
	  val gen = new Generation(Seq(Live(0,0), Live(0,1)))
	  assert( gen.cellsWithNeighbours.count(_.isInstanceOf[Dead]) === 10)
  }
  
  test("2 by 2 block is a still life"){
	  val gen = new Generation(Seq(Live(0,0), Live(0,1), Live(1,0), Live(1,1)))
	  assert( gen.tick.cells.toSet === gen.cells.toSet)
  }
  
  test("3 by 1 oscillator ticks as expected"){
	  val gen = new Generation(Seq(Live(1,0), Live(1,1), Live(1,2)))
	  val expected = Set(Live(0,1), Live(1,1), Live(2,1))
	  assert( gen.tick.cells.toSet === expected)
  }
  
  test("3 by 1 oscillator restores after 2 ticks"){
	  val gen = new Generation(Seq(Live(1,0), Live(1,1), Live(1,2)))
	  assert( gen.tick.tick.cells.toSet === gen.cells.toSet)
  }
  
}






