package Pagos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PayU implements InterfazPagos{

	
	
	@Override
	public boolean processPayment(TarjetaCredito tarjeta, InformacionPago informacion) {
		boolean s = false;
		if(tarjeta.getSaldo() >= informacion.getCantidad()) {
			tarjeta.setSaldo(tarjeta.getSaldo() - informacion.getCantidad());
			s = true;
		}
		logTransaccion("payU", tarjeta, informacion, s);
		return s;
	}
	
	public void logTransaccion(String pasarela, TarjetaCredito tarjeta, InformacionPago informacion, boolean s) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./datos/logTransactions.txt", true))) {
			writer.write(String.format("metodoDePago: %s, Tarjeta: %s, Comprador: %s, Cantidad: %.2f, Fue exitosa?: %b\n", 
                pasarela, tarjeta.getCardNumber(), tarjeta.getDueño(), informacion.getCantidad(), s));
			System.out.println("Transacción registrada en el archivo de log.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
