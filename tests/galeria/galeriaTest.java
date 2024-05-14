package galeria;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import piezas.Pieza;
import piezas.PiezaFisica;
import galeria.Galeria;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Propietario;
import persistencia.IPersistencia;
public class galeriaTest {
	
	private Galeria galeria;
	
	@BeforeEach
    public void setUp() {
        galeria = new Galeria();
        
        
    }
	@Test
    public void testAddPiezas() {
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezas(pieza);
        Collection<Pieza> inventario = galeria.getInventario();
        assertEquals(1, inventario.size());
        assertEquals(pieza, inventario.iterator().next());
    }
	@Test
	public void testDeletePiezaInventario() {
		PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
	    galeria.addPiezas(pieza);
	    galeria.deletePiezaInventario("Pintura de pedro");
	    Collection<Pieza> inventario = galeria.getInventario();
	    assertFalse(inventario.contains(pieza));
	    }
	@Test
	public void testAddPiezasSubasta() {
		PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
		Map<String, Oferta> ofertas = null;
        PiezaEnSubasta piezaEnSubasta = new PiezaEnSubasta(1000, 500, "disponible", pieza, ofertas);
        galeria.addPiezasSubasta(piezaEnSubasta);
        Map<String, PiezaEnSubasta> piezasSubasta = galeria.getPiezasEnSubasta();
        assertTrue(piezasSubasta.containsValue(piezaEnSubasta));
    }
	@Test
	public void testAddPiezasFijas() {
		PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        PiezaConPrecioFijo piezaConPrecioFijo = new PiezaConPrecioFijo(2000, pieza, "David");
        galeria.addPiezasFijas(piezaConPrecioFijo);
        Map<String, PiezaConPrecioFijo> piezasOfertasFijas = galeria.getPiezasEnOfertaFija();
        assertTrue(piezasOfertasFijas.containsValue(piezaConPrecioFijo));
    }
	@Test 
	public void testAddComprador() {
        Comprador comprador = new Comprador(false,  null, null, 0, 0);
        galeria.addComprador(comprador);
        
        Collection<Comprador> compradores = galeria.getCompradores();
        assertTrue(compradores.contains(comprador));
        assertEquals(comprador, compradores.iterator().next());
    }
	@Test
	public void testAddpropietario() {
        Propietario propietario = new Propietario("juan", null, null, null);
        galeria.addPropietario(propietario);
        
        Map<String,Propietario> propietarios = galeria.getPropietarios();
        assertTrue(propietarios.containsKey(propietario.getNombre()));
    }
	
	 
	 
}
