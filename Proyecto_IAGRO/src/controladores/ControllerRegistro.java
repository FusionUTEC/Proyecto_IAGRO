package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
			ControllerFormulario.listF.setVisible(false);


			AltaR.btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					int confirm = JOptionPane.showOptionDialog(null,
							"�Desea confirmar el registro?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,null, null, null);	
					if (JOptionPane.YES_OPTION== confirm) {
						crear();
					}
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
					ControllerFormulario.listF.setVisible(true);
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
							"�Desea borrar el registro seleccionado?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,null, null, null);	
					if (JOptionPane.YES_OPTION== confirm) {

						String id = ListaR.table.getValueAt(selected, 0).toString();

						Registro r = buscarR(id);
						r.setEstado(Estado.INACTIVO);

						JOptionPane.showMessageDialog(null, "Registro eliminado con �xito");
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
					System.out.println("Fila seleccionada "+selected);

					if(selected != (-1)) {
						datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
						regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);

						String id = ListaR.modelo.getValueAt(selected,0).toString();
						System.out.println("ID registro "+id);
						Registro r = regBean.buscar(id);

						List<Dato> datos = datoBean.obtenerDatos(r);
						cargarVista(datos);

					}



				} catch (NamingException e1) {}


			}
		});
		
		ListaR.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Boolean afi = Main.User.getTipo() == "Aficionado";
				
				
				
				DatoBeanRemote datoBean;
				RegistroBeanRemote regBean;
				try {

					int selected = ListaR.table.getSelectedRow();
					System.out.println("Fila seleccionada "+selected);

					if(selected != (-1)) {
						datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
						regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);

						String id = ListaR.modelo.getValueAt(selected,0).toString();
						System.out.println("ID registro "+id);
						Registro r = regBean.buscar(id);
						
						Usuario rU = r.getUsuario();
						
						Boolean noDue�o = Main.User.getIdUsuario() != rU.getIdUsuario(); 
						
						if(afi && noDue�o) {
							JOptionPane.showMessageDialog(null, "No tiene permiso para modificar este registro");
						}else {
							List<Dato> datos = datoBean.obtenerDatos(r);
							cargarVista(datos);
							
							///Guardar cambios
							VistaR.btnModificar.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									
									try {
										regBean.actualizar(r);
									} catch (ServiciosException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
								}
							});
						}

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
		
		VistaR.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
			}
		});

		
		VistaR.btnExportarReg.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				ModelImpExp model=new ModelImpExp();
				ControllerImpExp cont=new ControllerImpExp(VistaR, model);
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

			String Dep = AltaR.modelo.getValueAt(0, 4).toString().toUpperCase();

			Departamento d = dptoBean.buscar(Dep);

			
			  //convert String to LocalDate
			
			
			//Fecha
			String date = AltaR.modelo.getValueAt(1, 4).toString();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
			Timestamp fecha= Timestamp.valueOf(dateTime);

			//Casillas
			int filas = AltaR.modelo.getRowCount();



			//Usuario
			Usuario user = Main.User;

			Registro r = new Registro();
			r.setFechaHora(fecha);
			r.setUsuario(user);
			r.setFormulario(form);
			r.setDepartamento(d);
			r.setEstado(Estado.ACTIVO);

			//Enviar registro a la base
			if(d==null)	{
				JOptionPane.showMessageDialog(null, "El departamento ingresado no es correcto");
			}else {
				r = regBean.crear(r);

				//Datos de medici�n
				for(int i = 3; i<filas; i++) {

					Dato dato = new Dato();
					dato.setRegistro(r);

					Casilla c = casBean.buscar(AltaR.modelo.getValueAt(i, 0).toString());
					dato.setCasilla(c);
					System.out.println("Registr� la casilla "+c.getNombre());

					dato.setValor(AltaR.table.getValueAt(i, 4).toString());

					datoBean.crear(dato);

					
				}

				JOptionPane.showMessageDialog(null, "Registro ingresado con �xito");
			}



		} catch (NamingException | ServiciosException e) {}


	}

	//Para cargar plantilla
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


		moverFila("DEPARTAMENTO", 0, tabla);
		moverFila("FECHA Y HORA", 1, tabla);
		moverFila("USUARIO", 2, tabla);
		moverFila("COMENTARIOS", 3, tabla);


	}


	public static void cargarVista(List<Dato> datos) {

		VistaR = new VistaRegistro();
		V_Visualizar_Registro();
		ListaR.setVisible(false);

		try {
			CasillaBeanRemote casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DefaultTableModel tabla = VistaR.modelo;

		String[] columnNames = {"Nombre","Parametro","Tipo","Unidad","Valor"};	
		for(int column = 0; column < columnNames.length; column++){

			tabla.addColumn(columnNames[column]);
		}

		Object [] fila = new Object[columnNames.length];
		System.out.println("Cantidad de datos registrados "+datos.size());
		Registro reg = datos.get(0).getRegistro();
		
		String dep = reg.getDepartamento().getNombre();
		String fecha = reg.getFechaHora().toString();
		String user = reg.getUsuario().getNombreUsuario();
		
	
		addFila("DEPARTAMENTO", dep, tabla, fila);
		addFila("FECHA Y HORA", fecha, tabla, fila);
		addFila("USUARIO",user, tabla, fila);
		
		
	

		for(Dato d: datos) {

			Casilla c = d.getCasilla();

			fila[0] = c.getNombre();
			fila[1] = c.getParametro();
			fila[2] = c.getTipoInput();
			fila[3] = c.getUnidadMedida();
			fila[4] = d.getValor();

			tabla.addRow(fila);
			System.out.println("Cargu� la casilla "+c.getNombre());

		}
		
		VistaR.setVisible(true);

		/*moverFila("DEPARTAMENTO", 0, tabla);
		moverFila("FECHA Y HORA", 1, tabla);
		moverFila("USUARIO", 2, tabla);
		moverFila("COMENTARIOS", 3, tabla);*/

	}

	public static void moverFila(String nom, int pos, DefaultTableModel tabla) {
		for(int i = 0; i < tabla.getRowCount(); i++){

			if(tabla.getValueAt(i, 0).equals(nom)){

				tabla.moveRow(i, i, pos);

			}
		}
	}
	
	public static void addFila(String nom, String dato, DefaultTableModel tabla, Object [] fila) {
		fila[0] = nom;
		fila[1] = "N/A";
		fila[2] = "N/A";
		fila[3] = "N/A";
		fila[4] = dato;
		
		tabla.addRow(fila);
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


