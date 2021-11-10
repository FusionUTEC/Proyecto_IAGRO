package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Casilla;
import com.entities.Dato;
import com.entities.Departamento;
import com.entities.Estado;
import com.entities.Formulario;
import com.entities.Registro;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.CasillaBeanRemote;
import com.servicios.DatoBeanRemote;
import com.servicios.DepartamentoBeanRemote;
import com.servicios.FormularioBeanRemote;
import com.servicios.RegistroBeanRemote;

import vistas.AltaRegistro;
import vistas.ListadoRegistro;

public class ControllerRegistro implements Constantes {
	
	public static ListadoRegistro ListaR;
	public static AltaRegistro AltaR;
	public static Formulario form;
	
	
	
	public static void V_Listado_Registro() throws ServiciosException {
		
		ListaR = new ListadoRegistro();
		ListaR.setVisible(true);
		
		
		
		
	}
	
	
	
	public static void crear() {
		
		try {
		
		RegistroBeanRemote regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
		DepartamentoBeanRemote dptoBean = (DepartamentoBeanRemote)InitialContext.doLookup(RUTA_DepartamentoBean);
		CasillaBeanRemote casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);
		DatoBeanRemote datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
		
		Departamento d = dptoBean.buscar("MONTEVIDEO");
		//dptoBean.buscar(RUTA_CasillaBean)
		
		
		//Fecha
		LocalDateTime fe=LocalDateTime.now();	
		Timestamp fecha= Timestamp.valueOf(fe);
		
		//Casillas
		int filas = AltaR.modelo.getRowCount();
	
		
		
		
		
		//Usuario
		Usuario user = Main.User;
		
		Registro r = new Registro();
		//r.setDepartamento(d);
		r.setFechaHora(fecha);
		r.setUsuario(user);
		r.setFormulario(form);
		r.setDepartamento(d);
		r.setEstado(Estado.ACTIVO);
		
		//Enviar registro a la base
		
		r = regBean.crear(r);
		
		//Datos de medición
				for(int i = 0; i<filas; i++) {
					
				Dato dato = new Dato();
				dato.setRegistro(r);
				
				Casilla c = casBean.buscar(AltaR.modelo.getValueAt(i, 0).toString());
				dato.setCasilla(c);
				
				dato.setValor(AltaR.table.getValueAt(i, 4).toString());
					
				datoBean.crear(dato);
					
				}
		
		
		} catch (NamingException | ServiciosException e) {}
			
		
	}
	
	public static void cargarTabla() {

		List<Casilla> casillas = form.getCasillas();
		
		//CasillaBeanRemote casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);
		//List<Casilla> casillas = casBean.obtenerTodos();
		
		try {
			AltaR = new AltaRegistro();
			AltaR.setVisible(true);
			
			AltaR.btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					crear();
				}
			});
			
			AltaR.btnVolver.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					AltaR.setVisible(false);
					Main.menuP.setVisible(true);
				}
			});
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	
	public static Registro buscarR(String id) {
		
		RegistroBeanRemote regBean;
		try {
			regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
			Registro r = regBean.buscar(id);
			
			return r;
		} catch (NamingException e) {
			return null;
		}	
			
	}
	
}


