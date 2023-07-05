package busquedaGenerica

import scala.math.{min, sqrt}


object BusquedaRecursiva {
  def busquedaFibonacci[A](coleccion: List[A], aBuscar: A)(mayorQue: (A, A) => Boolean): Int = {
    @annotation.tailrec
    def go(inicio: Int, f0: Int, f1: Int): Int = {
      val indice = min(inicio + f0, coleccion.size - 1)
      if (coleccion(indice) == aBuscar)
        return indice
      if (mayorQue(aBuscar, coleccion(indice))) { // n > v(indice)
        go(indice, f1 - f0, f0)
      } else { // n < v(indice)
        go(inicio, f0 - (f1 - f0), f1 - f0)
      }
    }
    @annotation.tailrec
    def buscaFibonacci(f0: Int, f1: Int): (Int, Int) = {
      if ( (f0+f1) >= coleccion.size ) // Caso base
        (f0,f1)
      else // Caso inductivo
        buscaFibonacci(f1, f0+f1)
    }

    if (coleccion.isEmpty)
      return -1
    val tupla: (Int,Int) = buscaFibonacci(0,1)
    //println("El tam del vector es "+coleccion.size+" y los nfib son "+tupla._1+" y "+tupla._2)
    go(-1, tupla._1, tupla._2)
  }

  def busquedaSaltos[A](coleccion: List[A], aBuscar: A)(mayorQue: (A, A) => Boolean): Int = {
    val tam_bloque = sqrt(coleccion.size).toInt
    //println("Tam bloque: " + tam_bloque)

    // Busqueda lineal simple
    def busquedaLineal(inicio: Int, fin: Int): Int = {
      for (i <- inicio to fin) {
        if (coleccion(i) == aBuscar)
          return i
      }
      -1
    }

    // Entiendo que la funcion es tail recursive porque la ultima llamada de la funcion es sí misma
    @annotation.tailrec
    def go(inicio: Int): Int = {
      if (inicio >= coleccion.size) // Si el inicio supera el tamaño maximo, es que el elemento no esta
        return -1
      val fin = min(inicio + tam_bloque - 1, coleccion.size - 1)
      // (coleccion>=aBuscar) === !(aBuscar>coleccion)
      if ( !mayorQue(aBuscar, coleccion(fin)) ) {
        busquedaLineal(inicio, fin)
      } else { // si no, seguimos la recursion
        go(inicio + tam_bloque)
      }
    }
    // Si la lista esta vacia, ni empezamos
    if (coleccion.isEmpty)
      return -1
    go(0)
  }
}
