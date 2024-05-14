package piezas;

public class prePieza {
	private String Titulo;
    private String Anio;
    private String LugarDeCreacion;
    private String Autor;
    private boolean isFisico; 
    private String Dueños;
    private String ultimaVenta;
    
	public prePieza(String Titulo, String Anio, String LugarDeCreacion, String Autor, boolean isFisico, String Dueños, String valorUltimaVenta) {
		this.Titulo = Titulo;
		this.Anio = Anio;
		this.LugarDeCreacion = LugarDeCreacion;
		this.Autor = Autor;
		this.isFisico = isFisico;
		this.Dueños = Dueños;
		this.ultimaVenta = valorUltimaVenta;
		
	}

	public String getDueños() {
		return Dueños;
	}

	public void setDueños(String dueños) {
		Dueños = dueños;
	}

	public String getValorUltimaVenta() {
		return ultimaVenta;
	}

	public void setValorUltimaVenta(String valorUltimaVenta) {
		this.ultimaVenta = valorUltimaVenta;
	}

	public String getTitulo() {
		return Titulo;
	}

	public String getAnio() {
		return Anio;
	}

	public String getLugarDeCreacion() {
		return LugarDeCreacion;
	}

	public String getAutor() {
		return Autor;
	}

	public boolean isFisico() {
		return isFisico;
	}

}
