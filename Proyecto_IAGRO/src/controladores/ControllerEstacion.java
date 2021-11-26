package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.collections.EnumerationUtils;

import com.entities.Departamento;
import com.entities.Estacion;
import com.entities.Estado;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.DepartamentoBeanRemote;
import com.servicios.EstacionBean;
import com.servicios.EstacionBeanRemote;
import com.servicios.UsuarioBeanRemote;

import vistas.AltaEstacion;
import vistas.AltaUsuario;
import vistas.ListadoEstacion;
import vistas.ListadoUsuarios;

public class ControllerEstacion implements Constantes{

	public static AltaEstacion altaE;
	public static ListadoEstacion listE;


	//ventana Listado Estación
	public static void  V_ListaEst () throws NamingException, ServiciosException {

		if(listE == null) {
			listE=new ListadoEstacion();
		}
		
		listE.setVisible(true);

		obtenerTodos();

		listE.btnEliminar.addMouseListener(new MouseAdapter() {
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

		//Volver al Menú desde listado
		listE.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listE.setVisible(false);
				Main.menuP.setVisible(true);
			}
		});
		//Nueva Estación
		listE.btnNuevo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){


				try {
					V_AltaEstacion();
					String usuario=Main.User.getNombre();
					altaE.lblNombreUser.setText(usuario);
					System.out.println(usuario);
				} catch (NamingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ServiciosException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				altaE.btnRegistrar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String nom = altaE.textnombre.getText();
						int dpto=altaE.comboDpto.getSelectedIndex();
						String latitud = altaE.textLatitud.getText();
						String longitud = altaE.textLongitud.getText();
						String calAgua = altaE.textCalidadAgua.getText();
						String humedad = altaE.textHumedadRelativa.getText();

						int confirm = JOptionPane.showOptionDialog(null,
								"¿Desea dar de alta la Estación?",
								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sí se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {
							//hacer para los otros atributos
							try {
								boolean todoOK=camposVacios(nom,latitud,longitud,calAgua,humedad);

								if(todoOK) {
									todoOK=validarFormatos(longitud, latitud, calAgua,humedad);
									if (todoOK) {

										crear(nom, Long.valueOf(dpto), Float.valueOf(latitud),Float.valueOf(longitud), Float.valueOf(humedad),Float.valueOf(calAgua),Main.User.getIdUsuario());
										//actualizarListado(listE.modelo);
									}
								}

							} catch (NamingException  e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
				//Volver al listado
				altaE.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listE.setVisible(true);
						altaE.setVisible(false);
					}
				});
			}

		});

		//Modificar una Estación
		listE.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					V_ModEstacion();
				} catch (NamingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ServiciosException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				String usuario=Main.User.getNombre();
				altaE.lblNombreUser.setText(usuario);
				int row = listE.table.getSelectedRow();

				if( row != (-1)) {
					long id = (long) listE.table.getValueAt(row, 4);

					HashMap<Long, Estacion> map = new HashMap<>();
					List<Estacion> estacion;
					try {
						estacion = ControllerEstacion.obtenerTodos();
						for (Estacion es: estacion) {

							map.put(es.getIdEstacion(), es);
						}
					} catch (NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ServiciosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Estacion est =map.get(id);
					//Cargar datos de usuario

					altaE.textnombre.setText(est.getNombre());
					//altaE.comboDpto.setSelectedItem(est.getDepartamento().toString());
					altaE.textLatitud.setText(Float.toString(est.getLatitud()));
					altaE.textLongitud.setText(Float.toString(est.getLongitud()));
					altaE.textHumedadRelativa.setText(Float.toString(est.getHumedadRelativa()));
					altaE.textCalidadAgua.setText(Float.toString(est.getCalidadAgua()));

					int dp=Math.toIntExact(est.getDepartamento());
					altaE.comboDpto.setSelectedIndex(dp);

					//Guardar Cambios
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una Estación", null, 1);
				}

				//Volver al listado
				altaE.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listE.setVisible(true);
						altaE.setVisible(false);
					}
				});
				altaE.btnGuardar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String nom = altaE.textnombre.getText();
						Long dpto= (long) altaE.comboDpto.getSelectedIndex();
						String latitud = altaE.textLatitud.getText();
						String longitud = altaE.textLongitud.getText();
						String humedad = altaE.textHumedadRelativa.getText();
						String calAgua = altaE.textCalidadAgua.getText();
						int confirm = JOptionPane.showOptionDialog(null,
								"¿Desea modificar la Estación?",
								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sí se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {

							try {
								boolean todoOK=camposVacios(nom,latitud,longitud,calAgua,humedad);

								if(todoOK) {
									todoOK=validarFormatos(longitud, latitud, calAgua,humedad);
									if (todoOK) {
										ControllerEstacion.actualizar(nom, dpto,Float.valueOf(latitud),Float.valueOf( longitud), Float.valueOf(humedad), Float.valueOf(calAgua));
										actualizarListado(listE.modelo);
									}
								}

							} catch (NamingException | ServiciosException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					}

				});



			}
		});


	}

	//Ventana Alta Estación
	public static void V_AltaEstacion() throws NamingException, ServiciosException  {

		altaE=new AltaEstacion();

		altaE.setVisible(true);
		listE.setVisible(false);
		altaE.btnRegistrar.setVisible(true);
		altaE.btnGuardar.setVisible(false);


		Main.menuP.setVisible(false);
		altaE.comboDpto.setModel(new DefaultComboBoxModel (CompletarCombo()));


	}
	//Estaciones completar COMBOBOX CON DEPARTAMENTOS
	public static String [] CompletarCombo () throws NamingException, ServiciosException {

		List<Departamento> dptoList=obtenerDptos();
		String [] arrayDpto= new String [dptoList.size()];
		int i=0;
		for (Departamento d: dptoList) {
			arrayDpto[i]=d.getNombre();
			i++;

		}	

		return arrayDpto;	
	}

	//Ventana Modificar Estación	
	public static void V_ModEstacion() throws NamingException, ServiciosException {

		altaE=new AltaEstacion();
		altaE.textnombre.enable(false);
		listE.setVisible(false);
		altaE.setVisible(true);
		altaE.lblAltaEstacion.setText("MODIFICAR ESTACIÓN");
		altaE.btnRegistrar.setVisible(false);
		altaE.btnGuardar.setVisible(true);

		altaE.comboDpto.setModel(new DefaultComboBoxModel (CompletarCombo()));


	}

	//CREAR ESTACION
	public static void crear(String nombre, Long dpto,float latitud, float longitud,  float calidadAgua,float humedadRelativa, Long usuarioCreador) throws NamingException {


		EstacionBeanRemote estacionBean = (EstacionBeanRemote)
				InitialContext.doLookup(RUTA_EstacionBean);

		Estacion existeEst = estacionBean.buscarEst(nombre);
		boolean todoOK=true;
		if(existeEst != null) {
			JOptionPane.showMessageDialog(null, "El nombre de estación ingresada ya existe", null, 1);
			todoOK = false;
		}

		if (todoOK) {
			Estacion estacion = new Estacion();
			estacion.setNombre(nombre);
			estacion.setDepartamento(dpto);
			estacion.setLatitud(latitud);
			estacion.setLongitud(longitud);
			estacion.setCalidadAgua(calidadAgua);
			estacion.setHumedadRelativa(humedadRelativa);
			estacion.setIdInvestigador(Main.User);
			estacion.setEstado(estacion.getEstado().ACTIVO);



			try {
				estacionBean.crear(estacion);
				System.out.println(estacion.getIdEstacion());
				JOptionPane.showMessageDialog(null,"Estación registrada correctamente");
			
		} catch (ServiciosException e) {

			System.out.println(e.getMessage());
		}
		}
		try {
			actualizarListado(listE.modelo);
		} catch (NamingException | ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//LISTADO DE DEPARTAMENTOS
	public static  List<Departamento> obtenerDptos() throws NamingException, ServiciosException{
		DepartamentoBeanRemote dptoBean = (DepartamentoBeanRemote)
				InitialContext.doLookup(RUTA_DepartamentoBean);

		List<Departamento> list = dptoBean.obtenerTodos();
		return list;
	}

	//ACTUALIZAR LISTADO
	public static void actualizarListado(DefaultTableModel modelo) throws NamingException, ServiciosException {

		//CompletarCombo();
		int filas = modelo.getRowCount();

		for(int i = filas-1; i>=0; i--) {
			modelo.removeRow(i);
		}

		final String[] columnNames = {"Nombre","Departamento","Latitud","Longitud","Identificador"};
		//ORDEN DE LA TABLA
		TableRowSorter<TableModel> orden=new  TableRowSorter<>(modelo);
		listE.table.setRowSorter(orden);
		//SOLO MUESTRE CON ESTADOS ACTIVO

		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posición del array con una de las columnas de la tabla en base de datos.

		List<Estacion> estaciones = obtenerTodos();

		for (Estacion e: estaciones) {
			int dp=Math.toIntExact(e.getDepartamento());

			fila[0]=e.getNombre();
			fila[1]=listE.comboDpto.getItemAt(dp);
			fila[2]=e.getLatitud();
			fila[3]=e.getLongitud();
			fila[4]=e.getIdEstacion();
			if  (e.getEstado().equals(Estado.ACTIVO)) {
				modelo.addRow(fila);

			}

		}
	}


	//LISTADO DE ESTACIONES
	public static  List<Estacion> obtenerTodos() throws NamingException, ServiciosException{
		EstacionBeanRemote estacionBean = (EstacionBeanRemote)
				InitialContext.doLookup(RUTA_EstacionBean);

		List<Estacion> list = estacionBean.obtenerTodos();
		return list;
	}

	//ACTUALIZAR ESTACIÓN
	public static void actualizar(String nombre, Long departamento,float latitud, float longitud, float humedadRelativa, float calidadAgua) throws NamingException, ServiciosException {
		EstacionBeanRemote estacionBean = (EstacionBeanRemote)
				InitialContext.doLookup(RUTA_EstacionBean);


		Estacion estacion = new Estacion();
		estacion=estacionBean.buscarEst(nombre);
		//estacion.setIdEstacion(id);
		estacion.setNombre(nombre);
		estacion.setDepartamento(departamento);
		estacion.setLatitud(latitud);
		estacion.setLongitud(longitud);
		estacion.setHumedadRelativa(humedadRelativa);
		estacion.setCalidadAgua(calidadAgua);


		try {

			estacionBean.actualizar(estacion);
			JOptionPane.showMessageDialog(null,"Estación actualizada correctamente");

			System.out.println(estacion.getIdEstacion() + " " +estacion.getNombre());
		} catch (ServiciosException e) {

			System.out.println(e.getMessage());
		}
		actualizarListado(listE.modelo);
		System.out.println("Se actualizó exitosamente la estación");

	}


	//BORRAR ESTACIÓN
	public static void borrar() throws NamingException {
		EstacionBeanRemote estacionBean = (EstacionBeanRemote)
				InitialContext.doLookup(RUTA_EstacionBean);


		int row = listE.table.getSelectedRow();


		if( row != (-1)) {
			//long id = (long) listE.table.getValueAt(row, 4);
			String name=(String) listE.table.getValueAt(row, 0);
			try {
				int confirmado = JOptionPane.showOptionDialog(null,
						"¿Desea dar de baja la Estación seleccionada?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null, null, null);
				//Si el usuario elige sí se borra la fila
				if (JOptionPane.OK_OPTION == confirmado) {

					Estacion estacion = new Estacion();
					estacion=estacion=estacionBean.buscarEst(name);
					estacion.setEstado(estacion.getEstado().INACTIVO);
					estacionBean.actualizar(estacion);
					JOptionPane.showMessageDialog(null, "Estación eliminada con éxito");

					actualizarListado(listE.modelo);
				}

			} catch (NamingException e1) {
				System.out.println("No se puede borrar la estación");	
				e1.printStackTrace();
			} catch (ServiciosException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una estación", null, 1);
		}

	}

	public static boolean camposVacios(String nombre, String latitud, String longitud, String calAgua, String humRel) {

		boolean bandera = true;

		if(nombre.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Nombre", null, 1);
			return false;
		}
		if (latitud.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Latitud", null, 1);
			return false;

		}
		if(longitud.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Longitud", null, 1);
			return false;

		}

		if(calAgua.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Calidad del Agua", null, 1);
			return false;	

		}
		if(humRel.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Humedad Relativa", null, 1);
			return false;
		}

		return bandera;

	}

	public static boolean validarFormatos(String longitud, String latitud, String calAgua, String humRel) {

		boolean bandera = true;


		//latitud con números
		if(!latitud.matches("[0-9]+") && (!latitud.contains("."))) {
			JOptionPane.showMessageDialog(null, "El campo Latitud debe tener caracteres numéricos", null, 1);		
			return false;
		}

		//longitud con números
		if(!longitud.matches("[0-9]+") && (!longitud.contains("."))) {
			JOptionPane.showMessageDialog(null, "El campo Longitud debe tener caracteres numéricos", null, 1);
			return false;

		}

		//Cal Agua con números
		if(!calAgua.matches("[0-9]+") && (!calAgua.contains("."))) {
			JOptionPane.showMessageDialog(null, "El campo Calidad del Agua debe tener caracteres numéricos", null, 1);
			return false;
		}

		//Humedad Relativa con números
		if(!humRel.matches("[0-9]+")&& (!humRel.contains("."))) {
			JOptionPane.showMessageDialog(null, "El campo Humedad Relativa debe tener caracteres numéricos", null, 1);
			return false;

		}
		return bandera;
	}


}

