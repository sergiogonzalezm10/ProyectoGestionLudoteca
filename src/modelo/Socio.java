package modelo;

/**
 * Representa a un socio registrado en la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Socio extends Persona {

    /** Número de socio asignado al registrarse. */
    private String numeroSocio;

    /** Número de préstamos activos que tiene el socio actualmente. */
    private int prestamosActivos;

    /**
     * Constructor por parámetros.
     *
     * @param id           Identificador del socio.
     * @param nombre       Nombre completo.
     * @param email        Correo electrónico.
     * @param telefono     Teléfono de contacto.
     * @param numeroSocio  Número de socio.
     */
    public Socio(int id, String nombre, String email,
                 String telefono, String numeroSocio) {
        super(id, nombre, email, telefono);
        this.numeroSocio = numeroSocio;
        this.prestamosActivos = 0;
    }

    /**
     * Constructor de copia.
     *
     * @param otro Socio a copiar.
     */
    public Socio(Socio otro) {
        super(otro);
        this.numeroSocio = otro.numeroSocio;
        this.prestamosActivos = otro.prestamosActivos;
    }

    @Override
    public String getRol() {
        return "Socio";
    }

    /** @return El número de socio. */
    public String getNumeroSocio() { return numeroSocio; }

    /** @param numeroSocio El nuevo número de socio. */
    public void setNumeroSocio(String numeroSocio) { this.numeroSocio = numeroSocio; }

    /** @return El número de préstamos activos. */
    public int getPrestamosActivos() { return prestamosActivos; }

    /** @param prestamosActivos El nuevo número de préstamos activos. */
    public void setPrestamosActivos(int prestamosActivos) {
        this.prestamosActivos = prestamosActivos;
    }

    @Override
    public String toString() {
        return super.toString() + " | Nº socio: " + numeroSocio
                + " | Préstamos activos: " + prestamosActivos;
    }
}