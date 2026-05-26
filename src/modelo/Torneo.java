package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un torneo organizado en la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Torneo {

    /** Identificador del torneo. */
    private int id;

    /** Nombre del torneo. */
    private String nombre;

    /** Juego sobre el que se disputa el torneo. */
    private JuegoMesa juego;

    /** Fecha de celebración del torneo. */
    private LocalDate fecha;

    /** Número máximo de participantes. */
    private int maxParticipantes;

    /** Lista de socios inscritos en el torneo. */
    private List<Socio> inscritos;

    /**
     * Constructor por parámetros.
     *
     * @param id               Identificador del torneo.
     * @param nombre           Nombre del torneo.
     * @param juego            Juego del torneo.
     * @param fecha            Fecha de celebración.
     * @param maxParticipantes Número máximo de participantes.
     */
    public Torneo(int id, String nombre, JuegoMesa juego,
                  LocalDate fecha, int maxParticipantes) {
        this.id = id;
        this.nombre = nombre;
        this.juego = juego;
        this.fecha = fecha;
        this.maxParticipantes = maxParticipantes;
        this.inscritos = new ArrayList<>();
    }

    /**
     * Indica si el torneo tiene plazas libres.
     *
     * @return true si quedan plazas disponibles.
     */
    public boolean tienePlazas() {
        return inscritos.size() < maxParticipantes;
    }

    /**
     * Inscribe a un socio en el torneo si hay plazas y no estaba ya inscrito.
     *
     * @param socio Socio a inscribir.
     * @return true si se inscribió correctamente, false si ya estaba inscrito.
     */
    public boolean inscribirSocio(Socio socio) {
        if (inscritos.contains(socio)) {
            return false;
        }
        inscritos.add(socio);
        return true;
    }

    /** @return El identificador del torneo. */
    public int getId() { return id; }

    /** @return El nombre del torneo. */
    public String getNombre() { return nombre; }

    /** @return El juego del torneo. */
    public JuegoMesa getJuego() { return juego; }

    /** @return La fecha de celebración. */
    public LocalDate getFecha() { return fecha; }

    /** @return El número máximo de participantes. */
    public int getMaxParticipantes() { return maxParticipantes; }

    /** @return La lista de socios inscritos. */
    public List<Socio> getInscritos() { return inscritos; }

    @Override
    public String toString() {
        String estado = "";
        if (this.fecha.isBefore(java.time.LocalDate.now())) {
            estado = " [FINALIZADO]";
        }
        
        String plazasInfo = " (Plazas: " + this.inscritos.size() + "/" + this.maxParticipantes + ")";
        
        return "Torneo #" + id + ": " + nombre + " | Juego: " + juego.getTitulo() + 
                " | Fecha: " + fecha + plazasInfo + estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Torneo)) return false;
        Torneo t = (Torneo) o;
        return id == t.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}