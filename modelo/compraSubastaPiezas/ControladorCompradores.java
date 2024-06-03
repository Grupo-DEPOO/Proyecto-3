package compraSubastaPiezas;

import java.util.Iterator;
import java.util.Map;

import galeria.Galeria;
import piezas.Pieza;
import staff.Administrador;

public class ControladorCompradores {
	private Galeria galeria;
	
	public ControladorCompradores(Galeria galeria) {
		this.galeria = galeria;
	}
	
	public Comprador login(String usuario, String contraseña) {
		Comprador comprador = galeria.getComprador(usuario);
		if(comprador.getContraseña().compareTo(contraseña) == 0) {
			return comprador;
		}
		
		return null;
	}
	
	public void cambiarMetodoPago(Comprador comprador) {
		if(comprador.getMetodoPago().compareTo("Efectivo") == 0) {
			comprador.setMetodoPago("Tarjeta");
		} else if(comprador.getMetodoPago().compareTo("Tarjeta") == 0) {
			comprador.setMetodoPago("Efectivo");
		}else {
			comprador.setMetodoPago("Efectivo");
		}
		
		
	}
	
	public String getHistoriaComprador(Administrador admin, String id) {
		String historia = "";
		Comprador comprador = galeria.getComprador(id);
		historia = "piezas que ha comprado: \n";
		if(comprador.getPiezasCompradas().size() > 0) {
			for (Map.Entry<String, Pieza> entry : comprador.getPiezasCompradas().entrySet()) {
			    String clave = entry.getKey(); // Obtener la clave
			    Pieza pieza = entry.getValue(); // Obtener el valor (Pieza)

			    historia = historia + "Titulo: " + pieza.getTitulo() + "\n";
			    historia = historia + "Autor: " + pieza.getAutor() + "\n";
			    historia = historia + "LugarDeCreacion: " + pieza.getLugarDeCreacion() + "\n";
			    historia = historia + "Anio: " + pieza.getAnio() + "\n";
			    historia = historia + "Comprada por: " + clave + "\n";
			}
		}
		historia = historia + "piezas en propiedad: \n";
		if(galeria.getPropietarios().containsKey(id)) {
			Propietario propietario = galeria.getPropietario(id);
			for (Pieza pieza : propietario.getPiezas().values()) {
				historia = historia + " -Titulo: " + pieza.getTitulo() + "\n";
				historia = historia + " -Autor: " + pieza.getAutor() + "\n";
				historia = historia + " -LugarDeCreacion: " + pieza.getLugarDeCreacion() + "\n";
				historia = historia + " -Anio: " + pieza.getAnio() + "\n";
			}
			historia = historia + "valor de coleccion: " + propietario.getValorColeccion();
		}
		else {
			historia = historia + "valor de coleccion: " + "0";
		}
		
		return historia;
	}

}
