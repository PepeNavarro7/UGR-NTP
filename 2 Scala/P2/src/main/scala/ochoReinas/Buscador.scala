package ochoReinas
import scala.collection.mutable.ListBuffer

class Buscador(val dimension : Int) {
  private def resolverMAL : Tablero = {
    // Necesitamos que este tablero sea mutable, ya que lo mantendremos entre ejecuciones
    var memoria = Tablero(dimension, List[Celda]())
    println("Tablero inicial ->\n" + memoria.toString + '\n')

    def bucle(tablero: Tablero, fila: Int): Boolean = {
      if (fila >= dimension)
        return true
      for (col <- 0 until dimension) {
        if (!Conflicto.conflictoCeldaTablero(Celda(fila, col), tablero)) {
          val aux: Tablero = tablero.agregarReina(fila, col)
          memoria = aux
          println(tablero.toString+'\n')
          if (bucle(aux, fila + 1)) {
            return true
          }
          memoria = tablero
        }
      }
      false
    }
    bucle(memoria,0)
    memoria
  }
  def resolver : Tablero = {
    def go(tablero: Tablero, fila: Int): (Tablero,Boolean) = {
      if (fila >= dimension) // Condicion de salida, si hemos recorrido el tablero entero
        return (tablero,true)
      for (col <- 0 until dimension) {
        if (!Conflicto.conflictoCeldaTablero(Celda(fila, col), tablero)) {
          // Si se puede colocar la reina, creamos un nuevo tablero (tab)
          val tab : Tablero = tablero.agregarReina(fila, col)
          println(tab.toString+'\n')
          val aux: (Tablero,Boolean) = go(tab, fila+1)
          if(aux._2){ // Sólo si hay una solucion en el horizonte hacemos return, si no, seguimos el bucle
            return aux
          }
        }
      }
      (tablero,false) // Si no hay ninguna posicion posible despues del bucle for entero, devolvemos false
    }
    val tablero: Tablero = Tablero(dimension,List[Celda]())
    go(tablero,0)._1
  }
}
@main
def main(): Unit = {
  val solucion: Tablero = Buscador(8).resolver
  println("Fin->\n"+solucion.toString)
}