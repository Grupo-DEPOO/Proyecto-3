package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Propietario;
import galeria.Galeria;
import staff.ControladorEmpleados;
import staff.Empleado;
import staff.Administrador;
import staff.Operador;
import staff.Cajero;
import piezas.Pieza;
import piezas.PiezaFisica;
import piezas.PiezaVirtual;
import piezas.prePieza;

public class PersistenciaJson implements IPersistencia{
	
	private static final String NOMBRE_EMPLEADO = "nombre";
    private static final String TIPO_EMPLEADO = "tipoEmpleado";
    private static final String ISFISICO = "isFisico";

    public void cargarEmpleados( String archivo, ControladorEmpleados empleados)  throws IOException
    {
	        String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
	        JSONObject raiz = new JSONObject( jsonCompleto );
	
	        crearEmpleados( empleados, raiz.getJSONArray( "empleados" ) );
    }
    
	private void crearEmpleados( ControladorEmpleados empleados, JSONArray jEmpleados ) {
        int numEmpleados = jEmpleados.length( );
        for( int i = 0; i < numEmpleados; i++ )
        {
            JSONObject empleado = jEmpleados.getJSONObject( i );
            String tipoEmpleado = empleado.getString( TIPO_EMPLEADO );
            Empleado nuevoEmpleado = null;
            if( Cajero.CAJERO.equals( tipoEmpleado ) )
            {
                String nombre = empleado.getString( NOMBRE_EMPLEADO );
                String contraseña = empleado.getString("contraseña");
                nuevoEmpleado = new Cajero( nombre , contraseña);
                empleados.addEmpleado(nuevoEmpleado);
            }
            else if(Administrador.ADMINISTRADOR.equals(tipoEmpleado))
            {
            	String nombre = empleado.getString( NOMBRE_EMPLEADO );
            	String contraseña = empleado.getString("contraseña");
            	nuevoEmpleado = new Administrador( nombre, contraseña );
            	empleados.addEmpleado(nuevoEmpleado);
            }
            else if(Operador.OPERADOR.equals(tipoEmpleado))
            {
            	String nombre = empleado.getString( NOMBRE_EMPLEADO );
            	String contraseña = empleado.getString("contraseña");
            	nuevoEmpleado = new Operador( nombre, contraseña );
            	empleados.addEmpleado(nuevoEmpleado);
            }
			
 
        }
    }
	
	public void cargarPiezas( String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        crearPiezas( galeria, raiz.getJSONArray( "piezas" ) );
    }
	
