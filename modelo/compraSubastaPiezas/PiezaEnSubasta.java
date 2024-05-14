package compraSubastaPiezas;

import java.util.HashMap;
import java.util.Map;

import piezas.Pieza;

public class PiezaEnSubasta {
	private float valorMinimo;
	private float valorInicial;
	private String estado;
	private String ganador;
	private Map<String, Oferta> ofertas;
	private Pieza pieza;
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public PiezaEnSubasta(float valorMinimo, float valorInicial, String estado, Pieza pieza, Map<String, Oferta> ofertas){
		this.valorMinimo = valorMinimo;
		this.valorInicial = valorInicial;
		this.estado = estado;
		this.pieza = pieza;
		this.ofertas = ofertas;
		
	}
	
	public Pieza getPieza() {
		return pieza;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public Map<String, Oferta> getOfertas() {
		return ofertas;
	}
	
	public void addOfertas(String id, Oferta oferta) {
		ofertas.put(id, oferta);
	}
	
	public float getValorMinimo() {
		return valorMinimo;
	}
	
	public float getValorInicial() {
		return valorInicial;
	}
	
	public String getEstado() {
		return estado;
	}

}
