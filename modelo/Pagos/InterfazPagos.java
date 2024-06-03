package Pagos;

public interface InterfazPagos {
	boolean processPayment(TarjetaCredito tarjeta, InformacionPago informacion);
}
