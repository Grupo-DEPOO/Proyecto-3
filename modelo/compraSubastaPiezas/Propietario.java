package compraSubastaPiezas;
import java.util.Map;

import piezas.Pieza;
public class Propietario{

    private String Nombre;
    private int valorColeccion;
    private Map<String, Pieza> Piezas;

    // Constructor
    public Propietario(String Nombre, Map<String, Pieza> Piezas, int valorColeccion) {
        this.Nombre = Nombre;
        this.valorColeccion = valorColeccion;
        this.Piezas = Piezas;
    }

    // Getters
    public String getNombre() {
        return Nombre;
    }


    public void setValorColeccion(int valorColeccion) {
		this.valorColeccion = valorColeccion;
	}

	public int getValorColeccion() {
		return valorColeccion;
	}

	public Map<String, Pieza> getPiezas() {
        return Piezas;
    }
    
    public Pieza getPieza(String nombre){
        Pieza pieza1 = Piezas.get(nombre);
        return pieza1;
    }
    
}