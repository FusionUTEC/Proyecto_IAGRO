package vistas;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import controladores.Constantes;
import controladores.Main;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Importar extends JFrame implements Constantes {

	private JPanel contentPane;
	private JPanel panel;
	public JLabel lblImportExp;
	private JPanel banner;
	public JTable table;
	public DefaultTableModel modelo;
	private JScrollPane scrollPane;
	public JButton btnImportar;
	public JButton btnVolver;

	public JButton btnExportar;

	public  Importar() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AltaEstacion.class.getResource("/vistas/Logo_original.png")));


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



		btnImportar = new JButton("Importar");
		btnImportar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImportar.setBorderPainted(false);
		btnImportar.setVerticalAlignment(SwingConstants.TOP);
		btnImportar.setForeground(Color.WHITE);
		btnImportar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) azul));
		btnImportar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnImportar.setBackground(azul);
		btnImportar.setBounds(264, 370, 125, 27);
		panel.add(btnImportar);

		btnExportar = new JButton("Exportar");
		btnExportar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExportar.setBorderPainted(false);
		btnExportar.setVerticalAlignment(SwingConstants.TOP);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) verde));
		btnExportar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btnExportar.setBackground(verde);
		btnExportar.setBounds(388, 370, 125, 27);
		panel.add(btnExportar);

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
		
		lblImportExp = new JLabel("IMPORTAR-EXPORTAR ARCHIVO");
		lblImportExp.setHorizontalAlignment(SwingConstants.CENTER);
		lblImportExp.setBounds(178, 17, 381, 27);
		banner.add(lblImportExp);
		lblImportExp.setForeground(Color.WHITE);
		lblImportExp.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 22));
		this.setVisible(true);

	
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
		setVisible(false);
		this.setMinimumSize(new Dimension(670, 420));
		setLocationRelativeTo(null);

	}
	
}
