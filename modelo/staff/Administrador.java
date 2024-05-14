package staff;

public class Administrador extends Empleado {
    private String nombre;
    public static String ADMINISTRADOR = "Administrador";
    private String contraseña;

    @Override
    public String getContraseña() {
		return contraseña;
	}

	public Administrador (String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }
    
    @Override
    public String getNombre () {
        return nombre;
    }

    @Override
    public String getTipoEmpleado() {
        return ADMINISTRADOR;
    }
    

}
