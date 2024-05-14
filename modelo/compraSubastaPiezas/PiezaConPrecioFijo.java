package compraSubastaPiezas;

import piezas.Pieza;

public class PiezaConPrecioFijo {
	
	private int precio;
	private Pieza pieza;
	private String idComprador;
	
	public PiezaConPrecioFijo(int precio, Pieza pieza, String idComprador) {
		this.precio = precio;
		this.pieza = pieza;
		this.idComprador = idComprador;
	}


	public String getIdComprador() {
		return idComprador;
	}


	public void setIdComprador(String idComprador) {
		this.idComprador = idComprador;
	}


	public int getPrecio() {
		return precio;
	}

	public Pieza getPieza() {
		return pieza;
	}
	

}
