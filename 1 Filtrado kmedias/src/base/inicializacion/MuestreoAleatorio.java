package base.inicializacion;

import base.imagen.Pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * implementacion de la seleccion mediante muestreo aleatorio
 */
public class MuestreoAleatorio implements EstrategiaInicializacion {
    /**
     * metodo de seleccion de puntos
     * @param k numero de puntos a seleccionar
     * @param puntos coleccion de puntos
     * @return pixels seleccionados como centros
     */
    @Override
    public List<Pixel> seleccionar(int k, List<Pixel> puntos) {
        // obtenemos los indices de los puntos
        List<Integer> indices = puntos.stream()
                .map(punto -> punto.obtenerIndice())
                .collect(Collectors.toList());
        Collections.shuffle(indices); // mezclamos
        // seleccionamos los k primeros y los convertimos a pixel de nuevo
        Stream<Pixel> seleccionados = indices.stream()
                .limit(k)
                .map(indice -> new Pixel(indice));
        return seleccionados.collect(Collectors.toList());
    }
}
