package staff;

public class Operador extends Empleado {
    private String nombre;
    public static String OPERADOR = "Operador";
    private String contraseña;

    public Operador (String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    @Override
    public String getContraseña() {
		return contraseña;
	}

	@Override
    public String getNombre () {
        return nombre;
    }
    
    @Override 
    public String getTipoEmpleado () {
        return OPERADOR;
    }
}
