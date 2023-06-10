package busquedaGenerica
import BusquedaRecursiva.busquedaFibonacci
import scala.math.*

object Fibonacci extends App{
  val v = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610) //size=16
  val aBuscar = 55
  val mayor = (x: Int, y: Int) => if (x > y) true else false
  val busqueda = busquedaFibonacci(v, aBuscar)(mayor)

  println("Secuencia -> " + v.mkString(" "))
  println("Buscamos el  valor " + aBuscar)
  if (busqueda != -1)
    println("Resultado de busqueda: " + v(busqueda))
  else
    println("No se ha encontrado")
}
