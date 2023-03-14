package base.imagen;

/**
 * clase para aportar funciones de conversion entre
 * definiciones de colores, componentes, etc. Se trata de
 * funciones estaticas que se agrupan en una clase
 */
public class RGBA {
    /**
     * obtiene una componente a partir de un valor de color
     * @param objetivo componente objetivo
     * @param color color del que obtener el componente
     * @return valor de la compoente seleccionada
     */
    public static double obtenerComponente(ComponentesRGBA objetivo, int color){
        double valor = 0;
        switch (objetivo){
            case ROJO:
                valor = obtenerRojo(color);
                break;
            case VERDE:
                valor = obtenerVerde(color);
                break;
            case AZUL:
                valor = obtenerAzul(color);
                break;
            case ALFA:
                valor = obtenerAlfa(color);
                break;
        }

        // se devuelve el valor
        return valor;
    }
    /**
     * dado un valor de color devuelve su componente alpha
     * @param color color objetivo
     * @return valor de la componente alpha
     */
    private static double obtenerAlfa(int color){
        return ((0xff000000 & color) >>> 24)*1.0 / 256;
    }

    /**
     * a partir de un color devuelve el valor de la
     * componente roja
     * @param color color objetivo
     * @return valor de la componente roja
     */
    private static double obtenerRojo(int color){
        return ((0x00ff0000 & color) >>> 16)*1.0 / 256;
    }

    
    /**
     * a partir de un color devuelve el valor de la
     * componente verde
     * @param color color objetivo
     * @return valor de la componente roja
     */
    private static double obtenerVerde(int color){
        return ((0x0000ff00 & color) >>> 8)*1.0 / 256;
    }

    /**
     * a partir de un color devuelve el valor de la
     * componente azul
     * @param color color del que extraer el valor del componente
     * @return valor extraido
     */
    public static double obtenerAzul(int color){
        return ((0x0000ff & color) >>> 0)*1.0 / 256;
    }

    /**
     * mezcla de los componentes para obtener el valor de color
     * (entero) de cada pixel
     * @param punto pixel usado para calcular el indice del
     *              color considerando los valores de las
     *              componentes
     * @return indice de color calculado
     */
    public static int mezclar(Pixel punto){
        // se limitan todas las componentes y se componen
        // sus valores para generar el valor rgb completo
        int calpha = limitar((int) (256.0),
                0, 255) << 24;
        int cred = limitar((int)
                        (punto.obtenerComponente(ComponentesRGBA.ROJO)*256),
                0, 255) << 16;
        int cgreen = limitar((int)
                        (punto.obtenerComponente(ComponentesRGBA.VERDE)*256),
                0, 255) << 8;
        int cblue = limitar((int)
                        (punto.obtenerComponente(ComponentesRGBA.AZUL)*256),
                0, 255) << 0;

        // return their composition
        return calpha | cred | cgreen | cblue;
    }

    /**
     * metodo de mezcla a partir de las componentes rojo, verde
     * y azul, asumiendo valor 1 para alfa
     * @param rojo componente rojo
     * @param verde componente verde
     * @param azul componente azul
     * @return indice de color calculado
     */
    public static int mezclar(double rojo, double verde, double azul){
        // se limitan todas las componentes y se componen
        // sus valores para generar el valor rgb completo
        int calfa = limitar((int) (256.0), 0, 255) << 24;
        int crojo = limitar((int) (rojo*256), 0, 255) << 16;
        int cverde = limitar((int) (verde*256), 0, 255) << 8;
        int cazul = limitar((int) (azul*256), 0, 255) << 0;

        // se devuelve la composicion
        return calfa | crojo | cverde | cazul;
    }

    /**
     * limita los valores para asegurar estan en el rango indicado
     * @param valor valor a limitar
     * @param minimo minimo del intervalo considerado
     * @param maximo maximo del intervalo considerado
     * @return valor limitado y ajustado al intervalo
     */
    private static int limitar(int valor, int minimo, int maximo){
       int salida = valor;
       if(valor < minimo){
           salida = minimo;
       }
       else{
           if(valor > maximo){
               salida = maximo;
           }
       }

       // se devuelve el valor de salida
       return salida;
    }
}