	public void crearPiezas(Galeria galeria, JSONArray jPiezas) {
		int numPiezas = jPiezas.length( );
        for( int i = 0; i < numPiezas; i++ )
        {
            JSONObject pieza = jPiezas.getJSONObject( i );
            boolean isFisico = pieza.getBoolean(ISFISICO);
            Pieza nuevaPieza = null;
            if( PiezaFisica.ISFISICO == isFisico)
            {
                String titulo = pieza.getString( "titulo" );
                String anio = pieza.getString( "anio" );
                String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                String autor = pieza.getString( "autor" );
                boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                String tipo = pieza.getString( "tipo" );
                double profundidad = pieza.getDouble( "profundidad" );
                double alto = pieza.getDouble( "alto" );
                double ancho = pieza.getDouble( "ancho" );
                double peso = pieza.getDouble( "peso" );
                boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
                boolean needsOther = pieza.getBoolean( "needsOther" );
                nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
                galeria.addPiezas(nuevaPieza);
                if(isPrecioFijo == true) {
                	int precio = pieza.getInt("precio");
                	String idComprador = pieza.getString("idComprador");
                	PiezaConPrecioFijo nuevaPiezaPrecio = new PiezaConPrecioFijo(precio, nuevaPieza, idComprador);
                	galeria.addPiezasFijas(nuevaPiezaPrecio);
                } else {
                	float valorMinimo = pieza.getFloat("valorMinimo");
                	float valorInicial = pieza.getFloat("valorInicial");
                	String estado = pieza.getString("estado");
                	Map<String, Oferta> ofertas = new HashMap<String, Oferta>();
                	JSONArray jPujadores = pieza.getJSONArray("ofertas");
                    for (int j = 0; j < jPujadores.length(); j++) {
                        JSONObject pujador = jPujadores.getJSONObject(j);
                        String idComprador = pujador.getString("idComprador");
                        int valor = pujador.getInt("valor");
                        Oferta oferta = new Oferta(valor, galeria.getComprador(idComprador), nuevaPieza, "enSubasta");
                        ofertas.put(idComprador, oferta);
                        
                    }
                	PiezaEnSubasta nuevaPiezaSubasta = new PiezaEnSubasta(valorMinimo, valorInicial, estado, nuevaPieza,  ofertas);
                	galeria.addPiezasSubasta(nuevaPiezaSubasta);
                }
            }
            else if(PiezaVirtual.ISFISICO == isFisico)
            {
            	String titulo = pieza.getString( "titulo" );
                String anio = pieza.getString( "anio" );
                String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                String autor = pieza.getString( "autor" );
                boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                String tipo = pieza.getString( "tipo" );
                String formato = pieza.getString( "formato" );
            	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
            	if(isPrecioFijo == true) {
                	int precio = pieza.getInt("precio");
                	String idComprador = pieza.getString("idComprador");
                	PiezaConPrecioFijo nuevaPiezaPrecio = new PiezaConPrecioFijo(precio, nuevaPieza, idComprador);
                	galeria.addPiezasFijas(nuevaPiezaPrecio);
                } else {
                	float valorMinimo = pieza.getFloat("valorMinimo");
                	float valorInicial = pieza.getFloat("valorInicial");
                	String estado = pieza.getString("estado");
                	HashMap<String, Oferta> ofertas = new HashMap<String, Oferta>();
                	JSONArray jPujadores = pieza.getJSONArray("ofertas");
                    for (int j = 0; j < jPujadores.length(); j++) {
                        JSONObject pujador = jPujadores.getJSONObject(j);
                        String idComprador = pujador.getString("idComprador");
                        int valor = pujador.getInt("valor");
                        Oferta oferta = new Oferta(valor, galeria.getComprador(idComprador), nuevaPieza, "enSubasta");
                        ofertas.put(idComprador, oferta);
                    }
                	PiezaEnSubasta nuevaPiezaSubasta = new PiezaEnSubasta(valorMinimo, valorInicial, estado, nuevaPieza,  ofertas);
                	galeria.addPiezasSubasta(nuevaPiezaSubasta);
                }
            }
        }
	}
	public void cargarCompradores(String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        crearCompradores( galeria, raiz.getJSONArray( "compradores" ) );
	}
	private void crearCompradores( Galeria galeria, JSONArray jCompradores) {
        int numCompradores = jCompradores.length( );
        for( int i = 0; i < numCompradores; i++ )
        {
            JSONObject comprador = jCompradores.getJSONObject( i );
            Comprador nuevoComprador = null;
            Propietario nuevoPropietario = null;
            String nombre = comprador.getString( "nombre" );
            int capital = comprador.getInt("capital");
            boolean isVerificado = comprador.getBoolean("isVerificado");
            String metodoPago = comprador.getString("metodo-pago");
            int topeCompras = comprador.getInt("tope-compras");
            String contraseña = comprador.getString("contraseña");
            
            
            JSONArray jPiezas = comprador.getJSONArray("piezas");
            if(jPiezas.length() > 0) {
            	Map<String, Pieza> piezasPropietario = new HashMap<String, Pieza>();
            	int valorColeccion = comprador.getInt("valor_coleccion");
            	for (int j = 0; j < jPiezas.length(); j++) {
                    JSONObject pieza = jPiezas.getJSONObject(j);
                    boolean isFisico = pieza.getBoolean(ISFISICO);
                    Pieza nuevaPieza = null;
                    if( PiezaFisica.ISFISICO == isFisico)
                    {
                        String titulo = pieza.getString( "titulo" );
                        String anio = pieza.getString( "anio" );
                        String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                        String autor = pieza.getString( "autor" );
                        boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                        String tipo = pieza.getString( "tipo" );
                        double profundidad = pieza.getDouble( "profundidad" );
                        double alto = pieza.getDouble( "alto" );
                        double ancho = pieza.getDouble( "ancho" );
                        double peso = pieza.getDouble( "peso" );
                        boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
                        boolean needsOther = pieza.getBoolean( "needsOther" );
                        nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
                        piezasPropietario.put(titulo, nuevaPieza);
                    }
                    else if(PiezaVirtual.ISFISICO == isFisico)
                    {
                    	String titulo = pieza.getString( "titulo" );
                        String anio = pieza.getString( "anio" );
                        String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                        String autor = pieza.getString( "autor" );
                        boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                        String tipo = pieza.getString( "tipo" );
                        String formato = pieza.getString( "formato" );
                    	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
                    	piezasPropietario.put(titulo, nuevaPieza);
                    }
                }
            	nuevoPropietario = new Propietario(nombre, piezasPropietario, valorColeccion);
            	galeria.addPropietario(nuevoPropietario);
            }
            
            Map<String, Pieza> piezasCompradas = new HashMap<String, Pieza>();
            JSONArray jPiezasCompradas = comprador.getJSONArray("compradas");
            if(jPiezasCompradas.length() > 0) {
            	for (int j = 0; j < jPiezasCompradas.length(); j++) {
	            	JSONObject pieza = jPiezasCompradas.getJSONObject(j);
	                boolean isFisico = pieza.getBoolean(ISFISICO);
	                Pieza nuevaPieza = null;
	                if( PiezaFisica.ISFISICO == isFisico)
	                {
	                    String titulo = pieza.getString( "titulo" );
	                    String anio = pieza.getString( "anio" );
	                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
	                    String autor = pieza.getString( "autor" );
	                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
	                    String tipo = pieza.getString( "tipo" );
	                    double profundidad = pieza.getDouble( "profundidad" );
	                    double alto = pieza.getDouble( "alto" );
	                    double ancho = pieza.getDouble( "ancho" );
	                    double peso = pieza.getDouble( "peso" );
	                    boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
	                    boolean needsOther = pieza.getBoolean( "needsOther" );
	                    nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
	                    piezasCompradas.put(pieza.getString("key"), nuevaPieza);
	                }
	                else if(PiezaVirtual.ISFISICO == isFisico)
	                {
	                	String titulo = pieza.getString( "titulo" );
	                    String anio = pieza.getString( "anio" );
	                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
	                    String autor = pieza.getString( "autor" );
	                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
	                    String tipo = pieza.getString( "tipo" );
	                    String formato = pieza.getString( "formato" );
	                	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
	                	piezasCompradas.put(pieza.getString("key"), nuevaPieza);
	                }
	            }
            }
            
            nuevoComprador = new Comprador(isVerificado, nombre,  metodoPago, capital, topeCompras, contraseña, piezasCompradas);
            galeria.addComprador(nuevoComprador);
        }
    }
	
