package ochoReinas

import scala.annotation.tailrec

/**
 * clase para representar celdas del tablero
 *
 * @param fila
 * @param columna
 */
case class Celda(val fila : Int, val columna : Int)

/**
 * objeto con metodos para determinar conflicto entre celdas
 */
object Conflicto {
   /**
    * determina si hay conflicto entre dos celdas
    * @param celda1
    * @param celda2
    * @return
    */
   private def conflictoCeldaCelda(celda1 : Celda, celda2 : Celda) : Boolean = {
      val resultado = celda1.fila == celda2.fila ||
      celda1.columna == celda2.columna ||
         Math.abs(celda1.fila - celda2.fila) ==
            Math.abs(celda1.columna - celda2.columna)
      resultado
   }

   /**
    * determina si una celda esta en conflicto con la lista
    * de celdas pasadas como argumento. Es metodo privado
    * @param celda
    * @param resto
    * @return
    */
   private def conflictoCeldaLista(celda : Celda, resto : List[Celda]) : Boolean = {
      val celdaConflicto = resto.find(celdaResto => Conflicto.conflictoCeldaCelda(celdaResto, celda))
      celdaConflicto match {
         case None => false
         case _ => true
      }
   }

   /**
    * determina si se genera conflicto al agregar una nueva reina
    * en la celda indicada
    * @param celda
    * @param tablero
    * @return
    */
   def conflictoCeldaTablero(celda : Celda, tablero : Tablero) : Boolean =
      conflictoCeldaLista(celda, tablero.contenido)
}

/**
 * clase para representar el tablero
 * @param dimension numero de filas y columnas
 * @param contenido contenido del tablero, solo de las
 *                  celdas ocupadas
 */
class Tablero(val dimension : Int, val contenido : List[Celda]) {

   /**
    * se agrega nueva reina al tablero y se genera un tablero
    * nuevo
    * @param fila
    * @param columna
    * @return
    */
   def agregarReina(fila : Int, columna : Int) =
      new Tablero(dimension, Celda(fila, columna) :: contenido)

   /**
    * metodo to string
    * @return
    */
   override def toString : String = {
      (0 until dimension).map(fila => {
         // se obtienen las celdas que se correspondan con
         // la fila
         toStringAux(contenido.filter(celda => celda.fila == fila))
      }).mkString("\n")
   }

   /**
    * metodo auxiliar para toString
    * @param contenidoFila
    * @return
    */
   private def toStringAux(contenidoFila : List[Celda]) : String = {
      (0 until dimension).
         map(columna => {
            contenidoFila.find(celda => celda.columna == columna).getOrElse(None) match {
               case None => " 0 "
               case _ => " X "
            }
         }
      ).mkString
   }
}
