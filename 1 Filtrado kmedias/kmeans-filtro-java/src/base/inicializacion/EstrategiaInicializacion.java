package base.inicializacion;

import base.imagen.Pixel;

import java.util.List;

/**
 * interfaz para la seleccion de los centros iniciales
 */
public interface EstrategiaInicializacion {
    /**
     * metodo de seleccion de puntos
     * @param k numero de puntos a seleccionar
     * @param puntos coleccion de puntos. No se siempre
     *               se precisa este argumento, pero se
     *               mantiene para ofrecer una interfaz
     *               comun
     * @return lista con los puntos seleccionados
     */
    List<Pixel> seleccionar(int k, List<Pixel> puntos);
}
