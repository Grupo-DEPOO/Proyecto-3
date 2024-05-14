package persistencia;

import staff.ControladorEmpleados;

public class CentralPersistencia {
	
	public static final String JSON = "JSON";
	
	
	public static IPersistencia getPersistencia(  )
    {
            return new PersistenciaJson( );
    }

}
