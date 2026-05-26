package repositorio;

import excepciones.SocioNoEncontradoException;
import modelo.Buscable;
import modelo.Socio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio encargado de gestionar el almacenamiento de los socios.
 * Implementa la interfaz Buscable parametrizada para entidades de tipo Socio.
 *
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioSocios implements Buscable<Socio> {

    /** Almacenamiento indexado de socios por su identificador único. */
    private HashMap<Integer, Socio> socios;

    /**
     * Constructor por defecto. Inicializa el mapa de almacenamiento.
     */
    public RepositorioSocios() {
        this.socios = new HashMap<>();
    }

    /**
     * Añade un nuevo socio al almacenamiento.
     *
     * @param socio Socio a registrar.
     */
    public void agregar(Socio socio) {
        this.socios.put(socio.getId(), socio);
    }

    /**
     * Elimina un socio del almacenamiento mediante su identificador.
     *
     * @param id Identificador del socio a eliminar.
     * @throws SocioNoEncontradoException si el identificador no corresponde a ningún socio.
     */
    public void eliminar(int id) throws SocioNoEncontradoException {
        if (!this.socios.containsKey(id)) {
            throw new SocioNoEncontradoException("No se puede eliminar: el socio con ID " + id + " no existe.");
        }
        this.socios.remove(id);
    }

    /**
     * Busca un socio registrado mediante su identificador único.
     *
     * @param id Identificador del socio.
     * @return El socio que coincide con el identificador.
     * @throws SocioNoEncontradoException si el identificador no existe en el sistema.
     */
    public Socio buscarPorId(int id) throws SocioNoEncontradoException {
        if (!this.socios.containsKey(id)) {
            throw new SocioNoEncontradoException("El socio con ID " + id + " no está registrado.");
        }
        return this.socios.get(id);
    }

    /**
     * Busca socios cuyo nombre contenga la cadena proporcionada.
     *
     * @param texto Cadena de texto a buscar en los nombres de los socios.
     * @return Lista de socios que cumplen el criterio de búsqueda.
     */
    @Override
    public List<Socio> buscarPorNombre(String texto) {
        String criterio = texto.toLowerCase();
        return this.socios.values().stream()
                .filter(s -> s.getNombre().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve una lista con todos los socios registrados.
     *
     * @return Lista completa de socios.
     */
    public List<Socio> listarTodos() {
        return new ArrayList<>(this.socios.values());
    }

    /**
     * Modifica los datos de un socio existente.
     *
     * @param socio Socio con los datos actualizados.
     * @throws SocioNoEncontradoException si el socio no existe en el almacenamiento.
     */
    public void modificar(Socio socio) throws SocioNoEncontradoException {
        if (!this.socios.containsKey(socio.getId())) {
            throw new SocioNoEncontradoException("No se puede modificar: el socio no existe.");
        }
        this.socios.put(socio.getId(), socio);
    }
}