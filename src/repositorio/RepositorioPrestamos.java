package repositorio;

import modelo.Prestamo;
import excepciones.JuegoNoDisponibleException;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio que gestiona los préstamos de la ludoteca.
 * Utiliza un ArrayList para mantener el orden de registro.
 *
 * @author Sergio González
 * @version 1.0
 */
public class RepositorioPrestamos {

    /** Lista de préstamos registrados. */
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor por defecto.
     * Inicializa el repositorio vacío.
     */
    public RepositorioPrestamos() {
        this.prestamos = new ArrayList<>();
    }

    /**
     * Registra un nuevo préstamo.
     *
     * @param prestamo Préstamo a registrar.
     * @throws JuegoNoDisponibleException si el juego no está disponible.
     */
    public void agregar(Prestamo prestamo) throws JuegoNoDisponibleException {
        if (!prestamo.getJuego().isDisponible()) {
            throw new JuegoNoDisponibleException("El juego '"
                    + prestamo.getJuego().getTitulo() + "' no está disponible.");
        }
        prestamo.getJuego().prestar();
        prestamo.getSocio().setPrestamosActivos(
                prestamo.getSocio().getPrestamosActivos() + 1);
        prestamos.add(prestamo);
    }

    /**
     * Registra la devolución de un préstamo por su identificador.
     *
     * @param id Identificador del préstamo.
     * @throws JuegoNoDisponibleException si el préstamo no existe o ya fue devuelto.
     */
    public void devolver(int id) throws JuegoNoDisponibleException {
        Prestamo prestamo = buscarPorId(id);
        if (!prestamo.estaActivo()) {
            throw new JuegoNoDisponibleException("El préstamo con id " + id + " ya fue devuelto.");
        }
        prestamo.setFechaDevolucionReal(java.time.LocalDate.now());
        prestamo.getJuego().devolver();
        prestamo.getSocio().setPrestamosActivos(
                prestamo.getSocio().getPrestamosActivos() - 1);
    }

    /**
     * Busca un préstamo por su identificador.
     *
     * @param id Identificador del préstamo.
     * @return El préstamo encontrado.
     * @throws JuegoNoDisponibleException si no existe.
     */
    public Prestamo buscarPorId(int id) throws JuegoNoDisponibleException {
        for (Prestamo p : prestamos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new JuegoNoDisponibleException("No existe ningún préstamo con id " + id);
    }
    
    /**
     * Añade un préstamo directamente a la lista sin realizar comprobaciones de estado.
     * Método de uso exclusivo para la persistencia de datos desde ficheros.
     *
     * @param prestamo Préstamo procedente del fichero de datos.
     */
    public void cargarDesdeFichero(Prestamo prestamo) {
        this.prestamos.add(prestamo);
    }

    /**
     * Devuelve todos los préstamos registrados.
     *
     * @return Lista con todos los préstamos.
     */
    public List<Prestamo> listarTodos() {
        return new ArrayList<>(prestamos);
    }

    /**
     * Devuelve los préstamos que están activos actualmente.
     *
     * @return Lista de préstamos activos.
     */
    public List<Prestamo> listarActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.estaActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    /**
     * Devuelve los préstamos que están vencidos.
     *
     * @return Lista de préstamos vencidos.
     */
    public List<Prestamo> listarVencidos() {
        List<Prestamo> vencidos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.estaVencido()) {
                vencidos.add(p);
            }
        }
        return vencidos;
    }

    /**
     * Devuelve los préstamos de un socio concreto por su identificador.
     *
     * @param idSocio Identificador del socio.
     * @return Lista de préstamos del socio.
     */
    public List<Prestamo> buscarPorSocio(int idSocio) {
        List<Prestamo> resultado = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getSocio().getId() == idSocio) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Devuelve el número total de préstamos registrados.
     *
     * @return Número de préstamos.
     */
    public int getTotalPrestamos() {
        return prestamos.size();
    }
}