package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Casilla;
import com.entities.Estacion;
import com.entities.Estado;
import com.entities.Formulario;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.CasillaBeanRemote;
import com.servicios.EstacionBeanRemote;
import com.servicios.FormularioBeanRemote;

import vistas.AltaCasilla;

import vistas.ListadoCasilla;



public class ControllerCasillas implements Constantes{

	public static AltaCasilla altaC;
	public static ListadoCasilla listC;

	//ventana Listado Casilla
	public static void  V_ListaCasilla () throws NamingException, ServiciosException {

		listC=new ListadoCasilla();
		listC.setVisible(true);
		obtenerTodos();


		listC.btnEliminar.addMouseListener(new MouseAdapter() {

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
		listC.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listC.setVisible(false);
				Main.menuP.setVisible(true);
			}
		});
		//Nueva Casilla
		listC.btnNuevo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){


				try {
					V_AltaCasilla();

				} catch (NamingException  |ServiciosException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();

				}
				altaC.btnRegistrar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String nombre = altaC.textnombre.getText();
						String desc=altaC.textDescripcion.getText();
						String parametro = (String) altaC.comboParametro.getSelectedItem();
						String tipoInput = (String) altaC.comboTipoValor.getSelectedItem();
						String unidad=(String) altaC.comboUnidad.getSelectedItem();



						int confirm = JOptionPane.showOptionDialog(null,
								"¿Desea dar de alta la Casilla?",
								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sí se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {
							try {
								boolean todoOK=camposVacios(nombre, parametro,tipoInput,unidad);

								if(todoOK) {

									crear(nombre,desc,parametro,tipoInput,unidad);
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
				altaC.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listC.setVisible(true);
						altaC.setVisible(false);
					}
				});
			}

		});


		//Modificar una Casilla
		listC.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					V_ModCasilla();

				} catch (NamingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ServiciosException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				int row = listC.table.getSelectedRow();


				if( row != (-1)) {
					long id = (long) listC.table.getValueAt(row, 5);


					HashMap<Long, Casilla> map = new HashMap<>();
					List<Casilla> casi;
					try {
						casi = obtenerTodos();
						for (Casilla c: casi) {

							map.put(c.getIdCasilla(), c);
						}
					} catch (NamingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ServiciosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Casilla cas =map.get(id);


					altaC.textnombre.setText(cas.getNombre());
					altaC.textDescripcion.setText(cas.getDescripcion());
					String parametro = listC.table.getValueAt(row, 2).toString();
					String tipIn = listC.table.getValueAt(row, 3).toString();
					String unidad = listC.table.getValueAt(row, 4).toString();

					switch(parametro) {

					case "":
						altaC.comboParametro.setSelectedIndex(1);
						break;
					case "Volumen": 
						altaC.comboParametro.setSelectedIndex(1);
						break;
					case "Densidad": 
						altaC.comboParametro.setSelectedIndex(2);
						break;
					case "Cantidad": 	
						altaC.comboParametro.setSelectedIndex(3);
						break;
					case "Porcentaje": 	
						altaC.comboParametro.setSelectedIndex(4);
						break;
					case "N/A": 	
						altaC.comboParametro.setSelectedIndex(5);
						break;
					}
					switch(tipIn) {
					case "":
						altaC.comboTipoValor.setSelectedIndex(0);
						break;
					case "Entero": 
						altaC.comboTipoValor.setSelectedIndex(1);
						break;
					case "Decimal": 
						altaC.comboTipoValor.setSelectedIndex(2);
						break;
					case "N/A": 	
						altaC.comboTipoValor.setSelectedIndex(3);
						break;
			
					}

					switch(unidad) {
					case "":
						altaC.comboUnidad.setSelectedIndex(0);
						break;
					case "mm":
						altaC.comboUnidad.setSelectedIndex(1);
						break;
					case "°C":
						altaC.comboUnidad.setSelectedIndex(2);
						break;
					case "µg/m3":
						altaC.comboUnidad.setSelectedIndex(3);
						break;
					case "g/m3":
						altaC.comboUnidad.setSelectedIndex(4);
						break;
					case "ppm":
						altaC.comboUnidad.setSelectedIndex(5);
						break;
					case "Unidad Dobson":
						altaC.comboUnidad.setSelectedIndex(6);
						break;
					case "mg/l":
						altaC.comboUnidad.setSelectedIndex(7);
						break;
					case "N/A":
						altaC.comboUnidad.setSelectedIndex(8);
						break;

					}
					//Guardar Cambios
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una Casilla", null, 1);
				}

				//Volver al listado
				altaC.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listC.setVisible(true);
						altaC.setVisible(false);
					}
				});
				altaC.btnGuardar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String nom = altaC.textnombre.getText();
						String desc=altaC.textDescripcion.getText();
						String parametro = (String) altaC.comboParametro.getSelectedItem();
						String tipoInput = (String) altaC.comboTipoValor.getSelectedItem();
						String unidad=(String) altaC.comboUnidad.getSelectedItem();


						int confirm = JOptionPane.showOptionDialog(null,
								"¿Desea modificar la Casilla?",
								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sí se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {
							try {
								boolean todoOK=camposVacios(nom,parametro,tipoInput,unidad);

								if(todoOK) {
									if (todoOK) {
										actualizar(nom,desc,parametro,tipoInput,unidad);
										JOptionPane.showMessageDialog(null,"Casilla actualizada correctamente");
										actualizarListado(listC.modelo);
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

	//Ventana Alta Casilla
	public static void V_AltaCasilla() throws NamingException, ServiciosException  {

		altaC=new AltaCasilla();

		altaC.setVisible(true);
		altaC.btnRegistrar.setVisible(true);
		altaC.btnGuardar.setVisible(false);
		listC.setVisible(false);

		Main.menuP.setVisible(false);
		//altaC.comboDpto.setModel(new DefaultComboBoxModel (CompletarCombo()));


	}

	//Ventana Modificar Casilla	
	public static void V_ModCasilla() throws NamingException, ServiciosException {

		altaC=new AltaCasilla();
		listC.setVisible(false);
		altaC.setVisible(true);
		altaC.lblAltaCasilla.setText("MODIFICAR CASILLA");
		altaC.btnRegistrar.setVisible(false);
		altaC.btnGuardar.setVisible(true);
		altaC.textnombre.enable(false);


	}

	//CREAR CASILLA
	public static void crear(String nombre, String descripcion,String parametro, String tipoInput,  String unidadMedida) throws NamingException {


		CasillaBeanRemote casillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		Casilla existeCas = casillaBean.buscar(nombre);
		boolean todoOK=true;

		if(existeCas!= null) {
			JOptionPane.showMessageDialog(null, "El nombre de la casilla ingresada ya existe", null, 1);
			todoOK = false;

		}
		if(todoOK) {


			Casilla casilla = new Casilla();
			casilla.setNombre(nombre);
			casilla.setDescripcion(descripcion);
			casilla.setParametro(parametro);
			casilla.setTipoInput(tipoInput);
			casilla.setUnidadMedida(unidadMedida);
			casilla.setEstado(casilla.getEstado().ACTIVO);


			try {
				casillaBean.crear(casilla);
				JOptionPane.showMessageDialog(null,"Casilla registrada correctamente");

			} catch (ServiciosException e) {

				System.out.println(e.getMessage());
			}

			try {
				actualizarListado(listC.modelo);

			} catch (NamingException | ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	//ACTUALIZAR CASILLA
	public static void actualizar(String nombre,String descripcion,String parametro, String tipoInput,  String unidadMedida) throws NamingException, ServiciosException {

		CasillaBeanRemote casillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		Casilla casilla = new Casilla();
		casilla=casillaBean.buscar(nombre);
		casilla.setDescripcion(descripcion);
		casilla.setParametro(parametro);
		casilla.setTipoInput(tipoInput);
		casilla.setUnidadMedida(unidadMedida);

		casillaBean.actualizar(casilla);

		System.out.println(casilla.getIdCasilla() + " " +casilla.getNombre());
		actualizarListado(listC.modelo);
		System.out.println("Se actualizó exitosamente la Casilla");

	}

	//LISTADO DE CASILLA
	public static  List<Casilla> obtenerTodos() throws NamingException, ServiciosException{
		CasillaBeanRemote casillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		List<Casilla> list = casillaBean.obtenerTodos();
		return list;
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
		listC.table.setRowSorter(orden);


		final String[] columnNames = {"Nombre","Descripción","Parametro", "Tipo Input", "Unidad de Medida", "Identificador"};
		// insertamos las columnas

		// Se crea un array que será una de las filas de la tabla. 
		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posición del array con una de las columnas de la tabla en base de datos.

		List<Casilla> casilla = obtenerTodos();
		//comboTipoCasilla.setModel(new DefaultComboBoxModel (ControllerCasilla.CompletarCombo()));
		for (Casilla c: casilla) {
			// String ca=Math.toIntExact(c.getTipoImput());
			//map.put(c.getIdCasilla(), c);

			fila[0]=c.getNombre();
			fila[1]=c.getDescripcion();
			fila[2]=c.getParametro();
			fila[3]=c.getTipoInput();
			fila[4]=c.getUnidadMedida();
			fila[5]=c.getIdCasilla();
			if  (c.getEstado().equals(Estado.ACTIVO)) {
				modelo.addRow(fila);
			}


		}
	}


	//BORRAR CASILLA
	public static void borrar() throws NamingException {

		FormularioBeanRemote formBean = (FormularioBeanRemote)
				InitialContext.doLookup(RUTA_FormularioBean);
		
	
		CasillaBeanRemote casillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);
		


		int row = listC.table.getSelectedRow();


		if( row != (-1)) {
			String name=(String) listC.table.getValueAt(row, 0);
			
			
			Formulario f = formBean.casillaActiva(name);
			
			if(f == null) {
				try {
					int confirmado = JOptionPane.showOptionDialog(null,
							"¿Desea dar de baja la Casilla seleccionada?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,null, null, null);
					//Si el usuario elige sí se borra la fila
					if (JOptionPane.OK_OPTION == confirmado) {
						Casilla cas = new  Casilla();
						//ArrayList <Casilla> casillas = new ArrayList<>();
						cas=casillaBean.buscar(name);
						//Setear estado a INACTIVO
						cas.setEstado(cas.getEstado().INACTIVO);

						casillaBean.actualizar(cas);
						JOptionPane.showMessageDialog(null, "Se borró exitosamente la Casilla seleccionada");

						actualizarListado(listC.modelo);
					}

				} catch (NamingException e1) {
					System.out.println("No se puede borrar la Casilla");	
					e1.printStackTrace();
				} catch (ServiciosException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Esta casilla está asociada a un formulario");
			}

			
		}
		else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una Casilla", null, 1);
		}

	}

	public static boolean camposVacios(String nombre, String parametro,String tipo, String unidad) {

		boolean bandera = true;

		if(nombre.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Nombre", null, 1);
			return false;
		}

		if(parametro.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Parametro", null, 1);
			return false;
		}
		if(tipo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Tipo Input", null, 1);
			return false;
		}

		if(unidad.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Unidad", null, 1);
			return false;
		}


		return bandera;

	}
	//LISTADO DE CASILLA
	public static  List<Casilla> obtenerCas() throws NamingException, ServiciosException{
		CasillaBeanRemote casillaBean = (CasillaBeanRemote)
				InitialContext.doLookup(RUTA_CasillaBean);

		List<Casilla> list = (List<Casilla>) casillaBean.obtenerTodos();
		System.out.println(list.size());
		return list;
	}
}
