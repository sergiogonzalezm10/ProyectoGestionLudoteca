package excepciones;

/**
 * Excepción que se lanza cuando se intenta prestar un juego que no está disponible.
 *
 * @author Sergio González
 * @version 1.0
 */
public class JuegoNoDisponibleException extends Exception {

    /**
     * Constructor con mensaje de error.
     *
     * @param mensaje Descripción del error.
     */
    public JuegoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}