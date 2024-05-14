package staff;

public class Cajero extends Empleado {
    public static String CAJERO = "Cajero";
    private String nombre; 
    private String contraseña;
    
    public Cajero(String nombre, String contraseña) {
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
        return CAJERO;
    }
    

}
