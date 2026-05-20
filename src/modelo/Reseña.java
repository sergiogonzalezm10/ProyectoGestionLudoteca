package modelo;

/**
 * Representa la valoración que un socio hace de un juego.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Reseña {

    /** Identificador de la reseña. */
    private int id;

    /** Socio que escribe la reseña. */
    private Socio socio;

    /** Juego valorado. */
    private JuegoMesa juego;

    /** Puntuación del 1 al 5. */
    private int puntuacion;

    /** Comentario opcional del socio. */
    private String comentario;

    /**
     * Constructor por parámetros.
     *
     * @param id         Identificador de la reseña.
     * @param socio      Socio que realiza la valoración.
     * @param juego      Juego valorado.
     * @param puntuacion Puntuación del 1 al 5.
     * @param comentario Comentario del socio.
     */
    public Reseña(int id, Socio socio, JuegoMesa juego,
                  int puntuacion, String comentario) {
        this.id = id;
        this.socio = socio;
        this.juego = juego;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    /** @return El identificador de la reseña. */
    public int getId() { return id; }

    /** @return El socio autor de la reseña. */
    public Socio getSocio() { return socio; }

    /** @return El juego valorado. */
    public JuegoMesa getJuego() { return juego; }

    /** @return La puntuación del 1 al 5. */
    public int getPuntuacion() { return puntuacion; }

    /** @param puntuacion La nueva puntuación. */
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    /** @return El comentario del socio. */
    public String getComentario() { return comentario; }

    /** @param comentario El nuevo comentario. */
    public void setComentario(String comentario) { this.comentario = comentario; }

    @Override
    public String toString() {
        return "[" + id + "] " + socio.getNombre() + " sobre '"
                + juego.getTitulo() + "' -> " + puntuacion + "/5 | " + comentario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reseña)) return false;
        Reseña r = (Reseña) o;
        return id == r.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}