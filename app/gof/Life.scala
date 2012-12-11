package gof

sealed trait Cell{
  def flip: Cell
  def x: Int
  def y: Int
  
  def isLiveNeighbour(other: Cell): Boolean = {
    import Math.abs
    return isLive && abs(x - other.x) <= 1 && abs(y - other.y) <= 1 && this != other
  }
  
  def isLive: Boolean
  
  def tick(liveNeibCount: Int): Cell
  
}

case class Dead(x: Int, y: Int) extends Cell{
  def flip = Live(x,y)
  
  def isLive = false

  def tick(liveNeibCount: Int): Cell = {
	  if(liveNeibCount == 3) flip else this
  }
  
}

case class Live(x: Int, y: Int) extends Cell{
  def flip = Dead(x,y)
  
  def isLive = true

  def tick(liveNeibCount: Int): Cell = {
	  if(liveNeibCount < 2 || liveNeibCount > 3) flip else this
  }
  
}

class Generation(cells: Seq[Cell]){
  
	def getNumberOfLiveNeighbours(cell: Cell) =	cells.filter(_.isLiveNeighbour(cell)).length
  
    private def tickCell(cell: Cell) = cell.tick(getNumberOfLiveNeighbours(cell))
    
  	def tick = new Generation(cells.map(tickCell))
  
}



