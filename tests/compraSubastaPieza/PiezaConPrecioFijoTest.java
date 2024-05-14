package compraSubastaPieza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.PiezaConPrecioFijo;
import piezas.Pieza;

public class PiezaConPrecioFijoTest {
	private PiezaConPrecioFijo piezaConPrecioFijo;
	
	@BeforeEach
	public void setUp() {
		piezaConPrecioFijo = new PiezaConPrecioFijo(0, null, null);
	}
	
	@Test
    public void testGetIdComprador() {      
        // Llama al método y verifica si el resultado es el esperado
        String resultadoEsperado = "123"; // Cambia esto al valor esperado
        String resultadoActual = piezaConPrecioFijo.getIdComprador();
        assertEquals(resultadoEsperado, resultadoActual);
    }
	
    @Test
    public void testGetPrecio() {        
        // Llama al método y verifica si el resultado es el esperado
        int resultadoEsperado = 100; // Cambia esto al valor esperado
        int resultadoActual = piezaConPrecioFijo.getPrecio();
        assertEquals(resultadoEsperado, resultadoActual);
    }
	
    @Test
    public void testGetPieza() {
        // Llama al método y verifica si el resultado no es nulo
        Pieza resultadoActual = piezaConPrecioFijo.getPieza();
        assertNotNull(resultadoActual);
    }
	
}
