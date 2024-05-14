package compraSubastaPieza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.Comprador;

public class CompradorTest {
	private Comprador comprador;

    @BeforeEach
    public void setUp() {
        comprador = new Comprador(false, "Juan", "Paypal", 1000, 500);
    }

    @Test
    public void testIsVerificado() {
        assertTrue(comprador.isVerificado());
    }

    @Test
    public void testGetCapital() {
        assertEquals(1000, comprador.getCapital());
    }

    @Test
    public void testGetTopeDeCompras() {
        assertEquals(500, comprador.getTopeDeCompras());
    }

    @Test
    public void testGetNombre() {
        assertEquals("Juan", comprador.getNombre());
    }

    @Test
    public void testGetMetodoPago() {
        assertEquals("PayPal", comprador.getMetodoPago());
    }

}