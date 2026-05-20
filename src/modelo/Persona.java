package modelo;

/**
 * Clase abstracta que representa a cualquier persona del sistema.
 *
 * @author Sergio González
 * @version 1.0
 */
public abstract class Persona {

    /** Identificador único de la persona. */
    private int id;

    /** Nombre completo de la persona. */
    private String nombre;

    /** Correo electrónico de contacto. */
    private String email;

    /** Teléfono de contacto. */
    private String telefono;

    /**
     * Constructor por parámetros.
     *
     * @param id       Identificador de la persona.
     * @param nombre   Nombre completo.
     * @param email    Correo electrónico.
     * @param telefono Teléfono de contacto.
     */
    public Persona(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Constructor de copia.
     *
     * @param otra Persona a copiar.
     */
    public Persona(Persona otra) {
        this.id = otra.id;
        this.nombre = otra.nombre;
        this.email = otra.email;
        this.telefono = otra.telefono;
    }

    /**
     * Devuelve el rol de la persona dentro del sistema.
     *
     * @return Cadena con el rol de la persona.
     */
    public abstract String getRol();

    /** @return El identificador de la persona. */
    public int getId() { return id; }

    /** @return El nombre completo. */
    public String getNombre() { return nombre; }

    /** @param nombre El nuevo nombre. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return El correo electrónico. */
    public String getEmail() { return email; }

    /** @param email El nuevo correo electrónico. */
    public void setEmail(String email) { this.email = email; }

    /** @return El teléfono de contacto. */
    public String getTelefono() { return telefono; }

    /** @param telefono El nuevo teléfono. */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | " + getRol() + " | " + email + " | " + telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona p = (Persona) o;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}