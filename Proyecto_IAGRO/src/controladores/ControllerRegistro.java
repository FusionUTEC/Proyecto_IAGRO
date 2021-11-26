package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
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
	public static Registro reg;

	public static void V_Alta_Registro()  {

		try {
			AltaR = new AltaRegistro();
			cargarTabla();
			AltaR.setVisible(true);
			ControllerFormulario.listF.setVisible(false);
			
			AltaR.modelo.setValueAt(Main.User.getNombreUsuario(), 2, 4);


			AltaR.btnRegistrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					int confirm = JOptionPane.showOptionDialog(null,
							"¿Desea confirmar el registro?",
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
		
		if(ListaR == null) {
			ListaR = new ListadoRegistro();
		}
		
		ListaR.setVisible(true);
		ListaR.perfil(Main.User.getTipo());
		ListaR.calendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				if(ListaR.calendar1.getCalendar() != null) {
					int dia=ListaR.calendar1.getCalendar().get(Calendar.DAY_OF_MONTH);
					int mes=ListaR.calendar1.getCalendar().get(Calendar.MONTH)+1;
					int año=ListaR.calendar1.getCalendar().get(Calendar.YEAR);
				
					//SimpleDateFormat formato=new SimpleDateFormat("dd-MM-yyyy HH:mm");
					String fecha5= corregir(dia)+"-"+corregir(mes)+"-"+año;
					
					
					if(	fecha5!=" " ) {
						ListaR.filtro.setRowFilter(RowFilter.regexFilter(fecha5,4));

					}
				}
				
				
			}
		});

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
							V_Listado_Registro();
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

				Boolean afi = Main.User.getTipo().contains("Aficionado") ;


				DepartamentoBeanRemote deptoBean;
				DatoBeanRemote datoBean;
				CasillaBeanRemote casBean;
				RegistroBeanRemote regBean;
				try {

					int selected = ListaR.table.getSelectedRow();
					System.out.println("Fila seleccionada "+selected);

					if(selected != (-1)) {
						datoBean = (DatoBeanRemote)InitialContext.doLookup(RUTA_DatoBean);
						regBean = (RegistroBeanRemote)InitialContext.doLookup(RUTA_RegistroBean);
						deptoBean = (DepartamentoBeanRemote)InitialContext.doLookup(RUTA_DepartamentoBean);
						casBean = (CasillaBeanRemote)InitialContext.doLookup(RUTA_CasillaBean);

						String id = ListaR.table.getValueAt(selected,0).toString();
						System.out.println("ID registro "+id);
						Registro r = regBean.buscar(id);

						Usuario rU = r.getUsuario();

						Boolean noDueño = Main.User.getIdUsuario() != rU.getIdUsuario(); 

						if(afi && noDueño) {
							JOptionPane.showMessageDialog(null, "No tiene permiso para modificar este registro");
						}else {
							List<Dato> datos = datoBean.obtenerDatos(r);
							cargarVista(datos);
							VistaR.btnModificar.setVisible(true);
							VistaR.btnExportarReg.setVisible(false);
							VistaR.edit = true;

							reg = r;

							///Guardar cambios
							VistaR.btnModificar.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {

									int confirm = JOptionPane.showOptionDialog(null,
											"¿Desea guardar los cambios?",
											"Exit Confirmation", JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE,null, null, null);	
									if (JOptionPane.YES_OPTION== confirm) {
										int tope = VistaR.modelo.getRowCount();
										try {
										for(int i = 0; i<tope ; i++) {
											String dato = VistaR.modelo.getValueAt(i, 0).toString();
											String valor = VistaR.modelo.getValueAt(i, 4).toString();

											switch(dato) {

											case "USUARIO":
												break;

											case "FECHA Y HORA":
												DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
												LocalDateTime dateTime = LocalDateTime.parse(valor, formatter);
												Timestamp fecha= Timestamp.valueOf(dateTime);
												r.setFechaHora(fecha);
												break;

											case "DEPARTAMENTO":
												Departamento d = deptoBean.buscar(valor.toUpperCase());
												r.setDepartamento(d);
												break;

											default:
												Casilla c = casBean.buscar(dato);
												datoBean.actualizar(r, c, valor);
												break;
											}
										}

										if(r.getDepartamento()!= null) {
											regBean.actualizar(r);
											JOptionPane.showMessageDialog(null, "Registro modificado con éxito");
										}else {
											JOptionPane.showMessageDialog(null, "No existe el departamento ingresado");
										}
										
										} catch (ServiciosException e1) {}
										catch(DateTimeParseException e2) {
											JOptionPane.showMessageDialog(null, "Formato de fecha no valido");
										}
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
				//Main.menuP.setVisible(true);
				V_Listado_Registro();
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

				//Datos de medición
				for(int i = 3; i<filas; i++) {

					Dato dato = new Dato();
					dato.setRegistro(r);

					Casilla c = casBean.buscar(AltaR.modelo.getValueAt(i, 0).toString());
					dato.setCasilla(c);
					System.out.println("Registré la casilla "+c.getNombre());

					dato.setValor(AltaR.table.getValueAt(i, 4).toString());

					datoBean.crear(dato);


				}

				JOptionPane.showMessageDialog(null, "Registro ingresado con éxito");
			}



		} catch (NamingException | ServiciosException e) {}
		catch(DateTimeParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha no valido");
		}


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
		VistaR.edit = false;
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
		
		Calendar calendar = GregorianCalendar.getInstance(); 
		Date fecha = reg.getFechaHora();
		calendar.setTime(fecha);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR); 
		int horas = calendar.get(Calendar.HOUR_OF_DAY); 
		int min = calendar.get(Calendar.MINUTE);
		
		String date = corregir(dia)+"-"+corregir(mes)+"-"+year+" "+corregir(horas)+":"+corregir(min);
		
	
		String user = reg.getUsuario().getNombreUsuario();


		addFila("DEPARTAMENTO", dep, tabla, fila);
		addFila("FECHA Y HORA", date, tabla, fila);
		addFila("USUARIO",user, tabla, fila);




		for(Dato d: datos) {

			Casilla c = d.getCasilla();

			fila[0] = c.getNombre();
			fila[1] = c.getParametro();
			fila[2] = c.getTipoInput();
			fila[3] = c.getUnidadMedida();
			fila[4] = d.getValor();

			tabla.addRow(fila);
			System.out.println("Cargué la casilla "+c.getNombre());

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
	
	public static String corregir(int v) {
		String dato;
		if(v < 10) {
			dato = "0" + v;
		}else {
			dato = v+"";
		}
		
		return dato;
	}

}


