package servicio;

import excepciones.JuegoNoDisponibleException;
import modelo.JuegoMesa;
import modelo.Prestamo;
import modelo.Socio;
import repositorio.RepositorioPrestamos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los préstamos.
 *
 * @author Sergio González
 * @version 1.0
 */
public class ServicioPrestamos {

    /** Repositorio de préstamos. */
    private RepositorioPrestamos repositorioPrestamos;

    /** Contador para generar identificadores únicos. */
    private int contadorId;

    /**
     * Constructor por parámetros.
     *
     * @param repositorioPrestamos Repositorio de préstamos a usar.
     */
    public ServicioPrestamos(RepositorioPrestamos repositorioPrestamos) {
        this.repositorioPrestamos = repositorioPrestamos;
        this.contadorId = 1;
    }

    /**
     * Sincroniza el contador de identificadores basándose en los datos existentes.
     */
    public void sincronizarContadorId() {
        int maxId = repositorioPrestamos.listarTodos().stream()
                .mapToInt(Prestamo::getId)
                .max()
                .orElse(0);
        this.contadorId = maxId + 1;
    }

    /**
     * Registra un nuevo préstamo de un juego a un socio.
     *
     * @param socio Socio que realiza el préstamo.
     * @param juego Juego a prestar.
     * @param dias  Número de días del préstamo.
     * @throws JuegoNoDisponibleException si el juego no está disponible.
     */
    public void prestar(Socio socio, JuegoMesa juego, int dias)
            throws JuegoNoDisponibleException {
        LocalDate hoy = LocalDate.now();
        LocalDate devolucion = hoy.plusDays(dias);
        Prestamo prestamo = new Prestamo(contadorId++, socio, juego, hoy, devolucion);
        repositorioPrestamos.agregar(prestamo);
    }

    /**
     * Registra la devolución de un préstamo.
     *
     * @param idPrestamo Identificador del préstamo.
     * @throws JuegoNoDisponibleException si el préstamo no existe o ya fue devuelto.
     */
    public void devolver(int idPrestamo) throws JuegoNoDisponibleException {
        repositorioPrestamos.devolver(idPrestamo);
    }

    /**
     * Devuelve todos los préstamos registrados.
     *
     * @return Lista con todos los préstamos.
     */
    public List<Prestamo> listarTodos() {
        return repositorioPrestamos.listarTodos();
    }

    /**
     * Devuelve los préstamos activos actualmente.
     *
     * @return Lista de préstamos activos.
     */
    public List<Prestamo> listarActivos() {
        return repositorioPrestamos.listarActivos();
    }

    /**
     * Devuelve los préstamos vencidos.
     *
     * @return Lista de préstamos vencidos.
     */
    public List<Prestamo> listarVencidos() {
        return repositorioPrestamos.listarVencidos();
    }

    /**
     * Devuelve los préstamos de un socio concreto.
     *
     * @param idSocio Identificador del socio.
     * @return Lista de préstamos del socio.
     */
    public List<Prestamo> listarPorSocio(int idSocio) {
        return repositorioPrestamos.listarTodos().stream()
                .filter(p -> p.getSocio().getId() == idSocio)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el número total de préstamos registrados.
     *
     * @return Número de préstamos.
     */
    public long contarPrestamos() {
        return repositorioPrestamos.listarTodos().size();
    }
}