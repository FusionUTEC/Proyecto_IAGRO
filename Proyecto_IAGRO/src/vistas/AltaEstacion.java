package vistas;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.entities.Departamento;
import com.exception.ServiciosException;

import controladores.ControllerEstacion;
import controladores.ControllerUsuario;

import java.awt.*;
import java.util.*;
//import org.jdatepicker.impl.DateComponentFormatter;
import java.util.List;

public class AltaEstacion extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	public JLabel lblAltaEstacion;
	private JPanel banner;
	public JTextField buscador;
	public JTextField textnombre;
	private JButton lupe;
	public JButton btnRegistrar;
	public JButton btnVolver;
	private JLabel lblNewLabel;
	private JLabel lblNombre;
	private JLabel lblCalidadAgua;
	private JLabel lblHumedadRelativa;
	public  JComboBox comboDpto;
	public JTextField textLatitud;
	public JTextField textLongitud;
	public JTextField textCalidadAgua;
	public JTextField textHumedadRelativa;
	private JLabel lblcreador;
	public JLabel lblNombreUser;
	public JButton btnGuardar;

	public  AltaEstacion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AltaEstacion.class.getResource("/vistas/Logo_original.png")));


		Color azul=new Color (104,171,196); //color azul 104,171,196 / 68abc4
		Color verde=new Color (166,187,95); //color verde 166,187,95 / a6bb5f 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 790, 426);
		contentPane.add(panel);
		panel.setLayout(null);


		banner = new JPanel();
		banner.setBounds(0, 0, 790, 64);
		panel.add(banner);
		banner.setBackground(verde);
		banner.setLayout(null);

		textnombre = new JTextField();
		textnombre.setBounds(170, 111, 116, 23);
		textnombre.setBackground(Color.LIGHT_GRAY);
		textnombre.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		textnombre.setOpaque(false); //Le quita la opacidad
		textnombre.setForeground(Color.LIGHT_GRAY);
		textnombre.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		panel.add(textnombre);

		lupe = new JButton("");
		lupe.setEnabled(false);
		lupe.setBackground(Color.WHITE);
		lupe.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		lupe.setIcon(new ImageIcon("C:\\Users\\Admin.DESKTOP-0Q23CA9\\Downloads\\lupa.png"));
		lupe.setBounds(599, 68, 29, 23);
		lupe.setOpaque(false);
		lupe.setBorder(null);
		panel.add(lupe);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrar.setBorderPainted(false);
		btnRegistrar.setVerticalAlignment(SwingConstants.TOP);
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnRegistrar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnRegistrar.setBackground(azul);
		btnRegistrar.setBounds(343, 373, 125, 27);
		panel.add(btnRegistrar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorderPainted(false);
		btnGuardar.setVerticalAlignment(SwingConstants.TOP);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnGuardar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnGuardar.setBackground(azul);
		btnGuardar.setBounds(343, 373, 125, 27);
		panel.add(btnGuardar);

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

		lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNombre.setBounds(74, 111, 62, 23);
		panel.add(lblNombre);

		JLabel lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblDepartamento.setBounds(386, 111, 101, 23);
		panel.add(lblDepartamento);

		comboDpto = new JComboBox();
		comboDpto.setBounds(533, 111, 130, 23);
		panel.add(comboDpto);
		
		JLabel lblLatitud = new JLabel("Latitud");
		lblLatitud.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblLatitud.setBounds(39, 202, 55, 23);
		panel.add(lblLatitud);

		JLabel lblLongitud = new JLabel("Longitud");
		lblLongitud.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblLongitud.setBounds(257, 202, 62, 23);
		panel.add(lblLongitud);

		lblCalidadAgua = new JLabel("Calidad del Agua");
		lblCalidadAgua.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblCalidadAgua.setBounds(481, 202, 122, 23);
		panel.add(lblCalidadAgua);

		lblHumedadRelativa = new JLabel("Humedad Relativa");
		lblHumedadRelativa.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblHumedadRelativa.setBounds(74, 292, 128, 23);
		panel.add(lblHumedadRelativa);

		textLatitud = new JTextField();
		textLatitud.setOpaque(false);
		textLatitud.setForeground(Color.LIGHT_GRAY);
		textLatitud.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		textLatitud.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		textLatitud.setBackground(Color.LIGHT_GRAY);
		textLatitud.setBounds(118, 202, 62, 23);
		panel.add(textLatitud);

		textLongitud = new JTextField();
		textLongitud.setOpaque(false);
		textLongitud.setForeground(Color.LIGHT_GRAY);
		textLongitud.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		textLongitud.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		textLongitud.setBackground(Color.LIGHT_GRAY);
		textLongitud.setBounds(343, 202, 62, 23);
		panel.add(textLongitud);

		textCalidadAgua = new JTextField();
		textCalidadAgua.setOpaque(false);
		textCalidadAgua.setForeground(Color.LIGHT_GRAY);
		textCalidadAgua.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		textCalidadAgua.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		textCalidadAgua.setBackground(Color.LIGHT_GRAY);
		textCalidadAgua.setBounds(624, 202, 62, 23);
		panel.add(textCalidadAgua);

		textHumedadRelativa = new JTextField();
		textHumedadRelativa.setOpaque(false);
		textHumedadRelativa.setForeground(Color.LIGHT_GRAY);
		textHumedadRelativa.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		textHumedadRelativa.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		textHumedadRelativa.setBackground(Color.LIGHT_GRAY);
		textHumedadRelativa.setBounds(224, 292, 62, 23);
		panel.add(textHumedadRelativa);

		lblcreador = new JLabel("Usuario Creador");
		lblcreador.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblcreador.setBounds(386, 292, 122, 23);
		panel.add(lblcreador);

		lblNombreUser = new JLabel("");
		lblNombreUser.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNombreUser.setBounds(541, 292, 87, 23);
		panel.add(lblNombreUser);

		lblAltaEstacion = new JLabel("ALTA DE ESTACIÓN");
		lblAltaEstacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaEstacion.setBounds(231, 17, 328, 27);
		banner.add(lblAltaEstacion);
		lblAltaEstacion.setForeground(Color.WHITE);
		lblAltaEstacion.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 22));
		this.setVisible(true);


		//chckbxNewCheckBox = new JCheckBox("New check box");
		//chckbxNewCheckBox.setBounds(28, 19, 160, 23);
		//panel_1.add(chckbxNewCheckBox);

		setVisible(false);
		this.setMinimumSize(new Dimension(670, 420));
		setLocationRelativeTo(null);
	}
}
