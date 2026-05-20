package modelo;

/**
 * Representa un juego de rol del catálogo.
 *
 * @author Sergio González
 * @version 1.0
 */
public class JuegoRol extends JuegoMesa implements Prestable {

    /** Ambientación o temática del juego de rol. */
    private String ambientacion;

    /**
     * Constructor por parámetros.
     *
     * @param id              Identificador del juego.
     * @param titulo          Título del juego.
     * @param minJugadores    Jugadores mínimos.
     * @param maxJugadores    Jugadores máximos.
     * @param duracionMinutos Duración estimada en minutos.
     * @param ambientacion    Temática o ambientación del juego.
     */
    public JuegoRol(int id, String titulo, int minJugadores,
                    int maxJugadores, int duracionMinutos, String ambientacion) {
        super(id, titulo, minJugadores, maxJugadores, duracionMinutos);
        this.ambientacion = ambientacion;
    }

    /**
     * Constructor de copia.
     *
     * @param otro JuegoRol a copiar.
     */
    public JuegoRol(JuegoRol otro) {
        super(otro);
        this.ambientacion = otro.ambientacion;
    }

    @Override
    public String descripcionTipo() {
        return "Rol (" + ambientacion + ")";
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

    /** @return La ambientación del juego de rol. */
    public String getAmbientacion() { return ambientacion; }

    /** @param ambientacion La nueva ambientación. */
    public void setAmbientacion(String ambientacion) {
        this.ambientacion = ambientacion;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}