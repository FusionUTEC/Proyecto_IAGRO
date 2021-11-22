package vistas;

import java.awt.Color;
import java.awt.Dimension;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DateFormatter;

import com.entities.Departamento;
import com.entities.Estado;
import com.entities.Registro;
import com.servicios.DepartamentoBeanRemote;
import com.servicios.RegistroBeanRemote;

import controladores.Constantes;


import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JLocaleChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ListadoRegistro extends JFrame implements Constantes{

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
	private JLabel lblDep;
	public JComboBox comboDept;
	public JButton btnVolver;
	public JDateChooser calendar1;
	public JButton btnFiltrar;
<<<<<<< Updated upstream
	public TableRowSorter<TableModel> filtro;
=======
	public JDateChooser calendar2;
	public TableRowSorter<TableModel> filtro;
	public TableRowSorter<TableModel> filtro2;
>>>>>>> Stashed changes
	public JButton btnModificar;

	public JButton btnEliminar;

	public JButton btnVisualizar;

	public HashMap<Long,Registro> map;


	public ListadoRegistro()   {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListadoEstacion.class.getResource("/vistas/Logo_original.png")));

		//Frame
		//Estilos.Ventana(this, contentPane, panel);

		Color azul=new Color (104,171,196); //color azul 104,171,196 / 68abc4
		Color verde=new Color (166,187,95); //color verde 166,187,95 / a6bb5f 
		setResizable(false);
		setTitle("Registros");
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

		lblNewLabel = new JLabel("LISTADO DE REGISTROS");
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

		lblDep = new JLabel("Departamento");
		lblDep.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblDep.setBounds(10, 88, 115, 25);
		panel.add(lblDep);






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
		btnModificar.setBounds(210, 370, 90, 27);
		panel.add(btnModificar);



		btnEliminar = new JButton("Eliminar");

		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnEliminar.setBorderPainted(false);
		btnEliminar.setBackground(verde);

		btnEliminar.setBounds(492, 369, 90, 27);

		panel.add(btnEliminar);


		//crea un array que contiene los nombre de las columnas
		final String[] columnNames = {"Identificador","Formulario","Departamento","Usuario","Fecha"};		// insertamos las columnas
		for(int column = 0; column < columnNames.length; column++){
			//agrega las columnas a la tabla
			modelo.addColumn(columnNames[column]);
		}
		//ORDEN DE LA TABLA
		TableRowSorter<TableModel> orden=new  TableRowSorter<>(modelo);
		table.setRowSorter(orden);
		// Se crea un array que ser√° una de las filas de la tabla. 
		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posici√≥n del array con una de las columnas de la tabla en base de datos.

		RegistroBeanRemote RegistroBean;
		try {
			RegistroBean = (RegistroBeanRemote)
					InitialContext.doLookup(RUTA_RegistroBean);

			map = new HashMap<>();
			//ControllerEstacion.CompletarCombo();
			List<Registro> form = RegistroBean.obtenerTodos();
			for (Registro f: form) {
				map.put(f.getIdRegistro(), f);

				fila[0]=f.getIdRegistro();
				fila[1]=f.getFormulario().getNombre();
				fila[2]=f.getDepartamento().getNombre();
				fila[3]=f.getUsuario().getNombre() + " " + f.getUsuario().getApellido();
				
				Calendar calendar = GregorianCalendar.getInstance(); 
				Date fecha = f.getFechaHora();
				calendar.setTime(f.getFechaHora());
				
				int dia = calendar.get(Calendar.DAY_OF_MONTH);
				int mes = calendar.get(Calendar.MONTH)+1;
				int year = calendar.get(Calendar.YEAR); 
				int horas = calendar.get(Calendar.HOUR_OF_DAY); 
				int min = calendar.get(Calendar.MINUTE);
			
				fila[4]= corregir(dia)+"-"+corregir(mes)+"-"+year+" "+corregir(horas)+":"+corregir(min);
				if  (f.getEstado().equals(Estado.ACTIVO)) {

					modelo.addRow(fila);

				}
			}

		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DepartamentoBeanRemote depBean;
		try {
			depBean = (DepartamentoBeanRemote)
					InitialContext.doLookup(RUTA_DepartamentoBean);

			comboDept = new JComboBox();

			comboDept.setBounds(118, 91, 204, 22);
			panel.add(comboDept);

			List<Departamento> dptoList= depBean.obtenerTodos();
			comboDept.addItem("");
			for (Departamento d: dptoList) {

				String nom = d.getNombre();

				comboDept.addItem(nom);

			}	

		} catch (NamingException e) {}




		btnVisualizar = new JButton("Visualizar Registro");
		btnVisualizar.setForeground(Color.WHITE);
		btnVisualizar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnVisualizar.setBorderPainted(false);
		btnVisualizar.setBackground(azul);
		btnVisualizar.setBounds(310, 369, 169, 27);
		panel.add(btnVisualizar);

		//////////////////****************************FILTROS********************************/////////////////7

		filtro=new  TableRowSorter<>(modelo);
<<<<<<< Updated upstream
=======
		filtro2=new  TableRowSorter<>(modelo);
>>>>>>> Stashed changes
		table.setRowSorter(filtro);
		table.setRowSorter(filtro2);
		calendar1 = new JDateChooser();
		
		

<<<<<<< Updated upstream
		calendar1.setBounds(414, 91, 168, 22);
=======
		calendar1.setBounds(398, 86, 115, 20);
>>>>>>> Stashed changes
		panel.add(calendar1);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
<<<<<<< Updated upstream
		lblFecha.setBounds(357, 88, 58, 22);
=======
		lblFecha.setBounds(353, 83, 58, 25);
>>>>>>> Stashed changes
		panel.add(lblFecha);

		btnFiltrar = new JButton("Restablecer Filtros");
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnFiltrar.setBorderPainted(false);
		btnFiltrar.setBackground(new Color(104, 171, 196));
		btnFiltrar.setBounds(611, 91, 169, 22);
		panel.add(btnFiltrar);

		calendar2= new JDateChooser();
		calendar2.setBounds(559, 86, 115, 20);
		panel.add(calendar2);


		/*comboDept.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				String selected = comboDept.getSelectedItem().toString();
				if(selected != "") {
					filtro.setRowFilter(RowFilter.regexFilter(selected, 2));

				}
				else {
					filtro.setRowFilter(null);
				}

			}
	});*/

		btnFiltrar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
<<<<<<< Updated upstream
			
				calendar1.setCalendar(null);
				filtro.setRowFilter(null);
				comboDept.setSelectedIndex(0);
			}

		});
		
	
