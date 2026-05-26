package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un juego de mesa genérico.
 * Sirve como base para todos los tipos de juegos del catálogo.
 *
 * @author Sergio González
 * @version 1.0
 */
public abstract class JuegoMesa implements Puntuable, Prestable {

    /** Identificador único del juego. */
    private int id;

    /** Título del juego. */
    private String titulo;

    /** Número mínimo de jugadores recomendado. */
    private int minJugadores;

    /** Número máximo de jugadores recomendado. */
    private int maxJugadores;

    /** Duración estimada de una partida en minutos. */
    private int duracionMinutos;

    /** Indica si el juego está disponible para préstamo. */
    private boolean disponible;

    /** Lista de puntuaciones recibidas por los socios. */
    private List<Integer> puntuaciones;

    /**
     * Constructor por parámetros.
     *
     * @param id              Identificador del juego.
     * @param titulo          Título del juego.
     * @param minJugadores    Jugadores mínimos.
     * @param maxJugadores    Jugadores máximos.
     * @param duracionMinutos Duración estimada en minutos.
     */
    public JuegoMesa(int id, String titulo, int minJugadores,
                     int maxJugadores, int duracionMinutos) {
        this.id = id;
        this.titulo = titulo;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.duracionMinutos = duracionMinutos;
        this.disponible = true;
        this.puntuaciones = new ArrayList<>();
    }

    /**
     * Constructor de copia.
     *
     * @param otro Juego a copiar.
     */
    public JuegoMesa(JuegoMesa otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.minJugadores = otro.minJugadores;
        this.maxJugadores = otro.maxJugadores;
        this.duracionMinutos = otro.duracionMinutos;
        this.disponible = otro.disponible;
        this.puntuaciones = new ArrayList<>(otro.puntuaciones);
    }

    /**
     * Devuelve una descripción específica del tipo de juego.
     * Cada subclase debe implementar este método.
     *
     * @return Descripción del tipo de juego.
     */
    public abstract String descripcionTipo();

    @Override
    public void agregarPuntuacion(int puntuacion) {
        puntuaciones.add(puntuacion);
    }

    @Override
    public double getValoracionMedia() {
        if (puntuaciones.isEmpty()) {
            return 0.0;
        }
        int suma = 0;
        for (int p : puntuaciones) {
            suma += p;
        }
        return (double) suma / puntuaciones.size();
    }

    /** @return El identificador del juego. */
    public int getId() { return id; }

    /** @param id El nuevo identificador. */
    public void setId(int id) { this.id = id; }

    /** @return El título del juego. */
    public String getTitulo() { return titulo; }

    /** @param titulo El nuevo título. */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** @return El número mínimo de jugadores. */
    public int getMinJugadores() { return minJugadores; }

    /** @param minJugadores El nuevo mínimo de jugadores. */
    public void setMinJugadores(int minJugadores) { this.minJugadores = minJugadores; }

    /** @return El número máximo de jugadores. */
    public int getMaxJugadores() { return maxJugadores; }

    /** @param maxJugadores El nuevo máximo de jugadores. */
    public void setMaxJugadores(int maxJugadores) { this.maxJugadores = maxJugadores; }

    /** @return La duración estimada en minutos. */
    public int getDuracionMinutos() { return duracionMinutos; }

    /** @param duracionMinutos La nueva duración estimada. */
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    /** @return true si el juego está disponible para préstamo. */
    public boolean isDisponible() { return disponible; }

    /** @param disponible El nuevo estado de disponibilidad. */
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    /** @return La lista de puntuaciones recibidas. */
    public List<Integer> getPuntuaciones() { return puntuaciones; }

    @Override
    public void prestar() {
        this.disponible = false;
        this.setDisponible(false);
    }

    @Override
    public void devolver() {
        this.disponible = true;
        this.setDisponible(true);
    }

    @Override
    public boolean estaDisponible() {
        return this.isDisponible();
    }
    
    @Override
    public String toString() {
        String estado;
        if (disponible) {
            estado = "Disponible";
        } else {
            estado = "Prestado";
        }
        return "[" + id + "] " + titulo + " | " + descripcionTipo() + " | "
                + minJugadores + "-" + maxJugadores + " jugadores | ~"
                + duracionMinutos + " min | " + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JuegoMesa)) return false;
        JuegoMesa j = (JuegoMesa) o;
        return id == j.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}