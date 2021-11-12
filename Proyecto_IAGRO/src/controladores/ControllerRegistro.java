package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
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
import vistas.VistaRegistro;

public class ControllerRegistro implements Constantes {
	
	public static ListadoRegistro ListaR;
	public static AltaRegistro AltaR;
	public static VistaRegistro VistaR;
	public static Formulario form;
	
	public static void V_Alta_Registro()  {
		
		try {
			AltaR = new AltaRegistro();
			cargarTabla();
			AltaR.setVisible(true);
			
			AltaR.btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					crear();
				}
			});
			
			AltaR.btnExportarPlant.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					ModelImpExp model=new ModelImpExp();
					ControllerImpExp cont=new ControllerImpExp(AltaR, model);

				}
			});
			
			AltaR.btnImportar.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					ModelImpExp model=new ModelImpExp();
					ControllerImpExp cont=new ControllerImpExp(AltaR, model);

				}
			});
			
			
			AltaR.btnVolver.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					AltaR.setVisible(false);
					Main.menuP.setVisible(true);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void V_Listado_Registro()  {
		
		ListaR = new ListadoRegistro();
		ListaR.setVisible(true);
		
		ListaR.btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int selected = ListaR.table.getSelectedRow();
				
				if(selected != (-1)) {
					
					int confirm = JOptionPane.showOptionDialog(null,
							"¿Desea borrar el registro seleccionado?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,null, null, null);	
					if (JOptionPane.YES_OPTION== confirm) {
					
					String id = ListaR.table.getValueAt(selected, 0).toString();
					
					Registro r = buscarR(id);
					r.setEstado(Estado.INACTIVO);
					
					RegistroBeanRemote regBean;
					
					try {
						regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
						regBean.actualizar(r);
						JOptionPane.showMessageDialog(null,"Registro eliminado correctamente");
					} catch (NamingException | ServiciosException e1) {}
					
					}
					
						
				}else {
					JOptionPane.showMessageDialog(null,"Debe seleccionar un registro");
				}
					
					
				
				
			}
		});
		
		ListaR.btnVisualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("Evento");
				
				DatoBeanRemote datoBean;
				RegistroBeanRemote regBean;
				try {
					
					int selected = ListaR.table.getSelectedRow();
					
					if(selected != (-1)) {
						datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
						regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
						
						String id = ListaR.modelo.getValueAt(selected,0).toString();
						Registro r = regBean.buscar(id);
						
						List<Dato> datos = datoBean.obtenerDatos(r);
						System.out.println("Entré");
						cargarVista(datos);
						
					}
					
					
				
				} catch (NamingException e1) {}
				
				
			}
		});
		
		ListaR.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ListaR.setVisible(false);
				Main.menuP.setVisible(true);
			}
		});
		
		
		
		
	}
	
	public static void V_Visualizar_Registro() {
		
		VistaR.btnExportarPlant.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				ModelImpExp model=new ModelImpExp();
				//ControllerImpExp cont=new ControllerImpExp(VistaR, model);

			}
		});
		
		
		VistaR.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VistaR.setVisible(false);
				Main.menuP.setVisible(true);
			}
		});
	}


	
	public static void crear() {
		
		try {
		
		RegistroBeanRemote regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
		DepartamentoBeanRemote dptoBean = (DepartamentoBeanRemote)InitialContext.doLookup(RUTA_DepartamentoBean);
		CasillaBeanRemote casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);
		DatoBeanRemote datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
		
		String Dep = AltaR.modelo.getValueAt(3, 4).toString().toUpperCase();
		
		Departamento d = dptoBean.buscar(Dep);
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
	
	//Para cargar plantilla
	public static void cargarTabla() {

		System.out.println("Hi");
		
		List<Casilla> casillas = form.getCasillas();
		System.out.println(casillas.get(0));
		
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
			
			System.out.println("Hola");
			
		}
		
		
		
		
	}
	

	public static void cargarVista(List<Dato> datos) {
		
		VistaR = new VistaRegistro();
		V_Visualizar_Registro();

		DefaultTableModel tabla = VistaR.modelo;
		
		String[] columnNames = {"Nombre","Parametro","Tipo","Unidad","Valor"};	
		for(int column = 0; column < columnNames.length; column++){
			
			tabla.addColumn(columnNames[column]);
		}

		Object [] fila = new Object[columnNames.length];
		
		for(Dato d: datos) {
			
			Casilla c = d.getCasilla();
				
				fila[0] = c.getNombre();
				fila[1] = c.getParametro();
				fila[2] = c.getTipoInput();
				fila[3] = c.getUnidadMedida();
				fila[4] = d.getValor();
				
				tabla.addRow(fila);
				
				System.out.println("Hola");
				
				VistaR.setVisible(true);
			
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
	
public static Registro eliminarR(String id) {
		
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


