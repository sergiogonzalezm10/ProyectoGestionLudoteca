
import excepciones.JuegoNoDisponibleException;
import excepciones.SocioNoEncontradoException;
import repositorio.GestorCSV;
import repositorio.RepositorioJuegos;
import repositorio.RepositorioPrestamos;
import repositorio.RepositorioSocios;
import repositorio.RepositorioTorneos;
import servicio.ServicioPrestamos;
import servicio.ServicioTorneos;
import ui.MenuPrincipal;

/**
 * Clase principal que actúa como punto de entrada de la aplicación de la ludoteca.
 * Se encarga de inicializar los repositorios, coordinar la carga de datos inicial desde 
 * los ficheros CSV, sincronizar los estados del sistema y lanzar la interfaz de usuario.
 * Al finalizar la ejecución, asegura el guardado de los datos persistentes.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de entrada al programa.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     * @throws SocioNoEncontradoException 
     * @throws JuegoNoDisponibleException 
     */
    public static void main(String[] args) throws SocioNoEncontradoException, JuegoNoDisponibleException {
    	// Crear repositorios
        RepositorioJuegos repoJuegos = new RepositorioJuegos();
        RepositorioSocios repoSocios = new RepositorioSocios();
        RepositorioPrestamos repoPrestamos = new RepositorioPrestamos();
        RepositorioTorneos repoTorneos = new RepositorioTorneos();

        // Crear el gestor de ficheros
        GestorCSV gestorCSV = new GestorCSV();

        System.out.println("Cargando base de datos de la ludoteca...");
        
        // Cargar datos de los ficheros CSV
        gestorCSV.cargarJuegos(repoJuegos);
        gestorCSV.cargarSocios(repoSocios);
        gestorCSV.cargarPrestamos(repoPrestamos, repoJuegos, repoSocios);
        gestorCSV.cargarTorneos(repoTorneos, repoJuegos);

        // Crear servicios
        ServicioPrestamos servicioPrestamos = new ServicioPrestamos(repoPrestamos);
        ServicioTorneos servicioTorneos = new ServicioTorneos(repoTorneos);

        // Sincronizar contadores de id
        servicioPrestamos.sincronizarContadorId();
        servicioTorneos.sincronizarContadorId();

        System.out.println("Datos cargados correctamente.");

        // Lanzar el menú principal
        MenuPrincipal menu = new MenuPrincipal(repoJuegos, repoSocios, repoPrestamos, repoTorneos);
        menu.mostrar();

        // Guardar datos al cerrar
        System.out.println("\nGuardando cambios en el sistema de ficheros...");
        gestorCSV.guardarJuegos(repoJuegos);
        gestorCSV.guardarSocios(repoSocios);
        gestorCSV.guardarPrestamos(repoPrestamos);
        gestorCSV.guardarTorneos(repoTorneos);
        
        System.out.println("Aplicación finalizada de forma segura.");
    }
}