package compraSubastaPieza;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaEnSubasta;
import piezas.Pieza;
import piezas.PiezaFisica;

import java.util.HashMap;
import java.util.Map;

public class PiezaEnSubastaTest {
    private PiezaEnSubasta piezaEnSubasta;
    private Pieza pieza;
    private Map<String, Oferta> ofertas;

    @BeforeEach
    void setUp() {
        pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        ofertas = new HashMap<>();
        piezaEnSubasta = new PiezaEnSubasta(200, 100, "en subasta", pieza, ofertas);
    }

    @Test
    void testConstructor() {
        assertEquals(100, piezaEnSubasta.getValorMinimo());
        assertEquals(200, piezaEnSubasta.getValorInicial());
        assertEquals("en subasta", piezaEnSubasta.getEstado());
        assertEquals(pieza, piezaEnSubasta.getPieza());
        assertEquals(ofertas, piezaEnSubasta.getOfertas());
    }

    @Test
    void testSetGanador() {
        piezaEnSubasta.setGanador("Alice");
        assertEquals("Alice", piezaEnSubasta.getGanador());
    }

    @Test
    void testAddOfertas() {
        Oferta oferta = new Oferta(150, new Comprador(true, "Juan Pedro", "efectivo", 100000, 2), pieza, "pendiente");
        piezaEnSubasta.addOfertas(pieza.getTitulo(), oferta);
        assertTrue(piezaEnSubasta.getOfertas().containsKey(pieza.getTitulo()));
        assertEquals(oferta, piezaEnSubasta.getOfertas().get(pieza.getTitulo()));
    }
}
