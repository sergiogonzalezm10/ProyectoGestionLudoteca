package ui;

import excepciones.JuegoNoDisponibleException;
import modelo.*;
import servicio.ServicioJuegos;

import java.util.List;
import java.util.Scanner;

/**
 * Menú de gestión de juegos de la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class MenuJuegos {

    /** Servicio de juegos. */
    private ServicioJuegos servicioJuegos;

    /** Scanner para leer entradas del usuario. */
    private Scanner scanner;

    /**
     * Constructor por parámetros.
     *
     * @param servicioJuegos Servicio de juegos.
     * @param scanner        Scanner compartido.
     */
    public MenuJuegos(ServicioJuegos servicioJuegos, Scanner scanner) {
        this.servicioJuegos = servicioJuegos;
        this.scanner = scanner;
    }

    /**
     * Muestra el menú de juegos y gestiona la navegación.
     * @throws JuegoNoDisponibleException 
     */
    public void mostrar() throws JuegoNoDisponibleException {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE JUEGOS ---");
            System.out.println("1. Añadir juego");
            System.out.println("2. Eliminar juego");
            System.out.println("3. Modificar juego");
            System.out.println("4. Buscar juego por id");
            System.out.println("5. Buscar juego por nombre");
            System.out.println("6. Listar todos los juegos");
            System.out.println("7. Listar juegos disponibles");
            System.out.println("8. Top 5 mejor valorados");
            System.out.println("9. Juego más largo");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    añadirJuego();
                    break;
                case 2:
                    eliminarJuego();
                    break;
                case 3:
                    modificarJuego();
                    break;
                case 4:
                    buscarPorId();
                    break;
                case 5:
                    buscarPorNombre();
                    break;
                case 6:
                    listarTodos();
                    break;
                case 7:
                    listarDisponibles();
                    break;
                case 8:
                    topCinco();
                    break;
                case 9:
                    juegoMasLargo();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Solicita los datos de un nuevo juego y lo añade al catálogo.
     */
    private void añadirJuego() {
        System.out.println("\n-- Añadir juego --");
        System.out.print("Id: ");
        int id = leerEntero();
        
        // Uso de la clase genérica para comprobar duplicados
        ResultadoSistema<JuegoMesa> verificacion = servicioJuegos.buscarJuegoConEstado(id);
        if (verificacion.getContenido() != null) {
            System.out.println("Error: Ya existe un juego registrado con el ID " + id + ".");
            return;
        }
        System.out.print("Título: ");
        String titulo = leerTexto();
        System.out.print("Mínimo de jugadores: ");
        int minJ = leerEntero();
        System.out.print("Máximo de jugadores: ");
        int maxJ = leerEntero();
        System.out.print("Duración estimada (minutos): ");
        int dur = leerEntero();
        System.out.println("Tipo: 1-Tablero  2-Cartas  3-Dados  4-Rol");
        System.out.print("Elige tipo: ");
        int tipo = leerEntero();

        JuegoMesa juego = null;
        switch (tipo) {
            case 1:
                System.out.print("¿Tiene expansión? (s/n): ");
                boolean expansion = scanner.nextLine().trim().equalsIgnoreCase("s");
                juego = new JuegoTablero(id, titulo, minJ, maxJ, dur, expansion);
                break;
            case 2:
                System.out.print("Número de cartas: ");
                int cartas = leerEntero();
                juego = new JuegoCartas(id, titulo, minJ, maxJ, dur, cartas);
                break;
            case 3:
                System.out.print("Número de dados: ");
                int dados = leerEntero();
                juego = new JuegoDados(id, titulo, minJ, maxJ, dur, dados);
                break;
            case 4:
                System.out.print("Ambientación: ");
                String ambientacion = leerTexto();
                juego = new JuegoRol(id, titulo, minJ, maxJ, dur, ambientacion);
                break;
            default:
                System.out.println("Tipo no válido.");
                return;
        }
        servicioJuegos.agregar(juego);
        System.out.println("Juego añadido correctamente.");
    }

    /**
     * Solicita un id y elimina el juego correspondiente.
     */
    private void eliminarJuego() {
        System.out.print("Id del juego a eliminar: ");
        int id = leerEntero();
        try {
            servicioJuegos.eliminar(id);
            System.out.println("Juego eliminado correctamente.");
        } catch (JuegoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Solicita un id y permite modificar el título del juego.
     */
    private void modificarJuego() {
        System.out.print("Id del juego a modificar: ");
        int id = leerEntero();
        try {
            JuegoMesa juego = servicioJuegos.buscarPorId(id);
            System.out.println("Juego actual: " + juego);
            System.out.print("Nuevo título (Enter para mantener): ");
            String titulo = scanner.nextLine().trim();
            if (!titulo.isEmpty()) {
                juego.setTitulo(titulo);
            }
            System.out.print("Nueva duración en minutos (0 para mantener): ");
            int dur = leerEntero();
            if (dur > 0) {
                juego.setDuracionMinutos(dur);
            }
            servicioJuegos.modificar(juego);
            System.out.println("Juego modificado correctamente.");
        } catch (JuegoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Busca y muestra un juego por su id usando la clase genérica.
     */
    private void buscarPorId() {
        System.out.print("Id del juego: ");
        int id = leerEntero();
        
        ResultadoSistema<JuegoMesa> resultado = servicioJuegos.buscarJuegoConEstado(id);
        System.out.println("[" + resultado.getMensaje() + "]");
        
        if (resultado.getContenido() != null) {
            System.out.println(resultado.getContenido());
        }
    }
    
    /**
     * Busca y muestra juegos por nombre.
     */
    private void buscarPorNombre() {
        System.out.print("Texto a buscar: ");
        String texto = leerTexto();
        List<JuegoMesa> resultado = servicioJuegos.buscarPorNombre(texto);
        if (resultado.isEmpty()) {
            System.out.println("No se encontraron juegos con ese nombre.");
        } else {
            for (JuegoMesa j : resultado) {
                System.out.println(j);
            }
        }
    }

    /**
     * Lista todos los juegos del catálogo.
     */
    private void listarTodos() {
        List<JuegoMesa> juegos = servicioJuegos.listarTodos();
        if (juegos.isEmpty()) {
            System.out.println("No hay juegos en el catálogo.");
        } else {
            for (JuegoMesa j : juegos) {
                System.out.println(j);
            }
        }
    }

    /**
     * Lista los juegos disponibles para préstamo.
     */
    private void listarDisponibles() {
        List<JuegoMesa> juegos = servicioJuegos.listarDisponibles();
        if (juegos.isEmpty()) {
            System.out.println("No hay juegos disponibles.");
        } else {
            for (JuegoMesa j : juegos) {
                System.out.println(j);
            }
        }
    }

    /**
     * Muestra el top 5 de juegos mejor valorados.
     */
    private void topCinco() {
        List<JuegoMesa> juegos = servicioJuegos.topCincoMejorValorados();
        if (juegos.isEmpty()) {
            System.out.println("No hay juegos valorados aún.");
        } else {
            System.out.println("-- Top 5 mejor valorados --");
            for (JuegoMesa j : juegos) {
                System.out.println(j.getTitulo() + " - " + j.getValoracionMedia() + "/5");
            }
        }
    }

    /**
     * Muestra el juego con la duración más larga.
     */
    private void juegoMasLargo() {
        JuegoMesa juego = servicioJuegos.juegoMasLargo();
        if (juego == null) {
            System.out.println("No hay juegos en el catálogo.");
        } else {
            System.out.println("Juego más largo: " + juego);
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
}