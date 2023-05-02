package base.inicializacion;

import base.imagen.Pixel;
import base.imagen.Utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
      //Obtenemos el minimo y el maximo, y  calculamos el incremento
      final List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);
      final double incremento = (double)(minMax.get(1) - minMax.get(0)) / (k - 1);
      // Partimos de un stream de 0 a k-2, y mapeamos cada i al pixel correspondiente
      List<Pixel> seleccionados = IntStream.range(0, (k - 1))
              .boxed()
              .map(i -> new Pixel(minMax.get(0) + (int) Math.round(incremento * i)))
              .collect(Collectors.toList());
      //Añadimos el ultimo pixel, el maximo
      seleccionados.add(new Pixel(minMax.get(1)));
      return seleccionados;
   }
}
