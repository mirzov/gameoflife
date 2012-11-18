package models

//import scala.collection.immutable.IndexedSeq

trait Field{
  
	def flip(row: Int, col: Int): Unit
	def apply(row: Int, col: Int): Boolean
	def size: (Int, Int)
	
	def flip(flipSeq: Seq[(Int,Int)]): Unit = 
		flipSeq.foreach(u => flip(u._1, u._2))
		
	def neibours(row: Int, col: Int): Int = {
		(for(
		    x <- (row - 1 to row + 1);
		    y <- (col - 1 to col + 1);
		    if ((x != row || y != col) && apply(x,y))
		) yield 1).sum
	} 
	
	def tick(): Unit = {
		val (rows,cols) = size
		val flips = for(
		    x <- 0 to rows - 1;
		    y <- 0 to cols - 1;
			neibs = neibours(x,y);
			live = this(x,y);
			if(live && (neibs < 2 || neibs > 3) || !live && neibs == 3)
		) yield (x,y)
		flip(flips)
	}
	
	def print: String = {
		val (rows,cols) = size
		(0 to rows - 1).map{x =>
		  	(0 to cols - 1).map{y => if(this(x,y)) "X" else "O"}.mkString(" ")
		}.mkString("\n")
	}
}


class LifeField(n: Int) extends Field {
  
	private val cells = (1 to n).map(i => new Array[Boolean](n)).toArray
	
	private def circ(x: Int) = (x + n) % n
	
	override def size = (n,n) 
	
	override def flip(row: Int, col: Int){
		val x = circ(row)
		val y = circ(col)
		cells(x)(y) = !cells(x)(y)
	}
	
	override def apply(row: Int, col: Int): Boolean = {
		val x = circ(row)
		val y = circ(col)
		cells(x)(y)
	}
		
}