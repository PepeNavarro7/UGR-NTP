package base.imagen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * clase para almacenar y manejar imagenes en formato jpeg
 */
public class Imagen {
    /**
     * datos miembro para el numero de pixels en ancho y alto
     */
    private int ancho;
    private int alto;

    /**
     * lista con valor de color para los pixels
     */
    private List<Integer> datos;

    /**
     * constructor de la clase
     * @param ancho numero de pixels en horizontal
     * @param alto numero de pixels en vertical
     */
    public Imagen(int ancho, int alto) {
        // se asignan los valores de los datos miembro
        this.ancho = ancho;
        this.alto = alto;

        // creacion del array de pixeks
        this.datos = new ArrayList<>();

        // se inicializan todos los valores a 0
        /*for(int i=0; i < ancho; i++){
            for(int j=0; j < alto; j++){
                datos.add(0);
            }
        }*/
        IntStream.range(0,ancho*alto)
                .forEach(value -> datos.add(0));
    }

    /**
     * devuelve el numero de pixels de ancho
     * @return numero de pixels en horizontal
     */
    public int obtenerAncho(){
        return ancho;
    }

    /**
     * devuelve el numero de pixels de alto
     * @return numero de pixels en vertical
     */
    public int obtenerAlto(){
        return alto;
    }

    /**
     * obtiene el valor de un pixel concreto
     * @param x fila objetivo
     * @param y columna objetivo
     * @return devuelve el valor del pixel objetivo
     */
    public int obtener(int x, int y){
        return datos.get(y* ancho + x);
    }

    /**
     * actualiza el valor de un determinado pixel
     * @param x fila objetivo
     * @param y columna objetivo
     * @param color valor a asignar al pixel objetivo
     */
    public void actualizar(int x, int y, int color){
        int indice = y*ancho+x;
        datos.set(indice, color);
    }

    /**
     * convierte los datos en puntos
     * @return lista de objetos de clase Pixel para representar
     *          el contenido de la imagen
     * NOTA: por implementar -> Implementado
     */
    public List<Pixel> convertirPuntos(){
        /*List<Pixel> lista = new ArrayList<>();
        for (Integer i : this.datos){ // datos es un List<Integer>
            // Por cada pixel codificado como entero, creo el pixel y lo guardo
            Pixel pixel = new Pixel (i);
            lista.add(pixel);
        }
        return lista;*/
        // Convertimos un flujo de Integers en uno de Pixeles
        List<Pixel> flujo = this.datos.stream()
                .map(i -> new Pixel(i))
                .collect(Collectors.toList());
        return flujo;
    }


    /**
     * se determina el numero de colores de la imagen
     * @return numero de colores presentes en la imagen
     * NOTA: por implementar -> Implementado
     */
    public long obtenerNumeroColores(){
        /*Set<Integer> set = new HashSet<>(); // Set no ordenado, nos da igual
        set.addAll(this.datos);
        return set.size();*/
        long recuento = this.datos.stream()
                .distinct()
                .count();
        return recuento;
    }
}
