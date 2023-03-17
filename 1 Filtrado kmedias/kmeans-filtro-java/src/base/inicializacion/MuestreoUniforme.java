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
      Random generador = new Random(); // se crea objeto de la clase Random
      List<Pixel> seleccionados = new ArrayList<>(), // Lista de k centros iniciales que se retorna
              marcas = new ArrayList<>(); // Lista de las k marcas
      List< List<Pixel> > pixelesIntervalos = new ArrayList<>(); // lista de k-1 intervalos
      List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);
      double incremento = (double)( minMax.get(1) - minMax.get(0) ) / (k - 1);
      List<Double> chances = new ArrayList<>(); // chances de cada k-1 intervalo
      int tamanio = puntos.size();

      marcas.add(new Pixel(minMax.get(0)) ); // La primera marca es el minimo
      // Calculamos las marcas intermedias, aumentando el indice incremento a incremento
      for(int i=1; i<k-1; ++i){
         int indice = minMax.get(0) + (int)Math.round(incremento * i);
         // cada marca será un nuevo pixel (que no tiene por qué existir en la imagen)
         marcas.add(new Pixel(indice));
      }
      marcas.add(new Pixel(minMax.get(1))); // Ultima marca, pixel maximo

      for(int i=0; i<marcas.size()-1; ++i){ // marcas.size-1 == k-1
         // encuentro los pixeles que hay en el intervalo entre una marca y la siguiente
         List<Pixel> aux = Utilidades.obtenerPuntosIntervalo(puntos, marcas.get(i).obtenerIndice(), marcas.get(i + 1).obtenerIndice());
         pixelesIntervalos.add(aux);
         chances.add((double)aux.size()/tamanio);
      }

      // Hago que la chance se arrastre, si es 25 10 50 15, se transforma a 25 35 85 100
      for(int i=1; i<chances.size(); ++i){
         chances.set(i,chances.get(i-1));
      }

      for(int i=0; i<k; ++i){
         double chance = generador.nextDouble(); // chance entre 0.0 y 1.0
         int tramoChance = -1;
         boolean salir = false;
         // Encuentro el tramo del que va a salir el pixelElegido
         for(int j=0; j<chances.size() && !salir; j++){
            if( chance < chances.get(j) ){
               tramoChance = j;
               salir = true;
            }
         }
         int indicePixelElegido = generador.nextInt() % pixelesIntervalos.get(tramoChance).size();
         Pixel pixelElegido = pixelesIntervalos.get(tramoChance).get(indicePixelElegido);
         seleccionados.add(pixelElegido);
      }
      return seleccionados;
   }
}
