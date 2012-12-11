package gof

sealed trait Cell{
  def flip: Cell
  def x: Int
  def y: Int
  
  def isNeighbour(other: Cell): Boolean = {
    import Math.abs
    return abs(x - other.x) <= 1 && abs(y - other.y) <= 1 && this != other
  }
  
  def tick(liveNeibCount: Int): Cell = {
    this
  }
  
}

case class Dead(x: Int, y: Int) extends Cell{
  def flip = Live(x,y)
}

case class Live(x: Int, y: Int) extends Cell{
  def flip = Dead(x,y)
}

class Generation(cells: Seq[Cell]){
  
  def getNumberOfLiveNeighbours(cell: Cell): Int = {
    cells.filter{ c => c match{
	      case Live(_,_) => c.isNeighbour(cell)
	      case _ => false
	    }
    }.length
  }
  
}



