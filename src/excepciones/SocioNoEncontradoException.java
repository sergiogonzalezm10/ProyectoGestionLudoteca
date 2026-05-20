package excepciones;

/**
 * Excepción que se lanza cuando no se encuentra un socio en el sistema.
 *
 * @author Sergio González
 * @version 1.0
 */
public class SocioNoEncontradoException extends Exception {

    /**
     * Constructor con mensaje de error.
     *
     * @param mensaje Descripción del error.
     */
    public SocioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}