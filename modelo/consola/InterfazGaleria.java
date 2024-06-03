package consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import compraSubastaPiezas.PiezaConPrecioFijo;
import compraSubastaPiezas.PiezaEnSubasta;
import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import compraSubastaPiezas.ControladorCompradores;
import compraSubastaPiezas.Oferta;
import staff.Administrador;
import staff.Cajero;
import Pagos.*;
import staff.ControladorEmpleados;
import galeria.Galeria;
import persistencia.CentralPersistencia;
import persistencia.IPersistencia;
import piezas.Pieza;
import piezas.PiezaFisica;
import piezas.PiezaVirtual;
import staff.Empleado;
import staff.Operador;

public class InterfazGaleria extends JFrame {

    private ControladorEmpleados unosEmpleados;
    private ControladorOfertasFijas ofertas;
    private ControladorSubasta subastasP;
    private Galeria galeria;
    private ControladorCompradores compradores1;
    private ProcesarPago procesador;
    private Login login;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();

    public InterfazGaleria() {
        setTitle("Galería de Arte");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnSalvar = new JButton("Salvar datos");
        JButton btnSalir = new JButton("Salir");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = new Login(unosEmpleados, compradores1, ofertas, galeria, procesador, subastasP, InterfazGaleria.this);
                login.frame.setVisible(true);
                dispose();
            }
        });
        btnSalvar.addActionListener(e -> salvarDatos());
        btnSalir.addActionListener(e -> System.exit(0));

        menuPanel.add(btnLogin);
        menuPanel.add(btnSalvar);
        menuPanel.add(btnSalir);

        mainPanel.add(menuPanel, "Menu");

      

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
    }
    

    public void cargarDatos() {
        try {
            unosEmpleados = new ControladorEmpleados();
            galeria = new Galeria();
            unosEmpleados.cargarEmpleados("./datos/datos2.json");
            procesador = new ProcesarPago("./datos/metodos.config");
            galeria.cargarDatos("./datos/datos.json");
            subastasP = new ControladorSubasta(galeria);
            ofertas = new ControladorOfertasFijas(galeria);
            compradores1 = new ControladorCompradores(galeria);
            ofertas.cargarOfertas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarDatos() {
        try {
            IPersistencia salvador = CentralPersistencia.getPersistencia();
            salvador.guardarDatosJSON("./datos/datos2.json", unosEmpleados, galeria);
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.", "Salvar Datos", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	InterfazGaleria app = new InterfazGaleria();
            app.cargarDatos();
            app.setVisible(true);
        });
    }
}