package modelo;

/**
 * Contrato para entidades que pueden recibir valoraciones numéricas.
 *
 * @author Sergio González
 * @version 1.0
 */
public interface Puntuable {

    /**
     * Registra una nueva puntuación.
     *
     * @param puntuacion Puntuación del 1 al 5.
     */
    void agregarPuntuacion(int puntuacion);

    /**
     * Calcula la valoración media de todas las puntuaciones recibidas.
     *
     * @return Media de las valoraciones, o 0.0 si no hay ninguna.
     */
    double getValoracionMedia();
}