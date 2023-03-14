package base.imagen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for(int i=0; i < ancho; i++){
            for(int j=0; j < alto; j++){
                datos.add(0);
            }
        }
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
     * NOTA: por implementar
     */
    public List<Pixel> convertirPuntos(){
        // se devuelva la lista de puntos
        return null;
    }

    /**
     * se determina el numero de colores de la imagen
     * @return numero de colores presentes en la imagen
     * NOTA: por implementar
     */
    public long obtenerNumeroColores(){
        // se devuelve el numero de entradas
        return 0L;
    }
}
