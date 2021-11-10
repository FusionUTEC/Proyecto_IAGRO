package controladores;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hibernate.engine.transaction.jta.platform.internal.JOnASJtaPlatform;

import java.awt.event.*;
import vistas.Importar;

public class ControllerImpExp implements ActionListener{
	//public static Importar vistaE;	

	Importar vistaE=new Importar();
	ModelImpExp modelE=new ModelImpExp();
	JFileChooser selectArchivo=new JFileChooser();
	File archivo;
	int contAccion;

	public ControllerImpExp(Importar vistaE, ModelImpExp modelE) {
		this.vistaE=vistaE;
		this.modelE=modelE;
		this.vistaE.btnImportar.addActionListener(this);
		this.vistaE.btnExportar.addActionListener(this);
	}
	public void AgregarFiltro() {
		selectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
		selectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		contAccion++;
		if(contAccion==1)AgregarFiltro();

		if(e.getSource()==vistaE.btnImportar) {
			if(selectArchivo.showDialog(null, "Seleccionar archivo")==JFileChooser.APPROVE_OPTION) {
				archivo=selectArchivo.getSelectedFile();
				if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
					JOptionPane.showMessageDialog(null, modelE.Importar(archivo, vistaE.table));
				}else {
					JOptionPane.showMessageDialog(null, "Elija un formato de archivo válido (*.xls o *.xlsx");
				}
			}
		}
		if(e.getSource()==vistaE.btnExportar) {
			if(selectArchivo.showDialog(null, "Exportar Archivo")==JFileChooser.APPROVE_OPTION) {
				archivo=selectArchivo.getSelectedFile();
				if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
					JOptionPane.showMessageDialog(null, modelE.Exportar(archivo, vistaE.table));
				}else {
					JOptionPane.showMessageDialog(null, "Elija un formato de archivo válido (*.xls o *.xlsx");
				}
			}
		}


		vistaE.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				;
				Main.menuP.setVisible(true);
				vistaE.setVisible(false);
			}
		});
	}
}