package modelo;

/**
 * Contrato que deben cumplir los juegos que pueden prestarse a domicilio.
 *
 * @author Sergio González
 * @version 1.0
 */
public interface Prestable {

    /**
     * Marca el juego como prestado.
     */
    void prestar();

    /**
     * Marca el juego como devuelto y disponible.
     */
    void devolver();

    /**
     * Indica si el juego está disponible para préstamo.
     *
     * @return true si está disponible.
     */
    boolean estaDisponible();
}