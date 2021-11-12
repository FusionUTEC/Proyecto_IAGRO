package controladores;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hibernate.engine.transaction.jta.platform.internal.JOnASJtaPlatform;
import org.hibernate.service.spi.ServiceException;

import java.awt.event.*;

import vistas.AltaRegistro;
import vistas.Importar;
import vistas.VistaRegistro;


public class ControllerImpExp implements ActionListener  {


	AltaRegistro vistaR=new AltaRegistro();
	ModelImpExp modelE=new ModelImpExp();
	JFileChooser selectArchivo=new JFileChooser();
	File archivo;
	int contAccion;

	public ControllerImpExp(AltaRegistro vistaR, ModelImpExp modelE) {
		this.vistaR=vistaR;
		this.modelE=modelE;
		this.vistaR.btnImportar.addActionListener(this);
		this.vistaR.btnExportarPlant.addActionListener(this);
	}
	public ControllerImpExp(VistaRegistro vistaR2, ModelImpExp model) {
		// TODO Auto-generated constructor stub
	}
	public void AgregarFiltro() {
		selectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
		selectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		contAccion++;
		if(contAccion==1)AgregarFiltro();

		if(e.getSource()==vistaR.btnImportar) {
			if(selectArchivo.showDialog(null, "Seleccionar archivo")==JFileChooser.APPROVE_OPTION) {
				archivo=selectArchivo.getSelectedFile();
				if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
					JOptionPane.showMessageDialog(null, modelE.Importar(archivo, vistaR.table));
				}else {
					JOptionPane.showMessageDialog(null, "Elija un formato de archivo válido *.xls o *.xlsx");
				}
			}
		}
		if(e.getSource()==vistaR.btnExportarPlant) {
			if(selectArchivo.showDialog(null, "Exportar Archivo")==JFileChooser.APPROVE_OPTION) {
				archivo=selectArchivo.getSelectedFile();
				if(archivo.getName().endsWith(".xls") || archivo.getName().endsWith(".xlsx")) {
					JOptionPane.showMessageDialog(null, modelE.Exportar(archivo,vistaR.table));
				}else {
					JOptionPane.showMessageDialog(null, "Elija un formato de archivo válido (*.xls o *.xlsx");
				}
			}

		}

		vistaR.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				;
				Main.menuP.setVisible(true);
				vistaR.setVisible(false);
			}
		});


	}
}