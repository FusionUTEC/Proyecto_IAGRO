package vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	public JLabel lblAltaDeUsuarios;
	public JTextField nombre;
	public JTextField apellido;
	public JTextField email;
	public JTextField nombreUsu;
	public JPasswordField contrasena;
	public JPasswordField confcontrasena;
	public JTextField reqCont;
	public JTextField cedula;
	public JTextField domicilio;
	public JTextField ciudad;
	public JTextField telefono;
	public JTextField ocupacion;
	public JButton btnRegistrar;
	public JButton btnVolver;
	public JButton btnCambiarPass;
	public JButton btnGuardar;
	public JLabel lblRol;
	public JLabel lblCedula;
	public JLabel lblDomicilio;
	public JComboBox comboRol;
	public JLabel lblOcupacion;
	public JLabel lblConfContrasea;
	public JLabel lblContrasena;
	public JLabel lblCiudad;
	public JLabel lblTelefono;
	public JLabel lblApellido;
	public JLabel lblNombre;
	public JLabel lblEmail;
	public JLabel lblNombreDeUsuario;
	public JLabel lblReqContraseña;

	/**
	 * Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					V_Alta_Usuario frame = new V_Alta_Usuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AltaUsuario() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AltaUsuario.class.getResource("/vistas/Logo_original.png")));

		Color verde=new Color (166,187,95); //color verde 166,187,95 / a6bb5f 
		Color azul=new Color (104,171,196); //color azul 104,171,196 / 68abc4

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 790, 426);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNombre.setBounds(49, 116, 84, 23);
		panel.add(lblNombre);

		nombre = new JTextField();
		nombre.setOpaque(false);
		nombre.setForeground(Color.LIGHT_GRAY);
		nombre.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		nombre.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		nombre.setBackground(Color.LIGHT_GRAY);
		nombre.setBounds(113, 116, 118, 23);
		panel.add(nombre);

		lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblApellido.setBounds(257, 116, 84, 23);
		panel.add(lblApellido);

		apellido = new JTextField();
		apellido.setOpaque(false);
		apellido.setForeground(Color.LIGHT_GRAY);
		apellido.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		apellido.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		apellido.setBackground(Color.LIGHT_GRAY);
		apellido.setBounds(321, 118, 118, 23);
		panel.add(apellido);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblEmail.setBounds(471, 116, 84, 23);
		panel.add(lblEmail);

		email = new JTextField();
		email.setOpaque(false);
		email.setForeground(Color.LIGHT_GRAY);
		email.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		email.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		email.setBackground(Color.LIGHT_GRAY);
		email.setBounds(522, 118, 200, 23);
		panel.add(email);

		lblNombreDeUsuario = new JLabel("Nombre de usuario");
		lblNombreDeUsuario.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNombreDeUsuario.setBounds(10, 194, 141, 23);
		panel.add(lblNombreDeUsuario);

		nombreUsu = new JTextField();
		nombreUsu.setOpaque(false);
		nombreUsu.setForeground(Color.LIGHT_GRAY);
		nombreUsu.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		nombreUsu.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		nombreUsu.setBackground(Color.LIGHT_GRAY);
		nombreUsu.setBounds(148, 196, 118, 23);
		panel.add(nombreUsu);

		lblContrasena = new JLabel("Contrase\u00F1a");
		lblContrasena.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblContrasena.setBounds(276, 194, 84, 23);
		panel.add(lblContrasena);

		contrasena = new JPasswordField();
		contrasena.setOpaque(false);
		contrasena.setForeground(Color.LIGHT_GRAY);
		contrasena.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		contrasena.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		contrasena.setBackground(Color.LIGHT_GRAY);
		contrasena.setBounds(361, 196, 118, 23);
		panel.add(contrasena);

		lblConfContrasea = new JLabel("Confirmar Contraseña");
		lblConfContrasea.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblConfContrasea.setBounds(489, 194, 161, 23);
		panel.add(lblConfContrasea);

		confcontrasena = new JPasswordField();
		confcontrasena.setOpaque(false);
		confcontrasena.setForeground(Color.LIGHT_GRAY);
		confcontrasena.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		confcontrasena.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		confcontrasena.setBackground(Color.LIGHT_GRAY);
		confcontrasena.setBounds(649, 196, 118, 23);
		panel.add(confcontrasena);

		lblRol = new JLabel("Rol");
		lblRol.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblRol.setBounds(68, 263, 44, 23);
		panel.add(lblRol);

		comboRol = new JComboBox();
		
		comboRol.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Investigador", "Aficionado"}));
		comboRol.setBounds(122, 266, 137, 22);
		panel.add(comboRol);

		lblCedula = new JLabel("Cedula");
		lblCedula.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblCedula.setBounds(305, 265, 72, 23);
		panel.add(lblCedula);

		cedula = new JTextField();
		cedula.setOpaque(false);
		cedula.setForeground(Color.LIGHT_GRAY);
		cedula.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		TextPrompt ced = new TextPrompt("Ej: 12345678", cedula);
		ced.setFont(new Font ("Trebuchet MS", Font.BOLD, 11));
		cedula.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		cedula.setBackground(Color.LIGHT_GRAY);
		cedula.setBounds(373, 265, 83, 23);
		panel.add(cedula);
		

		lblDomicilio = new JLabel("Domicilio");
		lblDomicilio.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblDomicilio.setBounds(507, 263, 84, 23);
		panel.add(lblDomicilio);

		domicilio = new JTextField();
		domicilio.setOpaque(false);
		domicilio.setForeground(Color.LIGHT_GRAY);
		domicilio.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		domicilio.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		domicilio.setBackground(Color.LIGHT_GRAY);
		domicilio.setBounds(590, 265, 110, 23);
		panel.add(domicilio);

		ciudad = new JTextField();
		ciudad.setOpaque(false);
		ciudad.setForeground(Color.LIGHT_GRAY);
		ciudad.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		ciudad.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		ciudad.setBackground(Color.LIGHT_GRAY);
		ciudad.setBounds(106, 332, 110, 23);
		panel.add(ciudad);

		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblCiudad.setBounds(49, 332, 65, 23);
		panel.add(lblCiudad);

		ocupacion = new JTextField();
		ocupacion.setOpaque(false);
		ocupacion.setForeground(Color.LIGHT_GRAY);
		ocupacion.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		ocupacion.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		ocupacion.setBackground(Color.LIGHT_GRAY);
		ocupacion.setBounds(590, 332, 110, 23);
		panel.add(ocupacion);

		lblOcupacion = new JLabel("Ocupación");
		lblOcupacion.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblOcupacion.setBounds(507, 330, 95, 23);
		panel.add(lblOcupacion);

		lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblTelefono.setBounds(257, 330, 110, 23);
		panel.add(lblTelefono);

		telefono = new JTextField();
		telefono.setOpaque(false);
		telefono.setForeground(Color.LIGHT_GRAY);
		telefono.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		telefono.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		telefono.setBackground(Color.LIGHT_GRAY);
		telefono.setBounds(329, 332, 110, 23);
		panel.add(telefono);

		btnVolver = new JButton("Volver");
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.setBorderPainted(false);
		btnVolver.setVerticalAlignment(SwingConstants.TOP);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBounds(10, 369, 52, 35);		
		Image volver = new ImageIcon(this.getClass().getResource("volver1.png")).getImage();
		btnVolver.setIcon(new ImageIcon(volver));
		btnVolver.setBackground(Color.WHITE);
		btnVolver.setBorder(null);
		btnVolver.setOpaque(false);
		panel.add(btnVolver);


		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrar.setBorderPainted(false);
		btnRegistrar.setVerticalAlignment(SwingConstants.TOP);
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnRegistrar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnRegistrar.setBackground(azul);
		btnRegistrar.setBounds(295, 377, 110, 27);
		panel.add(btnRegistrar);

		btnCambiarPass = new JButton("Cambiar Contraseña");
		btnCambiarPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCambiarPass.setBorderPainted(false);
		btnCambiarPass.setVerticalAlignment(SwingConstants.TOP);
		btnCambiarPass.setForeground(Color.WHITE);
		btnCambiarPass.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnCambiarPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnCambiarPass.setBackground(verde);
		btnCambiarPass.setBounds(413, 377, 142, 27);
		panel.add(btnCambiarPass);


		btnGuardar = new JButton("Guardar");
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorderPainted(false);
		btnGuardar.setVerticalAlignment(SwingConstants.TOP);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnGuardar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnGuardar.setBackground(azul);
		btnGuardar.setBounds(298, 377, 118, 27);
		panel.add(btnGuardar);

		JPanel banner = new JPanel();
		banner.setBackground(verde);
		banner.setBounds(0, 0, 790, 64);
		panel.add(banner);
		banner.setLayout(null);

		lblAltaDeUsuarios = new JLabel("ALTA DE USUARIO");
		lblAltaDeUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaDeUsuarios.setBounds(231, 20, 328, 27);
		banner.add(lblAltaDeUsuarios);
		lblAltaDeUsuarios.setForeground(Color.WHITE);
		lblAltaDeUsuarios.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 22));
		
		
		

		if(comboRol.getSelectedItem().equals("Aficionado")) {
			cedula.setVisible(false);
			lblCedula.setVisible(false);
			lblDomicilio.setVisible(false);
			domicilio.setVisible(false);
			ciudad.setVisible(false);
			lblCiudad.setVisible(false);
			lblTelefono.setVisible(false);
			telefono.setVisible(false);



		}
	}
}
