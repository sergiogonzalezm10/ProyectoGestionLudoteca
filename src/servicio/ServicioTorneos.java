package servicio;

import excepciones.SocioNoEncontradoException;
import excepciones.TorneoLlenoException;
import modelo.JuegoMesa;
import modelo.Socio;
import modelo.Torneo;
import repositorio.RepositorioTorneos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los torneos.
 *
 * @author Sergio González
 * @version 1.1
 */
public class ServicioTorneos {

    /** Repositorio de torneos. */
    private RepositorioTorneos repositorioTorneos;

    /** Contador para generar identificadores únicos. */
    private int contadorId;

    /**
     * Constructor por parámetros.
     *
     * @param repositorioTorneos Repositorio de torneos a usar.
     */
    public ServicioTorneos(RepositorioTorneos repositorioTorneos) {
        this.repositorioTorneos = repositorioTorneos;
        this.contadorId = 1;
    }

    /**
     * Sincroniza el contador de identificadores basándose en los datos existentes.
     */
    public void sincronizarContadorId() {
        int maxId = repositorioTorneos.listarTodos().stream()
                .mapToInt(Torneo::getId)
                .max()
                .orElse(0);
        this.contadorId = maxId + 1;
    }

    /**
     * Crea y añade un nuevo torneo.
     *
     * @param nombre           Nombre del torneo.
     * @param juego            Juego del torneo.
     * @param fecha            Fecha de celebración.
     * @param maxParticipantes Número máximo de participantes.
     */
    public void crear(String nombre, JuegoMesa juego,
                      LocalDate fecha, int maxParticipantes) {
        Torneo torneo = new Torneo(contadorId++, nombre, juego, fecha, maxParticipantes);
        repositorioTorneos.agregar(torneo);
    }

    /**
     * Inscribe a un socio en un torneo.
     *
     * @param idTorneo Identificador del torneo.
     * @param socio    Socio a inscribir.
     * @throws TorneoLlenoException       si el torneo está lleno.
     * @throws SocioNoEncontradoException si el torneo no existe.
     */
    public void inscribirSocio(int idTorneo, Socio socio)
            throws TorneoLlenoException, SocioNoEncontradoException {
        repositorioTorneos.inscribirSocio(idTorneo, socio);
    }

    /**
     * Devuelve todos los torneos registrados en el sistema.
     *
     * @return Lista de torneos totales.
     */
    public List<Torneo> listarTodos() {
        return repositorioTorneos.listarTodos();
    }

    /**
     * Devuelve los torneos que aún tienen plazas disponibles Y cuya fecha es 
     * igual o posterior a la fecha actual (no muestra los pasados).
     *
     * @return Lista de torneos vigentes con plazas.
     */
    public List<Torneo> listarConPlazas() {
        LocalDate hoy = LocalDate.now();
        return repositorioTorneos.listarTodos().stream()
                .filter(t -> !t.getFecha().isBefore(hoy)) // Filtra que NO sea menor a hoy
                .filter(Torneo::tienePlazas)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve los torneos que se celebran a partir de hoy (futuros).
     * Excluye estrictamente cualquier torneo cuya fecha sea anterior a la actual.
     *
     * @return Lista de torneos futuros ordenados por fecha.
     */
    public List<Torneo> listarFuturos() {
        LocalDate hoy = LocalDate.now();
        return repositorioTorneos.listarTodos().stream()
                .filter(t -> !t.getFecha().isBefore(hoy)) // Filtra que NO sea menor a hoy
                .sorted(Comparator.comparing(Torneo::getFecha))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el número total de torneos registrados.
     *
     * @return Número de torneos.
     */
    public long contarTorneos() {
        return repositorioTorneos.listarTodos().size();
    }
}