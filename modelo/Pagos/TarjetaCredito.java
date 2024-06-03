package Pagos;

import java.util.Map;

import piezas.Pieza;

public class TarjetaCredito {

	private String cardNumber;
	private String dueño;
	private int csv;
	private String expiryDate;
	private double saldo;
	
	public TarjetaCredito(String cardNumber, String dueño, int csv, String expiryDate, double saldo) {
		this.cardNumber = cardNumber;
		this.dueño = dueño;
		this.csv = csv;
		this.expiryDate = expiryDate;
		this.saldo = saldo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getDueño() {
		return dueño;
	}

	public int getCsv() {
		return csv;
	}

	public String getExpiryDate() {
		return expiryDate;
	}
	

	
}
