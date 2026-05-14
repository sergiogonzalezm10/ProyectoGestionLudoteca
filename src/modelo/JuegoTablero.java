package modelo;

/**
 * Representa un juego de tablero del catálogo.
 *
 * @author Sergio González
 * @version 1.0
 */
public class JuegoTablero extends JuegoMesa implements Prestable {

    /** Indica si el juego incluye expansiones. */
    private boolean tieneExpansion;

    /**
     * Constructor por parámetros.
     *
     * @param id              Identificador del juego.
     * @param titulo          Título del juego.
     * @param minJugadores    Jugadores mínimos.
     * @param maxJugadores    Jugadores máximos.
     * @param duracionMinutos Duración estimada en minutos.
     * @param tieneExpansion  true si incluye expansión.
     */
    public JuegoTablero(int id, String titulo, int minJugadores,
                        int maxJugadores, int duracionMinutos, boolean tieneExpansion) {
        super(id, titulo, minJugadores, maxJugadores, duracionMinutos);
        this.tieneExpansion = tieneExpansion;
    }

    /**
     * Constructor de copia.
     *
     * @param otro JuegoTablero a copiar.
     */
    public JuegoTablero(JuegoTablero otro) {
        super(otro);
        this.tieneExpansion = otro.tieneExpansion;
    }

    @Override
    public String descripcionTipo() {
        if (tieneExpansion) {
            return "Tablero (con expansión)";
        } else {
            return "Tablero";
        }
    }

    @Override
    public void prestar() {
        setDisponible(false);
    }

    @Override
    public void devolver() {
        setDisponible(true);
    }

    @Override
    public boolean estaDisponible() {
        return isDisponible();
    }

    /** @return true si el juego tiene expansión. */
    public boolean isTieneExpansion() { return tieneExpansion; }

    /** @param tieneExpansion Nuevo valor de expansión. */
    public void setTieneExpansion(boolean tieneExpansion) {
        this.tieneExpansion = tieneExpansion;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}