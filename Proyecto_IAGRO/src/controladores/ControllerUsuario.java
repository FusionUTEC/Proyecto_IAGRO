package controladores;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Administrador;
import com.entities.Aficionado;
import com.entities.Casilla;
import com.entities.Estado;
import com.entities.Formulario;
import com.entities.Investigador;
import com.entities.Usuario;
import com.exception.ServiciosException;

import com.servicios.UsuarioBeanRemote;

import vistas.AltaUsuario;

import vistas.ListadoUsuarios;
import vistas.MenuPrincipal;
import java.awt.GridLayout;

public class ControllerUsuario implements Constantes{


	//static AltaEstacion altaE;
	static ListadoUsuarios listU;
	static AltaUsuario altaU;


	//Ventana Alta Usuario
	public static void V_AltaUsuario() {

		altaU=new AltaUsuario();
		altaU.setVisible(true);
		listU.setVisible(false);
		altaU.btnRegistrar.setVisible(true);
		altaU.btnGuardar.setVisible(false);
		altaU.btnCambiarPass.setVisible(false);
		ocultar("Administrador");
		
		altaU.comboRol.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				String rol = altaU.comboRol.getSelectedItem().toString();
				ocultar(rol);

			}
		});

	}

	//Ventana Modificar Usuario	
	public static void V_ModUsuario() {

		altaU=new AltaUsuario();
		altaU.lblAltaDeUsuarios.setText("MODIFICAR USUARIO");
		altaU.setVisible(true);
		altaU.btnRegistrar.setVisible(false);
		altaU.btnGuardar.setVisible(true);
		altaU.btnGuardar.setBounds(290, 377, 125, 27);
		altaU.btnCambiarPass.setVisible(true);
		altaU.btnCambiarPass.setBounds(420, 377, 135, 27);
		altaU.contrasena.enable(false);
		altaU.confcontrasena.enable(false);
		altaU.lblConfContrasea.enable(false);
		altaU.lblContrasena.enable(false);

		//altaU.lblRol.setBounds(300, 194, 84, 23);
		//altaU.comboRol.setBounds(340, 194, 137, 23);

		//altaU.cedula.setBounds(560, 194, 95, 23);
		//altaU.lblCedula.setBounds(500, 194, 161, 23);

		//altaU.lblCiudad.setBounds(30, 265, 84, 23);
		//altaU.ciudad.setBounds(90, 265, 137, 23);

		//altaU.lblTelefono.setBounds(279, 265, 72, 23);
		//altaU.telefono.setBounds(350, 265, 83, 23);

		altaU.lblOcupacion.setBounds(49, 333, 105, 23);
		altaU.ocupacion.setBounds(130, 333, 105, 23);

		altaU.lblDomicilio.setBounds(490, 265, 84, 23);
		altaU.domicilio.setBounds(570, 265, 177, 23);

		altaU.nombreUsu.enable(false);
		altaU.email.enable(false);
		altaU.comboRol.enable(false);

	}

	//BORRAR USUARIO
	public static void borrar() throws NamingException {
		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
				InitialContext.doLookup(RUTA_UsuarioBean);


		int row = listU.table.getSelectedRow();

		if( row != (-1)) {
			//long id = (long) listU.table.getValueAt(row, 4);
			String userName=(String) listU.table.getValueAt(row, 3);
			try {
				int confirmado = JOptionPane.showOptionDialog(null,
						"¿Desea dar de baja el usuario seleccionado?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null, null, null);
				//Si el usuario elige sÃ± se borra la fila
				if (JOptionPane.OK_OPTION == confirmado) {

					Usuario user = new Usuario();
					user=usuarioBean.buscarUser(userName);
					//Setear estado a INACTIVO
					user.setEstado(user.getEstado().INACTIVO);
					
					usuarioBean.actualizar(user);
					JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
		


					actualizarListado(listU.modelo);
				}

			} catch (NamingException e1) {
				System.out.println("No se puede borrar el usuario");	
				e1.printStackTrace();
			} catch (ServiciosException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario", null, 1);
		}

	}


	//LISTADO DE USUARIOS
	public static  List<Usuario> obtenerTodos() throws NamingException, ServiciosException{
		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
				InitialContext.doLookup(RUTA_UsuarioBean);

		List<Usuario> list = usuarioBean.obtenerTodos();
		return list;
	}

	//ACTUALIZAR LISTADO
	public static void actualizarListado(DefaultTableModel modelo) throws NamingException, ServiciosException {

		int filas = modelo.getRowCount();
		//ORDEN DE LA TABLA
		TableRowSorter<TableModel> orden=new  TableRowSorter<>(modelo);
		listU.table.setRowSorter(orden);

		for(int i = filas-1; i>=0; i--) {
			modelo.removeRow(i);
		}

		final String[] columnNames = {"Nombre","Apellido","Correo","Usuario","Identificador","Rol"};


		Object [] fila = new Object[columnNames.length]; 
		// Se carga cada posiciÃ±n del array con una de las columnas de la tabla en base de datos.

		List<Usuario> usuarios = ControllerUsuario.obtenerTodos();
		for (Usuario u: usuarios) {
			fila[0]=u.getNombre();
			fila[1]=u.getApellido();
			fila[2]=u.getMail();
			fila[3]=u.getNombreUsuario();
			fila[4]=u.getIdUsuario();
			fila[5]=u.getTipo();
			if  (u.getEstado().equals(Estado.ACTIVO)) {
				modelo.addRow(fila);

			}
		}

	}

	public static void  V_ListaUser () throws NamingException {

		listU=new ListadoUsuarios();
		listU.setVisible(true);
		
		listU.btnEliminar.addMouseListener(new MouseAdapter() {
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

		//Volver al MenÃ± desde listado
		listU.btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//listU.setVisible(false);
				Main.menuP.setVisible(true);
				listU.setVisible(false);
			}
		});


		//Nuevo Usuario
		listU.btnNuevo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){

				V_AltaUsuario();
				
				altaU.btnRegistrar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String ap = altaU.apellido.getText();
						String nom = altaU.nombre.getText();
						String mail = altaU.email.getText();
						String user = altaU.nombreUsu.getText();
						String pass = String.valueOf(altaU.contrasena.getPassword());
						String pass2 = String.valueOf(altaU.confcontrasena.getPassword());
						String tipo = (String) altaU.comboRol.getSelectedItem();

						String cedula = altaU.cedula.getText();

						String ciudad = altaU.ciudad.getText();
						String domicilio = altaU.domicilio.getText();
						String telefono = altaU.telefono.getText();
						String ocupacion = altaU.ocupacion.getText();


						if (pass.equals(pass2)) {
							int confirm = JOptionPane.showOptionDialog(null,

									"¿Desea dar de alta el usuario?",
									"Exit Confirmation", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sÃ± se borra la fila
							if (JOptionPane.YES_OPTION== confirm) {
								//hacer para los otros atributos
								try {
									crear(ap, nom, mail, user, pass, tipo,ciudad,cedula, domicilio, telefono, ocupacion); 

								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}else {
						JOptionPane.showMessageDialog(null, "Las Contraseñas ingresadas no coinciden");
						}

					}
				});
				//Volver al listado
				altaU.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listU.setVisible(true);
						altaU.setVisible(false);
					}
				});
			}

		});

		//Modificar un usuario
		listU.btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				V_ModUsuario();	

				//Cargar datos de usuario
				try {cargarDatos();} catch (NamingException e2) {}

				listU.setVisible(false);


				//Cambio de Contraseña - 
				altaU.btnCambiarPass.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String user = altaU.nombreUsu.getText();
						JPasswordField cont = new JPasswordField();
						JPasswordField confContr = new JPasswordField();
						JPanel panel = new JPanel(new GridLayout(0, 1));
						panel.add(new JLabel("Contraseña"));
						panel.add(cont);
						panel.add(new JLabel("Confirmar Contraseña"));
						panel.add(confContr);

						int result = JOptionPane.showConfirmDialog(null, panel, "Cambio de Contraseña",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						if (result == JOptionPane.OK_OPTION ) {

							String pass=String.valueOf(cont.getPassword());
							String confPass=String.valueOf(confContr.getPassword());

							if(pass.equals(confPass)) {
								try {
									actualizarPass(user,pass);
								} catch (NamingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}else {
								JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
							}

						} else { 

							System.out.println("Cancelled");
						}
					}

				});

				//Volver al listado
				altaU.btnVolver.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						listU.setVisible(true);
						altaU.setVisible(false);
					}
				});

				altaU.btnGuardar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						String ap = altaU.apellido.getText();
						String nom = altaU.nombre.getText();
						String mail = altaU.email.getText();
						String user = altaU.nombreUsu.getText();
						String tipo = (String) altaU.comboRol.getSelectedItem();
						String cedula = altaU.cedula.getText();
						String ciudad = altaU.ciudad.getText();
						String domicilio = altaU.domicilio.getText();
						String telefono = altaU.telefono.getText();
						String ocupacion = altaU.ocupacion.getText();

						int confirm = JOptionPane.showOptionDialog(null,

								"¿Desea modificar el usuario?",

								"Exit Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,null, null, null);							//Si el usuario elige sÃ± se borra la fila
						if (JOptionPane.YES_OPTION== confirm) {


							try {
								actualizar(ap, mail, nom, user,tipo,ciudad,cedula, domicilio,telefono, ocupacion);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					}
				});

			}
		});


	}

	//CREAR USUARIOS 
	public static void crear(String ap, String nom, String mail, String user, String pass, String tipo,
			String ciudad, String documento, String domicilio, String telefono,String ocupacion) throws Exception {

		boolean todoOK = camposVacios(ap, nom, mail, user, tipo, ciudad, documento, domicilio, telefono, ocupacion);
		if (todoOK) {
			todoOK = validarFormatos(mail, user);
		}
		if(todoOK) {

			todoOK=validarContraseña(pass);

		}

		if(todoOK && !tipo.equalsIgnoreCase("Aficionado") ) {
			todoOK=ciValida(documento);
		}
		if(todoOK && !tipo.equalsIgnoreCase("Aficionado")) {
			todoOK=validarTel(telefono);
		}



		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
				InitialContext.doLookup(RUTA_UsuarioBean);

		Usuario existeUser = usuarioBean.usuarioExistente(user);

		if(existeUser != null) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya existe", null, 1);
			todoOK = false;
		}

		Usuario existeMail = usuarioBean.correoExistente(mail);
		if(existeMail != null) {
			JOptionPane.showMessageDialog(null, "El correo ingresado ya existe", null, 1);
			todoOK = false;
		}

		if(todoOK) {


			switch(tipo) {


			case "Administrador":

				Administrador admin= new Administrador();
				admin.setApellido(ap);
				admin.setNombre(nom);
				admin.setMail(mail);
				admin.setNombreUsuario(user);
				admin.setContraseña(pass);
				admin.setTipo(tipo);
				admin.setEstado(admin.getEstado().ACTIVO);


				admin.setCiudad(ciudad);

				admin.setDocumento(documento);
				admin.setDomicilio(domicilio);
				admin.setTelefono(telefono);


				try {

					usuarioBean.crearAd(admin);
					admin=usuarioBean.buscarAdm(admin.getNombreUsuario());
					JOptionPane.showMessageDialog(null,"Usuario creado correctamente");


				} catch (ServiciosException e) {
					System.out.println(e.getMessage());
				}
				break;


			case "Investigador":

				Investigador invest= new Investigador();
				invest.setApellido(ap);
				invest.setNombre(nom);
				invest.setMail(mail);
				invest.setNombreUsuario(user);
				invest.setContraseña(pass);
				invest.setTipo(tipo);
				invest.setEstado(invest.getEstado().ACTIVO);


				invest.setCiudad(ciudad);
				invest.setDocumento(documento);
				invest.setDomicilio(domicilio);
				invest.setTelefono(telefono);

				try {
					usuarioBean.crearIn(invest);
					invest=usuarioBean.buscarInv(invest.getNombreUsuario());
					//System.out.println("Se creÃ± exitosamente el usuario Investigador");
					JOptionPane.showMessageDialog(null,"Usuario creado correctamente");
				} catch (ServiciosException e) {

					System.out.println(e.getMessage());
				}
				break;


			case "Aficionado":
				
				Aficionado aficionado= new Aficionado();
				aficionado.setApellido(ap);
				aficionado.setNombre(nom);
				aficionado.setMail(mail);
				aficionado.setNombreUsuario(user);
				aficionado.setContraseña(pass);
				aficionado.setTipo(tipo);
				aficionado.setEstado(aficionado.getEstado().ACTIVO);
				
				aficionado.setOcupacion(ocupacion);


				try {
					usuarioBean.crearAf(aficionado);
					aficionado=usuarioBean.buscarAfi(aficionado.getNombreUsuario());
					JOptionPane.showMessageDialog(null,"Usuario creado correctamente");

				} catch (ServiciosException e) {

					System.out.println(e.getMessage());
				}
				break;			   
			}

			try {
				actualizarListado(listU.modelo);
			} catch (NamingException | ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void ocultar(String rol) {

		if(rol.equalsIgnoreCase("Aficionado")) {

			altaU.cedula.setVisible(false);
			altaU.lblCedula.setVisible(false);
			altaU.domicilio.setVisible(false);
			altaU.lblDomicilio.setVisible(false);
			altaU.ciudad.setVisible(false);
			altaU.lblCiudad.setVisible(false);
			altaU.telefono.setVisible(false);
			altaU.lblTelefono.setVisible(false);

			altaU.lblOcupacion.setVisible(true);
			altaU.ocupacion.setVisible(true);
			
			altaU.lblOcupacion.setBounds(305,265,95, 23);
			altaU.ocupacion.setBounds(387, 265, 110, 23);

		}else {

			altaU.cedula.setVisible(true);
			altaU.lblCedula.setVisible(true);
			altaU.domicilio.setVisible(true);
			altaU.lblDomicilio.setVisible(true);
			altaU.ciudad.setVisible(true);
			altaU.lblCiudad.setVisible(true);
			altaU.telefono.setVisible(true);
			altaU.lblTelefono.setVisible(true);

			altaU.lblOcupacion.setVisible(false);
			altaU.ocupacion.setVisible(false);
			

		}




	}

	public static void enviarDatos() throws ServiciosException, NamingException {
		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
				InitialContext.doLookup(RUTA_UsuarioBean);

		String rol = altaU.comboRol.getSelectedItem().toString();




		switch(rol) {

		case "Administrador": altaU.comboRol.setSelectedIndex(0);
		Administrador adm = new Administrador();

		adm = usuarioBean.buscarAdm(altaU.nombreUsu.getText());
		adm.setApellido(altaU.apellido.getText());
		adm.setNombre(altaU.nombre.getText());
		adm.setMail(altaU.email.getText());
		adm.setDocumento(altaU.cedula.getText());
		adm.setDomicilio(altaU.domicilio.getText());
		adm.setCiudad(altaU.ciudad.getText());
		adm.setTelefono(altaU.telefono.getText());

		usuarioBean.actualizarAd(adm);



		break;

		case "Investigador": altaU.comboRol.setSelectedIndex(1);
		Investigador inv = new Investigador();
		inv = usuarioBean.buscarInv(altaU.nombreUsu.getText());

		inv.setApellido(altaU.apellido.getText());
		inv.setNombre(altaU.nombre.getText());
		inv.setMail(altaU.email.getText());
		inv.setDocumento(altaU.cedula.getText());
		inv.setDomicilio(altaU.domicilio.getText());
		inv.setCiudad(altaU.ciudad.getText());
		inv.setTelefono(altaU.telefono.getText());

		usuarioBean.actualizarIn(inv);
		break;

		case "Aficionado": altaU.comboRol.setSelectedIndex(2);
		Aficionado afi = new Aficionado();

		afi = usuarioBean.buscarAfi(altaU.nombreUsu.getText());

		afi.setApellido(altaU.apellido.getText());
		afi.setNombre(altaU.nombre.getText());
		afi.setMail(altaU.email.getText());
		afi.setOcupacion(altaU.ocupacion.getText());

		usuarioBean.actualizarAf(afi);
		break;

		}

		actualizarListado(listU.modelo);

	}


	public static void cargarDatos() throws NamingException {

		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote) InitialContext.doLookup(RUTA_UsuarioBean);

		int row = listU.table.getSelectedRow();

		if( row != (-1)) {
			String user = listU.table.getValueAt(row, 3).toString();
			String rol = listU.table.getValueAt(row, 5).toString();

			ocultar(rol);


			switch(rol) {

			case "Administrador": altaU.comboRol.setSelectedIndex(0);
			Administrador adm = usuarioBean.buscarAdm(user);
			altaU.apellido.setText(adm.getApellido());
			altaU.nombre.setText(adm.getNombre());
			altaU.email.setText(adm.getMail());
			altaU.contrasena.setText(adm.getContraseña());
			altaU.confcontrasena.setText(adm.getContraseña());
			altaU.nombreUsu.setText(adm.getNombreUsuario());
			altaU.cedula.setText(adm.getDocumento());
			altaU.domicilio.setText(adm.getDomicilio());
			altaU.ciudad.setText(adm.getCiudad());
			altaU.telefono.setText(adm.getTelefono());
			break;

			case "Investigador": altaU.comboRol.setSelectedIndex(1);
			Investigador inv = usuarioBean.buscarInv(user);
			altaU.apellido.setText(inv.getApellido());
			altaU.nombre.setText(inv.getNombre());
			altaU.email.setText(inv.getMail());
			altaU.nombreUsu.setText(inv.getNombreUsuario());
			altaU.contrasena.setText(inv.getContraseña());
			altaU.confcontrasena.setText(inv.getContraseña());
			altaU.cedula.setText(inv.getDocumento());
			altaU.domicilio.setText(inv.getDomicilio());
			altaU.ciudad.setText(inv.getCiudad());
			altaU.telefono.setText(inv.getTelefono());
			break;

			case "Aficionado": altaU.comboRol.setSelectedIndex(2);
			Aficionado afi = usuarioBean.buscarAfi(user);
			altaU.apellido.setText(afi.getApellido());
			altaU.nombre.setText(afi.getNombre());
			altaU.contrasena.setText(afi.getContraseña());
			altaU.confcontrasena.setText(afi.getContraseña());
			altaU.email.setText(afi.getMail());
			altaU.nombreUsu.setText(afi.getNombreUsuario());
			altaU.ocupacion.setText(afi.getOcupacion());
			break;

			}

		}

	}

	public static void actualizar(String ap,String mail,String nom, String user, String tipo,
			String ciudad, String documento, String domicilio, String telefono, String ocupacion) throws Exception {

		boolean todoOK = camposVacios(ap, nom, mail, user, tipo, ciudad, documento, domicilio, telefono, ocupacion);

		if(todoOK) {
			todoOK = validarFormatos(mail, user);
			if(todoOK && !tipo.equalsIgnoreCase("Aficionado")) {
				todoOK=ciValida(documento);	
			}
			if(todoOK && !tipo.equalsIgnoreCase("Aficionado")) {
				todoOK=validarTel(telefono);
			}
		}
		



		if(todoOK) {


			UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
					InitialContext.doLookup(RUTA_UsuarioBean);
			switch(tipo) {

			case "Administrador": altaU.comboRol.setSelectedIndex(0);
			Administrador adm = new Administrador();

			adm = usuarioBean.buscarAdm(altaU.nombreUsu.getText());
			adm.setApellido(altaU.apellido.getText());
			adm.setNombre(altaU.nombre.getText());
			adm.setMail(altaU.email.getText());
			adm.setDocumento(altaU.cedula.getText());
			adm.setDomicilio(altaU.domicilio.getText());
			adm.setCiudad(altaU.ciudad.getText());
			adm.setTelefono(altaU.telefono.getText());

			usuarioBean.actualizarAd(adm);



			break;

			case "Investigador": altaU.comboRol.setSelectedIndex(1);
			Investigador inv = new Investigador();
			inv = usuarioBean.buscarInv(altaU.nombreUsu.getText());

			inv.setApellido(altaU.apellido.getText());
			inv.setNombre(altaU.nombre.getText());
			inv.setMail(altaU.email.getText());
			inv.setDocumento(altaU.cedula.getText());
			inv.setDomicilio(altaU.domicilio.getText());
			inv.setCiudad(altaU.ciudad.getText());
			inv.setTelefono(altaU.telefono.getText());

			usuarioBean.actualizarIn(inv);
			break;

			case "Aficionado": altaU.comboRol.setSelectedIndex(2);
			Aficionado afi = new Aficionado();

			afi = usuarioBean.buscarAfi(altaU.nombreUsu.getText());

			afi.setApellido(altaU.apellido.getText());
			afi.setNombre(altaU.nombre.getText());
			afi.setMail(altaU.email.getText());
			afi.setOcupacion(altaU.ocupacion.getText());

			usuarioBean.actualizarAf(afi);
			break;

			}

			actualizarListado(listU.modelo);

			JOptionPane.showMessageDialog(null,"Usuario actualizado correctamente");
		}
		//	actualizarListado(listU.modelo);
		// TODO Auto-generated catch block

	}



	//metodo para actualizar PASSWORD
	public static void actualizarPass(String nombreUsuario, String contraseña) throws NamingException {

		UsuarioBeanRemote usuarioBean = (UsuarioBeanRemote)
				InitialContext.doLookup(RUTA_UsuarioBean);

		boolean todoOK =validarContraseña(contraseña);

		if(todoOK) {
			Usuario user=new Usuario();
			user=usuarioBean.buscarUser(nombreUsuario);
			System.out.println(nombreUsuario);
			user.setContraseña(contraseña);
			System.out.println(contraseña);

			try {
				usuarioBean.actualizar(user);
				JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente");
			} catch (ServiciosException e) {

				System.out.println(e.getMessage());
			}
		}
	}		
	public static boolean ciValida(String Ced) throws Exception {


		int correcto=0;
		String ced=altaU.cedula.getText().trim();
		int cedula[]; // Vector donde van a estar los digitos de la cedula
		int factor[] = {8,1,2,3,4,7,6,0};// factor a multiplicar

		cedula = new int[8];	
		int suma=0;
		boolean bandera=true;
		// comprobacion para que se hayan ingresado solo numeros //
		if(bandera) {

			for(int i=0;i<ced.length();i++){
				if(ced.charAt(i) == '0' || ced.charAt(i)== '1' || ced.charAt(i)=='2' 
						|| ced.charAt(i)== '3' || ced.charAt(i) == '4' || ced.charAt(i)== '5' || ced.charAt(i)=='6' 
						|| ced.charAt(i) == '7' || ced.charAt(i)== '8' || ced.charAt(i)=='9'){
					correcto++;
					cedula[i]=Integer.parseInt("" +ced.charAt(i));

					suma = suma + (cedula[i]*factor[i]);
				}
			}

			if (correcto!=8){	// Le faltaron digitos ingresar o ingreso otro tipo de caracter
				JOptionPane.showMessageDialog(null, "Datos no válidos o faltantes en el campo Cedula", null, 1);
				return false;
			} else {
				// Caso de ingreso correcto hacemos la verificacion

				// Realizo el modulo de la suma entre 10, guardo el resto

				int resto=suma%10;
				if (resto == cedula[7]) {
					System.out.println("Correcto");
				} else {
					JOptionPane.showMessageDialog(null,"Cedula No válida: No coincide el digito verificador : "+ resto+ " --> Digito ingresado :"+cedula[7]);
					return false;
				}

			}

		}
		return bandera;
	}

	public static boolean camposVacios(String ap, String nom, String mail, String user, String tipo,
			String ciudad, String documento, String domicilio, String telefono, String ocupacion) {

		boolean bandera = true;

		if(nom.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Nombre", null, 1);
			return false;
		}
		if(ap.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Apellido", null, 1);
			return false;
		}

		if(mail.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Correo", null, 1);
			return false;
		}
		if(user.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Nombre de Usuario", null, 1);
			return false;
		}

		if(tipo != "Aficionado") {

			if(domicilio.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe completar el campo Domicilio", null, 1);
				return false;
			}

			if(ciudad.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe completar el campo Ciudad", null, 1);
				return false;
			}

			if(documento.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe completar el campo Cedula", null, 1);
				return false;
			}

			if(telefono.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe completar el campo Telefono", null, 1);
				return false;
			}

		}else {
			if(ocupacion.isBlank()) {
				JOptionPane.showMessageDialog(null, "Debe completar el campo Ocupacion", null, 1);
				return false;
			}
		}


		return bandera;

	}


	public static boolean validarFormatos(String mail, String user) {

		boolean bandera = true;

		//Minimo de caracteres
		if(user.length() <8) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario debe tener al menos 8 caracteres", null, 1);
			return false;
		}


		//Usuario sin nÃºmeros
		
		String numeros = user.replaceAll("[*a-zA-ZÀ-ÿ\u00f1\u00d1]", "");

		if(numeros.matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario no puede contener números", null, 1);
			return false;
		}


		//correo no valido
		if(!mail.contains("@")) {

			JOptionPane.showMessageDialog(null, "El correo ingresado no es válido", null, 1);

			return false;
		}
		
		return bandera;

	}

	public static boolean validarContraseña(String pass) {


		boolean bandera = true;

		//Minimo caracteres
		if(pass.length() <8) {
			JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres", null, 1);
			return false;
		}


		//Contraseña con números y letras
//cambio git
		

		String letras = pass.replaceAll("[*0-9]", "");
		//cambio
		String numeros = pass.replaceAll("[*a-zA-ZÀ-ÿ\u00f1\u00d1]", "");


		if(!numeros.matches("[0-9]+") || !letras.matches("[a-zA-ZÀ-ÿ\u00f1\u00d1]+")) {
			JOptionPane.showMessageDialog(null, "La contraseña debe contener números y letras", null, 1);
			return false;
		}
		
		if(pass.isBlank()) {
			JOptionPane.showMessageDialog(null, "Debe completar el campo Contraseña", null, 1);
			return false;
		}
	

		return bandera;
	}
	
	public static boolean validarTel(String tel) {
		
		boolean bandera = true;
		
		if(!tel.matches("[0-9]+")){
			JOptionPane.showMessageDialog(null, "El teléfono ingresado no es váido", null, 1);
			return false;
		}
		return bandera;
	}

}
