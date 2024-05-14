package piezas;

public class PiezaVirtual extends Pieza{

    private String Tipo;
    private String Formato;
    private String Titulo;
    private String Anio;
    private String LugarDeCreacion;
    private String Autor;
    public static boolean ISFISICO = false;
    private boolean isBodega;
    private boolean PrecioFijo;


	public PiezaVirtual(String Tipo, String Formato, String Titulo, String Anio, String LugarDeCreacion, String Autor, boolean PrecioFijo){
        this.Formato = Formato;
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
	
    public String getFormato(){
        return Formato;
    }
    
    @Override
    public String getTitulo() {
        return Titulo;
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
    public boolean isBodega() {
        return isBodega;
    }
    
    @Override
    public boolean isFisico() {
        return false;
    }


    @Override
    public boolean isPrecioFijo() {
        return PrecioFijo;
    }
    

}