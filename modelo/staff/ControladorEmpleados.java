package staff;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import persistencia.CentralPersistencia;
import persistencia.IPersistencia;
import persistencia.PersistenciaJson;

public class ControladorEmpleados implements InterfazUsuarios {
	
	private Map<String, Empleado> empleados;
	
	public ControladorEmpleados( ){
        empleados = new HashMap<String, Empleado>( );
    }
	
	public Collection<Empleado> getEmpleados() {
		return empleados.values();
	}
	
	public void addEmpleado(Empleado empleado) {
		empleados.put(empleado.getNombre(), empleado);
	}
	
	public Empleado getEmpleado(String nombre) {
		return empleados.get(nombre);
	}
	
	public Empleado login(String usuario, String contraseña) {
		Empleado user = empleados.get(usuario);
		if(user.getContraseña().equals(contraseña)) {
			return user;
		}
		return null;
		
	}
	
	public void cargarEmpleados( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		cargador.cargarEmpleados(archivo, this);
    }
	

}
