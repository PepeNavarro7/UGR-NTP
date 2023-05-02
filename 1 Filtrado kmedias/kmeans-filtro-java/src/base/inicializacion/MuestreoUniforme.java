package base.inicializacion;

import base.imagen.Pixel;
import base.imagen.Utilidades;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    * NOTA: por implementar -> Implementado
    */
   /**
    * Se hacen k-1 grupos uniformemente repartidos, se calcula la chance de que un punto
    * sea de cada grupo y se escogen aleatoriamente los centroides de cada grupo
    */
   @Override
   public List<Pixel> seleccionar(int k, List<Pixel> puntos) {
      /*// elijo las k marcas uniformemente
      List<Pixel> marcas = eligeMarcas(k, puntos);
      // distribuyo los pixeles de cada intervalo
      List<List<Pixel>> pixelesIntervalos = pixelesPorIntervalo(puntos, marcas);
      // calculo la chance de cada intervalo
      List<Double> chances = calculaChances(pixelesIntervalos,puntos.size());
      // y selecciono los centros
      List<Pixel> seleccionados = eligeCentrosIniciales(pixelesIntervalos, chances, k);

      return seleccionados;*/
      final List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);
      final double incremento = (double)( minMax.get(1) - minMax.get(0) ) / (k - 1);
      List<Pixel> marcas = IntStream.range(0, (k - 1))
              .boxed()
              .map(i -> new Pixel(minMax.get(0) + (int) Math.round(incremento * i)))
              .collect(Collectors.toList());
      //Añadimos el ultimo pixel, el maximo
      marcas.add(new Pixel(minMax.get(1)));
      return marcas;
   }

   private List<Pixel> eligeMarcas(int numMarcas, List<Pixel> puntos) {
      /*List<Pixel> marcas = new ArrayList<>();
      List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);
      double incremento = (double)( minMax.get(1) - minMax.get(0) ) / (numMarcas - 1);
      // Para 1000 puntos y 5 marcas, 0 250 500 750 1000, incrementos de (1000-0)/4=250

      marcas.add(new Pixel(minMax.get(0)) ); // La primera marca es el minimo
      // Calculamos las marcas intermedias, aumentando el indice incremento a incremento
      for(int i=1; i<numMarcas-1; ++i){
         int indice = minMax.get(0) + (int)Math.round(incremento * i);
         // cada marca será un nuevo pixel (que no tiene por qué existir en la imagen)
         marcas.add(new Pixel(indice));
      }
      marcas.add(new Pixel(minMax.get(1))); // Ultima marca, pixel maximo

      return marcas;*/

   }

   private List<List<Pixel>> pixelesPorIntervalo(List<Pixel> puntos, List<Pixel> marcas) {
      List<List<Pixel>> pixelesIntervalos = new ArrayList<>();

      for(int i=0; i<marcas.size()-1; ++i){ // marcas.size-1 == k-1 == los intervalos que hay (entre marca.k y marca.k+1)
         // encuentro los pixeles que hay en el intervalo entre una marca y la siguiente
         double desde=marcas.get(i).obtenerIndice(), hasta=marcas.get(i+1).obtenerIndice();
         List<Pixel> aux = Utilidades.obtenerPuntosIntervalo(puntos, desde, hasta);
         pixelesIntervalos.add(aux);
      }

      return pixelesIntervalos;
   }

   private List<Double> calculaChances(List<List<Pixel>> pixelesIntervalos, int total) {
      List<Double> chances = new ArrayList<>();

      // calculo las chances de cada intervalo (tam del intervalo / tam total)
      for(List<Pixel> intervalo : pixelesIntervalos){
         chances.add((double)intervalo.size() / total);
      }
      // Hago que la chance se arrastre, si es .25 .10 .50 .15, se transforma a .25 .35 .85 1.00
      for(int i=1; i<chances.size(); ++i){
         chances.set(i,chances.get(i)+chances.get(i-1));
      }
      chances.set(chances.size()-1,1.0);
      return chances;
   }

   private List<Pixel> eligeCentrosIniciales(List<List<Pixel>> pixelesIntervalos, List<Double> chances, int numCentros) {
      Random generador = new Random(); // se crea objeto de la clase Random
      List<Pixel> centrosSeleccionados = new ArrayList<>();

      // Elegimos los pixeles que serviran de centroides iniciales
      for(int i=0; i<numCentros; ++i){
         double chance = generador.nextDouble(); // genero chance entre 0.0 y 1.0
         int tramo = 0;
         boolean encontrado = false;

         // Encuentro el tramo del que va a salir el pixelElegido
         while (!encontrado){
            if(chance < chances.get(tramo)){
               encontrado = true;
            } else {
               tramo++;
            }
         }
         // Una vez tengo el tramo, escojo aleatoriamente un pixel de ese tramo
         int indiceAleatorio = generador.nextInt(pixelesIntervalos.get(tramo).size());
         Pixel pixelElegido = pixelesIntervalos.get(tramo).get(indiceAleatorio);
         centrosSeleccionados.add(pixelElegido);
      }

      return centrosSeleccionados;
   }
}
