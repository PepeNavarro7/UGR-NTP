package kmedias;

import base.convergencia.*;
import base.imagen.Imagen;
import base.imagen.Pixel;
import base.imagen.Utilidades;
import base.inicializacion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * clase para implementar la funcionalidad del algoritmo de
 * las k-medias, aplicado al filtrado de imagenes para reducir
 * la escala de colores
 */
public class KMedias {
   /**
    * imagen a procesar
    */
   private Imagen imagen;

   /**
    * coleccion de datos sobre los pixels que componen la
    * imagen, definidos como componentes red-green-blue
    */
   private List<Pixel> pixels;

   /**
    * numero de grupos a formar
    */
   private int k;

   /**
    * objeto responsable de seleccionar los centroides
    * iniciales
    */
   private EstrategiaInicializacion inicializador;

   /**
    * objeto responsable de determinar si el algoritmo debe
    * detenerse
    */
   private EstrategiaConvergencia parada;

   /**
    * contador de iteraciones ejecutadas
    */
   private int iteraciones;

   /**
    * constructor de la clase
    * @param imagenInicial imagen a filtrar
    * @param k numero de grupos a formar
    * @param inicializador responsable de inicializacion
    * @param parada responsable de determinacion de parada
    */
   private KMedias(Imagen imagenInicial, int k, EstrategiaInicializacion inicializador,
                  EstrategiaConvergencia parada){
      // se asigna la imagen con la que trabajan
      this.imagen = imagenInicial;

      // se obtienen los puntos de la imagen
      pixels = imagenInicial.convertirPuntos();

      // se asigna el valor de k
      this.k = k;

      // se asignan el inicializador y el particionador
      this.inicializador = inicializador;
      this.parada = parada;

      // se inicializa el contador de iteraciones
      iteraciones = 0;
   }

   /**
    * patron factoria para la creacion del objeto de acuerdo a los
    * parametros de configuracion seleccionados
    * @param k numero de grupos a formar
    * @param modoInicializacion objeto responsable de la inicializacion
    * @param modoConvergencia objeto responsable de la condicion de parada
    * @param maxIteraciones maximo numero de iteraciones a realizar
    * @param umbralEstabilidad umbral para criterio de estabilidad
    * @param umbralRuido umbral para criterio de ruio
    * @param imagen imagen a filtrar
    * @return objeto construido
    */
   public static KMedias factoria(int k, ModoInicializacion modoInicializacion,
                                  ModoConvergencia modoConvergencia,
                                  int maxIteraciones, double umbralEstabilidad,
                                  double umbralRuido, Imagen imagen){
      // se crea el inicializador
      EstrategiaInicializacion inicializador = null;
      switch (modoInicializacion){
         case MUESTREO_ALEATORIO: inicializador = new MuestreoAleatorio();
            break;
         case MUESTREO_UNIFORME: inicializador = new MuestreoUniforme();
            break;
         case SELECCION_UNIFORME:inicializador = new SeleccionUniforme();
            break;
      }

      // se crea el determinador de convergencia
      EstrategiaConvergencia determinador = null;
      switch (modoConvergencia){
         case ITERACIONES : determinador = new ConvergenciaIteraciones(maxIteraciones);
            break;
         case ESTABILIDAD: determinador = new ConvergenciaEstabilidad(
                 maxIteraciones, umbralEstabilidad);
            break;
         case RUIDO : determinador = new ConvergenciaRuido(
                 maxIteraciones, umbralRuido);
            break;
      }

      // se crea el objeto
      return new KMedias(imagen, k, inicializador, determinador);
   }

   /**
    * devuelve el numero de iteraciones usadas
    * @return numero de iteraciones ejecutadas
    */
   public int obtenerIteraciones(){
      return iteraciones;
   }

   /**
    * realiza la inicializacion del algoritmo delegando en el dato
    * miembro inicializador
    * @return lista de pixels con los centros iniciales
    */
   private List<Pixel> inicializar(){
      return inicializador.seleccionar(k, pixels);
   }

   /**
    * ejecucion del algoritmo de particionado
    * @return imagen resultante tras realizar la reduccion de colores
    */
   public Imagen ejecutar(){
      // se eligen los centros iniciales
      List<Pixel> centros = inicializar();

      // se llama al metodo recursivo que itera hasta convergencia
      List<Pixel> nuevosCentros = iterar(centros);

      // ahora se crea una nueva imagen a partir de la original
      // asignando cada pixel al centro mas cercano
      return aplicarFiltro(nuevosCentros);
   }

   /**
    * metodo que implementa las iteraciones necesarias del algoritmo
    * hasta obtener la convergencia
    * @param centros centros de partida
    * @return lista de nuevos centros
    */
   private List<Pixel> iterar(List<Pixel> centros){
      //List<Pixel> resultado;
      this.iteraciones++;
      Map<Pixel,List<Pixel>> mapaCentros = this.clasificar(centros);
      List<Pixel> nuevosCentros = this.actualizar(mapaCentros);
      boolean converge = this.parada.convergencia(this.pixels,centros,nuevosCentros,this.iteraciones);
      if (converge){
         return nuevosCentros;
      } else {
         return iterar(nuevosCentros);
      }
      //return resultado;
   }

   /**
    * metodo de clasificacion de los pixels de acuerdo a los centros
    * pasados como argumento
    * @param centros centros a considerar
    * @return clasificacion de los pixels por cercania a los centros
    * NOTA: por implementar -> Implementado
    * Para cada pixel, se busca su centro mas cercano de la lista que se aporta
    * y se guarda asociado a él en el map
    */
   private Map<Pixel, List<Pixel>> clasificar(List<Pixel> centros){
      return this.pixels.stream()
              .collect(Collectors.groupingBy(
                      pixel -> pixel.obtenerMasCercano(centros) ) );
   }

   /**
    * actualizacion de la lista de centros obtenidos de acuerdo a
    * la clasificacion y centroides pasados como argumento
    * @param clasificacion mapa con clasificacion de centros
    * @return nueva lista de centros
    * NOTA: por implementar -> Implementado
    */
   private List<Pixel> actualizar(Map<Pixel, List<Pixel>> clasificacion){
      return clasificacion.values()
              .stream()
              .map(lista -> Utilidades.calcularMedia(lista))
              .collect(Collectors.toList());
   }

   /**
    * metodo para aplicar el filtro a la imagen y generar asi la
    * imagen resultante
    * @param centros lista de centros a usar para el filtrado
    * @return imagen procesada
    * NOTA: por implementar
    */
   private Imagen aplicarFiltro(List<Pixel> centros){
      Imagen resultado = new Imagen(this.imagen.obtenerAncho(),this.imagen.obtenerAlto());
      IntStream.range(0,this.imagen.obtenerAncho()).forEach(x -> {
         IntStream.range(0,this.imagen.obtenerAlto()).forEach(y -> {
            Pixel nuevoColor = this.pixels.get(y*this.imagen.obtenerAncho()+x).obtenerMasCercano(centros);
            resultado.actualizar(x,y,nuevoColor.obtenerIndice());
         } );
      } );
      return resultado;
   }
}
