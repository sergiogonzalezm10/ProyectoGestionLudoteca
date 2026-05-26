package ui;

import excepciones.JuegoNoDisponibleException;
import excepciones.SocioNoEncontradoException;
import modelo.JuegoMesa;
import modelo.Prestamo;
import modelo.Socio;
import servicio.ServicioJuegos;
import servicio.ServicioPrestamos;
import servicio.ServicioSocios;

import java.util.List;
import java.util.Scanner;

/**
 * Menú de gestión de préstamos de la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class MenuPrestamos {

    /** Servicio de préstamos. */
    private ServicioPrestamos servicioPrestamos;

    /** Servicio de juegos. */
    private ServicioJuegos servicioJuegos;

    /** Servicio de socios. */
    private ServicioSocios servicioSocios;

    /** Scanner para leer entradas del usuario. */
    private Scanner scanner;

    /**
     * Constructor por parámetros.
     *
     * @param servicioPrestamos Servicio de préstamos.
     * @param servicioJuegos    Servicio de juegos.
     * @param servicioSocios    Servicio de socios.
     * @param scanner           Scanner compartido.
     */
    public MenuPrestamos(ServicioPrestamos servicioPrestamos,
                         ServicioJuegos servicioJuegos,
                         ServicioSocios servicioSocios,
                         Scanner scanner) {
        this.servicioPrestamos = servicioPrestamos;
        this.servicioJuegos = servicioJuegos;
        this.servicioSocios = servicioSocios;
        this.scanner = scanner;
    }

    /**
     * Muestra el menú de préstamos y gestiona la navegación.
     */
    public void mostrar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
            System.out.println("1. Realizar préstamo");
            System.out.println("2. Registrar devolución");
            System.out.println("3. Listar todos los préstamos");
            System.out.println("4. Listar préstamos activos");
            System.out.println("5. Listar préstamos vencidos");
            System.out.println("6. Listar préstamos de un socio");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    realizarPrestamo();
                    break;
                case 2:
                    registrarDevolucion();
                    break;
                case 3:
                    listarTodos();
                    break;
                case 4:
                    listarActivos();
                    break;
                case 5:
                    listarVencidos();
                    break;
                case 6:
                    listarPorSocio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Solicita los datos y realiza un nuevo préstamo.
     */
    private void realizarPrestamo() {
        System.out.println("\n-- Realizar préstamo --");
        System.out.print("Id del socio: ");
        int idSocio = leerEntero();
        System.out.print("Id del juego: ");
        int idJuego = leerEntero();
        System.out.print("Días de préstamo: ");
        int dias = leerEntero();
        try {
            Socio socio = servicioSocios.buscarPorId(idSocio);
            JuegoMesa juego = servicioJuegos.buscarPorId(idJuego);
            servicioPrestamos.prestar(socio, juego, dias);
            System.out.println("Préstamo realizado correctamente.");
        } catch (SocioNoEncontradoException | JuegoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Solicita un id y registra la devolución del préstamo.
     */
    private void registrarDevolucion() {
        System.out.print("Id del préstamo a devolver: ");
        int id = leerEntero();
        try {
            servicioPrestamos.devolver(id);
            System.out.println("Devolución registrada correctamente.");
        } catch (JuegoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lista todos los préstamos registrados.
     */
    private void listarTodos() {
        List<Prestamo> prestamos = servicioPrestamos.listarTodos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    /**
     * Lista los préstamos activos.
     */
    private void listarActivos() {
        List<Prestamo> prestamos = servicioPrestamos.listarActivos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    /**
     * Lista los préstamos vencidos.
     */
    private void listarVencidos() {
        List<Prestamo> prestamos = servicioPrestamos.listarVencidos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos vencidos.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    /**
     * Lista los préstamos de un socio concreto.
     */
    private void listarPorSocio() {
        System.out.print("Id del socio: ");
        int idSocio = leerEntero();
        List<Prestamo> prestamos = servicioPrestamos.listarPorSocio(idSocio);
        if (prestamos.isEmpty()) {
            System.out.println("Este socio no tiene préstamos registrados.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
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
}