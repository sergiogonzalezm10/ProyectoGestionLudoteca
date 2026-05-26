package servicio;

import excepciones.JuegoNoDisponibleException;
import modelo.JuegoMesa;
import modelo.ResultadoSistema;
import repositorio.RepositorioJuegos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los juegos.
 *
 * @author Sergio González
 * @version 1.0
 */
public class ServicioJuegos {

    /** Repositorio de juegos. */
    private RepositorioJuegos repositorioJuegos;

    /**
     * Constructor por parámetros.
     *
     * @param repositorioJuegos Repositorio de juegos a usar.
     */
    public ServicioJuegos(RepositorioJuegos repositorioJuegos) {
        this.repositorioJuegos = repositorioJuegos;
    }

    /**
     * Añade un juego al catálogo.
     *
     * @param juego Juego a añadir.
     */
    public void agregar(JuegoMesa juego) {
        repositorioJuegos.agregar(juego);
    }

    /**
     * Elimina un juego del catálogo por su identificador.
     *
     * @param id Identificador del juego.
     * @throws JuegoNoDisponibleException si el juego no existe.
     */
    public void eliminar(int id) throws JuegoNoDisponibleException {
        repositorioJuegos.eliminar(id);
    }

    /**
     * Busca un juego por su identificador.
     *
     * @param id Identificador del juego.
     * @return El juego encontrado.
     * @throws JuegoNoDisponibleException si el juego no existe.
     */
    public JuegoMesa buscarPorId(int id) throws JuegoNoDisponibleException {
        return repositorioJuegos.buscarPorId(id);
    }

    /**
     * Busca un juego por su identificador empaquetando el resultado en la estructura genérica.
     * Captura la excepción y la transforma en una respuesta segura para la UI.
     * (Requisito obligatorio de estructura genérica propia).
     *
     * @param id Identificador del juego a buscar.
     * @return Un objeto ResultadoSistema parametrizado con el tipo JuegoMesa.
     */
    public ResultadoSistema<JuegoMesa> buscarJuegoConEstado(int id) {
        try {
            JuegoMesa juego = this.repositorioJuegos.buscarPorId(id);
            return new ResultadoSistema<>(juego, "Juego de mesa localizado con éxito.");
        } catch (JuegoNoDisponibleException e) {
            return new ResultadoSistema<>(null, e.getMessage());
        }
    }

    /**
     * Modifica un juego existente.
     *
     * @param juego Juego con los datos actualizados.
     * @throws JuegoNoDisponibleException si el juego no existe.
     */
    public void modificar(JuegoMesa juego) throws JuegoNoDisponibleException {
        repositorioJuegos.modificar(juego);
    }

    /**
     * Devuelve todos los juegos del catálogo.
     *
     * @return Lista con todos los juegos.
     */
    public List<JuegoMesa> listarTodos() {
        return repositorioJuegos.listarTodos();
    }

    /**
     * Devuelve los juegos disponibles para préstamo.
     *
     * @return Lista de juegos disponibles.
     */
    public List<JuegoMesa> listarDisponibles() {
        return repositorioJuegos.listarTodos().stream()
                .filter(j -> j.isDisponible())
                .collect(Collectors.toList());
    }

    /**
     * Devuelve los juegos ordenados por título alfabéticamente.
     *
     * @return Lista de juegos ordenados por título.
     */
    public List<JuegoMesa> listarOrdenadosPorTitulo() {
        return repositorioJuegos.listarTodos().stream()
                .sorted(Comparator.comparing(j -> j.getTitulo()))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el top 5 de juegos mejor valorados.
     *
     * @return Lista con los 5 juegos mejor valorados.
     */
    public List<JuegoMesa> topCincoMejorValorados() {
        return repositorioJuegos.listarTodos().stream()
                .filter(j -> j.getValoracionMedia() > 0)
                .sorted((j1, j2) -> Double.compare(j2.getValoracionMedia(),
                        j1.getValoracionMedia()))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Busca juegos cuyo título contenga el texto indicado.
     * Delega en el repositorio para aprovechar la interfaz genérica Buscable<T>.
     *
     * @param texto Texto a buscar.
     * @return Lista de juegos que coinciden.
     */
    public List<JuegoMesa> buscarPorNombre(String texto) {
        return repositorioJuegos.buscarPorNombre(texto);
    }

    /**
     * Devuelve el número total de juegos en el catálogo.
     *
     * @return Número de juegos.
     */
    public long contarJuegos() {
        return repositorioJuegos.listarTodos().stream()
                .count();
    }

    /**
     * Devuelve el juego con la duración máxima estimada.
     *
     * @return El juego más largo, o null si no hay juegos.
     */
    public JuegoMesa juegoMasLargo() {
        return repositorioJuegos.listarTodos().stream()
                .max(Comparator.comparingInt(j -> j.getDuracionMinutos()))
                .orElse(null);
    }
}