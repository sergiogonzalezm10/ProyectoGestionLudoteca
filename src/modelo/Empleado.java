package modelo;

/**
 * Representa a un empleado de la ludoteca.
 *
 * @author Sergio González
 * @version 1.0
 */
public class Empleado extends Persona {

    /** Cargo o puesto del empleado. */
    private String cargo;

    /**
     * Constructor por parámetros.
     *
     * @param id       Identificador del empleado.
     * @param nombre   Nombre completo.
     * @param email    Correo electrónico.
     * @param telefono Teléfono de contacto.
     * @param cargo    Cargo del empleado.
     */
    public Empleado(int id, String nombre, String email,
                    String telefono, String cargo) {
        super(id, nombre, email, telefono);
        this.cargo = cargo;
    }

    /**
     * Constructor de copia.
     *
     * @param otro Empleado a copiar.
     */
    public Empleado(Empleado otro) {
        super(otro);
        this.cargo = otro.cargo;
    }

    @Override
    public String getRol() {
        return "Empleado (" + cargo + ")";
    }

    /** @return El cargo del empleado. */
    public String getCargo() { return cargo; }

    /** @param cargo El nuevo cargo. */
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String toString() {
        return super.toString();
    }
}