package repositorio;

import modelo.Buscable;
import modelo.Socio;
import excepciones.SocioNoEncontradoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Repositorio que gestiona los socios de la ludoteca.
 * Utiliza un HashMap para acceso rápido por identificador.
 *
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioSocios implements Buscable<Socio> {

    /** Socios indexados por su identificador. */
    private HashMap<Integer, Socio> socios;

    /**
     * Constructor por defecto.
     * Inicializa el repositorio vacío.
     */
    public RepositorioSocios() {
        this.socios = new HashMap<>();
    }

    /**
     * Añade un socio al repositorio.
     *
     * @param socio Socio a añadir.
     */
    public void agregar(Socio socio) {
        socios.put(socio.getId(), socio);
    }

    /**
     * Elimina un socio del repositorio por su identificador.
     *
     * @param id Identificador del socio a eliminar.
     * @throws SocioNoEncontradoException si el socio no existe.
     */
    public void eliminar(int id) throws SocioNoEncontradoException {
        if (!socios.containsKey(id)) {
            throw new SocioNoEncontradoException("No existe ningún socio con id " + id);
        }
        socios.remove(id);
    }

    /**
     * Busca un socio por su identificador.
     *
     * @param id Identificador del socio.
     * @return El socio encontrado.
     * @throws SocioNoEncontradoException si el socio no existe.
     */
    public Socio buscarPorId(int id) throws SocioNoEncontradoException {
        if (!socios.containsKey(id)) {
            throw new SocioNoEncontradoException("No existe ningún socio con id " + id);
        }
        return socios.get(id);
    }

    /**
     * Busca socios cuyo nombre contenga el texto indicado.
     *
     * @param texto Texto a buscar en el nombre.
     * @return Lista de socios que coinciden.
     */
    @Override
    public List<Socio> buscarPorNombre(String texto) {
        List<Socio> resultado = new ArrayList<>();
        for (Socio socio : socios.values()) {
            if (socio.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(socio);
            }
        }
        return resultado;
    }

    /**
     * Devuelve todos los socios del repositorio.
     *
     * @return Lista con todos los socios.
     */
    public List<Socio> listarTodos() {
        return new ArrayList<>(socios.values());
    }

    /**
     * Modifica un socio ya existente en el repositorio.
     *
     * @param socio Socio con los datos actualizados.
     * @throws SocioNoEncontradoException si el socio no existe.
     */
    public void modificar(Socio socio) throws SocioNoEncontradoException {
        if (!socios.containsKey(socio.getId())) {
            throw new SocioNoEncontradoException("No existe ningún socio con id " + socio.getId());
        }
        socios.put(socio.getId(), socio);
    }

    /**
     * Devuelve el número de socios registrados.
     *
     * @return Número de socios.
     */
    public int getTotalSocios() {
        return socios.size();
    }
}