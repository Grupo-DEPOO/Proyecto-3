package consola;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorCompradores;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import galeria.Galeria;
import persistencia.CentralPersistencia;
import persistencia.IPersistencia;
import staff.ControladorEmpleados;

public class ConsolaComprador {

    private ControladorEmpleados unosEmpleados;
    private ControladorOfertasFijas ofertas;
    private ControladorSubasta subastasP;
    private Galeria galeria;
    private ControladorCompradores compradores1;	
	
	
	public void cargarDatos() {
    	try
        {
            unosEmpleados = new ControladorEmpleados( );
            galeria = new Galeria();
            unosEmpleados.cargarEmpleados("./datos/datos2.json");
            galeria.cargarDatos("./datos/datos2.json");
            subastasP = new ControladorSubasta(galeria);
            ofertas = new ControladorOfertasFijas(galeria);
            compradores1 = new ControladorCompradores(galeria);
            ofertas.cargarOfertas();
        } catch( IOException e )
        {
            e.printStackTrace();
        }
    	
    }
	
	 public void correrAplicacion( ) {
	        Scanner myObj = new Scanner(System.in);
	        boolean continuar = true;

	        while (continuar) {
	        	System.out.println("1. Iniciar sesion\n"
	                    + "2. guardar\n" + "3. Salir\n" );
	            String decision = myObj.nextLine();

	            switch (decision) {
	                case "1":
	                	probarComprador();
	                    break;
	                case "2":
	                	salvarDatos();
	                    break; 
	                case "3":
	                	 continuar = false;
		                 System.out.println("Saliendo de la aplicación...");
		                 break;
	                   
	                default:
	                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
	                    break;
	            }
	        }
	    }
	 
	 
	 public void salvarDatos() {
	    	try
	        {
	    		IPersistencia salvador = CentralPersistencia.getPersistencia();
	    		salvador.guardarDatosJSON("./datos/datos2.json", unosEmpleados, galeria);
	    		
	    		
	        }catch( IOException e )
	        {
	            e.printStackTrace();
	        }
	    }
	
	public void probarComprador() {
        try {
            
            Scanner myObj = new Scanner(System.in);
            System.out.println("Digite su usuario: ");
            String usuario = myObj.nextLine();
            Scanner myObj2 = new Scanner(System.in);
            System.out.println("Digite su contraseña: ");
            String contraseña = myObj2.nextLine();
            Comprador comprador = compradores1.login(usuario, contraseña);
            funcionalidadesComprador(comprador);
        } catch (Exception e) {
            System.out.println("Error al iniciar sesion: " + e.getMessage());
            // Aquí puedes decidir si deseas manejar el error de alguna manera específica o simplemente continuar con la ejecución del programa.
        }
    }
    
    public void funcionalidadesComprador(Comprador comprador) {
    	Scanner myObj = new Scanner(System.in);
    	System.out.println("1. Hacer una oferta a una pieza\n2. Hacer una oferta a una subasta\n3. Consultar historia pieza\n4. Consultar historia artista");
    	String decision = myObj.nextLine();
    	if(decision.compareTo("1") == 0) {
    		System.out.println("Piezas a oferta fija disponibles: ");
    		Collection<PiezaConPrecioFijo> piezasAoferta = galeria.getEnOfertaFija(); 
    		for (PiezaConPrecioFijo piezaConPrecioFijo : piezasAoferta) {
    			System.out.println("-------------------------");
    			System.out.println("Comprador: " + piezaConPrecioFijo.getIdComprador());
    			System.out.println("Pieza: " + piezaConPrecioFijo.getPieza().getTitulo());
    			System.out.println("Precio: " + piezaConPrecioFijo.getPrecio());
    			System.out.println("-------------------------");
				
			}
    		Scanner myObj2 = new Scanner(System.in);
	    	System.out.println("Digite el nombre de la pieza que desea hacer una oferta: ");
	    	String id = myObj2.nextLine();
	    	Scanner myObj3 = new Scanner(System.in);
	    	System.out.println("Digite el dinero que desea ofertar recuerde que debe ofrecer mas del precio de la pieza: ");
	    	int valor = myObj3.nextInt();
	    	ofertas.ofertarPieza(comprador, valor, id);
    	} else if(decision.compareTo("2") == 0) {
    		System.out.println("Subasta disponibles: ");
    		Collection<PiezaEnSubasta> subastas = galeria.getPiezasEnSubasta().values();
    		for (PiezaEnSubasta piezaEnSubasta : subastas) {
    			if(piezaEnSubasta.getEstado().compareTo("disponible") == 0) {
	    			System.out.println("-------------------------");
	    			System.out.println("Valor inicial: " + piezaEnSubasta.getValorInicial());
	    			System.out.println("Estado: " + piezaEnSubasta.getEstado());
	    			System.out.println("Pieza: " + piezaEnSubasta.getPieza().getTitulo());
	    			System.out.println("-------------------------");
    			}
			}
    		
    		Scanner myObj2 = new Scanner(System.in);
	    	System.out.println("Digite el nombre de la pieza que desea hacer una oferta: ");
	    	String id = myObj2.nextLine();
	    	Scanner myObj3 = new Scanner(System.in);
	    	System.out.println("Digite el dinero que desea ofertar recuerde que debe ofrecer mas del precio de la pieza: ");
	    	int valor = myObj3.nextInt();
	    	subastasP.ofertarPieza(comprador, valor, id);
    	} else if(decision.compareTo("3") == 0) {
    		System.out.println("Digite el nombre de la pieza: ");
            String id = myObj.nextLine();
            String historia = galeria.getHistoriaPieza(id);
            System.out.println(historia);
            
    	} else if(decision.compareTo("4") == 0) {
    		System.out.println("Digite el nombre del artista: ");
            String id = myObj.nextLine();
            String historia = galeria.getHistoriaAutor(id);
            System.out.println(historia);
            
    	}
    	
    }
    
    public static void main( String[] args )
    {   
    	ConsolaComprador ca = new ConsolaComprador( );
        ca.cargarDatos();
        ca.correrAplicacion();
    }
    

}
