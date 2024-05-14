package compraSubastaPieza;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.Propietario;
import piezas.Pieza;
import piezas.PiezaFisica;

import java.util.HashMap;
import java.util.Map;

public class PropietarioTest {
    private Propietario propietario;
    private Map<String, Pieza> piezas;

    @BeforeEach
    void setUp() {
        piezas = new HashMap<>();
        Pieza pieza = new PiezaFisica("pintura", "Pintura de pedro", "2020", "Colombia", "Pedro", true, 2, 3.1, 1.2, 2.2, false, false);
        piezas.put(pieza.getTitulo(), pieza);
        propietario = new Propietario("Alice", "123456789", "alice@example.com", piezas);
    }

    @Test
    void testConstructor() {
        assertEquals("Alice", propietario.getNombre());
        assertEquals("123456789", propietario.getNumeroDeTelefono());
        assertEquals("alice@example.com", propietario.getCorreo());
        assertEquals(piezas, propietario.getPiezas());
    }

    @Test
    void testGetPieza() {
        Pieza pieza = propietario.getPieza("Pintura de pedro");
        assertNotNull(pieza);
        assertEquals("Pintura de pedro", pieza.getTitulo());
    }
}
