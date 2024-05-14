package compraSubastaPieza;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import piezas.Pieza;
import piezas.PiezaFisica;

public class OfertaTest {
    private Oferta oferta;
    private Comprador comprador;
    private Pieza pieza;

    @BeforeEach
    void setUp() {
        comprador = new Comprador(true, "Juan Pedro", "efectivo", 100000, 2);
        pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        oferta = new Oferta(500, comprador, pieza, "pendiente");
    }

    @Test
    void testConstructor() {
        assertEquals(500, oferta.getValor());
        assertEquals(comprador, oferta.getComprador());
        assertEquals(pieza, oferta.getPieza());
        assertEquals("pendiente", oferta.getEstado());
    }

    @Test
    void testSetEstado() {
        oferta.setEstado("aceptada");
        assertEquals("aceptada", oferta.getEstado());
    }
}
