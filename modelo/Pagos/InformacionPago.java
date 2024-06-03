package Pagos;

public class InformacionPago {

	private double cantidad;
	private String numero;
	private String id;

	public InformacionPago(double cantidad, String numero, String id) {
		this.cantidad = cantidad;
		this.numero = numero;
		this.id = id;
	}

	public double getCantidad() {
		return cantidad;
	}

	public String getNumero() {
		return numero;
	}

	public String getId() {
		return id;
	}
	
}
