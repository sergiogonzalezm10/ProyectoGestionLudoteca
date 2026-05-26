package ui;

import excepciones.JuegoNoDisponibleException;
import excepciones.SocioNoEncontradoException;
import modelo.Socio;
import modelo.JuegoMesa;
import servicio.ServicioSocios;
import servicio.ServicioJuegos;

import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de gestionar la interfaz de usuario para el módulo de socios.
 * Permite realizar altas, bajas, modificaciones, búsquedas y valoraciones de juegos.
 *
 * @author Sergio González
 * @version 1.0
 */
public class MenuSocios {

    /** Servicio para gestionar la lógica de negocio de los socios. */
    private ServicioSocios servicioSocios;

    /** Servicio para gestionar la lógica de negocio de los juegos. */
    private ServicioJuegos servicioJuegos;

    /** Escáner para capturar las entradas de datos del usuario por consola. */
    private Scanner scanner;

    /**
     * Constructor por parámetros de la clase MenuSocios.
     *
     * @param servicioSocios Servicio de socios a utilizar.
     * @param servicioJuegos Servicio de juegos a utilizar.
     * @param scanner        Instancia de Scanner compartida para la lectura de datos.
     */
    public MenuSocios(ServicioSocios servicioSocios, ServicioJuegos servicioJuegos, Scanner scanner) {
        this.servicioSocios = servicioSocios;
        this.servicioJuegos = servicioJuegos;
        this.scanner = scanner;
    }

