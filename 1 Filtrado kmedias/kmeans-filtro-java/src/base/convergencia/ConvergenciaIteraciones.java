package base.convergencia;

import base.imagen.Pixel;

import java.util.List;

/**
 * clase para implementar la convergencia considerando
 * unicamente el numero de iteraciones
 */
public class ConvergenciaIteraciones extends EstrategiaConvergencia {

   /**
    * constructor de la clase. Solo se necesita el numero maximo
    * de iteraciones
    * @param maxIter numero maximo de iteraciones
    */
   public ConvergenciaIteraciones(int maxIter){
      this.maxIter = maxIter;
      // se asigna el umbral aunque no se usara
      this.umbral = 0;
   }

   /**
    * metodo de chequeo de la convergencia. Solo se considera
    * el numero de iteraciones
    * @param datos conjunto de puntos del problema a considerar
    * @param centros1 centros de la iteracion i-1
    * @param centros2 centros de la iteracion i
    * @param iter iteracion en curso
    * @return resultado de la comprobacion
    */
   @Override
   public boolean convergencia(List<Pixel> datos, List<Pixel> centros1,
                               List<Pixel> centros2, int iter) {
      boolean parada = false;

      // se considera el numero de iteraciones
      if(iter >= maxIter){
         parada = true;
      }

      // se devuelve el resultado
      return parada;
   }
}
