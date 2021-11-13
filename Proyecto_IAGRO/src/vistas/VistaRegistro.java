package vistas;

import java.awt.Color;
import java.awt.Dimension;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Estado;
import com.entities.Formulario;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.FormularioBeanRemote;
import com.servicios.UsuarioBeanRemote;

import controladores.Constantes;
import controladores.ControllerFormulario;
import controladores.Main;

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VistaRegistro extends JFrame implements Constantes {

	private static final long serialVersionUID = 1L;



	public JPanel panel;
	public JPanel contentPane;
	public JPanel banner;

	public JTable table;
	public DefaultTableModel modelo;
	private JScrollPane scrollPane;
	public JButton btnVolver;
	public JButton btnExportarReg;


	public HashMap<Long,Formulario> map;
	public JButton btnModificar;


	public VistaRegistro() {
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
		
		JLabel lblAltaRegistro = new JLabel("REGISTRO");
		lblAltaRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltaRegistro.setForeground(Color.WHITE);
		lblAltaRegistro.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 25));
		lblAltaRegistro.setBounds(230, 22, 328, 27);
		banner.add(lblAltaRegistro);


		// creamos el modelo de Tabla
		modelo= new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				
					return false;
				
				
			}
		};


		// se crea la Tabla con el modelo DefaultTableModel
		table = new JTable(modelo);
		table.setFont(new Font("Yu Gothic UI", Font.PLAIN, 13));
		table.setBounds(41, 110, 714, 222);


		scrollPane = new JScrollPane();

		scrollPane.setBounds(34, 145, 721, 184);
		panel.add(scrollPane);
		scrollPane.setViewportView(table);

	


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
		
		btnModificar = new JButton("Guardar Cambios");
		btnModificar.setVisible(false);
		btnModificar.setVerticalAlignment(SwingConstants.TOP);
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnModificar.setBorderPainted(false);
		btnModificar.setBackground(new Color(104, 171, 196));
		btnModificar.setBounds(600, 351, 155, 27);
		panel.add(btnModificar);
		
		btnExportarReg = new JButton("Exportar Registro");
		btnExportarReg.setVerticalAlignment(SwingConstants.TOP);
		btnExportarReg.setForeground(Color.WHITE);
		btnExportarReg.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnExportarReg.setBorderPainted(false);
		btnExportarReg.setBackground(azul);
		btnExportarReg.setBounds(317, 351, 155, 27);
		panel.add(btnExportarReg);


		

		

	}
}

