package galeria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Propietario;
import persistencia.CentralPersistencia;
import persistencia.IPersistencia;
import piezas.Pieza;
import piezas.prePieza;
import staff.Empleado;

public class Galeria {
	
	private Map<String, PiezaConPrecioFijo> piezasOfertasFijas;
	private Map<String, PiezaEnSubasta> piezasSubasta;
	private Map<String, Pieza> inventario; 
	private Map<String, Comprador> compradores; 
	private Map<String, Propietario> propietarios; 
	private Map<String, prePieza> prePiezas;
	private Map<String, List<String>> autores;

	public Galeria() {
		inventario = new HashMap<String, Pieza>( );
		piezasOfertasFijas = new HashMap<String, PiezaConPrecioFijo>( );
		piezasSubasta = new HashMap<String, PiezaEnSubasta>( );
		compradores = new HashMap<String, Comprador>( );
		propietarios = new HashMap<String, Propietario>( );
		prePiezas = new HashMap<String, prePieza>( );
		autores = new HashMap<String, List<String>>();
	}
	
	public void addPiezas(Pieza pieza) {
		inventario.put(pieza.getTitulo(), pieza);
	}
	
	public Collection<Pieza> getInventario() {
		return inventario.values();
	}
	
	public void deletePiezaInventario(String id) {
		inventario.remove(id);
	}
	
	public void addPiezasSubasta(PiezaEnSubasta pieza) {
		String id = pieza.getPieza().getTitulo();
		piezasSubasta.put(id, pieza);
	}
	
	
	public Map<String, PiezaEnSubasta> getPiezasEnSubasta() {
		return piezasSubasta;
	}
	
	public PiezaEnSubasta getPiezaEnSubasta(String id) {
		return piezasSubasta.get(id);
	}
	
	public void addPiezasFijas(PiezaConPrecioFijo pieza) {
		String id = pieza.getPieza().getTitulo();
		piezasOfertasFijas.put(id, pieza);
	}
	
	public Map<String, PiezaConPrecioFijo> getPiezasEnOfertaFija() {
		return piezasOfertasFijas;
	}
	
	public Collection<PiezaConPrecioFijo> getEnOfertaFija() {
		return piezasOfertasFijas.values();
	}
	
	public PiezaConPrecioFijo getPiezaEnOfertaFija(String id) {
		return piezasOfertasFijas.get(id);
	}
	
	
	public void addComprador(Comprador comprador) {
		String id = comprador.getNombre();
		compradores.put(id, comprador);
	}
	
	public Collection<Comprador> getCompradores() {
		return compradores.values();
	}
	
	public Comprador getComprador(String id) {
		return compradores.get(id);
	}
	
	public void addPropietario(Propietario propietario) {
		String id = propietario.getNombre();
		propietarios.put(id, propietario);
	}
	
	public Map<String, Propietario> getPropietarios() {
		return propietarios;
	}
	
	public Propietario getPropietario(String id) {
		return propietarios.get(id);
	}
	
	public Map<String, prePieza> getPrePiezas() {
		return prePiezas;
	}
	
	public void addPrePieza(prePieza pieza) {
		String id = pieza.getTitulo();
		prePiezas.put(id, pieza);
	}
	
	public void addAutor(String nombre, String pieza) {
		if(autores.containsKey(nombre)) {
			autores.get(nombre).add(pieza);
		} else {
			List<String> piezas = new ArrayList<String>();
			piezas.add(pieza);
			autores.put(nombre, piezas);
		}
	}
	
	public String getHistoriaAutor(String autor) {
		String historia = "";
		List<String> piezasAutor = autores.get(autor);
		for (String nombrePieza : piezasAutor) {
			historia = historia + '\n';
			historia = historia + "Pieza: " + nombrePieza + "\n";
			historia = historia + "Fecha de creacion: " + prePiezas.get(nombrePieza).getAnio() + "\n";
			historia = historia + "Ventas: " + prePiezas.get(nombrePieza).getValorUltimaVenta() + "\n";
		}
		return historia;
	}
	
	public Collection<prePieza>  getHistoriaPre() {

		return prePiezas.values();
	}
	
	
	public String getHistoriaPieza(String id) {
		prePieza pieza = prePiezas.get(id);
		String historia = "";
		historia = historia + '\n';
		historia = historia + "Titulo: " + pieza.getTitulo() + "\n";
		historia = historia + "Anio: " + pieza.getAnio() + "\n";
		historia = historia + "Lugar de creacion: " + pieza.getLugarDeCreacion() + "\n";
		historia = historia + "Autor: " + pieza.getAutor() + "\n";
		historia = historia + "esFisica: " + pieza.isFisico() + "\n";
		historia = historia + "Dueños: " + pieza.getDueños() + "\n";
		historia = historia + "Valor ultima venta: " + pieza.getValorUltimaVenta() + "\n";
		return historia;
	}
	
	public void cargarDatos( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		cargador.cargarCompradores(archivo, this);
		cargador.cargarPiezas(archivo, this);
		cargador.cargarHistoriaPiezas(archivo, this);
    }
	

	
	
	/*public void cargarCompradores( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		
    }
	public void cargarPropietarios( String archivo ) throws IOException{
		IPersistencia cargador = CentralPersistencia.getPersistencia();
		
    }*/
	
}
