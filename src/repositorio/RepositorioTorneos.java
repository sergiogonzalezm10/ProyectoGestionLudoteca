package repositorio;

import modelo.Socio;
import modelo.Torneo;
import excepciones.SocioNoEncontradoException;
import excepciones.TorneoLlenoException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Repositorio que gestiona los torneos de la ludoteca.
 * Utiliza un TreeSet para mantener los torneos ordenados por fecha.
 *
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioTorneos {

    /** Torneos ordenados por fecha de celebración. */
    private TreeSet<Torneo> torneos;

    /**
     * Constructor por defecto.
     * Inicializa el repositorio vacío con ordenación por fecha.
     */
    public RepositorioTorneos() {
        this.torneos = new TreeSet<>(new Comparator<Torneo>() {
            @Override
            public int compare(Torneo t1, Torneo t2) {
                int resultado = t1.getFecha().compareTo(t2.getFecha());
                if (resultado == 0) {
                    return Integer.compare(t1.getId(), t2.getId());
                }
                return resultado;
            }
        });
    }

    /**
     * Añade un torneo al repositorio.
     *
     * @param torneo Torneo a añadir.
     */
    public void agregar(Torneo torneo) {
        torneos.add(torneo);
    }

    /**
     * Busca un torneo por su identificador.
     *
     * @param id Identificador del torneo.
     * @return El torneo encontrado, o null si no existe.
     */
    public Torneo buscarPorId(int id) {
        for (Torneo t : torneos) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Inscribe a un socio en un torneo.
     *
     * @param idTorneo Identificador del torneo.
     * @param socio    Socio a inscribir.
     * @throws TorneoLlenoException          si el torneo no tiene plazas.
     * @throws SocioNoEncontradoException    si el torneo no existe.
     */
    public void inscribirSocio(int idTorneo, Socio socio)
            throws TorneoLlenoException, SocioNoEncontradoException {
        Torneo torneo = buscarPorId(idTorneo);
        if (torneo == null) {
            throw new SocioNoEncontradoException("No existe ningún torneo con id " + idTorneo);
        }
        if (!torneo.tienePlazas()) {
            throw new TorneoLlenoException("El torneo '" + torneo.getNombre() + "' está lleno.");
        }
        torneo.inscribirSocio(socio);
    }

    /**
     * Devuelve todos los torneos ordenados por fecha.
     *
     * @return Lista de torneos ordenados.
     */
    public List<Torneo> listarTodos() {
        return new ArrayList<>(torneos);
    }

    /**
     * Devuelve el número total de torneos registrados.
     *
     * @return Número de torneos.
     */
    public int getTotalTorneos() {
        return torneos.size();
    }
}