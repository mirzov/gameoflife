package gof

sealed trait Cell{
  def flip: Cell
  def x: Int
  def y: Int
  
  def tick(liveNeibCount: Int): Cell
  
  def isNeighbourOf(other: Cell): Boolean = {
    val dx = Math.abs(x - other.x)
    val dy = Math.abs(y - other.y) 
    return dx <= 1 && dy <= 1 && (dx + dy) > 0
  }
  
}

case class Dead(x: Int, y: Int) extends Cell{
  def flip = Live(x,y)
  
  def tick(liveNeibCount: Int): Cell = {
	  if(liveNeibCount == 3) flip else this
  }
  
}

case class Live(x: Int, y: Int) extends Cell{
  def flip = Dead(x,y)
  
  def tick(liveNeibCount: Int): Cell = {
	  if(liveNeibCount < 2 || liveNeibCount > 3) flip else this
  }
  
}

class Generation(val cells: Seq[Live]){
  
	def getNumberOfLiveNeighbours(cell: Cell) =	cells.filter(_.isNeighbourOf(cell)).length
	
	def cellsWithNeighbours: Seq[Cell] = {
		cells.flatMap{cell => {
				for(
				    i <- (cell.x - 1) to (cell.x + 1);
					j <- (cell.y - 1) to (cell.y + 1)
				) yield Dead(i,j)
			}
		}
		.distinct
		.diff(cells.map(_.flip)) ++ cells
	}
  
    private def tickCell(cell: Cell) = cell.tick(getNumberOfLiveNeighbours(cell))
    
  	def tick = new Generation(
  	    cellsWithNeighbours
  	    .map(tickCell)
  	    .collect{case live: Live => live}
  	)
  
}



