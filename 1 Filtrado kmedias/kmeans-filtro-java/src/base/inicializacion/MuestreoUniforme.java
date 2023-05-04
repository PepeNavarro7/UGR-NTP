package base.inicializacion;

import base.imagen.Pixel;
import base.imagen.Utilidades;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    * Se hacen k (inicialmente pensé que eran k-1 grupos) grupos uniformemente
    * repartidos, se calcula la chance de que un punto
    * sea de cada grupo y se escogen aleatoriamente los centroides de cada grupo
    */
   @Override
   public List<Pixel> seleccionar(int k, List<Pixel> puntos) {

      // distribuyo los N pixeles de cada k intervalo, delimitados por las k+1 marcas
      Map< Integer,List<Pixel> > porIntervalo = pixelesPorIntervalo(puntos, k);
      // calculo la chance de cada intervalo
      List<Double> distribucion = calculaChances(porIntervalo);
      // Acumulo la chance
      List<Double> acumulada = acumulaDistribucion(distribucion);
      // y selecciono los centros
      List<Pixel> seleccionados = eligeCentrosIniciales(porIntervalo, acumulada, k);

      return seleccionados;
   }



   private Map< Integer,List<Pixel> > pixelesPorIntervalo(List<Pixel> puntos, int intervalos) {
      // Para 1000 puntos, 4 intervalos, 5 marcas, 0 250 500 750 1000, incrementos de (1000-0)/4=250
      // Para 500 puntos, 2 intervalos, 3 marcas, 250 500 750, incrementos de (750-250)/2=250
      final List<Integer> minMax = Utilidades.obtenerMinimoMaximo(puntos);
      System.out.println("Minimo:"+minMax.get(0)+" // Maximo:"+minMax.get(1));
      final int incremento = ( minMax.get(1) - minMax.get(0) ) / (intervalos);
      System.out.println(incremento);
      return IntStream.range(0, intervalos)
              .boxed()
              // Creamos un map con los enteros inciales como key, y la lista de pixeles como value
              .collect(Collectors.toMap(
                      Function.identity(),
                      indice -> { int izq = minMax.get(0) + incremento * indice;
                         int der = izq + incremento;
                         System.out.println("Min->"+izq+" Max->"+der);
                         List<Pixel> aux = Utilidades.obtenerPuntosIntervalo(puntos, izq, der);
                         System.out.println("puntos->"+aux.size());
                         return  aux; }
              ) );
   }



   private List<Double> calculaChances(Map< Integer,List<Pixel> > pixelesIntervalos) {
      // calculo las chances de cada intervalo (tam del intervalo / tam total)
      long total = pixelesIntervalos.values().stream().flatMap(List::stream).count();

      List<Double> chances =
              pixelesIntervalos.values()
              .stream()
              .map(lista -> ((double) lista.size() / total))
              .collect(Collectors.toList());
      System.out.println("Chances"+chances);
      return chances;
   }
   private List<Double> acumulaDistribucion(List<Double> chances) {
      return IntStream.range(0, chances.size())
              .boxed()
              .map(indice -> chances.stream().limit(indice+1))
              .map(flujo -> flujo.reduce(0.0, (x, y) -> x + y))
              .collect(Collectors.toList());
   }

   private List<Pixel> eligeCentrosIniciales(Map< Integer,List<Pixel> > pixelesIntervalos, List<Double> chances, int numCentros) {
      Random generador = new Random();
      // Partimos de un rango igual al numero de centros que se deben seleccionar
      List<Pixel> centrosSeleccionados =
              IntStream.range(0, numCentros)
              .boxed()
      // De cada punto, sacamos un rand, y con ese rand calculamos en qué intervalo va a estar el punto
              .map(indice -> generador.nextDouble())
              .map(chance -> {
                 for (int intervalo = 0; ; intervalo++)
                    if (chance < chances.get(intervalo))
                       return intervalo;
              })
      // Y sabiendo el tramo, cogemos un pixel aleatorio de ESE tramo
              .map(tramo -> {
                 int indiceAleatorio = generador.nextInt(
                         pixelesIntervalos.get(tramo).size()
                 );
                 return pixelesIntervalos.get(tramo).get(indiceAleatorio);
              } )
              .collect(Collectors.toList());
      return centrosSeleccionados;
   }
}
