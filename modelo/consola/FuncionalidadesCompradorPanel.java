package consola;

import javax.swing.*;

import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorCompradores;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import galeria.Galeria;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class FuncionalidadesCompradorPanel extends JPanel {

    private Comprador comprador;
    private ControladorOfertasFijas ofertas;
    private ControladorSubasta subastasP;
    private Galeria galeria;
    private ControladorCompradores compradores1;
    private InterfazGaleria interfazGaleria;

    public FuncionalidadesCompradorPanel(Comprador comprador, ControladorOfertasFijas ofertas, ControladorSubasta subastasP, Galeria galeria, ControladorCompradores compradores1, InterfazGaleria interfazGaleria) {
        this.comprador = comprador;
        this.ofertas = ofertas;
        this.subastasP = subastasP;
        this.galeria = galeria;
        this.compradores1 = compradores1;
        this.interfazGaleria = interfazGaleria;
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton btnOfertaPieza = new JButton("Hacer una oferta a una pieza");
        JButton btnOfertaSubasta = new JButton("Hacer una oferta a una subasta");
        JButton btnConsultarHistoriaPieza = new JButton("Consultar historia pieza");
        JButton btnConsultarHistoriaArtista = new JButton("Consultar historia artista");
        JButton btnCambiarMetodoPago = new JButton("Cambiar método de pago");
        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        

        btnOfertaPieza.addActionListener(e -> hacerOfertaPieza());
        btnOfertaSubasta.addActionListener(e -> hacerOfertaSubasta());
        btnConsultarHistoriaPieza.addActionListener(e -> consultarHistoriaPieza());
        btnConsultarHistoriaArtista.addActionListener(e -> consultarHistoriaArtista());
        btnCambiarMetodoPago.addActionListener(e -> cambiarMetodoPago());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        buttonPanel.add(btnOfertaPieza);
        buttonPanel.add(btnOfertaSubasta);
        buttonPanel.add(btnConsultarHistoriaPieza);
        buttonPanel.add(btnConsultarHistoriaArtista);
        buttonPanel.add(btnCambiarMetodoPago);
        buttonPanel.add(btnCerrarSesion);

        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private void cerrarSesion() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();

        interfazGaleria.setVisible(true);
    }

    private void hacerOfertaPieza() {
        String piezasInfo = "Piezas a oferta fija disponibles:\n";
        Collection<PiezaConPrecioFijo> piezasAoferta = galeria.getEnOfertaFija(); 
        for (PiezaConPrecioFijo pieza : piezasAoferta) {
            piezasInfo += "-------------------------\n";
            piezasInfo += "Comprador: " + pieza.getIdComprador() + "\n";
            piezasInfo += "Pieza: " + pieza.getPieza().getTitulo() + "\n";
            piezasInfo += "Precio: " + pieza.getPrecio() + "\n";
            piezasInfo += "-------------------------\n";
        }
        JTextArea textArea = new JTextArea(piezasInfo);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Piezas Disponibles", JOptionPane.INFORMATION_MESSAGE);

        String id = JOptionPane.showInputDialog(this, "Digite el nombre de la pieza que desea hacer una oferta:");
        String valorStr = JOptionPane.showInputDialog(this, "Digite el dinero que desea ofertar recuerde que debe ofrecer más del precio de la pieza:");
        int valor = Integer.parseInt(valorStr);
        ofertas.ofertarPieza(comprador, valor, id);
    }

    private void hacerOfertaSubasta() {
        String subastasInfo = "Subastas disponibles:\n";
        Collection<PiezaEnSubasta> subastas = galeria.getPiezasEnSubasta().values();
        for (PiezaEnSubasta pieza : subastas) {
            if (pieza.getEstado().equals("disponible")) {
                subastasInfo += "-------------------------\n";
                subastasInfo += "Valor inicial: " + pieza.getValorInicial() + "\n";
                subastasInfo += "Estado: " + pieza.getEstado() + "\n";
                subastasInfo += "Pieza: " + pieza.getPieza().getTitulo() + "\n";
                subastasInfo += "-------------------------\n";
            }
        }
        JTextArea textArea = new JTextArea(subastasInfo);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Subastas Disponibles", JOptionPane.INFORMATION_MESSAGE);

        String id = JOptionPane.showInputDialog(this, "Digite el nombre de la pieza que desea hacer una oferta:");
        String valorStr = JOptionPane.showInputDialog(this, "Digite el dinero que desea ofertar recuerde que debe ofrecer más del precio de la pieza:");
        int valor = Integer.parseInt(valorStr);
        subastasP.ofertarPieza(comprador, valor, id);
    }

    private void consultarHistoriaPieza() {
        String id = JOptionPane.showInputDialog(this, "Digite el nombre de la pieza:");
        String historia = galeria.getHistoriaPieza(id);
        JOptionPane.showMessageDialog(this, historia, "Historia de la Pieza", JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarHistoriaArtista() {
        String id = JOptionPane.showInputDialog(this, "Digite el nombre del artista:");
        String historia = galeria.getHistoriaAutor(id);
        JOptionPane.showMessageDialog(this, historia, "Historia del Artista", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarMetodoPago() {
        compradores1.cambiarMetodoPago(comprador);
        JOptionPane.showMessageDialog(this, "Ahora su método de pago es: " + comprador.getMetodoPago(), "Método de Pago Cambiado", JOptionPane.INFORMATION_MESSAGE);
    }
}