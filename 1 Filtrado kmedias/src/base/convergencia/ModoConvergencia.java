package base.convergencia;

/**
 * enumerado para los modos de convergencia
 */
public enum ModoConvergencia {
   /**
    * parada por numero de iteraciones
    */
   ITERACIONES,

   /**
    * parada por numero de iteraciones y limite de iteraciones
    */
   ESTABILIDAD,

   /**
    * parada por ratio señal/ruido y limite de iteraciones
    */
   RUIDO
}
