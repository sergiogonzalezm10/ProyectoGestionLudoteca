package modelo;

import java.util.List;

/**
 * Contrato para repositorios que soportan búsqueda por texto libre.
 *
 * @author Sergio González
 * @version 1.0
 */
public interface Buscable<T> {

    /**
     * Busca elementos cuyo título o nombre contenga el texto indicado.
     *
     * @param texto Texto a buscar.
     * @return Lista con los elementos que coinciden.
     */
    List<T> buscarPorNombre(String texto);
}