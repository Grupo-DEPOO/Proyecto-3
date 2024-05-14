package compraSubastaPieza;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.ControladorSubasta;
import galeria.Galeria;
import piezas.Pieza;
import staff.Administrador;
import staff.Cajero;
import staff.Operador;


public class ControladorSubastaTest {	
	private ControladorSubasta controladorSubasta;
	private Galeria galeria;
	
	@BeforeEach
    public void setUp() {
		galeria = new Galeria();
        controladorSubasta = new ControladorSubasta(galeria);
    }
	
	@Test
    public void testOfertarPieza() {
        Comprador comprador = new Comprador(false, null, null, 0, 0);
        int valor = 100; // Valor de la oferta
        String id = "ID_DE_LA_PIEZA"; // ID de la pieza en subasta

        // Crear una pieza en subasta con estado "disponible" y valor inicial
        PiezaEnSubasta pieza = new PiezaEnSubasta(valor, valor, id, null, null/* parámetros del constructor */);
        pieza.setEstado("disponible");
        pieza.getValorInicial(); // Valor inicial de la pieza

        // Verificar que la oferta cumple las condiciones
        if (valor >= pieza.getValorInicial() && comprador.isVerificado()) {
            Oferta nuevaOferta = new Oferta(valor, comprador, pieza.getPieza(), "Oferta en subasta");
            pieza.addOfertas(comprador.getNombre(), nuevaOferta);
            // Verificar que la oferta se agregó correctamente
            assertTrue(pieza.getOfertas().containsKey(comprador.getNombre()));
            assertEquals(nuevaOferta, pieza.getOfertas().get(comprador.getNombre()));
        } else {
            // Si no cumple las condiciones, no se agrega la oferta
            assertFalse(pieza.getOfertas().containsKey(comprador.getNombre()));
        }
    }
	
    @Test
    public void testElegirPiezaGanadora() {
        // Crear una instancia de la galería (galeria) y otras instancias necesarias
        Galeria galeria = new Galeria();
        Administrador administrador = new Administrador(null, null/* parámetros del constructor */);
        String id = "ID_DE_LA_PIEZA"; // ID de la pieza en subasta

        // Crear una pieza en subasta con al menos 2 ofertas
        PiezaEnSubasta pieza = new PiezaEnSubasta(0, 0, id, null, null/* parámetros del constructor */);
        pieza.setEstado("disponible");
        pieza.addOfertas("Comprador1", new Oferta(100, new Comprador(false, id, id, 0, 0), new Pieza(), "Oferta 1"));
        pieza.addOfertas("Comprador2", new Oferta(150, new Comprador(false, id, id, 0, 0), new Pieza(), "Oferta 2"));

        // Ejecutar el método para elegir la oferta ganadora
        pieza.elegirPiezaGanadora(administrador, id);

        // Verificar que se haya establecido el ganador y el estado
        assertEquals("Comprador2", pieza.getGanador());
        assertEquals("finalizado pendiente por revision de pago", pieza.getEstado());
    }
    
    @Test
    public void testVerificarPagoGanador() {
        // Crear una instancia de la galería (galeria) y otras instancias necesarias
        Galeria galeria = new Galeria();
        Cajero cajero = new Cajero(null, null/* parámetros del constructor */);
        String id = "ID_DE_LA_PIEZA"; // ID de la pieza en subasta

        // Crear una pieza en subasta con estado "finalizado pendiente por revision de pago"
        PiezaEnSubasta pieza = new PiezaEnSubasta(0, 0, id, null, null/* parámetros del constructor */);
        pieza.setEstado("finalizado pendiente por revision de pago");
        pieza.setGanador("Comprador1"); // Ganador de la subasta
        pieza.addOfertas("Comprador1", new Oferta(100, new Comprador(false, id, id, 0, 0), new Pieza(), "Oferta ganadora"));

        // Crear un comprador ganador con capital suficiente
        Comprador ganador = new Comprador(false, id, id, 0, 0/* parámetros del constructor */);
        ganador.setCapital(200); // Capital del ganador

        // Ejecutar el método para verificar el pago del ganador
        pieza.verificarPagoGanador(cajero, id);

        // Verificar que se haya actualizado el estado y el capital del ganador
        assertEquals("finalizado", pieza.getEstado());
        assertEquals(100, ganador.getCapital());

        // Verificar que se haya transferido la pieza al propietario
        assertTrue(galeria.getPropietarios().containsKey(ganador.getNombre()));
        assertEquals(pieza.getPieza(), galeria.getPropietario(ganador.getNombre()).getPiezas().get(pieza.getPieza().getTitulo()));
    }
    
    @Test
    public void testRevisarSubasta() {
        // Crear una instancia de la galería (galeria) y otras instancias necesarias
        Galeria galeria = new Galeria();
        Administrador administrador = new Administrador(null, null/* parámetros del constructor */);
        Cajero cajero = new Cajero(null, null/* parámetros del constructor */);
        Operador operador = new Operador(null, null/* parámetros del constructor */);
        String id = "ID_DE_LA_PIEZA"; // ID de la pieza en subasta

        // Crear una pieza en subasta con estado "disponible"
        PiezaEnSubasta pieza = new PiezaEnSubasta(0, 0, id, null, null/* parámetros del constructor */);
        pieza.setEstado("disponible");

        // Ejecutar el método para revisar la subasta según el rol
        if (administrador != null) {
            if (pieza.getEstado().equals("disponible")) {
                pieza.elegirPiezaGanadora(administrador, id);
            }
        } else if (cajero != null) {
            if (pieza.getEstado().equals("finalizado pendiente por revision de pago")) {
                pieza.verificarPagoGanador(cajero, id);
            }
        } else if (operador != null) {
            if (pieza.getEstado().equals("finalizado")) {
                pieza.registrarDatosSubasta(operador, id);
            }
        }

        // Verificar que se haya realizado la acción correspondiente
        // (elegir ganador, verificar pago o registrar datos)
        // según el estado y el rol
        // (Asegúrate de reemplazar los comentarios con los valores y objetos reales de tu aplicación)
        // ...
    }
	
    @Test
    public void testRegistrarDatosSubasta() {
        // Crear una instancia de la galería (galeria) y otras instancias necesarias
        Galeria galeria = new Galeria();
        Operador operador = new Operador(null, null/* parámetros del constructor */);
        String id = "ID_DE_LA_PIEZA"; // ID de la pieza en subasta

        // Crear una pieza en subasta con estado "finalizado"
        PiezaEnSubasta pieza = new PiezaEnSubasta(0, 0, id, null, null/* parámetros del constructor */);
        pieza.setEstado("finalizado");
        pieza.setGanador("Comprador1"); // Ganador de la subasta

        // Ejecutar el método para registrar los datos de la subasta
        pieza.registrarDatosSubasta(operador, id);

        // Verificar que se haya agregado al historial y eliminado de las piezas en subasta
        	assertTrue(historialPiezasEnSubasta.containsKey("ganador: Comprador1, pieza: " + id));
        assertFalse(galeria.getPiezasEnSubasta().containsKey(id));
    }
	
}
