package busquedaGenerica

import org.scalameter.{Bench, Key, Persistor, Warmer, config}
import org.scalameter.api.{Context, Gen}
import BusquedaRecursiva.{busquedaSaltos,busquedaFibonacci}

import scala.collection.View

/**
 * objeto para hacer las mediciones de tiempo generando
 * gráficos indicativos con los resultados obtenidos
 */
object TestRegresion extends Bench.OfflineRegressionReport {

   // se define la configuracion estandar de las pruebas
   override def defaultConfig = Context(
      Key.exec.maxWarmupRuns := 5,
      Key.exec.benchRuns := 20,
      Key.verbose := true
   )

   // se define el numero de busquedas a realizar en cada coleccion
   val numeroAccesos = 50

   // se definen los tamaños de las colecciones a considerar: desde 1000
   // hasta 4000, con saltos de 500: 1000, 1500, 2000, ...., 4000
   val tams = Gen.range("size")(1000, 4000, 500)

   // se generan las colecciones y el conjunto de indices a buscar: se
   // produce una tupla con la coleccion y los indides sobre los que
   // se hara la busqueda. colecciones es un generador de tupas de la
   // forma:
   // - lista sobre la que se buscara
   // - conjunto de indices de los valores a buscar
   val colecciones: Gen[(List[Int], List[Int])] = for {tam <- tams}
      yield (
         Seq.fill(tam)(util.Random.nextInt(tam)).sorted.toList,
         (0 until numeroAccesos).map(_ => util.Random.nextInt(tam)).toList
      )

   // se define la funcion mayor para poder realizar la busqueda
   val mayor = (x: Int, y: Int) => x > y

   /**
    * prueba usando el metodo de busqueda binaria: puede implementarse
    * para comparar con los metodos de busqueda a saltos
    */
   performance of "Busquedas en secuencias - binaria" in {
      /*using(colecciones) curve "binaria" in {
         xs => {
            for (indice <- xs._2)
               BusquedaRecursiva.busquedaBinaria(xs._1, xs._1(indice), mayor)
         }
      }*/
      using(colecciones) curve "saltos" in {
         xs => {
            for (indice <- xs._2)
               busquedaSaltos(xs._1, xs._1(indice))(mayor)
         }
      }
      using(colecciones) curve "fibonacci" in {
         xs => {
            for (indice <- xs._2)
               busquedaFibonacci(xs._1, xs._1(indice))(mayor)
         }
      }
   }
}
