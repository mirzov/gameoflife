package models


case class Cell(x: Int, y: Int, world: LifeField){
  
  def isAlive: Boolean = world(x,y)
  
  def flip : Unit = world.flip(x,y)
  
}

class LifeField(n: Int) {

  val field = new Array[Array[Boolean]](n)
  (1 to n).foreach{
     i => field(i) = new Array[Boolean](n)
  }
  
  def apply(i: Int, j: Int): Boolean = field(i)(j)
  
  def flip(i: Int, j: Int): Unit = {
    field(i)(j) = !field(i)(j)
  }
  
}