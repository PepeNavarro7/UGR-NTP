package base.convergencia;

import base.imagen.Pixel;

import java.util.List;

/**
 * clase para comprobar la convergencia comparando la imagen
 * con el ruido producido por el filtrado
 */
public class ConvergenciaRuido extends EstrategiaConvergencia {
   /**
    * constructor de la clase
    * @param maxIter maximo numero de iteraciones permitidas
    * @param umbral umbral de relacion señal / ruido
    */
   public ConvergenciaRuido(int maxIter, double umbral) {
      this.maxIter = maxIter;
      this.umbral = umbral;
   }

   /**
    * metodo de chequo de convergencia. Se considera el ratio
    * entre señal y ruido. Los datos necesarios son los puntos
    * de la coleccion, los centros de la iteracion i y el numero
    * de iteraciones
    * @param datos conjunto de puntos del problema a considerar
    * @param centros1 centros de la iteracion i-1
    * @param centros2 centros de la iteracion i
    * @param iter iteracion en curso
    * @return resultado del chequeo
    * NOTA: por implementar
    */
   @Override
   public boolean convergencia(List<Pixel> datos, List<Pixel> centros1,
                               List<Pixel> centros2, int iter) {
      boolean parada = false;
      double sennalTotal=0, ruidoTotal = 0;
      // condicion 1: que se haya alcanzado el maximo de iteraciones
      if(iter > this.maxIter){
         parada = true;
      } else {
         for(Pixel pixel : datos){
            Pixel centroCercano = pixel.obtenerMasCercano(centros2);
            sennalTotal+=pixel.calcularSennal();
            ruidoTotal+= pixel.calcularRuido(centroCercano);
         }
         if( (sennalTotal / ruidoTotal) > this.umbral){
            parada = true;
         }
      }

      // se devuelve el resultado
      return parada;
   }
}
