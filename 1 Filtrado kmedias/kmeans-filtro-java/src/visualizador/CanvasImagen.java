package visualizador;

import base.imagen.Imagen;
import base.imagen.Utilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * clase para visualizar las imagenes
 */
public class CanvasImagen extends JComponent {
    /**
     * nombre de la imagen por defecto
     */
    private final static String defecto = "mandril.jpeg";

    /**
     * path a la imagen
     */
    private String ruta;

    /**
     * imagen mostrada
     */
    private Imagen imagen = null;

    /**
     * constructor de la clase
     */
    public CanvasImagen(){
        ruta = "./data/";

        // se carga la imagen por defecto
        ruta = ruta + defecto;
        imagen = Utilidades.cargarImagen(ruta);
    }

    /**
     * devuelve la imagen
     * @return imagen representada en el canvas
     */
    public Imagen obtenerImagen(){
        return imagen;
    }

    /**
     * permite asignar la imagen a visualizar
     * @param imagen imagen a visualizar
     */
    public void asignarImagen(Imagen imagen){
        this.imagen = imagen;
        repaint();
    }

    /**
     * metodo de utilidad para cargar la imagen de un fichero
     * @param ruta ruta de ubicacion del fichero
     */
    public void cargarFichero(String ruta){
        // se asigna el valor de ruta
        this.ruta = ruta;

        // se produce la llamada a recargar
        recargar();
    }

    /**
     * metodo para producir la recarga de los datos de la imagen
     * en el componente grafico
     */
    public void recargar(){
        // si se ha dado valor concreto a ruta, se toma el
        // archivo indicado. En caso contrario, se carga
        // la imagen de lenna
        if(ruta != "./data/"){
            imagen = Utilidades.cargarImagen(ruta);
        }
        else{
            ruta = ruta + defecto;
            imagen = Utilidades.cargarImagen(ruta);
        }

        // se produce el repintado
        repaint();
    }

    /**
     * devuelve la dimension del componente (debe llamarse
     * de esta forma al estar sobrescrito)
     * @return dimension de la imagen del canvas
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imagen.obtenerAncho(),
                imagen.obtenerAlto());
    }

    /**
     * devuelve el numero de colores
     * @return numero de colores
     */
    public long obtenerNumeroColores(){
        return imagen.obtenerNumeroColores();
    }

    /**
     * devuelve el numero de pixels de ancho de la imagen
     * @return numero de pixels en la dimension horizontal
     */
    public int obtenerAncho(){
        return imagen.obtenerAncho();
    }

    /**
     * devuelve el numero de pixels de alto de la imagen
     * @return numero de pixels en la orientacion vertical
     */
    public int obtenerAlto(){
        return imagen.obtenerAlto();
    }

    /**
     * devuelve la ruta
     * @return devuelve la ruta del fichero desde el que
     * se cargo la imagen del canvas
     */
    public String obtenerRuta(){
        return ruta;
    }

    /**
     * metodo de pintado
     * @param graphics objeto Graphics asociado
     */
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // se obtiene el ancho
        int ancho = imagen.obtenerAncho();

        // se obtiene el alto
        int alto = imagen.obtenerAlto();

        // se crea buffer
        BufferedImage buffer = new BufferedImage(ancho, alto,
                BufferedImage.TYPE_INT_ARGB);

        // se cargan los datos en el buffer
        for(int i=0; i < ancho; i++){
            for(int j=0; j < alto; j++){
                int color = imagen.obtener(i, j);
                buffer.setRGB(i, j, color);
            }
        }

        // se ordena el pintado
        graphics.drawImage(buffer, 0, 0, null);
    }
}
