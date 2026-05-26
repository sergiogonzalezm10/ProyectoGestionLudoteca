package modelo;

/**
 * Representa un juego de dados del catálogo.
 *
 * @author Sergio González
 * @version 1.0
 */
public class JuegoDados extends JuegoMesa{

    /** Número de dados que incluye el juego. */
    private int numDados;

    /**
     * Constructor por parámetros.
     *
     * @param id              Identificador del juego.
     * @param titulo          Título del juego.
     * @param minJugadores    Jugadores mínimos.
     * @param maxJugadores    Jugadores máximos.
     * @param duracionMinutos Duración estimada en minutos.
     * @param numDados        Número de dados incluidos.
     */
    public JuegoDados(int id, String titulo, int minJugadores,
                      int maxJugadores, int duracionMinutos, int numDados) {
        super(id, titulo, minJugadores, maxJugadores, duracionMinutos);
        this.numDados = numDados;
    }

    /**
     * Constructor de copia.
     *
     * @param otro JuegoDados a copiar.
     */
    public JuegoDados(JuegoDados otro) {
        super(otro);
        this.numDados = otro.numDados;
    }

    @Override
    public String descripcionTipo() {
        return "Dados (" + numDados + " dados)";
    }

    /** @return El número de dados del juego. */
    public int getNumDados() { return numDados; }

    /** @param numDados El nuevo número de dados. */
    public void setNumDados(int numDados) { this.numDados = numDados; }

    @Override
    public String toString() {
        return super.toString();
    }
}