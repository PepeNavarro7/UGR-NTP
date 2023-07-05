package base.convergencia;

import base.imagen.Pixel;

import java.util.List;

/**
 * clase abstracta para la estrategia de chequeo de
 * convergencia
 */
public abstract class EstrategiaConvergencia {
   /**
    * valor de umbral usado. Solo se necesita para algunas
    * de las estrategias
    */
   protected double umbral;

   /**
    * maximo numero de iteraciones permitidas. Todas las
    * estrategias consideran este limite, para evitar la
    * no finalizacion del proceso
    */
   protected int maxIter;

   /**
    * metodo de deteccion de convergencia. No todos los
    * argumentos se necesitan en todas las estrategias, pero
    * se mantienen para dar interfaz comun a todas ellas
    * @param datos conjunto de puntos del problema a considerar
    * @param centros1 centros de la iteracion i-1
    * @param centros2 centros de la iteracion i
    * @param iter iteracion en curso
    * @return resultado de la comprobacion
    */
   abstract public boolean convergencia(List<Pixel> datos,
                                        List<Pixel> centros1, List<Pixel> centros2,
                                        int iter);
}
