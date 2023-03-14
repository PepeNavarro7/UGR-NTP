package base.inicializacion;

import base.imagen.Pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        ArrayList<Pixel> seleccionados = new ArrayList<>();

        // se seleccionan k puntos al azar
        ArrayList<Integer> indices = new ArrayList<>();
        for(int i = 0; i < puntos.size(); i++){
            indices.add(i);
        }

        // se barajan los indices
        Collections.shuffle(indices);

        // se seleccionan los puntos de los k primeros indices
        for(int i=0; i < k; i++){
            seleccionados.add(puntos.get(indices.get(i)));
        }

        // se devuelve la lista de puntos seleccionados
        return seleccionados;
    }
}
