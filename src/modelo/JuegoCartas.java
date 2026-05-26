package modelo;

/**
 * Representa un juego de cartas del catálogo.
 *
 * @author Sergio González
 * @version 1.0
 */
public class JuegoCartas extends JuegoMesa {

    /** Número de cartas que contiene el juego. */
    private int numCartas;

    /**
     * Constructor por parámetros.
     *
     * @param id              Identificador del juego.
     * @param titulo          Título del juego.
     * @param minJugadores    Jugadores mínimos.
     * @param maxJugadores    Jugadores máximos.
     * @param duracionMinutos Duración estimada en minutos.
     * @param numCartas       Número de cartas del juego.
     */
    public JuegoCartas(int id, String titulo, int minJugadores,
                       int maxJugadores, int duracionMinutos, int numCartas) {
        super(id, titulo, minJugadores, maxJugadores, duracionMinutos);
        this.numCartas = numCartas;
    }

    /**
     * Constructor de copia.
     *
     * @param otro JuegoCartas a copiar.
     */
    public JuegoCartas(JuegoCartas otro) {
        super(otro);
        this.numCartas = otro.numCartas;
    }

    @Override
    public String descripcionTipo() {
        return "Cartas (" + numCartas + " cartas)";
    }


    /** @return El número de cartas del juego. */
    public int getNumCartas() { return numCartas; }

    /** @param numCartas El nuevo número de cartas. */
    public void setNumCartas(int numCartas) { this.numCartas = numCartas; }

    @Override
    public String toString() {
        return super.toString();
    }
}