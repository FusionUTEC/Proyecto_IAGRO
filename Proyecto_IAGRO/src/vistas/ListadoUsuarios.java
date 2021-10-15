package vistas;

import java.awt.Color;
import java.awt.Dimension;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import com.servicios.UsuarioBeanRemote;

import model.Usuario;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoUsuarios extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	
	public JPanel panel;
	public JPanel contentPane;
	public JPanel banner;
	private JLabel lblNewLabel;
	public JTable table;
	public DefaultTableModel modelo;
	private JTable table_1;
	private JTable table_2;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1_1_1;
	private JTextField textField_2;
	public JButton btnNuevo;
	public JButton btnModificar;
	public JButton btnEliminar;
	
	public HashMap<Long,Usuario> map;
	 
	
	public ListadoUsuarios() throws NamingException {
		
		//Frame
		//Estilos.Ventana(this, contentPane, panel);
		
		Color azul=new Color (104,171,196); //color azul 104,171,196 / 68abc4
		Color verde=new Color (166,187,95); //color verde 166,187,95 / a6bb5f 
		setResizable(false);
		setTitle("Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);


		//Panel Principal
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 790, 426);
		contentPane.add(panel);
		
		
		//Estilos.PanelSuperior(banner,panel);
		panel.setLayout(null);
		
		banner = new JPanel();
		banner.setBounds(0, 0, 790, 64);
		panel.add(banner);
		banner.setBackground(verde);
		banner.setLayout(null);
		
		lblNewLabel = new JLabel("LISTADO DE USUARIOS");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(231, 20, 328, 27);
		banner.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		
		
		
		// creamos el modelo de Tabla
		modelo= new DefaultTableModel() {
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		

		// se crea la Tabla con el modelo DefaultTableModel
		table = new JTable(modelo);
		table.setFont(new Font("Yu Gothic UI", Font.PLAIN, 13));
		table.setBounds(41, 110, 714, 222);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 770, 213);
		panel.add(scrollPane);
		scrollPane.setViewportView(table);
		
		lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 99, 63, 14);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(64, 98, 123, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Apellido");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(197, 96, 63, 20);
		panel.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(252, 98, 135, 20);
		panel.add(textField_1);
		
		lblNewLabel_1_1_1 = new JLabel("Usuario");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(397, 96, 63, 20);
		panel.add(lblNewLabel_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(448, 98, 135, 20);
		panel.add(textField_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(604, 97, 135, 22);
		panel.add(comboBox);
		
		JButton lupe = new JButton("");
		lupe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lupe.setBorderPainted(false);
		lupe.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		lupe.setBackground(Color.WHITE);
		lupe.setIcon(new ImageIcon("C:\\Users\\Estudio\\Desktop\\Imprimir\\Agosto\\lupa.png"));
		lupe.setBounds(749, 97, 28, 23);
		lupe.setOpaque(false);
		panel.add(lupe);
		
		btnNuevo = new JButton("Nuevo Usuario");
		btnNuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNuevo.setBorderPainted(false);
		btnNuevo.setVerticalAlignment(SwingConstants.TOP);
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnNuevo.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnNuevo.setBackground(azul);
		btnNuevo.setBounds(221, 370, 125, 27);
		panel.add(btnNuevo);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBorderPainted(false);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModificar.setVerticalAlignment(SwingConstants.TOP);
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnModificar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) verde));
		btnModificar.setBackground(verde);
		btnModificar.setBounds(356, 370, 90, 27);
		panel.add(btnModificar);
		
		
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnEliminar.setBorderPainted(false);
		btnEliminar.setBackground(verde);
		btnEliminar.setBounds(456, 370, 90, 27);
		panel.add(btnEliminar);
		
		//crea un array que contiene los nombre de las columnas
		final String[] columnNames = {"Nombre","Apellido","Correo", "Usuario", "Identificador"};
		// insertamos las columnas
		for(int column = 0; column < columnNames.length; column++){
			//agrega las columnas a la tabla
			modelo.addColumn(columnNames[column]);
		}


		
			// Se crea un array que ser� una de las filas de la tabla. 
			Object [] fila = new Object[columnNames.length]; 
			// Se carga cada posici�n del array con una de las columnas de la tabla en base de datos.
			
			UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote) InitialContext.doLookup("Proyecto/UsuarioBean!com.servicios.UsuarioBeanRemote");
			
			map = new HashMap<>();
			
			List<Usuario> usuarios = usuarioBean.obtenerTodos();
	     	   for (Usuario u: usuarios) {
	     		   
	     		  map.put(u.getIdUsuario(), u);
	     		   
	     		   fila[0]=u.getNombre();
	     		   fila[1]=u.getApellido();
	     		   fila[2]=u.getMail();
	     		   fila[3]=u.getNombreUsuario();
	     		   fila[4]=u.getIdUsuario();
	     		   modelo.addRow(fila);
	     	   }
			
			
	}
}

