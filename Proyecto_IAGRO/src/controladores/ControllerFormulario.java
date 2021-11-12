package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.formula.ptg.ValueOperatorPtg;
import org.hibernate.type.LocalDateType;

import com.entities.Casilla;

import com.entities.Estado;
import com.entities.Formulario;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.CasillaBean;
import com.servicios.CasillaBeanRemote;

import com.servicios.FormularioBeanRemote;

import vistas.AltaFormulario;
import vistas.ListadoFormulario;

public class ControllerFormulario implements Constantes {

	public static AltaFormulario altaF;
	public static ListadoFormulario listF;


	//ventana Listado Formulario
	public static void  V_ListaForm () throws NamingException, ServiciosException {

		listF=new ListadoFormulario();
		listF.setVisible(true);
		obtenerTodos();
		

		listF.btnEliminar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					borrar();
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		listF.btnRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
				int id = listF.table.getSelectedRow();
				if(id != (-1)) {
					String name = listF.modelo.getValueAt(id, 1).toString();
					
					FormularioBeanRemote formBean = (FormularioBeanRemote)InitialContext.doLookup(RUTA_FormularioBean);
					
					Formulario form = formBean.buscarForm(name);
					form.getCasillas().get(0);
					ControllerRegistro.form = form;
					//
					ControllerRegistro.V_Alta_Registro();
				}
					
				} catch (NamingException e1) {}
			}
		});

		//Volver al Menú desde listado
		listF.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listF.setVisible(false);
				Main.menuP.setVisible(true);
			}
		});

		//Nuevo Formulario
		listF.btnNuevo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){


				try {
					V_AltaForm();


					//visualizar hora linda
					LocalDateTime fecha = LocalDateTime.now();
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm:ss");

					int hora=fecha.getHour();
					int min=fecha.getMinute();
					int sec= fecha.getSecond();
					String fech=fecha.format(formato);
					altaF.lblfechaHoy.setText(fech);

					String usuario=Main.User.getNombre();
					altaF.lblUser.setText(usuario);

				} catch (NamingException  |ServiciosException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();

				}
				altaF.btnRegistrar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						

						String comentario = altaF.textResumen.getText();
						//Guardar fecha y hora en BD
						DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm:ss");
						LocalDateTime fe=LocalDateTime.now();
						fe.format(formato);
						Timestamp fecha= Timestamp.valueOf(fe);
						String nombre=altaF.nombre.getText();	

						int confirm = JOptionPane.showOptionDialog(null,
								"¿Desea dar de alta el Formulario?",
								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sí se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {
							try {
								boolean todoOK=camposVacios(nombre);

								if(todoOK) {


									try {

										crear(comentario,fecha,Main.User.getIdUsuario(),nombre);
									} catch (ServiciosException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									JOptionPane.showMessageDialog(null,"Formulario registrado correctamente");
									//actualizarListado(listE.modelo);

								}

							} catch (NamingException  e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						

					}
				});
				//Volver al listado
				altaF.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listF.setVisible(true);
						altaF.setVisible(false);
					}
				});
			}

		});
		//Modificar un Formulario
		listF.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					V_ModForm();
				} catch (NamingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				//visualizar hora linda
				LocalDateTime fecha = LocalDateTime.now();
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm:ss");

				String fech=fecha.format(formato);
				String usuario=Main.User.getNombre();
				int row = listF.table.getSelectedRow();


				if( row != (-1)) {
					long id = (long) listF.table.getValueAt(row, 0);

					HashMap<Long, Formulario> map = new HashMap<>();
					List<Formulario> form;

					try {
						form = ControllerFormulario.obtenerTodos();
						for (Formulario f: form) {
							map.put(f.getIdFormulario(), f);

						}
					} catch (NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ServiciosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Formulario fo =map.get(id);


					altaF.nombre.setText(fo.getNombre());
					altaF.lblUser.setText(usuario);
					altaF.textResumen.setText(fo.getComentarios());
					altaF.lblfechaHoy.setText(fech);
					List<Casilla>casi=fo.getCasillas();
					for(Casilla c: casi) {
						altaF.map.put(c.getIdCasilla(), c);

					}

					altaF.cargarCasillas();
					//Guardar Cambios
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Formulario", null, 1);
				}

				//Volver al listado
				altaF.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listF.setVisible(true);
						altaF.setVisible(false);
					}
				});
				altaF.btnGuardar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String nom = altaF.nombre.getText();
						String res = altaF.textResumen.getText();
						//Guardar fecha y hora en BD
						LocalDateTime fe=LocalDateTime.now();	
						String nombre=altaF.nombre.getText();
						Timestamp fecha= Timestamp.valueOf(fe);			

						try {
							boolean todoOK=camposVacios(nom);

							if(todoOK) {

								try {
									ControllerFormulario.actualizar(res,fecha,Main.User.getIdUsuario(),nom);
								} catch (ServiciosException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								JOptionPane.showMessageDialog(null,"Formulario actualizado correctamente");
								actualizarListado(listF.modelo);

							}
						} catch (NamingException | ServiciosException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}


				});


			}


		});

	}

	//});
	//}

	//LISTADO DE FORMULARIOS
	public static  List<Formulario> obtenerTodos() throws NamingException, ServiciosException{
		FormularioBeanRemote formBean = (FormularioBeanRemote)
				InitialContext.doLookup(RUTA_FormularioBean);

		List<Formulario> list = formBean.obtenerTodos();
		return list;
	}

	//Ventana Modificar Formulario	
	public static void V_ModForm() throws NamingException  {

		altaF=new AltaFormulario();
		altaF.nombre.enable(false);
		listF.setVisible(false);
		altaF.setVisible(true);
		altaF.lblAltaDeFormulario.setText("MODIFICAR FORMULARIO");
		altaF.btnRegistrar.setVisible(false);
		altaF.btnGuardar.setVisible(true);

	}

	//ACTUALIZAR FORMULARIO
	public static void actualizar(String resumen,Timestamp fecha, Long idUser,String nombre) throws NamingException, ServiciosException {
		FormularioBeanRemote formBean = (FormularioBeanRemote)
				InitialContext.doLookup(RUTA_FormularioBean);


		CasillaBeanRemote CasillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);


		Formulario form = new Formulario();
		form=formBean.buscarForm(nombre);
		form.setFechaHora(fecha);
		form.setIdUsuario(idUser);
		form.setNombre(nombre);
		form.setComentarios(resumen);
		ArrayList <Casilla> casillas = new ArrayList<>();

		for (Entry<Long, Casilla> entry : altaF.map.entrySet()) {

			Casilla c = new Casilla();
			String nom = entry.getValue().getNombre();
			c = CasillaBean.buscar(nom);


			casillas.add(c);
		}
		form.setCasillas(casillas);

		try {

			formBean.actualizar(form);

			System.out.println(form.getIdFormulario() + " " + form.getNombre());
		} catch (ServiciosException e) {

			System.out.println(e.getMessage());
		}
		actualizarListado(listF.modelo);
		System.out.println("Se actualizó exitosamente el Formulario");

	}


	//Ventana Alta Formulario
	public static void V_AltaForm() throws NamingException, ServiciosException  {

		altaF=new AltaFormulario();
		altaF.setVisible(true);
		altaF.btnRegistrar.setVisible(true);
		altaF.btnGuardar.setVisible(false);
		altaF.cargarCasillas();
		listF.setVisible(false);
		Main.menuP.setVisible(false);
		//altaE.comboDpto.setModel(new DefaultComboBoxModel (CompletarCombo()));

	}

	//ACTUALIZAR LISTADO
	public static void actualizarListado(DefaultTableModel modelo) throws NamingException, ServiciosException {

		//CompletarCombo();
		int filas = modelo.getRowCount();

		for(int i = filas-1; i>=0; i--) {
			modelo.removeRow(i);
		}

		//ORDEN DE LA TABLA
		TableRowSorter<TableModel> orden=new  TableRowSorter<>(modelo);
		listF.table.setRowSorter(orden);

		final String[] columnNames = {"Identificador","Nombre","Comentarios","Fecha", "Usuario","Cantidad de Casillas"};

		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posición del array con una de las columnas de la tabla en base de datos.

		List<Formulario> form = obtenerTodos();
		for (Formulario f: form) {
			//map.put(f.getIdFormulario(), f);

			fila[0]=f.getIdFormulario();
			fila[1]=f.getNombre();
			fila[2]=f.getComentarios();
			fila[3]=f.getFechaHora();
			fila[4]=f.getIdUsuario();
			fila[5]=f.getCasillas().size();
			if  (f.getEstado().equals(Estado.ACTIVO)) {
				modelo.addRow(fila);

			}
		}

	}
	public static void crear(String comentario,Timestamp fecha, Long idUser, String nombre) throws NamingException, ServiciosException {

		FormularioBeanRemote FormularioBean = (FormularioBeanRemote)
				InitialContext.doLookup(RUTA_FormularioBean);

		CasillaBeanRemote CasillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		Formulario f = new Formulario();
		FormularioBean.buscarForm(nombre);
		ArrayList<Casilla> casillas = new ArrayList<>();
		f.setComentarios(comentario);
		f.setFechaHora(fecha);
		f.setIdUsuario(idUser);
		f.setNombre(nombre);
		f.setEstado(f.getEstado().ACTIVO);
		for (Entry<Long, Casilla> entry : altaF.map.entrySet()) {

			Casilla c = new Casilla();
			String nom = entry.getValue().getNombre();
			c = CasillaBean.buscar(nom);		

			casillas.add(c);
		}

		f.setCasillas(casillas);


		try {
			FormularioBean.crear(f);

		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizarListado(listF.modelo);

	}

	//BORRAR FORMULARIO
	public static void borrar() throws NamingException {

		FormularioBeanRemote formularioBean = (FormularioBeanRemote)
				InitialContext.doLookup(RUTA_FormularioBean);

		CasillaBeanRemote CasillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		int row = listF.table.getSelectedRow();

		if( row != (-1)) {
			String name=(String) listF.table.getValueAt(row, 1);
			try {
				int confirmado = JOptionPane.showOptionDialog(null,
						"¿Desea dar de baja el Formulario seleccionado?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null, null, null);
				//Si el usuario elige sí se borra la fila
				if (JOptionPane.OK_OPTION == confirmado) {
					Formulario form = new Formulario();
					ArrayList <Casilla> casillas = new ArrayList<>();
					form=formularioBean.buscarForm(name);
					//Setear estado a INACTIVO
					form.setEstado(form.getEstado().INACTIVO);

					formularioBean.actualizar(form);
					System.out.println("Se borró exitosamente el Formulario");

					actualizarListado(listF.modelo);
				}

			} catch (NamingException e1) {
				System.out.println("No se puede borrar el Formulario");	
				e1.printStackTrace();
			} catch (ServiciosException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un Formulario", null, 1);
		}

	}
	public static boolean camposVacios(String nombre) {

		boolean bandera = true;

		if(nombre.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Nombre", null, 1);
			return false;
		}


		return bandera;

	}

}


