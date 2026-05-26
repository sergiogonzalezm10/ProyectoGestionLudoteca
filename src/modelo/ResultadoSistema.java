package modelo;

/**
 * Clase genérica encargada de empaquetar un resultado del sistema junto con un mensaje de estado.
 * Se utiliza para devolver cualquier tipo de objeto junto con un texto informativo que indica
 * si la operación se ha realizado correctamente o si ha ocurrido algún error.
 *
 * @author Sergio González
 * @version 1.0
 * @param <T> Tipo de entidad genérica que va a contener el resultado.
 */
public class ResultadoSistema<T> {
    
    /** El objeto o entidad resultante de la operación (por ejemplo, un Socio o un Juego). */
    private T contenido;
    
    /** Mensaje de texto que describe el estado o el resultado de la operación realizada. */
    private String mensaje;

    /**
     * Constructor por parámetros para inicializar el contenido y el mensaje del resultado.
     *
     * @param contenido El objeto o entidad que se desea almacenar.
     * @param mensaje   El texto descriptivo con el estado de la operación.
     */
    public ResultadoSistema(T contenido, String mensaje) {
        this.contenido = contenido;
        this.mensaje = mensaje;
    }

    /**
     * Devuelve el objeto o entidad almacenado en el resultado.
     *
     * @return El contenido genérico de tipo T.
     */
    public T getContenido() { 
        return contenido; 
    }

    /**
     * Devuelve el mensaje descriptivo sobre el estado de la operación.
     *
     * @return Una cadena de texto con el mensaje de estado.
     */
    public String getMensaje() { 
        return mensaje; 
    }
}