=======

				//calendario 1
				int dia=calendar1.getCalendar().get(Calendar.DAY_OF_MONTH);
				int mes=calendar1.getCalendar().get(Calendar.MONTH);
				int aÒo=calendar1.getCalendar().get(Calendar.YEAR);
				Date fecha1=new Date((aÒo-1900),mes,dia);

				//calendario 2
				int dia2=calendar1.getCalendar().get(Calendar.DAY_OF_MONTH);
				int mes2=calendar1.getCalendar().get(Calendar.MONTH);
				int aÒo2=calendar1.getCalendar().get(Calendar.YEAR);
				Date fecha2=new Date((aÒo2-1900),mes2,dia2);

				//pasada a String de calendario 1
				String d=String.valueOf(dia);
				String m=String.valueOf(mes+1);
				String a=String.valueOf(aÒo);
				String f=a+"-"+m+"-"+d;

				//pasada a String de calendario 2
				String d2=String.valueOf(dia2);
				String m2=String.valueOf(mes2+1);
				String a2=String.valueOf(aÒo2);
				String f2=a2+"-"+m2+"-"+d2;

				SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
				String fecha5=formato.format(fecha2);
				String selected = comboDept.getSelectedItem().toString();


				if(dia<10) {
					int p=0;
					String cero= String.valueOf(p);
					String di1=cero.concat(d);
					String f1=a+"-"+m+"-"+di1;
					String di2=cero.concat(d2);
					String f2_1=a2+"-"+m2+"-"+di2;

					try {
						//formato a DATE de calendario 1
						Date f3=formato.parse(f1);
						//formato a DATE de calendario 1
						Date f4=formato.parse(f2_1);
						System.out.println(f3);
						System.out.println(f4);
						//AGREGAR TODOS LOS FILTROS
						filtro.setRowFilter(RowFilter.dateFilter(ComparisonType.AFTER, f3, 4));
						filtro2.setRowFilter(RowFilter.dateFilter(ComparisonType.BEFORE, f4, 4));

						

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


			}
>>>>>>> Stashed changes




		});

	}
<<<<<<< Updated upstream
	
	public static String corregir(int v) {
		String dato;
		if(v < 10) {
			dato = "0" + v;
		}else {
			dato = v+"";
		}
		
		return dato;
	}
=======

>>>>>>> Stashed changes
}




