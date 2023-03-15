package base.inicializacion;

import base.imagen.Pixel;
import base.imagen.Utilidades;

import java.util.*;

/**
 * clase que implementa el muestreo uniforme para la
 * estrategia de inicializacion
 */
public class MuestreoUniforme implements EstrategiaInicializacion {
   /**
    * constructor por defecto
    */
   public MuestreoUniforme(){
   }

   /**
    * metodo de seleccion de k puntos de acuerdo a la
    * distribucion de pixels en los intervalos de color
    * @param k numero de puntos a seleccionar
    * @param puntos coleccion de puntos. No se siempre
    *               se precisa este argumento, pero se
    *               mantiene para ofrecer una interfaz
    *               comun
    * @return pixels seleccionados como centros
    * NOTA: por implementar
    */
   /**
    * Se hacen k-1 grupos uniformemente repartidos, se calcula la chance de que un punto
    * sea de cada grupo y se escogen aleatoriamente los centroides de cada grupo
    */
   @Override
   public List<Pixel> seleccionar(int k, List<Pixel> puntos) {
      // se crea objeto de la clase Random
      Random generador = new Random();

      // se devuelve la lista de centroides seleccionados
      return null;
   }
}
