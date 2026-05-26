package ui;

import excepciones.SocioNoEncontradoException;
import excepciones.TorneoLlenoException;
import modelo.JuegoMesa;
import modelo.ResultadoSistema;
import modelo.Socio;
import modelo.Torneo;
import servicio.ServicioJuegos;
import servicio.ServicioSocios;
import servicio.ServicioTorneos;
import excepciones.JuegoNoDisponibleException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Menú de gestión de torneos de la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class MenuTorneos {

    /** Servicio de torneos. */
    private ServicioTorneos servicioTorneos;

    /** Servicio de juegos. */
    private ServicioJuegos servicioJuegos;

    /** Servicio de socios. */
    private ServicioSocios servicioSocios;

    /** Scanner para leer entradas del usuario. */
    private Scanner scanner;

    /**
     * Constructor por parámetros.
     *
     * @param servicioTorneos Servicio de torneos.
     * @param servicioJuegos  Servicio de juegos.
     * @param servicioSocios  Servicio de socios.
     * @param scanner         Scanner compartido.
     */
    public MenuTorneos(ServicioTorneos servicioTorneos,
                       ServicioJuegos servicioJuegos,
                       ServicioSocios servicioSocios,
                       Scanner scanner) {
        this.servicioTorneos = servicioTorneos;
        this.servicioJuegos = servicioJuegos;
        this.servicioSocios = servicioSocios;
        this.scanner = scanner;
    }

    /**
     * Muestra el menú de torneos y gestiona la navegación.
     * @throws SocioNoEncontradoException 
     */
    public void mostrar() throws SocioNoEncontradoException {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE TORNEOS ---");
            System.out.println("1. Crear torneo");
            System.out.println("2. Inscribir socio en torneo");
            System.out.println("3. Listar todos los torneos");
            System.out.println("4. Listar torneos con plazas");
            System.out.println("5. Listar torneos futuros");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    crearTorneo();
                    break;
                case 2:
                    inscribirSocio();
                    break;
                case 3:
                    listarTodos();
                    break;
                case 4:
                    listarConPlazas();
                    break;
                case 5:
                    listarFuturos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Solicita los datos y crea un nuevo torneo.
     */
    private void crearTorneo() {
        System.out.println("\n-- Crear torneo --");
        System.out.print("Nombre del torneo: ");
        String nombre = leerTexto();
        System.out.print("Id del juego: ");
        int idJuego = leerEntero();
        ResultadoSistema<JuegoMesa> resultadoJuego = servicioJuegos.buscarJuegoConEstado(idJuego);
        if (resultadoJuego.getContenido() == null) {
            System.out.println("Error: " + resultadoJuego.getMensaje());
            return;
        }

        System.out.print("Fecha (yyyy-MM-dd): ");
        LocalDate fecha = leerFecha();
        System.out.print("Máximo de participantes: ");
        int max = leerEntero();
        
        servicioTorneos.crear(nombre, resultadoJuego.getContenido(), fecha, max);
        System.out.println("Torneo creado correctamente.");
    }

    /**
     * Solicita los datos e inscribe a un socio en un torneo.
     * @throws SocioNoEncontradoException
     */
    private void inscribirSocio() throws SocioNoEncontradoException{
        System.out.print("Id del torneo: ");
        int idTorneo = leerEntero();
        System.out.print("Id del socio: ");
        int idSocio = leerEntero();
        ResultadoSistema<Socio> resultadoSocio = servicioSocios.buscarSocioConEstado(idSocio);
        if (resultadoSocio.getContenido() == null) {
            System.out.println("Error: " + resultadoSocio.getMensaje());
            return;
        }
        Torneo torneoActual = null;
        for (Torneo t : servicioTorneos.listarTodos()) {
            if (t.getId() == idTorneo) {
                torneoActual = t;
                break;
            }
        }

        if (torneoActual == null) {
            System.out.println("Error: No existe ningún torneo registrado con el ID " + idTorneo);
            return;
        }
        
        Socio socioAInscribir = resultadoSocio.getContenido();
        if (torneoActual.getInscritos().contains(socioAInscribir)) {
            System.out.println("\n[AVISO] El socio " + socioAInscribir.getNombre() + " (ID: " + idSocio + ") Ya está registrado en este torneo.");
            System.out.println("No se puede volver a inscribir. Operación cancelada.");
            return; 
        }
        
        try {
            servicioTorneos.inscribirSocio(idTorneo, socioAInscribir);
            System.out.println("Socio inscrito correctamente.");
        } catch (TorneoLlenoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lista todos los torneos.
     */
    private void listarTodos() {
        List<Torneo> torneos = servicioTorneos.listarTodos();
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos registrados.");
        } else {
            for (Torneo t : torneos) {
                System.out.println(t);
            }
        }
    }

    /**
     * Lista los torneos con plazas disponibles.
     */
    private void listarConPlazas() {
        List<Torneo> torneos = servicioTorneos.listarConPlazas();
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos con plazas disponibles.");
        } else {
            for (Torneo t : torneos) {
                System.out.println(t);
            }
        }
    }

    /**
     * Lista los torneos futuros ordenados por fecha.
     */
    private void listarFuturos() {
        List<Torneo> torneos = servicioTorneos.listarFuturos();
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos futuros.");
        } else {
            for (Torneo t : torneos) {
                System.out.println(t);
            }
        }
    }

    /**
     * Lee un entero del teclado validando que sea un número.
     *
     * @return El entero introducido.
     */
    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada no válida. Introduce un número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    /**
     * Lee una cadena de texto no vacía del teclado.
     *
     * @return El texto introducido.
     */
    private String leerTexto() {
        String texto = scanner.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("El campo no puede estar vacío: ");
            texto = scanner.nextLine().trim();
        }
        return texto;
    }

    /**
     * Lee una fecha en formato yyyy-MM-dd del teclado.
     *
     * @return La fecha introducida.
     */
    private LocalDate leerFecha() {
        while (true) {
            try {
                String texto = scanner.nextLine().trim();
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.print("Formato incorrecto. Introduce la fecha (yyyy-MM-dd): ");
            }
        }
    }
}