	public void cargarHistoriaPiezas(String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );
        
        crearHistoriaPiezas( galeria, raiz.getJSONArray( "allPiezas" ) );
	}
	
	private void crearHistoriaPiezas( Galeria galeria, JSONArray jAllPiezas) {
		int numPiezas = jAllPiezas.length( );
		
		 for( int i = 0; i < numPiezas; i++ ){
	            JSONObject pieza = jAllPiezas.getJSONObject( i );
	            prePieza nuevaPieza = null;
	            
	            String titulo = pieza.getString( "titulo" );
	            String anio = pieza.getString( "anio" );
	            String lugar = pieza.getString( "LugarDeCreacion" );
	            String autor = pieza.getString( "autor" );
	            boolean isFisico = pieza.getBoolean( "isFisico" );
	            String dueños = pieza.getString("dueños");
	            String ultimaVenta = pieza.getString("ultimaVenta");
	            nuevaPieza = new prePieza(titulo, anio, lugar, autor, isFisico, dueños, ultimaVenta);
	            galeria.addPrePieza(nuevaPieza);
	            galeria.addAutor(autor, titulo);
	            
	     }
		
	}
	
	public JSONArray guardarPiezas(Galeria galeria) throws IOException {

	    JSONObject jsonRaiz = new JSONObject();

	    JSONArray jsonArrayPiezas = new JSONArray();

	    for (PiezaEnSubasta piezaEnSubasta : galeria.getPiezasEnSubasta().values()) {
	        JSONObject jsonPieza = new JSONObject();
	        Pieza pieza = piezaEnSubasta.getPieza();
	        jsonPieza.put("titulo", pieza.getTitulo());
	        jsonPieza.put("anio", pieza.getAnio());
	        jsonPieza.put("LugarDeCreacion", pieza.getLugarDeCreacion());
	        jsonPieza.put("autor", pieza.getAutor());
	        jsonPieza.put("isFisico", pieza.isFisico());
	        jsonPieza.put("precioFijo", false);
	        jsonPieza.put("valorMinimo", piezaEnSubasta.getValorMinimo());
	        jsonPieza.put("valorInicial", piezaEnSubasta.getValorInicial());
	        jsonPieza.put("estado", piezaEnSubasta.getEstado());
	        JSONArray Ofertas = new JSONArray();
	        for(Oferta oferta: piezaEnSubasta.getOfertas().values()) {
	        	JSONObject jsonOferta = new JSONObject();
	        	jsonOferta.put("idComprador", oferta.getComprador().getNombre());
	        	jsonOferta.put("valor", oferta.getValor());
	        	Ofertas.put(jsonOferta);
	        }
	        jsonPieza.put("ofertas", Ofertas);
	        if( pieza.isFisico() == true) {
	        	PiezaFisica piezaF = (PiezaFisica)pieza;
	        	jsonPieza.put("tipo", piezaF.getTipo());
	        	jsonPieza.put("alto", piezaF.getAlto());
	        	jsonPieza.put("ancho", piezaF.getAncho());
	        	jsonPieza.put("Materiales de construccion", "");
	        	jsonPieza.put("profundidad", piezaF.getProfundidad());
	        	jsonPieza.put("peso", piezaF.getPeso());
	        	jsonPieza.put("needsElectricity", piezaF.needsElectricity());
	        	jsonPieza.put("needsOther", piezaF.needsOther());
	        }
	        else {
	        	PiezaVirtual piezaV = (PiezaVirtual)pieza;
	        	jsonPieza.put("tipo", piezaV.getTipo());
	        	jsonPieza.put("formato", piezaV.getFormato());
	        }
	        jsonArrayPiezas.put(jsonPieza);
	    }
	    
	    for( PiezaConPrecioFijo piezaOferta : galeria.getEnOfertaFija() ) {
	    	JSONObject jsonPieza = new JSONObject();
	        Pieza pieza = piezaOferta.getPieza();
	        jsonPieza.put("titulo", pieza.getTitulo());
	        jsonPieza.put("anio", pieza.getAnio());
	        jsonPieza.put("LugarDeCreacion", pieza.getLugarDeCreacion());
	        jsonPieza.put("autor", pieza.getAutor());
	        jsonPieza.put("isFisico", pieza.isFisico());
	        jsonPieza.put("precioFijo", true);
	        jsonPieza.put("idComprador", piezaOferta.getIdComprador());
	        jsonPieza.put("precio", piezaOferta.getPrecio());
	        if( pieza.isFisico() == true) {
	        	PiezaFisica piezaF = (PiezaFisica)pieza;
	        	jsonPieza.put("tipo", piezaF.getTipo());
	        	jsonPieza.put("alto", piezaF.getAlto());
	        	jsonPieza.put("ancho", piezaF.getAncho());
	        	jsonPieza.put("Materiales de construccion", "");
	        	jsonPieza.put("profundidad", piezaF.getProfundidad());
	        	jsonPieza.put("peso", piezaF.getPeso());
	        	jsonPieza.put("needsElectricity", piezaF.needsElectricity());
	        	jsonPieza.put("needsOther", piezaF.needsOther());
	        }
	        else {
	        	PiezaVirtual piezaV = (PiezaVirtual)pieza;
	        	jsonPieza.put("tipo", piezaV.getTipo());
	        	jsonPieza.put("formato", piezaV.getFormato());
	        }
	        jsonArrayPiezas.put(jsonPieza);
	    }

	    jsonRaiz.put("piezas", jsonArrayPiezas);
	    
	    return jsonArrayPiezas;

	}
	
	public JSONArray guardarCompradores(Galeria galeria) throws IOException {

	    JSONObject jsonRaiz = new JSONObject();

	    JSONArray jsonArrayCompradores = new JSONArray();

	   for(Comprador comprador: galeria.getCompradores()) {
		   JSONObject jsonComprador = new JSONObject();
		   jsonComprador.put("nombre", comprador.getNombre());
		   jsonComprador.put("capital", comprador.getCapital());
		   jsonComprador.put("isVerificado", comprador.isVerificado());
		   jsonComprador.put("metodo-pago", comprador.getMetodoPago());
		   jsonComprador.put("tope-compras", comprador.getTopeDeCompras());
		   JSONArray Compradas = new JSONArray();
		   if(comprador.getPiezasCompradas().isEmpty() == false) {
			   for (Map.Entry<String, Pieza> entry : comprador.getPiezasCompradas().entrySet()) {
				   String clave = entry.getKey(); // Obtener la clave
				   Pieza pieza = entry.getValue();
				   JSONObject jsonPieza = new JSONObject();
				   jsonPieza.put("key", clave);
			        jsonPieza.put("titulo", pieza.getTitulo());
			        jsonPieza.put("anio", pieza.getAnio());
			        jsonPieza.put("LugarDeCreacion", pieza.getLugarDeCreacion());
			        jsonPieza.put("autor", pieza.getAutor());
			        jsonPieza.put("isFisico", pieza.isFisico());
			        jsonPieza.put("precioFijo", false);
			        if( pieza.isFisico() == true) {
			        	PiezaFisica piezaF = (PiezaFisica)pieza;
			        	jsonPieza.put("tipo", piezaF.getTipo());
			        	jsonPieza.put("alto", piezaF.getAlto());
			        	jsonPieza.put("ancho", piezaF.getAncho());
			        	jsonPieza.put("profundidad", piezaF.getProfundidad());
			        	jsonPieza.put("Materiales de construccion", "");
			        	jsonPieza.put("peso", piezaF.getPeso());
			        	jsonPieza.put("needsElectricity", piezaF.needsElectricity());
			        	jsonPieza.put("needsOther", piezaF.needsOther());
			        }
			        else {
			        	PiezaVirtual piezaV = (PiezaVirtual)pieza;
			        	jsonPieza.put("tipo", piezaV.getTipo());
			        	jsonPieza.put("formato", piezaV.getFormato());
			        }
			        Compradas.put(jsonPieza);
			   }
		   }
		   jsonComprador.put("compradas", Compradas);
		   JSONArray propiedad = new JSONArray();
		   if(galeria.getPropietarios().containsKey(comprador.getNombre())) {
			   Propietario propetario = galeria.getPropietario(comprador.getNombre());
			   for(Pieza pieza : propetario.getPiezas().values()) {
				   JSONObject jsonPieza = new JSONObject();
			        jsonPieza.put("titulo", pieza.getTitulo());
			        jsonPieza.put("anio", pieza.getAnio());
			        jsonPieza.put("LugarDeCreacion", pieza.getLugarDeCreacion());
			        jsonPieza.put("autor", pieza.getAutor());
			        jsonPieza.put("isFisico", pieza.isFisico());
			        jsonPieza.put("precioFijo", false);
			        if( pieza.isFisico() == true) {
			        	PiezaFisica piezaF = (PiezaFisica)pieza;
			        	jsonPieza.put("tipo", piezaF.getTipo());
			        	jsonPieza.put("alto", piezaF.getAlto());
			        	jsonPieza.put("ancho", piezaF.getAncho());
			        	jsonPieza.put("profundidad", piezaF.getProfundidad());
			        	jsonPieza.put("Materiales de construccion", "");
			        	jsonPieza.put("peso", piezaF.getPeso());
			        	jsonPieza.put("needsElectricity", piezaF.needsElectricity());
			        	jsonPieza.put("needsOther", piezaF.needsOther());
			        }
			        else {
			        	PiezaVirtual piezaV = (PiezaVirtual)pieza;
			        	jsonPieza.put("tipo", piezaV.getTipo());
			        	jsonPieza.put("formato", piezaV.getFormato());
			        }
			        propiedad.put(jsonPieza);
			   }
			   
		   }
		jsonComprador.put("piezas", propiedad);
		if (galeria.getPropietarios().containsKey(comprador.getNombre())) {
			jsonComprador.put("valor_coleccion", galeria.getPropietario(comprador.getNombre()).getValorColeccion());
		} else {
			jsonComprador.put("valor_coleccion", 0);
		}
		jsonComprador.put("contraseña", comprador.getContraseña());
		
		jsonArrayCompradores.put(jsonComprador);

	   }
	   
	   return jsonArrayCompradores;

	}
	
	public JSONArray guardarAllPiezas(Galeria galeria) throws IOException {
		
		JSONObject jsonRaiz = new JSONObject();

	    JSONArray jsonArrayHistoria = new JSONArray();
	    
	    for(prePieza pieza: galeria.getHistoriaPre()) {
	    	JSONObject jsonPieza = new JSONObject();
	    	jsonPieza.put("titulo", pieza.getTitulo());
	    	jsonPieza.put("anio", pieza.getAnio());
	    	jsonPieza.put("LugarDeCreacion", pieza.getLugarDeCreacion());
	    	jsonPieza.put("autor", pieza.getAutor());
	    	jsonPieza.put("isFisico", pieza.isFisico());
	    	jsonPieza.put("dueños", pieza.getDueños());
	    	jsonPieza.put("ultimaVenta", pieza.getValorUltimaVenta());
	    	jsonArrayHistoria.put(jsonPieza);
	    }
	    jsonRaiz.put("allPiezas", jsonArrayHistoria);
	    
	    return jsonArrayHistoria;

		
	}
	
	public JSONArray guardarEmpleados(ControladorEmpleados empleados) throws IOException {
		
		JSONObject jsonRaiz = new JSONObject();

	    JSONArray jsonArrayEmpleados = new JSONArray();
	    
	    for(Empleado empleado: empleados.getEmpleados()) {
	    	JSONObject jsonEmpleado = new JSONObject();
	    	jsonEmpleado.put("nombre", empleado.getNombre());
	    	jsonEmpleado.put("tipoEmpleado", empleado.getTipoEmpleado());
	    	jsonEmpleado.put("contraseña", empleado.getContraseña());
	    	jsonArrayEmpleados.put(jsonEmpleado);
	    }
	    jsonRaiz.put("empleados", jsonArrayEmpleados);

	    return jsonArrayEmpleados;
		
	}
	
	public void guardarDatosJSON(String rutaArchivo, ControladorEmpleados empleados, Galeria galeria) throws IOException {
        // Utilizar FileWriter para escribir el contenido en el archivo JSON
		
		JSONObject jsonRaiz = new JSONObject();
		
		JSONArray jsonArrayEmpleados = guardarEmpleados(empleados);
		JSONArray jsonArrayAllPiezas = guardarAllPiezas(galeria);
		JSONArray jsonArrayCompradores = guardarCompradores(galeria);
		JSONArray jsonArrayPiezas = guardarPiezas(galeria);
		jsonRaiz.put("empleados", jsonArrayEmpleados);
		jsonRaiz.put("compradores", jsonArrayCompradores);
		jsonRaiz.put("piezas", jsonArrayPiezas);
		jsonRaiz.put("allPiezas", jsonArrayAllPiezas);
		
        try (FileWriter archivo = new FileWriter(rutaArchivo)) {
            archivo.write(jsonRaiz.toString());
        }
    }
	
	/*public void cargarPropietarios(String archivo, Galeria galeria) throws IOException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );
        
        crearPropietarios( galeria, raiz.getJSONArray( "propietarios" ) );
	}

	private void crearPropietarios( Galeria galeria, JSONArray jPropietarios) {
        int numPropietarios = jPropietarios.length( );
        for( int i = 0; i < numPropietarios; i++ )
        {
            JSONObject propietario = jPropietarios.getJSONObject( i );
            Propietario nuevoPropietario = null;
            String nombre = propietario.getString( "nombre" );
            String numero = propietario.getString("numero");
            String correo = propietario.getString("correo");
            Map<String, Pieza> piezasPropietario = new HashMap<String, Pieza>();
            JSONArray jPiezas = propietario.getJSONArray("piezas");
            for (int j = 0; j < jPiezas.length(); j++) {
                JSONObject pieza = jPiezas.getJSONObject(j);
                boolean isFisico = pieza.getBoolean(ISFISICO);
                Pieza nuevaPieza = null;
                if( PiezaFisica.ISFISICO == isFisico)
                {
                    String titulo = pieza.getString( "titulo" );
                    String anio = pieza.getString( "anio" );
                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                    String autor = pieza.getString( "autor" );
                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                    String tipo = pieza.getString( "tipo" );
                    double profundidad = pieza.getDouble( "profundidad" );
                    double alto = pieza.getDouble( "alto" );
                    double ancho = pieza.getDouble( "ancho" );
                    double peso = pieza.getDouble( "peso" );
                    boolean needsElectricity = pieza.getBoolean( "needsElectricity" );
                    boolean needsOther = pieza.getBoolean( "needsOther" );
                    nuevaPieza = new PiezaFisica(tipo, titulo, anio, lugarDeCreacion, autor, isPrecioFijo, profundidad,  alto, ancho, peso, needsElectricity, needsOther);
                    piezasPropietario.put(titulo, nuevaPieza);
                }
                else if(PiezaVirtual.ISFISICO == isFisico)
                {
                	String titulo = pieza.getString( "titulo" );
                    String anio = pieza.getString( "anio" );
                    String lugarDeCreacion = pieza.getString( "LugarDeCreacion" );
                    String autor = pieza.getString( "autor" );
                    boolean isPrecioFijo = pieza.getBoolean( "precioFijo" );
                    String tipo = pieza.getString( "tipo" );
                    String formato = pieza.getString( "formato" );
                	nuevaPieza = new PiezaVirtual(tipo, formato, titulo,  anio,  lugarDeCreacion, autor, isPrecioFijo);
                	piezasPropietario.put(titulo, nuevaPieza);
                }
            }
            nuevoPropietario = new Propietario(nombre, numero, correo, piezasPropietario);
            galeria.addPropietario(nuevoPropietario);
        }
    }*/

	/*private void salvarEmpleados( ControladorEmpleados empleados, JSONObject jobject )
    {
        JSONArray jEmpleados = new JSONArray( );
        for( Empleado empleado : aerolinea.getClientes( ) )
        {
            // Acá también se utilizaron dos estrategias para salvar los clientes.
            // Para los clientes naturales, esta clase extrae la información de los objetos y la organiza para que luego sea salvada.
            // Para los clientes corporativos, la clase ClienteCorporativo hace todo lo que está en sus manos para persistir un cliente
            if( ClienteNatural.NATURAL.equals( cliente.getTipoCliente( ) ) )
            {
                JSONObject jCliente = new JSONObject( );
                jCliente.put( NOMBRE_CLIENTE, cliente.getIdentificador( ) );
                jClientes.put( jCliente );
            }
            else
            {
                ClienteCorporativo cc = ( ClienteCorporativo )cliente;
                JSONObject jCliente = cc.salvarEnJSON( );
                jClientes.put( jCliente );
            }
        }

        jobject.put( "clientes", jClientes );
    }*/
}
