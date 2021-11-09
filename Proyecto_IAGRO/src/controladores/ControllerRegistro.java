package controladores;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.entities.Departamento;
import com.entities.Registro;
import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.DepartamentoBeanRemote;
import com.servicios.RegistroBeanRemote;

import vistas.AltaRegistro;
import vistas.ListadoRegistro;

public class ControllerRegistro implements Constantes {
	
	public static ListadoRegistro ListaR;
	public static AltaRegistro AltaR;
	
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
}
