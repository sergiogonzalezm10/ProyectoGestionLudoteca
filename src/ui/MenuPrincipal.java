package ui;

import excepciones.JuegoNoDisponibleException;
import excepciones.SocioNoEncontradoException;
import repositorio.*;
import servicio.*;

import java.util.Scanner;

/**
 * Clase encargada de mostrar el menú principal de la aplicación.
 * Coordina el acceso a los diferentes submódulos de gestión de la ludoteca
 * e inicializa las capas de servicio del sistema.
 *
 * @author Sergio González
 * @version 1.0
 */
public class MenuPrincipal {

    /** Servicio para la gestión de la lógica de negocio de los juegos. */
    private ServicioJuegos servicioJuegos;

    /** Servicio para la gestión de la lógica de negocio de los socios. */
    private ServicioSocios servicioSocios;

    /** Servicio para la gestión de la lógica de negocio de los préstamos. */
    private ServicioPrestamos servicioPrestamos;

    /** Servicio para la gestión de la lógica de negocio de los torneos. */
    private ServicioTorneos servicioTorneos;

    /** Escáner para la lectura de datos introducidos por el usuario mediante consola. */
    private Scanner scanner;

    /**
     * Constructor que inicializa los servicios y el scanner.
     *
     * @param repoJuegos     Repositorio que contiene los datos de los juegos.
     * @param repoSocios     Repositorio que contiene los datos de los socios.
     * @param repoPrestamos  Repositorio que contiene los datos de los préstamos.
     * @param repoTorneos    Repositorio que contiene los datos de los torneos.
     */
    public MenuPrincipal(RepositorioJuegos repoJuegos, RepositorioSocios repoSocios,
                         RepositorioPrestamos repoPrestamos, RepositorioTorneos repoTorneos) {
        this.servicioJuegos = new ServicioJuegos(repoJuegos);
        this.servicioSocios = new ServicioSocios(repoSocios);
        this.servicioPrestamos = new ServicioPrestamos(repoPrestamos);
        this.servicioTorneos = new ServicioTorneos(repoTorneos);
        this.scanner = new Scanner(System.in);
        this.servicioPrestamos.sincronizarContadorId();
        this.servicioTorneos.sincronizarContadorId();
    }

    /**
     * Muestra la interfaz del menú principal por consola y gestiona la navegación 
     * hacia los distintos submenús del sistema según la opción seleccionada.
     */
    public void mostrar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("     GESTIÓN DE LA LUDOTECA");
            System.out.println("========================================");
            System.out.println("1. Gestión de juegos");
            System.out.println("2. Gestión de socios");
            System.out.println("3. Gestión de préstamos");
            System.out.println("4. Gestión de torneos");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    new MenuJuegos(servicioJuegos, scanner).mostrar();
                    break;
                case 2:
                    new MenuSocios(servicioSocios, servicioJuegos, scanner).mostrar();
                    break;
                case 3:
                    new MenuPrestamos(servicioPrestamos, servicioJuegos,
                            servicioSocios, scanner).mostrar();
                    break;
                case 4:
                    new MenuTorneos(servicioTorneos, servicioJuegos,
                            servicioSocios, scanner).mostrar();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    /**
     * Método auxiliar privado para leer valores enteros desde la consola de forma segura,
     * controlando que la entrada sea numéricamente válida y limpiando el búfer del escáner.
     *
     * @return El número entero introducido por el usuario.
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