package base.convergencia;

import base.imagen.Pixel;

import java.util.List;
import java.util.stream.IntStream;

/**
 * calculo de convergencia considerando unicamente los centros
 * de dos iteraciones consecutivas y si se ha alcanzado el
 * maximo numero de iteraciones
 */
public class ConvergenciaEstabilidad extends EstrategiaConvergencia{
   /**
    * constructor de la clase
    * @param maxIter maximo numero de iteraciones permitidas
    * @param umbral valor maximo de distancia entre cada par
    *               de centros de iteraciones sucesivas
    */
   public ConvergenciaEstabilidad(int maxIter, double umbral){
      this.maxIter = maxIter;
      this.umbral = umbral;
   }

   @Override
   /**
    * metodo de calculo de convergencia
    * @param datos lista completa de puntos. Se mantiene para
    *              ofrece una interfaz comun, pero no se usa
    * @param centros1 centros en iteracion i-1
    * @param centros2 centros en iteracion i
    * @return resultado de la comprobacion
    */
   public boolean convergencia(List<Pixel> datos, List<Pixel> centros1,
                               List<Pixel> centros2, int iter) {
      // condicion 1: que se haya alcanzado el maximo de iteraciones
      if(iter > maxIter){
         return true;
      }

      // en caso contrario, se va calculando la distancia entre
      // centros consecutivos
      double distancia =
              IntStream.range(0, centros1.size())
              .boxed()
              .map(indice -> centros1.get(indice)
                      .distanciaCuadratica( centros2.get(indice) )
              )
              .reduce(0.0,(x,y)->x+y);

      // se comprueba ahora la segunda condicion
      return distancia < umbral;
   }
}
