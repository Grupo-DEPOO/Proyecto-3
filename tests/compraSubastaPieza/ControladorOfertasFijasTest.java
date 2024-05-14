package compraSubastaPieza;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import staff.Administrador;
import staff.Cajero;
import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaConPrecioFijo;
import galeria.Galeria;
import piezas.Pieza;
import piezas.PiezaFisica;

import java.util.Map;

public class ControladorOfertasFijasTest {
    private ControladorOfertasFijas controladorOfertasFijas;
    private Galeria galeria;

    @BeforeEach
    public void setUp() {
        galeria = new Galeria();
        controladorOfertasFijas = new ControladorOfertasFijas(galeria);
    }
    
    @Test
    public void testOfertarPieza() {
        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        controladorOfertasFijas.ofertarPieza(comprador, 12000, pieza.getTitulo()); 
        assertTrue(galeria.getPiezasEnOfertaFija().containsKey(pieza.getTitulo()));
    }
    
    


    
    @Test
    void testVerificarOfertaCajero() {
        // Preparación de datos
        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(200, pieza, "David"));
        controladorOfertasFijas.ofertarPieza(comprador, 300, "Juan Pedro");
        Cajero cajero = new Cajero("Andres", "80090");

        // Ejecución del método a probar
        controladorOfertasFijas.verificarOfertaCajero(cajero, comprador.getNombre() );

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertEquals(0, ofertas.size());
        assertEquals(700, comprador.getCapital());
        assertFalse(galeria.getPiezasEnOfertaFija().containsKey("David"));
    }

    

    @Test
    void testDarPiezaAComprador() {
        // Preparación de datos
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        controladorOfertasFijas.darPiezaAComprador("Juan Carlos", pieza);

        // Verificación del resultado
        Map<String, Pieza> piezas = galeria.getPropietario("Juan Carlos").getPiezas();
        assertTrue(piezas.containsKey(pieza.getTitulo()));
        assertEquals(pieza, piezas.get(pieza.getTitulo()));
    }

    @Test
    void testConfirmarVenta() {
        // Preparación de datos
    	Administrador administrador = new Administrador("Juan", "123");
        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(700, pieza, "disponible"));
        controladorOfertasFijas.ofertarPieza(comprador, 800, pieza.getTitulo());
        controladorOfertasFijas.verificarOfertaAdministrador(administrador, pieza.getTitulo());

        // Ejecución del método a probar
        controladorOfertasFijas.confirmarVenta(administrador, pieza.getTitulo());

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertFalse(ofertas.containsKey(pieza.getTitulo()));
        assertFalse(galeria.getPiezasEnOfertaFija().containsKey(pieza.getTitulo()));
    }

    @Test
    void testRevisarOferta() {
        // Preparación de datos
    	Administrador administrador = new Administrador("Juan", "123");

        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(500, pieza, "disponible"));
        controladorOfertasFijas.ofertarPieza(comprador, 600, pieza.getTitulo());
        Cajero cajero = new Cajero("Andres","80090"); 

        // Ejecución del método a probar
        controladorOfertasFijas.revisarOferta(administrador, cajero, pieza.getTitulo());

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertEquals(1, ofertas.size());
        assertEquals("pendiente de revision por cajero", ofertas.get(pieza.getTitulo()).getEstado());
    }
    @Test
    void testverificarOfertaAdministrador() {
        // Preparación de datos
    	Administrador administrador = new Administrador("Juan", "123");

        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(500, pieza, "disponible"));
        controladorOfertasFijas.ofertarPieza(comprador, 600, pieza.getTitulo());
        Cajero cajero = new Cajero("Andres","80090"); 
        controladorOfertasFijas.revisarOferta(administrador, cajero, pieza.getTitulo());
        // Ejecución del método a probar
        controladorOfertasFijas.verificarOfertaAdministrador(administrador, pieza.getTitulo());

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertEquals(1, ofertas.size());
        assertEquals("pendiente de revision por cajero", ofertas.get(pieza.getTitulo()).getEstado());
    }

    @Test
    void testverificarOfertaCajero() {
    	// Preparación de datos
    	Administrador administrador = new Administrador("Juan", "123");

        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(500, pieza, "disponible"));
        controladorOfertasFijas.ofertarPieza(comprador, 600, pieza.getTitulo());
        Cajero cajero = new Cajero("Andres","80090"); 
        controladorOfertasFijas.revisarOferta(administrador, cajero, pieza.getTitulo());
        controladorOfertasFijas.verificarOfertaAdministrador(administrador, pieza.getTitulo());
        // Ejecución del método a probar
        controladorOfertasFijas.verificarOfertaCajero(cajero, pieza.getTitulo());

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertEquals(1, ofertas.size());
        assertEquals("pendiente de revision por cajero", ofertas.get(pieza.getTitulo()).getEstado());
    }

    @Test
    void testCargarOfertas() {
        // Preparación de datos
        Comprador comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        PiezaFisica pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        galeria.addPiezasFijas(new PiezaConPrecioFijo(900, pieza, "disponible"));
        controladorOfertasFijas.ofertarPieza(comprador, 1000, "Estatua");

        // Ejecución del método a probar
        controladorOfertasFijas.cargarOfertas();

        // Verificación del resultado
        Map<String, Oferta> ofertas = controladorOfertasFijas.getOfertas();
        assertEquals(1, ofertas.size());
        assertEquals("pendiente de revision por administrador", ofertas.values().iterator().next().getEstado());
    }
} 
