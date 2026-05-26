package repositorio;

import excepciones.JuegoNoDisponibleException;
import modelo.Buscable;
import modelo.JuegoMesa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio que gestiona el catálogo de juegos de la ludoteca.
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioJuegos implements Buscable<JuegoMesa> {

    /** Almacenamiento indexado de juegos por su identificador único. */
    private HashMap<Integer, JuegoMesa> catalogo;

    /**
     * Constructor por defecto. Inicializa el mapa del catálogo.
     */
    public RepositorioJuegos() {
        this.catalogo = new HashMap<>();
    }

    /**
     * Añade un nuevo juego al catálogo.
     *
     * @param juego Juego de mesa a registrar.
     */
    public void agregar(JuegoMesa juego) {
        this.catalogo.put(juego.getId(), juego);
    }

    /**
     * Elimina un juego del catálogo mediante su identificador.
     *
     * @param id Identificador del juego a eliminar.
     * @throws JuegoNoDisponibleException si el identificador no corresponde a ningún juego.
     */
    public void eliminar(int id) throws JuegoNoDisponibleException {
        if (!this.catalogo.containsKey(id)) {
            throw new JuegoNoDisponibleException("No se puede eliminar: el juego con ID " + id + " no existe.");
        }
        this.catalogo.remove(id);
    }

    /**
     * Busca un juego registrado mediante su identificador único.
     *
     * @param id Identificador del juego.
     * @return El juego que coincide con el identificador.
     * @throws JuegoNoDisponibleException si el identificador no existe en el sistema.
     */
    public JuegoMesa buscarPorId(int id) throws JuegoNoDisponibleException {
        if (!this.catalogo.containsKey(id)) {
            throw new JuegoNoDisponibleException("El juego con ID " + id + " no está registrado.");
        }
        return this.catalogo.get(id);
    }

    /**
     * Busca juegos cuyo título contenga la cadena de texto proporcionada.
     *
     * @param texto Cadena de texto a buscar en el título.
     * @return Lista de juegos que cumplen el criterio.
     */
    public List<JuegoMesa> buscarPorNombre(String texto) {
        String criterio = texto.toLowerCase();
        return this.catalogo.values().stream()
                .filter(j -> j.getTitulo().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve una lista con todos los juegos del catálogo.
     *
     * @return Lista completa de juegos de mesa.
     */
    public List<JuegoMesa> listarTodos() {
        return new ArrayList<>(this.catalogo.values());
    }

    /**
     * Devuelve una lista con los juegos que están actualmente disponibles.
     *
     * @return Lista de juegos disponibles.
     */
    public List<JuegoMesa> listarDisponibles() {
        return this.catalogo.values().stream()
                .filter(JuegoMesa::isDisponible)
                .collect(Collectors.toList());
    }

    /**
     * Modifica los datos de un juego existente.
     *
     * @param juego Juego con los datos actualizados.
     * @throws JuegoNoDisponibleException si el juego no existe en el catálogo.
     */
    public void modificar(JuegoMesa juego) throws JuegoNoDisponibleException {
        if (!this.catalogo.containsKey(juego.getId())) {
            throw new JuegoNoDisponibleException("No se puede modificar: el juego no existe.");
        }
        this.catalogo.put(juego.getId(), juego);
    }
}