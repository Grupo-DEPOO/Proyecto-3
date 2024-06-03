package consola;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Pagos.ProcesarPago;
import compraSubastaPiezas.Comprador;
import compraSubastaPiezas.ControladorCompradores;
import compraSubastaPiezas.ControladorOfertasFijas;
import compraSubastaPiezas.ControladorSubasta;
import galeria.Galeria;
import staff.ControladorEmpleados;
import staff.Empleado;

public class Login implements ActionListener{

	JFrame frame = new JFrame();
	JButton loginBtn = new JButton("Login");
	JButton resetBtn = new JButton("Resetear");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("Usuario:");
	JLabel userPasswordLabel = new JLabel("Contraseña:");
	JLabel messageLabel = new JLabel();
	private ControladorEmpleados unosEmpleados;
	private ControladorCompradores compradores1;
    private ControladorOfertasFijas ofertas;
    private ControladorSubasta subastasP;
    private Galeria galeria;
    private ProcesarPago procesador;
    private InterfazGaleria interfazGaleria; 

	
	
	public Login(ControladorEmpleados unosEmpleados, ControladorCompradores compradores1, ControladorOfertasFijas ofertas, Galeria galeria, ProcesarPago procesador, ControladorSubasta subastasP, InterfazGaleria interfazGaleria) {
		this.unosEmpleados = unosEmpleados;
        this.compradores1 = compradores1;
        this.ofertas = ofertas;
        this.subastasP = subastasP;
        this.galeria = galeria;
        this.procesador = procesador;
        this.interfazGaleria = interfazGaleria;

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        loginBtn.setBounds(125, 200, 100, 25);
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(this);

        resetBtn.setBounds(225, 200, 100, 25);
        resetBtn.setFocusable(false);
        resetBtn.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginBtn);
        frame.add(resetBtn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);


        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==resetBtn) {
			userIDField.setText("");
			userPasswordField.setText("");
		}
		if(e.getSource()==loginBtn) {
			
			String user = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			
			try {
	            Empleado empleado = unosEmpleados.login(user, password);
	            JOptionPane.showMessageDialog(frame, "Inicio de sesion exitoso", "Iniciar sesion", JOptionPane.INFORMATION_MESSAGE);
	            JFrame funcionalidadesFrame = new JFrame("Funcionalidades del Empleado");
                frame.dispose();
                funcionalidadesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                funcionalidadesFrame.setSize(600, 400);
                funcionalidadesFrame.add(new FuncionalidadesEmpleadoPanel(empleado, ofertas, galeria, compradores1, subastasP, procesador, interfazGaleria));
                funcionalidadesFrame.setVisible(true);
                funcionalidadesFrame.setLocationRelativeTo(null);
	            
	        } catch (Exception a) {
	        	try {
	        		Comprador comprador = compradores1.login(user, password);
	        	    JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso", "Iniciar sesión", JOptionPane.INFORMATION_MESSAGE);
	        	    JFrame funcionalidadesFrame = new JFrame("Funcionalidades del Comprador");

	        	    frame.dispose();
	        	    funcionalidadesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	    funcionalidadesFrame.setSize(600, 400);
	        	    funcionalidadesFrame.add(new FuncionalidadesCompradorPanel(comprador, ofertas, subastasP, galeria, compradores1, interfazGaleria));
	        	    funcionalidadesFrame.setLocationRelativeTo(null);
	        	    funcionalidadesFrame.setVisible(true);
	            } catch (Exception s) {
	                JOptionPane.showMessageDialog(frame, "Error al iniciar sesión: " + s.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }

			}
			
		}	
	
	
}
