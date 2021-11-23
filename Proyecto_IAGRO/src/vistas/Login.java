package vistas;

import java.awt.Color;



import java.awt.EventQueue;
import java.awt.Image;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.entities.Usuario;
import com.exception.ServiciosException;
import com.servicios.UsuarioBeanRemote;

import controladores.ControllerUsuario;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;


import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class Login extends JFrame {

	//public JFrame frameLogin;
	public JPanel panel;
	public JButton btnojo;
	public JButton btningresar;
	public JTextField textNombreDeUsuario;
	public JPasswordField Passcontraseña;
	//color #68abc4
	//letra Canter Light

	public Login() throws NamingException {

		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("Logo_original.png")));
		setBounds(100, 100, 501, 442);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null); 

		Color azul=new Color (104,171,196);

		//panel con logo IAGRO
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 487, 394);
		getContentPane().add(panel);
		panel.setBackground(Color.WHITE);
		setTitle("Login");
		JLabel Icon_Container = new JLabel("");	
		Icon_Container.setBounds(133, 11, 250, 101);
		//Image icon = new ImageIcon(this.getClass().getResource("final_v1.png")).getImage();
		Image icon = new ImageIcon(this.getClass().getResource("logo_2.png")).getImage();
		panel.setLayout(null);
		Icon_Container.setIcon(new ImageIcon(icon));
		panel.add(Icon_Container);

		//campo de texto para usuario
		textNombreDeUsuario = new JTextField();
		textNombreDeUsuario.setBounds(136, 151, 189, 30);
		textNombreDeUsuario.setForeground(azul);
		textNombreDeUsuario.setOpaque(false);
		textNombreDeUsuario.setBackground(new Color(104,171,196));
		textNombreDeUsuario.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(104, 171, 196))); 
		TextPrompt nombreDeUsuario = new TextPrompt("Nombre de Usuario", textNombreDeUsuario);
		nombreDeUsuario.changeAlpha(255f);
		nombreDeUsuario.changeStyle(Font.ITALIC | Font.BOLD);
		panel.add(textNombreDeUsuario);
		textNombreDeUsuario.setColumns(10);
		//Image icon_log = new ImageIcon(this.getClass().getResource("iAgro_utec.png")).getImage();

		//campo para contraseña
		Passcontraseña = new JPasswordField();
		Passcontraseña.setBounds(133, 223, 189, 30);
		Passcontraseña.setForeground(azul);
		Passcontraseña.setOpaque(false);
		Passcontraseña.setBackground(new Color(104,171,196));
		Passcontraseña.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(104, 171, 196))); 
		TextPrompt cont = new TextPrompt("Contraseña", Passcontraseña);
		cont.changeAlpha(255f);
		cont.changeStyle(Font.ITALIC | Font.BOLD);
		panel.add(Passcontraseña);

		//botón de imagen de ojo
		Image ojo = new ImageIcon(this.getClass().getResource("ojo_gris.png")).getImage();
		btnojo = new JButton("");
		btnojo.setBackground(Color.WHITE);
		panel.add(btnojo);
		btnojo.setBounds(300, 223, 36, 35);
		btnojo.setIcon(new ImageIcon(ojo));
		btnojo.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnojo.setForeground(new Color(255, 255, 255));
		//btnojo.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnojo.setBorder(null);
		btnojo.setOpaque(false);	
		panel.add(btnojo);

		//boton login
		btningresar = new JButton("Ingresar");
		btningresar.setBounds(169, 317, 127, 30);
		panel.add(btningresar);
		btningresar.setForeground(UIManager.getColor("Button.darkShadow"));
		btningresar.setFont(new Font("Candara Light", Font.BOLD, 13));
		btningresar.setBackground(Color.WHITE);
		btningresar.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow"), 3, true));






		//botón con imagen de ingreso
		/*Image ingreso = new ImageIcon(this.getClass().getResource("login_3.png")).getImage();
		JButton btningreso = new JButton("");
		btningreso.setBackground(Color.WHITE);
		panel.add(btningreso);
		btningreso.setBounds(208, 321, 50, 50);
		btningreso.setIcon(new ImageIcon(ingreso));
		btningreso.setHorizontalTextPosition(SwingConstants.RIGHT);
		btningreso.setForeground(new Color(255, 255, 255,255));
		btningreso.setBorder(null);
		btningreso.setOpaque(true);	
		panel.add(btningreso);*/


			}


		}

