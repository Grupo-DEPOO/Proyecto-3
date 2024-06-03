package consola;
import javax.swing.*;

import Pagos.ProcesarPago;
import compraSubastaPiezas.ControladorCompradores;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import compraSubastaPiezas.Oferta;
import compraSubastaPiezas.PiezaEnSubasta;
import galeria.Galeria;
import piezas.PiezaFisica;
import piezas.PiezaVirtual;
import staff.Administrador;
import staff.Cajero;
import staff.ControladorEmpleados;
import staff.Empleado;
import staff.Operador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class FuncionalidadesEmpleadoPanel extends JPanel {
	
	private Empleado empleado;
    private ControladorOfertasFijas ofertas;
    private ControladorSubasta subastasP;
    private Galeria galeria;
    private ControladorCompradores compradores1;
    private ProcesarPago procesador;
    private InterfazGaleria interfazGaleria;

    public FuncionalidadesEmpleadoPanel(Empleado empleado, ControladorOfertasFijas ofertas, Galeria galeria, ControladorCompradores compradores1, ControladorSubasta subastasP, ProcesarPago procesador, InterfazGaleria interfazGaleria) {
        this.empleado = empleado;
        this.ofertas = ofertas;
        this.galeria = galeria;
        this.compradores1 = compradores1;
        this.subastasP = subastasP;
        this.procesador = procesador;
        this.interfazGaleria = interfazGaleria;
        setLayout(new BorderLayout());
        initUI();
    }
        
    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Usted es un: " + empleado.getTipoEmpleado(), JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        String[] options;
        if (empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
            options = new String[]{"Ofertas", "Subastas", "Consultar historia pieza", "Consultar historia comprador", "Consultar historia artista"};
        } else {
            options = new String[]{"Ofertas", "Subastas", "Consultar historia pieza", "Consultar historia artista"};
        }

        JList<String> optionsList = new JList<>(options);
        panel.add(new JScrollPane(optionsList), BorderLayout.CENTER);

        JButton button = new JButton("Seleccionar");
        JButton cerrar = new JButton("Cerrar sesión");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2)); 
        buttonPanel.add(button);
        buttonPanel.add(cerrar);

        // Añadir el panel de botones al sur del panel principal
        panel.add(buttonPanel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = optionsList.getSelectedValue();
                if (selectedOption != null) {
                    handleSelection(selectedOption);
                } else {
                    JOptionPane.showMessageDialog(panel, "Seleccione una opción", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cerrarSesion();
                
            }
        });

        add(panel);
     
    }
    
    private void cerrarSesion() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        interfazGaleria.setVisible(true);
    }

    private void handleSelection(String option) {
        switch (option) {
            case "Ofertas":
                handleOfertas();
                break;
            case "Subastas":
                handleSubastas();
                break;
            case "Consultar historia pieza":
                handleHistoriaPieza();
                break;
            case "Consultar historia comprador":
                if (empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
                    handleHistoriaComprador();
                }
                break;
            case "Consultar historia artista":
                handleHistoriaArtista();
                break;
        }
    }

    private void handleOfertas() {
        Collection<Oferta> ofertasFijas = ofertas.getOfertas().values();
        StringBuilder sb = new StringBuilder();
        for (Oferta oferta : ofertasFijas) {
            if ((empleado.getTipoEmpleado().compareTo("Administrador") == 0 && (oferta.getEstado().compareTo("pendiente de revision por administrador") == 0 || oferta.getEstado().compareTo("pendiente de confirmacion de venta") == 0)) ||
                (empleado.getTipoEmpleado().compareTo("Cajero") == 0 && oferta.getEstado().compareTo("pendiente de revision por cajero") == 0)) {
                sb.append("---------------------------------------------------\n")
                  .append("Oferta hecha por: ").append(oferta.getComprador().getNombre()).append(" ")
                  .append(oferta.getPieza().getTitulo()).append("\n")
                  .append(oferta.getEstado()).append("\n")
                  .append("---------------------------------------------------\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        String[] options;
        if (empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
            options = new String[]{"Verificar oferta", "Agregar pieza", "Salir"};
        } else {
            options = new String[]{"Verificar oferta", "Salir"};
        }

        int option = JOptionPane.showOptionDialog(this, new JScrollPane(textArea), "Ofertas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (option == 0) {
            handleVerificarOferta();
        } else if (empleado.getTipoEmpleado().compareTo("Administrador") == 0 && option == 1) {
            handleAgregarPieza();
        }
    }

    private void handleVerificarOferta() {
        String id = JOptionPane.showInputDialog(this, "Digite el id de la oferta:");
        if (empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
            ofertas.revisarOferta((Administrador) empleado, null, id);
        } else if (empleado.getTipoEmpleado().compareTo("Cajero") == 0) {
            Oferta oferta = ofertas.getOferta(id);
            if (oferta.getComprador().getMetodoPago().compareTo("Tarjeta") != 0) {
                ofertas.revisarOferta(null, (Cajero) empleado, id);
            } else {
                handlePagoConTarjeta(id);
            }
        }
        JOptionPane.showMessageDialog(this, ofertas.getOferta(id).getEstado(), "Estado de la oferta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handlePagoConTarjeta(String id) {
        Set<String> metodos = procesador.getMetodos();
        StringBuilder sb = new StringBuilder("Metodos de pago:\n");
        for (String metodo : metodos) {
            sb.append("Método: ").append(metodo).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Metodos de Pago", JOptionPane.INFORMATION_MESSAGE);

        String metodo = JOptionPane.showInputDialog(this, "Digite un metodo de pago:");
        String numero = JOptionPane.showInputDialog(this, "Digite el numero de la tarjeta:");
        String csv = JOptionPane.showInputDialog(this, "Digite el CSV de la tarjeta:");
        String fecha = JOptionPane.showInputDialog(this, "Digite la fecha de expiracion de la tarjeta:");
        ofertas.verificarOfertaCajeroTarjeta((Cajero) empleado, id, numero, ofertas.getOferta(id).getComprador().getNombre(), Integer.parseInt(csv), fecha, metodo, procesador);
    }

    private void handleAgregarPieza() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre de la pieza:");
        String anio = JOptionPane.showInputDialog(this, "Año de la pieza:");
        String lugar = JOptionPane.showInputDialog(this, "Lugar de creación:");
        String autor = JOptionPane.showInputDialog(this, "Autor:");
        int isFisica = JOptionPane.showConfirmDialog(this, "¿Es física?", "", JOptionPane.YES_NO_OPTION);

        if (isFisica == JOptionPane.YES_OPTION) {
            String tipo = JOptionPane.showInputDialog(this, "Tipo:");
            double profundidad = Double.parseDouble(JOptionPane.showInputDialog(this, "Profundidad:"));
            double alto = Double.parseDouble(JOptionPane.showInputDialog(this, "Alto:"));
            double ancho = Double.parseDouble(JOptionPane.showInputDialog(this, "Ancho:"));
            double peso = Double.parseDouble(JOptionPane.showInputDialog(this, "Peso:"));
            PiezaFisica pieza = new PiezaFisica(tipo, nombre, anio, lugar, autor, false, profundidad, alto, ancho, peso, false, false);
            galeria.addPiezas(pieza);
        } else {
            String tipo = JOptionPane.showInputDialog(this, "Tipo:");
            String formato = JOptionPane.showInputDialog(this, "Formato:");
            PiezaVirtual piezaVirtual = new PiezaVirtual(tipo, formato, nombre, anio, lugar, autor, false);
            galeria.addPiezas(piezaVirtual);
        }
    }

    private void handleSubastas() {
        Collection<PiezaEnSubasta> subastas = galeria.getPiezasEnSubasta().values();
        StringBuilder sb = new StringBuilder();
        for (PiezaEnSubasta piezaEnSubasta : subastas) {
            sb.append("---------------------------------------------------\n")
              .append("Subasta: ").append(piezaEnSubasta.getPieza().getTitulo()).append("\n")
              .append("Estado: ").append(piezaEnSubasta.getEstado()).append("\n")
              .append("---------------------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        String[] options = new String[]{"Verificar oferta", "Salir"};
        int option = JOptionPane.showOptionDialog(this, new JScrollPane(textArea), "Subastas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (option == 0) {
            handleVerificarSubasta();
        }
    }

    private void handleVerificarSubasta() {
        String id = JOptionPane.showInputDialog(this, "Digite el id de la oferta:");
        if (empleado.getTipoEmpleado().compareTo("Administrador") == 0) {
            subastasP.revisarSubasta((Administrador) empleado,(Cajero) null,(Operador) null, id);
        } else if (empleado.getTipoEmpleado().compareTo("Cajero") == 0) {
        	PiezaEnSubasta piezaSubasta = galeria.getPiezaEnSubasta(id);
        	String ganador = piezaSubasta.getGanador();
            if (galeria.getComprador(ganador).getMetodoPago().compareTo("Tarjeta") != 0) {
                subastasP.revisarSubasta((Administrador) null, (Cajero) empleado, (Operador) null, id);
            } else {
                handlePagoConTarjetaSubasta(id);
            }
        }
        JOptionPane.showMessageDialog(this, galeria.getPiezaEnSubasta(id).getEstado(), "Estado de la oferta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handlePagoConTarjetaSubasta(String id) {
        Set<String> metodos = procesador.getMetodos();
        StringBuilder sb = new StringBuilder("Metodos de pago:\n");
        PiezaEnSubasta piezaSubasta = galeria.getPiezaEnSubasta(id);
    	String ganador = piezaSubasta.getGanador();
        for (String metodo : metodos) {
            sb.append("Método: ").append(metodo).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Metodos de Pago", JOptionPane.INFORMATION_MESSAGE);

        String metodo = JOptionPane.showInputDialog(this, "Digite un metodo de pago:");
        String numero = JOptionPane.showInputDialog(this, "Digite el numero de la tarjeta:");
        String csv = JOptionPane.showInputDialog(this, "Digite el CSV de la tarjeta:");
        String fecha = JOptionPane.showInputDialog(this, "Digite la fecha de expiracion de la tarjeta:");
        subastasP.verificarPagoGanadorTarjeta((Cajero) empleado, id, numero, ganador, Integer.parseInt(csv), fecha, metodo, procesador);
    }

    private void handleHistoriaPieza() {
        String piezaId = JOptionPane.showInputDialog(this, "Digite el nombre de la pieza:");
        String historia = galeria.getHistoriaPieza(piezaId);
        JOptionPane.showMessageDialog(this, historia, "Historia de la pieza", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleHistoriaComprador() {
        String compradorId = JOptionPane.showInputDialog(this, "Digite el nombre del comprador:");
        String historia = compradores1.getHistoriaComprador((Administrador) empleado, compradorId);
        JOptionPane.showMessageDialog(this, historia, "Historia del comprador", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleHistoriaArtista() {
        String artistaId = JOptionPane.showInputDialog(this, "Digite el nombre del artista:");
        String historia = galeria.getHistoriaAutor(artistaId);
        JOptionPane.showMessageDialog(this, historia, "Historia del artista", JOptionPane.INFORMATION_MESSAGE);
    }


}
