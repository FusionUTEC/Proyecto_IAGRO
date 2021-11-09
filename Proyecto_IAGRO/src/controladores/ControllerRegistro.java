package controladores;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Casilla;
import com.entities.Departamento;
import com.entities.Formulario;
import com.entities.Registro;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.CasillaBeanRemote;
import com.servicios.DepartamentoBeanRemote;
import com.servicios.FormularioBeanRemote;
import com.servicios.RegistroBeanRemote;

import vistas.AltaRegistro;
import vistas.ListadoRegistro;

public class ControllerRegistro implements Constantes {
	
	public static ListadoRegistro ListaR;
	public static AltaRegistro AltaR;
	public static Formulario form;
	
	public static void main(String[] args) throws ServiciosException {
		
		AltaR = new AltaRegistro();
		AltaR.setVisible(true);
		cargarTabla();
		
	}
	
	public static void crear() {
		
		try {
			
		DepartamentoBeanRemote dptoBean = (DepartamentoBeanRemote)InitialContext.doLookup(RUTA_DepartamentoBean);
		
		//Departamento
		String dep = AltaR.comboDpto.getSelectedItem().toString();
		Departamento d = dptoBean.buscar(dep);
		
		//Fecha
		LocalDateTime fe=LocalDateTime.now();	
		Timestamp fecha= Timestamp.valueOf(fe);
		
		//Casillas
		int filas = AltaR.modelo.getRowCount();
		String[][] datos = new String[filas][];
		
		
		for(int i = 0; i<filas; i++) {
			
			String[] dato = new String[5];
			
			dato[0] = AltaR.modelo.getValueAt(i, 0).toString();
			dato[1] = AltaR.modelo.getValueAt(i, 1).toString();
			dato[2] = AltaR.modelo.getValueAt(i, 2).toString();
			dato[3] = AltaR.modelo.getValueAt(i, 3).toString();
			dato[4] = AltaR.modelo.getValueAt(i, 4).toString();
			
			datos[i] = dato;
			
		}
		
		//Usuario
		Usuario user = Main.User;
		
		Registro r = new Registro();
		r.setDepartamento(d);
		r.setFechaHora(fecha);
		r.setDatos(datos);
		r.setUsuario(user);
		
		//Enviar registro a la base
		RegistroBeanRemote regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
		regBean.crear(r);
		
		
		} catch (NamingException | ServiciosException e) {}
			
		
	}
	
	public static void cargarTabla() {

		List<Casilla> casillas = form.getCasillas();
		
		//CasillaBeanRemote casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);
		//List<Casilla> casillas = casBean.obtenerTodos();
		
		DefaultTableModel tabla = AltaR.modelo;
		
		String[] columnNames = {"Nombre","Parametro","Tipo","Unidad","Valor"};	
		for(int column = 0; column < columnNames.length; column++){
			
			tabla.addColumn(columnNames[column]);
		}

		Object [] fila = new Object[columnNames.length];
		
		for(Casilla c: casillas) {
			
			fila[0] = c.getNombre();
			fila[1] = c.getParametro();
			fila[2] = c.getTipoInput();
			fila[3] = c.getUnidadMedida();
			
			tabla.addRow(fila);
			
		}
		
		
	}
}