    /**
     * Muestra el menú de gestión de socios en bucle hasta que el usuario 
     * decide volver al menú principal seleccionando la opción de salida.
     */
    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("          GESTIÓN DE SOCIOS");
            System.out.println("========================================");
            System.out.println("1. Registrar nuevo socio");
            System.out.println("2. Dar de baja socio");
            System.out.println("3. Modificar datos de socio");
            System.out.println("4. Buscar socio por ID");
            System.out.println("5. Buscar socio por nombre");
            System.out.println("6. Listar todos los socios");
            System.out.println("7. Valorar un juego");
            System.out.println("0. Volver");
            System.out.println("========================================");
            System.out.print("Elige una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: registrarSocio(); break;
                case 2: eliminarSocio(); break;
                case 3: modificarSocio(); break;
                case 4: buscarSocioPorId(); break;
                case 5: buscarSocioPorNombre(); break;
                case 6: listarSocios(); break;
                case 7: valorarJuego(); break;
                case 0: System.out.println("Volviendo al menú principal..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    /**
     * Solicita los datos de un nuevo socio por pantalla. 
     * Comprueba directamente en la lista en memoria si el ID introducido está duplicado.
     */
    private void registrarSocio() {
        System.out.println("\n-- Registrar Nuevo Socio --");
        
        System.out.print("Introduce el ID que deseas asignarle (ej: 24): ");
        int id = leerEntero();
        
        List<Socio> todosLosSocios = servicioSocios.listarTodos();
        for (Socio s : todosLosSocios) {
            if (s.getId() == id) {
                System.out.println("\n[ERROR] El ID " + id + " ya está registrado y pertenece a: " + s.getNombre());
                System.out.println("No se permiten IDs duplicados. Operación cancelada.");
                return;
            }
        }
        
        System.out.print("Nombre completo: ");
        String nombre = leerTexto();
        System.out.print("Email: ");
        String email = leerTexto();
        System.out.print("Teléfono: ");
        String telefono = leerTexto();
        System.out.print("Número de socio: ");
        String numeroSocio = leerTexto();
        
        Socio nuevoSocio = new Socio(id, nombre, email, telefono, numeroSocio);
        servicioSocios.agregar(nuevoSocio);
        System.out.println("Socio registrado correctamente con el ID: " + id);
    }

    /**
     * Solicita el identificador de un socio para proceder a su eliminación del sistema.
     */
    private void eliminarSocio() {
        System.out.print("ID del socio a dar de baja: ");
        int id = leerEntero();
        try {
            servicioSocios.eliminar(id);
            System.out.println("Socio eliminado correctamente.");
        } catch (SocioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Permite la actualización selectiva de las propiedades de un socio existente.
     * Si se pulsa la tecla Intro sin escribir texto, el atributo correspondiente mantiene su valor original.
     */
    private void modificarSocio() {
        System.out.print("ID del socio a modificar: ");
        int id = leerEntero();
        try {
            Socio socio = servicioSocios.buscarPorId(id);
            
            System.out.print("Nuevo nombre (" + socio.getNombre() + ") [Enter para omitir]: ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) socio.setNombre(nombre);
            
            System.out.print("Nuevo email (" + socio.getEmail() + ") [Enter para omitir]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) socio.setEmail(email);
            
            System.out.print("Nuevo teléfono (" + socio.getTelefono() + ") [Enter para omitir]: ");
            String telefono = scanner.nextLine().trim();
            if (!telefono.isEmpty()) socio.setTelefono(telefono);
            
            servicioSocios.modificar(socio);
            System.out.println("Socio modified correctamente.");
            
        } catch (SocioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Solicita un identificador por consola y muestra la representación en formato 
     * de cadena del socio si es localizado en el sistema.
     */
    private void buscarSocioPorId() {
        System.out.print("ID del socio: ");
        int id = leerEntero();
        try {
            Socio socio = servicioSocios.buscarPorId(id);
            System.out.println(socio);
        } catch (SocioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Solicita un criterio de búsqueda textual y muestra la lista de socios
     * cuyos nombres coinciden de forma parcial con la cadena introducida.
     */
    private void buscarSocioPorNombre() {
        System.out.print("Nombre a buscar: ");
        String nombre = leerTexto();
        List<Socio> resultados = servicioSocios.buscarPorNombre(nombre);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron socios con ese nombre.");
        } else {
            for (Socio s : resultados) {
                System.out.println(s);
            }
        }
    }

    /**
     * Obtiene e imprime en pantalla el listado completo de los socios registrados en la aplicación.
     */
    private void listarSocios() {
        List<Socio> lista = servicioSocios.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay socios registrados.");
        } else {
            for (Socio s : lista) {
                System.out.println(s);
            }
        }
    }

    /**
     * Permite a un socio calificar un juego de mesa del catálogo introduciendo
     * una puntuación comprendida en el rango numérico del 1 al 5.
     */
    private void valorarJuego() {
        System.out.println("\n--- VALORAR UN JUEGO ---");
        System.out.print("Introduce tu ID de socio: ");
        int idSocio = leerEntero();
        
        Socio socio = null;
        try {
            socio = servicioSocios.buscarPorId(idSocio);
        } catch (SocioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.print("Introduce el ID del juego que quieres valorar: ");
        int idJuego = leerEntero();
        
        JuegoMesa juego = null;
        try {
            juego = servicioJuegos.buscarPorId(idJuego);
        } catch (JuegoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.print("Introduce la nota (del 1 al 5): ");
        int nota = leerEntero();
        if (nota < 1 || nota > 5) {
            System.out.println("Error: La nota debe estar entre 1 y 5.");
            return;
        }

        juego.agregarPuntuacion(nota);
        try {
            servicioJuegos.modificar(juego);
            System.out.println("¡Valoración guardada! Gracias por puntuar, " + socio.getNombre());
        } catch (JuegoNoDisponibleException e) {
            System.out.println("Error al guardar la valoración: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar privado encargado de validar la lectura de números enteros por consola.
     * Reintenta de forma continua la lectura ante entradas de texto no numéricas.
     *
     * @return El número entero válido introducido por teclado.
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
     * Método auxiliar privado encargado de validar que las lecturas de texto por consola
     * no contengan únicamente espacios en blanco o campos vacíos.
     *
     * @return Cadena de texto depurada introducida por teclado.
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