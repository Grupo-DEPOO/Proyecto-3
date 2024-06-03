package compraSubastaPiezas;

import java.util.Map;

import piezas.Pieza;

public class Comprador {
	
	private boolean isVerificado;
	private String nombre;
	private String metodoPago;
	private int capital;
	private String contraseña;
	private int topeDeCompras;
	private Map<String, Pieza> piezasCompradas;
	

	public Comprador(boolean isVerificado, String nombre, String metodoPago, int capital, int topeDeCompras, String contraseña, Map<String, Pieza> piezasCompradas) {
		this.isVerificado = isVerificado;
		this.nombre = nombre;
		this.piezasCompradas = piezasCompradas;
		this.metodoPago = metodoPago;
		this.capital = capital;
		this.topeDeCompras = topeDeCompras;
		this.contraseña = contraseña;
	}

	public String getContraseña() {
		return contraseña;
	}

	public boolean isVerificado() {
		return isVerificado;
	}

	public void setVerificado(boolean isVerificado) {
		this.isVerificado = isVerificado;
	}

	public int getCapital() {
		return capital;
	}
	
	public Map<String, Pieza> getPiezasCompradas() {
		return piezasCompradas;
	}
	
	public void addPiezaComprada(String id, Pieza pieza) {
		piezasCompradas.put(id, pieza);
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public int getTopeDeCompras() {
		return topeDeCompras;
	}

	public void setTopeDeCompras(int topeDeCompras) {
		this.topeDeCompras = topeDeCompras;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	
}
