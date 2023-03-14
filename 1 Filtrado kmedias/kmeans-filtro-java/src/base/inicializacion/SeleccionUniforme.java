package base.inicializacion;

import base.imagen.Pixel;
import base.imagen.Utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 * clase para la seleccion de puntos de forma uniforme
 * entre todos los posibles colores (exisentes). Si se
 * desean k puntos, entonces se parte el rango completo
 * en k-1 trozos y las marcas de los trozos corresponden
 * a los puntos seleccionados
 */
public class SeleccionUniforme implements EstrategiaInicializacion {
   /**
    * metodo de seleccion de puntos
    * @param k numero de puntos a seleccionar
    * @param puntos coleccion de puntos. Se usa para determinar
    *               el rango de valores
    * @return lista de pìxels seleccionados
    */
   @Override
   public List<Pixel> seleccionar(int k, List<Pixel> puntos) {
      List<Pixel> seleccionados = new ArrayList<>();

      // se determinan los valores maximo y minimo de
      // colores de los puntos
      List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);

      // se crea el punto inicial
      int colorMinimo = minMax.get(0);
      seleccionados.add(new Pixel(colorMinimo));

      // se determinan los valores intermedios
      int color = 0;
      Pixel punto;
      double incremento = (minMax.get(1) - minMax.get(0)) / (k - 1);
      for(int i=1; i < (k-1); i++){
         color = minMax.get(0) + (int)Math.round(incremento*i);

         // convierte a componentes el color
         punto = new Pixel(color);

         // se agrega a la coleccion de seleccionados
         seleccionados.add(punto);
      }

      // se agrega el correspondiente al maximo
      color = minMax.get(1);
      punto = new Pixel(color);

      // se agrega a la coleccion
      seleccionados.add(punto);

      // se devuelve la lista de valores seleccionados
      return seleccionados;
   }
}
