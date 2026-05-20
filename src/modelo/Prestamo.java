package modelo;

import java.time.LocalDate;

/**
 * Representa un préstamo de un juego a un socio.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Prestamo {

    /** Identificador del préstamo. */
    private int id;

    /** Socio que realiza el préstamo. */
    private Socio socio;

    /** Juego prestado. */
    private JuegoMesa juego;

    /** Fecha en que se realizó el préstamo. */
    private LocalDate fechaPrestamo;

    /** Fecha máxima de devolución. */
    private LocalDate fechaDevolucionPrevista;

    /** Fecha real de devolución, null si aún no se ha devuelto. */
    private LocalDate fechaDevolucionReal;

    /**
     * Constructor por parámetros.
     *
     * @param id                      Identificador del préstamo.
     * @param socio                   Socio que realiza el préstamo.
     * @param juego                   Juego prestado.
     * @param fechaPrestamo           Fecha del préstamo.
     * @param fechaDevolucionPrevista Fecha máxima de devolución.
     */
    public Prestamo(int id, Socio socio, JuegoMesa juego,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucionPrevista) {
        this.id = id;
        this.socio = socio;
        this.juego = juego;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.fechaDevolucionReal = null;
    }

    /**
     * Indica si el préstamo está activo, es decir, el juego no ha sido devuelto.
     *
     * @return true si el juego no ha sido devuelto.
     */
    public boolean estaActivo() {
        return fechaDevolucionReal == null;
    }

    /**
     * Indica si el préstamo está vencido.
     *
     * @return true si ha pasado la fecha de devolución y no se ha devuelto.
     */
    public boolean estaVencido() {
        return estaActivo() && LocalDate.now().isAfter(fechaDevolucionPrevista);
    }

    /** @return El identificador del préstamo. */
    public int getId() { return id; }

    /** @return El socio del préstamo. */
    public Socio getSocio() { return socio; }

    /** @return El juego prestado. */
    public JuegoMesa getJuego() { return juego; }

    /** @return La fecha del préstamo. */
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    /** @return La fecha máxima de devolución. */
    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }

    /** @return La fecha real de devolución, o null si no se ha devuelto. */
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }

    /** @param fechaDevolucionReal La fecha real de devolución. */
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    @Override
    public String toString() {
        String estado;
        if (!estaActivo()) {
            estado = "Devuelto";
        } else if (estaVencido()) {
            estado = "VENCIDO";
        } else {
            estado = "Activo";
        }
        return "[" + id + "] " + socio.getNombre() + " -> " + juego.getTitulo()
                + " | Préstamo: " + fechaPrestamo
                + " | Devolución prevista: " + fechaDevolucionPrevista
                + " | " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo p = (Prestamo) o;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
