
package piezas;
import java.util.List;

public class PiezaFisica extends Pieza{
	
	private String Titulo;
    private String Anio;
    private String LugarDeCreacion;
    private String Autor;
    public static boolean ISFISICO = true;
    private boolean PrecioFijo;  
    public String Tipo;
    public double Profundidad;
    public double Alto;
    public double Ancho;
    public List<String> MaterialesConstruccion;
    public double Peso;
    public boolean NeedsElectricity;
    public boolean NeedsOther;
    
    // Constructor
    public PiezaFisica(String Tipo, String Titulo, String Anio, String LugarDeCreacion, String Autor, boolean PrecioFijo, double Profundidad, double Alto, double Ancho, double Peso, boolean NeedsElectricity, boolean NeedsOther) {
        this.Profundidad = Profundidad;
        this.Alto = Alto;
        this.Ancho = Ancho;
        this.Peso = Peso;
        this.NeedsElectricity = NeedsElectricity;
        this.NeedsOther = NeedsOther;
        this.Titulo = Titulo;
        this.Anio = Anio;
        this.LugarDeCreacion = LugarDeCreacion;
        this.Autor = Autor;
        this.PrecioFijo = PrecioFijo;
        this.Tipo = Tipo;
    }

    // Getters
    public String getTipo() {
        return Tipo;
    }

    public double getProfundidad() {
        return Profundidad;
    }

    public Double getAlto() {
        return Alto;
    }

    public Double getAncho() {
        return Ancho;
    }

    public List<String> getMaterialesConstruccion() {
        return MaterialesConstruccion;
    }

    public double getPeso() {
        return Peso;
    }

    public boolean needsElectricity() {
        return NeedsElectricity;
    }

    public boolean needsOther() {
        return NeedsOther;
    }
    
    @Override
    public String getTitulo() {
        return Titulo;
    }
    
    @Override
    public boolean isFisico() {
        return true;
    }

    @Override
    public String getAnio() {
        return Anio;
    }

    @Override
    public String getLugarDeCreacion() {
        return LugarDeCreacion;
    }

    @Override
    public String getAutor() {
        return Autor;
    }


    @Override
    public boolean isPrecioFijo() {
        return PrecioFijo;
    }

    
    
}