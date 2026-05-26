package repositorio;

import excepciones.SocioNoEncontradoException;
import excepciones.TorneoLlenoException;
import modelo.Socio;
import modelo.Torneo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Repositorio encargado de gestionar el almacenamiento de los torneos.
 * Los datos se mantienen ordenados por su fecha de celebración de forma estructural.
 *
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioTorneos {

    /** Almacenamiento estructurado de torneos ordenados por fecha. */
    private Set<Torneo> torneos;

    /**
     * Constructor por defecto. Inicializa la colección con un criterio de ordenación.
     */
    public RepositorioTorneos() {
        this.torneos = new TreeSet<>(Comparator.comparing(Torneo::getFecha)
                .thenComparing(Torneo::getId));
    }

    /**
     * Añade un nuevo torneo al almacenamiento.
     *
     * @param torneo Torneo a registrar.
     */
    public void agregar(Torneo torneo) {
        this.torneos.add(torneo);
    }

    /**
     * Inscribe a un socio en un torneo específico tras realizar las validaciones básicas.
     *
     * @param idTorneo Identificador del torneo objetivo.
     * @param socio    Socio que solicita la inscripción.
     * @throws TorneoLlenoException       si las plazas del torneo están cubiertas.
     * @throws SocioNoEncontradoException si la entidad del socio contiene datos no válidos.
     */
    public void inscribirSocio(int idTorneo, Socio socio) 
            throws TorneoLlenoException, SocioNoEncontradoException {
        Torneo torneo = buscarPorId(idTorneo);
        if (torneo == null) {
            throw new IllegalArgumentException("No existe ningún torneo con el identificador: " + idTorneo);
        }
        torneo.inscribirSocio(socio);
    }

    /**
     * Busca un torneo registrado mediante su identificador único.
     *
     * @param id Identificador del torneo.
     * @return El torneo que coincide con el identificador o null si no se encuentra.
     */
    public Torneo buscarPorId(int id) {
        return this.torneos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Devuelve una lista con todos los torneos registrados en el sistema.
     *
     * @return Lista de torneos ordenados por fecha.
     */
    public List<Torneo> listarTodos() {
        return new ArrayList<>(this.torneos);
    }
}