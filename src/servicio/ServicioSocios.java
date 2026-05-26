package servicio;

import excepciones.SocioNoEncontradoException;
import modelo.ResultadoSistema;
import modelo.Socio;
import repositorio.RepositorioSocios;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los socios de la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class ServicioSocios {

    /** Repositorio encargado de la persistencia en memoria de los socios. */
    private RepositorioSocios repositorioSocios;

    /**
     * Constructor por parámetros.
     *
     * @param repositorioSocios Repositorio de socios a usar.
     */
    public ServicioSocios(RepositorioSocios repositorioSocios) {
        this.repositorioSocios = repositorioSocios;
    }

    /**
     * Añade un nuevo socio al sistema respetando su identificador manual.
     *
     * @param socio Objeto socio que contiene la información a registrar.
     */
    public void agregar(Socio socio) {
        this.repositorioSocios.agregar(socio);
    }

    /**
     * Elimina a un socio del sistema a partir de su identificador único.
     *
     * @param id Identificador único del socio a dar de baja.
     * @throws SocioNoEncontradoException si no existe ningún socio con ese identificador.
     */
    public void eliminar(int id) throws SocioNoEncontradoException {
        this.repositorioSocios.eliminar(id);
    }
    
    /**
     * Modifica o actualiza la información existente de un socio en el sistema.
     *
     * @param socio Objeto socio con los datos actualizados a persistir.
     * @throws SocioNoEncontradoException Si no se encuentra el socio que se pretende modificar.
     */
    public void modificar(Socio socio) throws SocioNoEncontradoException {
        this.repositorioSocios.modificar(socio);
    }

    /**
     * Busca y obtiene un socio concreto utilizando su identificador único.
     *
     * @param id Identificador único del socio a buscar.
     * @return El objeto socio que coincide con el ID.
     * @throws SocioNoEncontradoException Si no existe ningún socio registrado con el ID proporcionado.
     */
    public Socio buscarPorId(int id) throws SocioNoEncontradoException {
        return this.repositorioSocios.buscarPorId(id);
    }

    /**
     * Realiza una búsqueda segura de socio por ID devolviendo un envoltorio con el estado del resultado.
     * Evita la interrupción del flujo del programa controlando internamente las excepciones.
     *
     * @param id Identificador único del socio a verificar.
     * @return Un objeto ResultadoSistema que contiene al socio (si existe) y un mensaje con el estado del proceso.
     */
    public ResultadoSistema<Socio> buscarSocioConEstado(int id) {
        try {
            Socio socio = this.repositorioSocios.buscarPorId(id);
            return new ResultadoSistema<>(socio, "Socio localizado con éxito.");
        } catch (SocioNoEncontradoException e) {
            return new ResultadoSistema<>(null, e.getMessage());
        }
    }

    /**
     * Busca socios por un fragmento de texto contenido en su nombre.
     *
     * @param texto Cadena de texto o patrón de búsqueda para el filtrado.
     * @return Lista de socios cuyo nombre contiene el texto introducido.
     */
    public List<Socio> buscarPorNombre(String texto) {
        return this.repositorioSocios.buscarPorNombre(texto);
    }

    /**
     * Devuelve una lista con todos los socios actualmente registrados en la ludoteca.
     *
     * @return Lista que contiene la totalidad de los socios en el sistema.
     */
    public List<Socio> listarTodos() {
        return this.repositorioSocios.listarTodos();
    }

    /**
     * Filtra y obtiene una lista de aquellos socios que tienen alquileres o préstamos pendientes.
     *
     * @return Lista de socios con una cantidad de préstamos activos estrictamente superior a cero.
     */
    public List<Socio> listarConPrestamosActivos() {
        return this.repositorioSocios.listarTodos().stream()
                .filter(s -> s.getPrestamosActivos() > 0)
                .collect(Collectors.toList());
    }
}