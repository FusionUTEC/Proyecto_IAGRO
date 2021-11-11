package controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ModelImpExp  {

	Workbook wb;

	public String Importar (File archivo, JTable tablaD) {
		String respuesta="No se pudo realizar la importación";
		DefaultTableModel modeloT= new DefaultTableModel();
		tablaD.setModel(modeloT);
		try {
			wb=WorkbookFactory.create(new FileInputStream(archivo));
			//Asignarle hoja
			Sheet hoja=wb.getSheetAt(0);
			Iterator filaIterator=hoja.rowIterator();
			int indiceFila=-1;

			while (filaIterator.hasNext()){
				indiceFila++;
				Row fila=(Row)filaIterator.next();
				//Recorrer filas o celdas
				Iterator columnaIterator=fila.cellIterator();
				Object [] listaColumna=new Object[5];
				int indiceColumna=-1;

				while(columnaIterator.hasNext()){
					indiceColumna++;
					Cell celda=(Cell) columnaIterator.next();
					if(indiceFila==0) {
						modeloT.addColumn(celda.getStringCellValue());
					}else {
						if(celda!=null) {
							switch(celda.getCellType()) {
							case NUMERIC:
								listaColumna[indiceColumna]=(int)Math.round(celda.getNumericCellValue());
								break;
							case STRING:
								listaColumna[indiceColumna]=celda.getStringCellValue();
								break;
							default:
								listaColumna[indiceColumna]=celda.getDateCellValue();
								break;
							}
						}
					}
				}
				//SI EL INDICE=0 SE LE AGREGA EL NOMBRE DE CADA COLUMNA.
				if(indiceFila!=0)modeloT.addRow(listaColumna);

			}
			respuesta="Importación Exitosa";
		}catch (Exception e) {

		}
		return respuesta;
	}

	public String Exportar (File archivo, JTable tablaD) {
		String respuesta="No se pudo realizar la exportación";
		int numFila = tablaD.getRowCount();
		int numColumna=tablaD.getColumnCount();
		//Verificar Extensión del archivo 
		if (archivo.getName().endsWith(".xls")) {
			wb=new HSSFWorkbook();
		}else {
			wb=new XSSFWorkbook();
		}
		Sheet hoja=wb.createSheet("Prueba");
		try {
			for (int i=-1; i<numFila;i++) {
				Row fila=hoja.createRow(i+1);
				//Row fila=hoja.getRow(i);
				for(int j=0;j<numColumna;j++) {
					Cell celda=fila.createCell(j);
					if(i==-1) {
						celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
					}else {
						celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));

					}
					wb.write(new FileOutputStream(archivo));
				}

			}
			respuesta="Exportación exitosa";

		}catch (Exception e) {

		}


		return respuesta;
	}



}
