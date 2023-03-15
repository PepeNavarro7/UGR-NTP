package base.imagen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * clase con metodos estaticos de utilidad
 */
public class Utilidades {
    /**
     * metodo estatico para calcular la media de un conjunto
     * de puntos
     * @param puntos coleccion de puntos para el calculo de
     *               la media (del centro)
     * @return pixel cuyas componentes son los valores medios
     * calculados
     * NOTA: por implementar -> Implementado
     */
    public static Pixel calcularMedia(List<Pixel> puntos){
        // Calculo la media de cada una de las componentes
        double rojo = 0.0, verde = 0.0, azul = 0.0;
        for(Pixel aux : puntos){
            rojo += aux.obtenerComponente(ComponentesRGBA.ROJO);
            verde += aux.obtenerComponente(ComponentesRGBA.VERDE);
            azul += aux.obtenerComponente(ComponentesRGBA.AZUL);
        }
        rojo /= puntos.size();
        verde /= puntos.size();
        azul /= puntos.size();
        Pixel pixel_medio = new Pixel(rojo,verde,azul);
        return pixel_medio;
    }

    /**
     * calcula los valores minimos y maximos para todos los
     * puntos de una coleccion
     * @param puntos lista de pixels a coniderar
     * @return lista con los minimos y maximos de toda la coleccion
     * NOTA: por implementar -> No sé si está bien implementado
     */
    public static List<Integer> obtenerMinimoMaximo(List<Pixel> puntos){
        List<Integer> lista = new ArrayList<>();
        int max = Integer.MIN_VALUE, min=Integer.MAX_VALUE;
        for(Pixel aux : puntos){
            int indice = aux.obtenerIndice();
            if (indice>max){
                max = indice;
            } else if (indice < min) {
                min = indice;
            }
        }
        lista.add(min);
        lista.add(max);
        return null;
    }

    /**
     * obtiene una lista con todos los puntos que pertenecen
     * al intervalo especificado por minimo y maximo
     * @param puntos lista de puntos a analizar
     * @param minimo valor minimo del intervalo
     * @param maximo valor maximo del intervalo
     * @return lista de pixels que pertenecen al intervalo
     * NOTA: por implementar
     */
    public static List<Pixel> obtenerPuntosIntervalo(List<Pixel> puntos, double minimo, double maximo){
        // se devuelve la lista
        return null;
    }

    /**
     * metodo para leer un fichero y devolver la imagen creada
     * a partir de sus datos
     * @param ruta ruta desde la que hacer la carga de la imagen
     * @return imagen cargada
     */
    public static Imagen cargarImagen(String ruta){
        Imagen imagen = null;
        try {
            FileInputStream fichero = new FileInputStream(ruta);
            imagen = cargarImagen(fichero);

            // se asigna la ruta del fichero
            fichero.close();
        }catch(Exception e){
            System.out.println("error de carga de " + ruta);
            System.out.println(e);
        }

        // se devuelve la imagen creada o null
        return imagen;
    }

    /**
     * metodo auxiliar para realizar la carga de los datos de
     * la imagen y la construccion del objeto correspondiente
     * @param flujo flujo a usar para la carga de datos
     * @return imagen cargada
     */
    private static Imagen cargarImagen(InputStream flujo){
        Imagen imagen = null;
        try {
            BufferedImage buffer = ImageIO.read(flujo);
            // se crea la imagen
            imagen = new Imagen(buffer.getWidth(), buffer.getHeight());
            // se asignan los valores de los pixels
            for(int i=0; i < buffer.getWidth(); i++){
                for(int j=0; j < buffer.getHeight(); j++){
                    imagen.actualizar(i, j, buffer.getRGB(i, j));
                }
            }
        }catch(Exception e){
            System.out.println("error al cargar imagen");
            System.out.println(e);
        }

        // se devuelve la imagen creada o null
        return imagen;
    }

    /**
     * metodo publico para guardar una imagen en una ruta
     * @param imagen imagen a salvar
     * @param ruta ruta del archivo a generar
     */
    public static void salvarImagen(Imagen imagen, String ruta){
        try {
            FileOutputStream fichero = new FileOutputStream(ruta);
            BufferedImage buffer = new BufferedImage(imagen.obtenerAncho(),
                    imagen.obtenerAlto(), BufferedImage.TYPE_INT_RGB);
            for(int i=0; i < imagen.obtenerAncho(); i++){
                for(int j=0; j < imagen.obtenerAlto(); j++){
                    buffer.setRGB(i, j, imagen.obtener(i, j));
                }
            }

            // finalmente se guarda
            ImageIO.write(buffer, "jpeg", fichero);
            fichero.close();
        }catch(Exception e){
            System.out.println("error en salvado de fichero");
            System.out.println(e);
        }
    }

    /**
     * metodo para salvar el contenido de una imagen en formato
     * csv
     * @param imagen imagen a salver
     * @param ruta ruta del archivo en que hacer el salvado
     */
    public static void salvarFicheroCSV(Imagen imagen, String ruta){
        try {
            FileWriter fichero = new FileWriter(ruta);
            BufferedWriter buffer = new BufferedWriter(fichero);
            for(int i=0; i < imagen.obtenerAncho(); i++){
                for(int j=0; j < imagen.obtenerAlto(); j++){
                    int pixel = imagen.obtener(i, j);
                    double red = RGBA.obtenerComponente(ComponentesRGBA.ROJO, pixel);
                    double green = RGBA.obtenerComponente(ComponentesRGBA.VERDE, pixel);
                    double blue = RGBA.obtenerComponente(ComponentesRGBA.AZUL, pixel);

                    // se calcula tambien la mezcla de componentes para cada
                    // pixel
                    int mezcla = RGBA.mezclar(red, green, blue);
                    buffer.write(red + ", " + green + ", " + blue + ", " + mezcla + "\n");
                }
            }

            // finalmente se cierra el buffer
            buffer.close();
        }catch(Exception e){
            System.out.println("error en salvado de fichero");
            System.out.println(e);
        }
    }
}
