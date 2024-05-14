package piezas;

public abstract class Pieza {
    private String Titulo;
    private String Anio;
    private String LugarDeCreacion;
    private String Autor;
    private boolean isFisico;
    private boolean isBodega;
    private String Estado;
    private boolean PrecioFijo;
    //  Constructor
    

    // Getters
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

    public boolean isBodega() {
        return isBodega;
    }

    public String getEstado() {
        return Estado;
    }

    public boolean isPrecioFijo() {
        return PrecioFijo;
    }

}

