package vistas;

import java.awt.Color;
import java.awt.Dimension;

import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.entities.Casilla;
import com.entities.Estacion;
import com.entities.Estado;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.CasillaBeanRemote;
import com.servicios.EstacionBeanRemote;
import com.servicios.UsuarioBeanRemote;

import controladores.Constantes;

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ListadoCasilla extends JFrame implements Constantes{

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
	private JTextField textFieldNombre;
	public JComboBox comboTipoCasilla;
	public JButton btnVolver;
	public JButton btnNuevo;
	public JButton btnModificar;
	public JButton btnEliminar;

	public HashMap<Long,Casilla> map;


	public ListadoCasilla() throws ServiciosException  {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListadoCasilla.class.getResource("/vistas/Logo_original.png")));

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

		lblNewLabel = new JLabel("LISTADO DE CASILLAS");
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

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(74, 98, 123, 20);
		panel.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Tipo de dato");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(219, 96, 104, 20);
		panel.add(lblNewLabel_1_1);

		JButton lupe = new JButton("");
		lupe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lupe.setBorderPainted(false);
		lupe.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		lupe.setBackground(Color.WHITE);
		lupe.setIcon(new ImageIcon("C:\\Users\\Estudio\\Desktop\\Imprimir\\Agosto\\lupa.png"));
		lupe.setBounds(749, 97, 28, 23);
		lupe.setOpaque(false);
		panel.add(lupe);

		btnNuevo = new JButton("Nueva Casilla");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNuevo.setBorderPainted(false);
		btnNuevo.setVerticalAlignment(SwingConstants.TOP);
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnNuevo.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnNuevo.setBackground(azul);
		btnNuevo.setBounds(221, 370, 125, 27);
		panel.add(btnNuevo);

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

		comboTipoCasilla = new JComboBox();
		comboTipoCasilla.setBounds(321, 98, 141, 20);
		panel.add(comboTipoCasilla);

		//crea un array que contiene los nombre de las columnas
		final String[] columnNames = {"Nombre","Descripción","Parametro", "Tipo Input", "Unidad de Medida", "Identificador"};
		// insertamos las columnas
		for(int column = 0; column < columnNames.length; column++){
			//agrega las columnas a la tabla
			modelo.addColumn(columnNames[column]);
		}

		// Se crea un array que será una de las filas de la tabla. 
		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posición del array con una de las columnas de la tabla en base de datos.

		CasillaBeanRemote casillaBean;
		try {
			casillaBean = (CasillaBeanRemote)
					InitialContext.doLookup(RUTA_CasillaBean);
			map = new HashMap<>();
			//ControllerCasilla.CompletarCombo();
			List<Casilla> casilla = casillaBean.obtenerTodos();
			//comboTipoCasilla.setModel(new DefaultComboBoxModel (ControllerCasilla.CompletarCombo()));
			for (Casilla c: casilla) {
				// String ca=Math.toIntExact(c.getTipoImput());
				map.put(c.getIdCasilla(), c);

				fila[0]=c.getNombre();
				fila[1]=c.getDescripcion();
				fila[2]=c.getParametro();
				fila[3]=c.getTipoInput();
				fila[4]=c.getUnidadMedida();
				fila[5]=c.getIdCasilla();
				if  (c.getEstado().equals(Estado.ACTIVO)) {
					modelo.addRow(fila);
				}
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();


		}
	}
}


