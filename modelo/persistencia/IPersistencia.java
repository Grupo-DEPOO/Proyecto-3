package persistencia;

import java.io.IOException;

import galeria.Galeria;
import staff.ControladorEmpleados;

public interface IPersistencia {

	public  void cargarEmpleados( String archivo, ControladorEmpleados empleados ) throws IOException;
	public void cargarPiezas(String archivo, Galeria galeria) throws IOException;
	public void cargarCompradores(String archivo, Galeria galeria) throws IOException;
	public void cargarHistoriaPiezas(String archivo, Galeria galeria) throws IOException;
	public void guardarDatosJSON(String archivo, ControladorEmpleados empleados, Galeria galeria) throws IOException;

}
