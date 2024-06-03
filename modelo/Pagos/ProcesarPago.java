package Pagos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcesarPago {
	
	private Map<String, InterfazPagos> metodos = new HashMap<>();

    public ProcesarPago(String path) {
        loadMetodos(path);
    }
    
    public Set<String> getMetodos() {
        return metodos.keySet();
    }

    private void loadMetodos(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Class<?> clazz = Class.forName(line);
                    InterfazPagos metodo = (InterfazPagos) clazz.getDeclaredConstructor().newInstance();
                    metodos.put(clazz.getSimpleName(), metodo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean processPayment(String nombreMetodo, TarjetaCredito tarjeta, InformacionPago informacion) {
    	InterfazPagos metodo = metodos.get(nombreMetodo);
        if (metodo == null) {
            throw new IllegalArgumentException("El metodo de pago no se encontro: " + nombreMetodo);
        }
        return metodo.processPayment(tarjeta, informacion);
    }

}
