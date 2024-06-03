package compraSubastaPiezas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import galeria.Galeria;
import piezas.Pieza;
import Pagos.*;
import staff.Administrador;
import staff.Cajero;
import staff.Operador;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorSubasta {
	 
	private Galeria galeria;
	private Map<String, PiezaEnSubasta> historialPiezasEnSubasta;

	public ControladorSubasta(Galeria galeria) {
		this.galeria = galeria;
	}
	
	public void addPiezaASubasta(PiezaEnSubasta pieza) {
		galeria.addPiezasSubasta(pieza);
	}
	
	public void ofertarPieza(Comprador comprador,int valor, String id) {
		
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().compareTo("disponible") == 0) {
			if((valor >= pieza.getValorInicial()) && (comprador.isVerificado() == true)) {
				Oferta nuevaOferta = new Oferta(valor, comprador, pieza.getPieza(), "Oferta en subasta");
				pieza.addOfertas(comprador.getNombre(), nuevaOferta);
			}
		}
		
	}
	public void elegirPiezaGanadora(Administrador adminitrador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getOfertas().size() >= 2) {
			Collection<Oferta> ofertas = pieza.getOfertas().values();
			Oferta ofertaGanadora = new Oferta(0, null, null, null);
			for (Oferta oferta : ofertas) {
				if((oferta.getValor() > ofertaGanadora.getValor()) || (ofertaGanadora == (Oferta) null))
					ofertaGanadora = oferta;
			}
			pieza.setGanador(ofertaGanadora.getComprador().getNombre());
			pieza.setEstado("finalizado pendiente por revision de pago");
		}
	}
	public void verificarPagoGanador(Cajero cajero, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().equals("finalizado pendiente por revision de pago")) {
			Comprador ganador = galeria.getComprador(pieza.getGanador());
			if(pieza.getOfertas().get(pieza.getGanador()).getValor() <= ganador.getCapital()) {
				Pieza piezaGanador = pieza.getPieza();
				ganador.addPiezaComprada(pieza.getValorMinimo() + " en " + getFecha(), piezaGanador);
				galeria.deletePiezaInventario(pieza.getPieza().getTitulo());
				ganador.setCapital(ganador.getCapital() - pieza.getOfertas().get(pieza.getGanador()).getValor());
				galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).setDueños(galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).getDueños() + ", " + ganador.getNombre());
				galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).setValorUltimaVenta(pieza.getValorMinimo() + " en " + getFecha());;
				pieza.setEstado("finalizado");
				if(galeria.getPropietarios().containsKey(ganador.getNombre())) {
					galeria.getPropietario(ganador.getNombre()).getPiezas().put(piezaGanador.getTitulo(), piezaGanador);
					Propietario prope = galeria.getPropietario(ganador.getNombre());
					prope.getPiezas().put(pieza.getPieza().getTitulo(), pieza.getPieza());
					prope.setValorColeccion(prope.getValorColeccion() + (int)pieza.getValorMinimo());
				}else {
					Map<String, Pieza> piezas = new HashMap<String, Pieza>();
					piezas.put(piezaGanador.getTitulo(), piezaGanador);
					Propietario nuevoPropietario = new Propietario(ganador.getNombre(), piezas, (int) pieza.getValorMinimo());
					galeria.addPropietario(nuevoPropietario);
				}
			} else {
				pieza.setEstado("disponible");
				pieza.getOfertas().remove(pieza.getGanador());
				pieza.setGanador(null);
			}
		}
	}
	
	public void verificarPagoGanadorTarjeta(Cajero cajero, String id, String cardNumber, String dueño, int csv, String expiryDate, String metodo, ProcesarPago procesador) {
		TarjetaCredito tarjeta = galeria.getTarjeta(dueño);
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().equals("finalizado pendiente por revision de pago")) {
			Comprador ganador = galeria.getComprador(pieza.getGanador());
			if(tarjeta.getCardNumber().compareToIgnoreCase(cardNumber) == 0 && tarjeta.getCsv() == csv && tarjeta.getExpiryDate().compareToIgnoreCase(expiryDate) == 0){
				InformacionPago info = new InformacionPago(pieza.getOfertas().get(pieza.getGanador()).getValor(), getRandomString(), dueño);
				boolean resultado = procesador.processPayment(metodo, tarjeta, info);
				if(resultado) {
					Pieza piezaGanador = pieza.getPieza();
					ganador.addPiezaComprada(pieza.getValorMinimo() + " en " + getFecha(), piezaGanador);
					galeria.deletePiezaInventario(pieza.getPieza().getTitulo());
					ganador.setCapital(ganador.getCapital() - pieza.getOfertas().get(pieza.getGanador()).getValor());
					galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).setDueños(galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).getDueños() + ", " + ganador.getNombre());
					galeria.getPrePiezas().get(pieza.getPieza().getTitulo()).setValorUltimaVenta(pieza.getValorMinimo() + " en " + getFecha());;
					pieza.setEstado("finalizado");
					if(galeria.getPropietarios().containsKey(ganador.getNombre())) {
						galeria.getPropietario(ganador.getNombre()).getPiezas().put(piezaGanador.getTitulo(), piezaGanador);
						Propietario prope = galeria.getPropietario(ganador.getNombre());
						prope.getPiezas().put(pieza.getPieza().getTitulo(), pieza.getPieza());
						prope.setValorColeccion(prope.getValorColeccion() + (int)pieza.getValorMinimo());
					}else {
						Map<String, Pieza> piezas = new HashMap<String, Pieza>();
						piezas.put(piezaGanador.getTitulo(), piezaGanador);
						Propietario nuevoPropietario = new Propietario(ganador.getNombre(), piezas, (int) pieza.getValorMinimo());
						galeria.addPropietario(nuevoPropietario);
					}
				} else {
					pieza.setEstado("disponible");
					pieza.getOfertas().remove(pieza.getGanador());
					pieza.setGanador(null);
				}
			}
		}
	}
	

	
	public void revisarSubasta(Administrador administrador, Cajero cajero, Operador operador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(administrador != null) {
			if(pieza.getEstado().compareTo("disponible") == 0) {
				elegirPiezaGanadora(administrador, id);
			}
		}else if(cajero != null) {
			if(pieza.getEstado().compareTo("finalizado pendiente por revision de pago") == 0)
				verificarPagoGanador(cajero, id);
		}else if(operador != null) {
			if(pieza.getEstado().compareTo("finalizado") == 0)
				registrarDatosSubasta(operador, id);
		}
	}

	public void registrarDatosSubasta(Operador operador, String id) {
		PiezaEnSubasta pieza = galeria.getPiezaEnSubasta(id);
		if(pieza.getEstado().equals("finalizado")) {
			String llave = "ganador: " + pieza.getGanador() + ", pieza: " + id;
			galeria.getPiezasEnSubasta().remove(id);
		}
		
	}
	
	public String getFecha() {
		LocalDateTime tiempoActual = LocalDateTime.now();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        String tiempoFormateado = tiempoActual.format(formato);
        
        return tiempoFormateado;
        
	}
	
	public static String generateRandomString(int length) {
        // Definir el conjunto de caracteres permitidos
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        // Generar la cadena aleatoria
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
	
	public String getRandomString() {
		String randomString = generateRandomString(10);

        return randomString;
    }
	
	


}
