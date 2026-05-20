package excepciones;

/**
 * Excepción que se lanza cuando se intenta inscribir a un socio en un torneo que ya está lleno.
 *
 * @author Sergio González
 * @version 1.0
 */
public class TorneoLlenoException extends Exception {

    /**
     * Constructor con mensaje de error.
     *
     * @param mensaje Descripción del error.
     */
    public TorneoLlenoException(String mensaje) {
        super(mensaje);
    }
}