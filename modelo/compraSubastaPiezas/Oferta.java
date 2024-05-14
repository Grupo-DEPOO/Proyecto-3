package compraSubastaPiezas;

import piezas.Pieza;

public class Oferta {

	private int valor;
	private Comprador comprador;
	private Pieza pieza;
	private String estado; 
	
	public Oferta(int valor, Comprador comprador, Pieza pieza, String estado) {
		this.valor = valor;
		this.comprador = comprador;
		this.pieza = pieza;
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getValor() {
		return valor;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public Pieza getPieza() {
		return pieza;
	}
	
}
