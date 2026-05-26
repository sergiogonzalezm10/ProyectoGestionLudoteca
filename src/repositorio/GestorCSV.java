package repositorio;

import excepciones.JuegoNoDisponibleException;
import excepciones.SocioNoEncontradoException;
import modelo.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase encargada de la persistencia de datos en ficheros planos con formato CSV.
 * Mapea las colecciones de los repositorios hacia ficheros físicos y viceversa,
 * garantizando la conservación de la información entre ejecuciones del sistema.
 *
 * @author Sergio González
 * @version 1.0
 */
public class GestorCSV {

    /** Ruta del fichero físico de almacenamiento para el catálogo de juegos. */
    private static final String RUTA_JUEGOS = "src/recursos/juegos.csv";
    
    /** Ruta del fichero físico de almacenamiento para el registro de socios. */
    private static final String RUTA_SOCIOS = "src/recursos/socios.csv";
    
    /** Ruta del fichero físico de almacenamiento para el histórico de préstamos. */
    private static final String RUTA_PRESTAMOS = "src/recursos/prestamos.csv";
    
    /** Ruta del fichero físico de almacenamiento para la planificación de torneos. */
    private static final String RUTA_TORNEOS = "src/recursos/torneos.csv";

    /**
     * Exporta los datos del catálogo de juegos de mesa desde el repositorio hacia el fichero CSV.
     * Si la colección está vacía, el proceso se omite de forma segura.
     *
     * @param repositorio Repositorio que contiene la lista de juegos de mesa a persistir.
     */
    public void guardarJuegos(RepositorioJuegos repositorio) {
        List<JuegoMesa> lista = repositorio.listarTodos();
        if (lista.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_JUEGOS))) {
            for (JuegoMesa juego : lista) {
                bw.write(juegosALinea(juego));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar juegos: " + e.getMessage());
        }
    }

    /**
     * Importa y reconstruye los objetos de tipo juego de mesa desde el fichero CSV hacia el repositorio.
     * Verifica la existencia del fichero en disco antes de iniciar la lectura.
     *
     * @param repositorio Repositorio donde se agregarán los juegos cargados con éxito.
     */
    public void cargarJuegos(RepositorioJuegos repositorio) {
        if (!Files.exists(Paths.get(RUTA_JUEGOS))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_JUEGOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    JuegoMesa juego = lineaAJuego(linea);
                    if (juego != null) repositorio.agregar(juego);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar juegos: " + e.getMessage());
        }
    }

    /**
     * Exporta la información de los socios de la ludoteca desde el repositorio hacia el fichero CSV.
     * Si la colección está vacía, el proceso se omite de forma segura.
     *
     * @param repositorio Repositorio que contiene la lista de socios a guardar.
     */
    public void guardarSocios(RepositorioSocios repositorio) {
        List<Socio> lista = repositorio.listarTodos();
        if (lista.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_SOCIOS))) {
            for (Socio socio : lista) {
                bw.write(socioALinea(socio));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar socios: " + e.getMessage());
        }
    }

    /**
     * Importa y reconstruye los registros de socios desde el fichero CSV hacia el repositorio.
     * Verifica la existencia del fichero en disco antes de iniciar la lectura.
     *
     * @param repositorio Repositorio donde se registrarán los socios recuperados con éxito.
     */
    public void cargarSocios(RepositorioSocios repositorio) {
        if (!Files.exists(Paths.get(RUTA_SOCIOS))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_SOCIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Socio socio = lineaASocio(linea);
                    if (socio != null) repositorio.agregar(socio);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar socios: " + e.getMessage());
        }
    }

    /**
     * Exporta los datos de los préstamos realizados desde el repositorio hacia el fichero CSV.
     * Si la colección está vacía, el proceso se omite de forma segura.
     *
     * @param repositorio Repositorio que contiene el listado de préstamos a guardar.
     */
    public void guardarPrestamos(RepositorioPrestamos repositorio) {
        List<Prestamo> lista = repositorio.listarTodos();
        if (lista.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_PRESTAMOS))) {
            for (Prestamo prestamo : lista) {
                bw.write(prestamoALinea(prestamo));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar préstamos: " + e.getMessage());
        }
    }

    /**
     * Importa los registros de préstamos vinculando dinámicamente sus relaciones con socios y juegos.
     * Requiere de los repositorios auxiliares de juegos y socios para mapear de manera precisa las referencias cruzadas.
     *
     * @param repositorio Repositorio destino para almacenar los préstamos reconstruidos.
     * @param repoJuegos  Repositorio auxiliar de juegos para enlazar el juego de mesa prestado.
     * @param repoSocios  Repositorio auxiliar de socios para enlazar el socio solicitante.
     */
    public void cargarPrestamos(RepositorioPrestamos repositorio, RepositorioJuegos repoJuegos, RepositorioSocios repoSocios) {
        if (!Files.exists(Paths.get(RUTA_PRESTAMOS))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PRESTAMOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    try {
                        String[] partes = linea.split(",");
                        int id = Integer.parseInt(partes[0]);
                        int idSocio = Integer.parseInt(partes[1]);
                        int idJuego = Integer.parseInt(partes[2]);
                        LocalDate fechaPrestamo = LocalDate.parse(partes[3]);
                        LocalDate fechaPrevista = LocalDate.parse(partes[4]);
                        String fechaRealStr = partes[5].trim();

                        Socio socio = repoSocios.buscarPorId(idSocio);
                        JuegoMesa juego = repoJuegos.buscarPorId(idJuego);

                        Prestamo prestamo = new Prestamo(id, socio, juego, fechaPrestamo, fechaPrevista);
                        if (!fechaRealStr.equals("null")) {
                            prestamo.setFechaDevolucionReal(LocalDate.parse(fechaRealStr));
                        }
                        repositorio.cargarDesdeFichero(prestamo);
                    } catch (SocioNoEncontradoException | JuegoNoDisponibleException e) {
                        System.out.println("Error al cargar préstamo: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar préstamos: " + e.getMessage());
        }
    }

    /**
     * Exporta los datos de los torneos organizados desde el repositorio hacia el fichero CSV.
     * Si la colección está vacía, el proceso se omite de forma segura.
     *
     * @param repositorio Repositorio que contiene el listado de torneos a guardar.
     */
    public void guardarTorneos(RepositorioTorneos repositorio) {
        List<Torneo> lista = repositorio.listarTodos();
        if (lista.isEmpty()) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_TORNEOS))) {
            for (Torneo torneo : lista) {
                bw.write(torneoALinea(torneo));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar torneos: " + e.getMessage());
        }
    }

    /**
     * Importa los registros de torneos desde el fichero CSV vinculando de forma precisa el juego de mesa asignado.
     *
     * @param repositorio Repositorio destino para almacenar los torneos reconstruidos.
     * @param repoJuegos  Repositorio auxiliar de juegos para enlazar la referencia del juego del torneo.
     */
    public void cargarTorneos(RepositorioTorneos repositorio, RepositorioJuegos repoJuegos) {
        if (!Files.exists(Paths.get(RUTA_TORNEOS))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_TORNEOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    try {
                        String[] partes = linea.split(",");
                        int id = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        int idJuego = Integer.parseInt(partes[2]);
                        LocalDate fecha = LocalDate.parse(partes[3]);
                        int maxParticipantes = Integer.parseInt(partes[4]);

                        JuegoMesa juego = repoJuegos.buscarPorId(idJuego);
                        Torneo torneo = new Torneo(id, nombre, juego, fecha, maxParticipantes);
                        repositorio.agregar(torneo);
                    } catch (JuegoNoDisponibleException e) {
                        System.out.println("Error al cargar torneo: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar torneos: " + e.getMessage());
        }
    }

    /**
     * Convierte una instancia de un juego de mesa en su representación textual formateada en CSV.
     * Evalúa el subtipo específico del juego para añadir las columnas correspondientes y serializa el histórico de notas.
     *
     * @param juego Instancia del juego de mesa a serializar.
     * @return Una cadena de texto con los datos delimitados por comas.
     */
    private String juegosALinea(JuegoMesa juego) {
        String tipo = "";
        String extra = "";
        if (juego instanceof JuegoTablero) {
            tipo = "TABLERO";
            extra = String.valueOf(((JuegoTablero) juego).isTieneExpansion());
        } else if (juego instanceof JuegoCartas) {
            tipo = "CARTAS";
            extra = String.valueOf(((JuegoCartas) juego).getNumCartas());
        } else if (juego instanceof JuegoDados) {
            tipo = "DADOS";
            extra = String.valueOf(((JuegoDados) juego).getNumDados());
        } else if (juego instanceof JuegoRol) {
            tipo = "ROL";
            extra = ((JuegoRol) juego).getAmbientacion();
        }

        // Recuperar notas y unirlas con punto y coma
        String puntuacionesStr = "SIN_NOTAS";
        List<Integer> pts = juego.getPuntuaciones();
        if (pts != null && !pts.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pts.size(); i++) {
                sb.append(pts.get(i));
                if (i < pts.size() - 1) sb.append(";");
            }
            puntuacionesStr = sb.toString();
        }

        return juego.getId() + "," + juego.getTitulo() + ","
                + juego.getMinJugadores() + "," + juego.getMaxJugadores() + ","
                + juego.getDuracionMinutos() + "," + juego.isDisponible() + ","
                + tipo + "," + extra + "," + puntuacionesStr;
    }

    /**
     * Procesa una línea de texto CSV para instanciar y configurar un subtipo específico de juego de mesa.
     * Se encarga de parsear los atributos comunes, el estado de disponibilidad y el listado histórico de puntuaciones.
     *
     * @param linea Cadena de texto CSV leída desde el archivo.
     * @return La instancia de JuegoMesa correspondiente bien configurada, o null si la línea tiene errores de formato.
     */
    private JuegoMesa lineaAJuego(String linea) {
        try {
            String[] p = linea.split(",");
            int id = Integer.parseInt(p[0]);
            String titulo = p[1];
            int minJ = Integer.parseInt(p[2]);
            int maxJ = Integer.parseInt(p[3]);
            int dur = Integer.parseInt(p[4]);
            boolean disponible = Boolean.parseBoolean(p[5]);
            String tipo = p[6];
            String extra = p[7];

            JuegoMesa juego = null;
            if (tipo.equals("TABLERO")) {
                juego = new JuegoTablero(id, titulo, minJ, maxJ, dur, Boolean.parseBoolean(extra));
            } else if (tipo.equals("CARTAS")) {
                juego = new JuegoCartas(id, titulo, minJ, maxJ, dur, Integer.parseInt(extra));
            } else if (tipo.equals("DADOS")) {
                juego = new JuegoDados(id, titulo, minJ, maxJ, dur, Integer.parseInt(extra));
            } else if (tipo.equals("ROL")) {
                juego = new JuegoRol(id, titulo, minJ, maxJ, dur, extra);
            }
            
            if (juego != null) {
                juego.setDisponible(disponible);
                
                // Cargar notas si existen en la columna 9
                if (p.length > 8) {
                    String puntuacionesStr = p[8].trim();
                    if (!puntuacionesStr.equals("SIN_NOTAS") && !puntuacionesStr.isEmpty()) {
                        String[] notas = puntuacionesStr.split(";");
                        for (String nota : notas) {
                            juego.agregarPuntuacion(Integer.parseInt(nota));
                        }
                    }
                }
            }
            return juego;
        } catch (Exception e) {
            System.out.println("Error al leer juego: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convierte una instancia de Socio en su representación textual delimitada por comas.
     *
     * @param socio Objeto socio a serializar.
     * @return Cadena de texto estructurada con los campos del socio.
     */
    private String socioALinea(Socio socio) {
        return socio.getId() + "," + socio.getNombre() + ","
                + socio.getEmail() + "," + socio.getTelefono() + ","
                + socio.getNumeroSocio() + "," + socio.getPrestamosActivos();
    }

    /**
     * Parsea una línea de texto CSV para instanciar un objeto Socio con sus campos de contacto y estado.
     *
     * @param linea Cadena de texto extraída del archivo CSV.
     * @return El objeto Socio instanciado, o null en caso de error durante la conversión de formatos.
     */
    private Socio lineaASocio(String linea) {
        try {
            String[] p = linea.split(",");
            Socio socio = new Socio(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]);
            socio.setPrestamosActivos(Integer.parseInt(p[5]));
            return socio;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transforma las propiedades de un préstamo en su línea correspondiente formateada en CSV.
     * Traduce a una cadena "null" si la fecha de devolución real no se ha registrado aún.
     *
     * @param prestamo Instancia del préstamo a procesar.
     * @return Cadena de texto serializada con los IDs relacionados y las fechas del ciclo de vida del préstamo.
     */
    private String prestamoALinea(Prestamo prestamo) {
        String fechaReal = prestamo.getFechaDevolucionReal() == null ? "null" : prestamo.getFechaDevolucionReal().toString();
        return prestamo.getId() + "," + prestamo.getSocio().getId() + ","
                + prestamo.getJuego().getId() + "," + prestamo.getFechaPrestamo() + ","
                + prestamo.getFechaDevolucionPrevista() + "," + fechaReal;
    }

    /**
     * Transforma los campos de planificación de un torneo en su representación equivalente en formato CSV.
     *
     * @param torneo Instancia del torneo a serializar.
     * @return Cadena de texto plana con los campos del torneo y el ID del juego asignado.
     */
    private String torneoALinea(Torneo torneo) {
        return torneo.getId() + "," + torneo.getNombre() + ","
                + torneo.getJuego().getId() + "," + torneo.getFecha() + ","
                + torneo.getMaxParticipantes();
    }
}