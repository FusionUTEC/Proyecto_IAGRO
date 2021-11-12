package vistas;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controladores.Main;

import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.ComponentOrientation;

public class MenuPrincipal extends JFrame {

	public JPanel contentPane;
	public JPanel panelAdministrador;
	public JPanel panelInvestigador;
	public JPanel panelAficionado;
	public JButton btnsalir;
	public JButton btnRegistro;
	public JButton btnFormularioAfi;
	public JButton btnFormularioInv;
	public JButton btnFormularioAdm;
	private Image form;
	public JButton btnRegistroInv;
	public JButton btnRegistroAdm;
	public JButton btnRegistroAfi;
	private Image reg;
	public JButton btnCasillasAdm;
	public JButton btnCasillasInv;
	public Image casilla;
	public JButton btnEstacionesAdm;
	public JButton btnEstacionesInv;
	private Image estaciones;
	public static JButton btnUsuariosAdm;
	private Image usuarios;
	private Image menu;
	private JPanel panel_Superior;
	private JPanel panel_inferior;
	public JLabel logOut;
	private Color verde;
	private Color azul;



	public MenuPrincipal() throws NamingException {


		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("Logo_original.png")));
		Color azul=new Color (104,171,196); //color azul 104,171,196 / 68abc4
		Color verde=new Color (166,187,95); //color verde 166,187,95 / a6bb5f 
		setResizable(false);
		setTitle("Menu Principal");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		//Panel Principal
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 806, 426);
		contentPane.add(panel);

		//panel lateral Admin
		panelAdministrador = new JPanel();
		panelAdministrador.setToolTipText("Registros");
		panelAdministrador.setBounds(0, 59, 175, 356);
		panelAdministrador.setBackground(azul);
		panel.add(panelAdministrador);
		panelAdministrador.setLayout(null);

		//panel blanco lateral Investigador
		panelInvestigador = new JPanel();
		panelInvestigador.setBounds(547, 72, 158, 356);
		panelInvestigador.setBackground(azul);
		panel.add(panelInvestigador);
		panelInvestigador.setVisible(false);
		panelInvestigador.setLayout(null);


		//panel Aficionado
		panelAficionado = new JPanel();
		panelAficionado.setBounds(200, 59,175, 369);
		panelAficionado.setBackground(azul);
		panel.add(panelAficionado);
		panelAficionado.setLayout(null);
		panelAficionado.setVisible(false);


		//logo iagro
		JLabel Icon_Container = new JLabel("");
		Icon_Container.setBounds(377, 140, 160, 148);
		Image icon = new ImageIcon(this.getClass().getResource("iagro_Menu.png")).getImage();
		panel.setLayout(null);
		panel.setLayout(null);
		Icon_Container.setIcon(new ImageIcon(icon));
		panel.add(Icon_Container);


		//Botón Aficionado Registro 
		btnRegistroAfi = new JButton("Registros");
		btnRegistroAfi.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegistroAfi.setToolTipText("Registros");
		btnRegistroAfi.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnRegistroAfi.setForeground(new Color(255, 255, 255));
		btnRegistroAfi.setBounds(19, 150, 146, 40);
		reg= new ImageIcon(this.getClass().getResource("Registros.png")).getImage();
		btnRegistroAfi.setIcon(new ImageIcon(reg));
		btnRegistroAfi.setBackground(Color.WHITE);
		btnRegistroAfi.setBorder(null);
		btnRegistroAfi.setOpaque(false);
		panelAficionado.add(btnRegistroAfi);
		
		btnFormularioAfi = new JButton("Formularios");
		btnFormularioAfi.setHorizontalAlignment(SwingConstants.LEFT);
		btnFormularioAfi.setToolTipText("Formularios");
		btnFormularioAfi.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnFormularioAfi.setForeground(new Color(255, 255, 255));
		btnFormularioAfi.setBounds(9, 89, 156, 40);
		Image form = new ImageIcon(this.getClass().getResource("formulario.png")).getImage();
		btnFormularioAfi.setIcon(new ImageIcon(form));
		btnFormularioAfi.setBackground(Color.WHITE);
		btnFormularioAfi.setBorder(null);
		btnFormularioAfi.setOpaque(false);
		panelAficionado.add(btnFormularioAfi);


		//botones Admin

		btnFormularioAdm = new JButton("Formularios");
		btnFormularioAdm.setHorizontalAlignment(SwingConstants.LEFT);
		btnFormularioAdm.setToolTipText("Formularios");
		btnFormularioAdm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnFormularioAdm.setForeground(new Color(255, 255, 255));
		btnFormularioAdm.setBounds(19, 38, 156, 40);
		//Image formu = new ImageIcon(this.getClass().getResource("formulario.png")).getImage();
		btnFormularioAdm.setIcon(new ImageIcon(form));
		btnFormularioAdm.setBackground(Color.WHITE);
		btnFormularioAdm.setBorder(null);
		btnFormularioAdm.setOpaque(false);
		panelAdministrador.add(btnFormularioAdm);

		btnCasillasAdm = new JButton("Casillas");
		btnCasillasAdm.setHorizontalAlignment(SwingConstants.LEFT);
		btnCasillasAdm.setToolTipText("Casillas");
		btnCasillasAdm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnCasillasAdm.setForeground(new Color(255, 255, 255));
		btnCasillasAdm.setBounds(19, 89, 156, 40);
		casilla= new ImageIcon(this.getClass().getResource("casilla.png")).getImage();
		btnCasillasAdm.setIcon(new ImageIcon(casilla));
		btnCasillasAdm.setBackground(Color.WHITE);
		btnCasillasAdm.setBorder(null);
		btnCasillasAdm.setOpaque(false);
		panelAdministrador.add(btnCasillasAdm);

		btnRegistroAdm= new JButton("Registros");
		btnRegistroAdm.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegistroAdm.setToolTipText("Registros");
		btnRegistroAdm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnRegistroAdm.setForeground(new Color(255, 255, 255));
		btnRegistroAdm.setBounds(19, 140, 156, 40);
		btnRegistroAdm.setIcon(new ImageIcon(reg));
		btnRegistroAdm.setBackground(Color.WHITE);
		btnRegistroAdm.setBorder(null);
		btnRegistroAdm.setOpaque(false);
		panelAdministrador.add(btnRegistroAdm);

		btnEstacionesAdm = new JButton("Estaciones");
		btnEstacionesAdm.setHorizontalAlignment(SwingConstants.LEFT);
		btnEstacionesAdm.setToolTipText("Estaciones");
		btnEstacionesAdm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnEstacionesAdm.setForeground(new Color(255, 255, 255));
		btnEstacionesAdm.setBounds(19, 199, 156, 40);
		estaciones= new ImageIcon(this.getClass().getResource("estaciones.png")).getImage();
		btnEstacionesAdm.setIcon(new ImageIcon(estaciones));
		btnEstacionesAdm.setBackground(Color.WHITE);
		btnEstacionesAdm.setBorder(null);
		btnEstacionesAdm.setOpaque(false);
		panelAdministrador.add(btnEstacionesAdm);

		btnUsuariosAdm = new JButton("Usuarios");
		btnUsuariosAdm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuariosAdm.setHorizontalAlignment(SwingConstants.LEFT);
		btnUsuariosAdm.setToolTipText("Usuarios");
		btnUsuariosAdm.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnUsuariosAdm.setForeground(new Color(255, 255, 255));
		btnUsuariosAdm.setBounds(19, 250, 156, 40);
		usuarios= new ImageIcon(this.getClass().getResource("usuarios.png")).getImage();
		btnUsuariosAdm.setIcon(new ImageIcon(usuarios));
		btnUsuariosAdm.setBackground(Color.WHITE);
		btnUsuariosAdm.setBorder(null);
		btnUsuariosAdm.setOpaque(false);
		panelAdministrador.add(btnUsuariosAdm);


		//Botones Investigador

		btnFormularioInv = new JButton("Formularios");
		btnFormularioInv.setToolTipText("Formularios");
		btnFormularioInv.setFont(new Font("Voces", Font.BOLD, 14));
		btnFormularioInv.setForeground(new Color(255, 255, 255));
		btnFormularioInv.setBounds(18, 35, 130, 40);
		btnFormularioInv.setIcon(new ImageIcon(form));
		btnFormularioInv.setBackground(Color.WHITE);
		btnFormularioInv.setBorder(null);
		btnFormularioInv.setOpaque(false);
		panelInvestigador.add(btnFormularioInv);

		btnRegistroInv = new JButton("Registros");
		btnRegistroInv.setToolTipText("Registros");
		btnRegistroInv.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnRegistroInv.setForeground(new Color(255, 255, 255));
		btnRegistroInv.setBounds(-8, 154, 156, 40);
		btnRegistroInv.setIcon(new ImageIcon(reg));
		btnRegistroInv.setBackground(Color.WHITE);
		btnRegistroInv.setBorder(null);
		btnRegistroInv.setOpaque(false);
		panelInvestigador.add(btnRegistroInv);

		btnCasillasInv = new JButton("Casillas");
		btnCasillasInv.setToolTipText("Casillas");
		btnCasillasInv.setFont(new Font("Voces", Font.BOLD, 14));
		btnCasillasInv.setForeground(new Color(255, 255, 255));
		btnCasillasInv.setBounds(2, 100, 130, 40);
		btnCasillasInv.setIcon(new ImageIcon(casilla));
		btnCasillasInv.setBackground(Color.WHITE);
		btnCasillasInv.setBorder(null);
		btnCasillasInv.setOpaque(false);
		panelInvestigador.add(btnCasillasInv);

		btnEstacionesInv = new JButton("Estaciones");
		btnEstacionesInv.setToolTipText("Estaciones");
		btnEstacionesInv.setFont(new Font("Voces", Font.BOLD, 14));
		btnEstacionesInv.setForeground(new Color(255, 255, 255));
		btnEstacionesInv.setBounds(0, 222, 156, 40);
		btnEstacionesInv.setIcon(new ImageIcon(estaciones));
		btnEstacionesInv.setBackground(Color.WHITE);
		btnEstacionesInv.setBorder(null);
		btnEstacionesInv.setOpaque(false);
		panelInvestigador.add(btnEstacionesInv);


		//panel superior azul
		panel_Superior = new JPanel();
		panel_Superior.setBounds(0, 0, 796, 71);
		panel_Superior.setBackground(azul);
		panel.add(panel_Superior);

		//panel inferios azul
		panel_inferior = new JPanel();
		panel_inferior.setBounds(0, 395, 796, 20);
		panel_inferior.setBackground(azul);
		panel.add(panel_inferior);
		menu = new ImageIcon(this.getClass().getResource("menu.png")).getImage();
		panel.setLayout(null);
		panel_Superior.setLayout(null);

		//icono Log Out
		btnsalir = new JButton("");
			
		btnsalir.setToolTipText("");
		btnsalir.setFont(new Font("Voces", Font.BOLD, 14));
		btnsalir.setForeground(new Color(255, 255, 255));
		btnsalir.setBounds(710, 11, 62, 49);
		Image log_out = new ImageIcon(this.getClass().getResource("logout.png")).getImage();
		btnsalir.setIcon(new ImageIcon(log_out));
		btnsalir.setBackground(Color.WHITE);
		btnsalir.setBorder(null);
		btnsalir.setOpaque(false);
		panel_Superior.add(btnsalir);


		//panel de Monitoreo
		Image monitoreo= new ImageIcon(this.getClass().getResource("Monitoreo.png")).getImage();
		panel_Superior.setLayout(null);
		JLabel monitoreo1 = new JLabel("");
		monitoreo1.setBounds(221, -17, 460, 104);
		panel_Superior.add(monitoreo1);
		monitoreo1.setIcon(new ImageIcon(monitoreo));

	}

	public void perfil (String tipo) {

		if (tipo.equalsIgnoreCase("Administrador")) {
			panelAdministrador.setVisible(true);
			panelInvestigador.setVisible(false);
			panelAficionado.setVisible(false);

			
		}if (tipo.equalsIgnoreCase("Investigador")) {
			panelAdministrador.setVisible(false);
			panelInvestigador.setVisible(true);
			panelInvestigador.setBounds(0, 59, 158, 356);
			panelAficionado.setVisible(false);
	

		}if (tipo.equalsIgnoreCase("Aficionado")) {
			panelAdministrador.setVisible(false);
			panelInvestigador.setVisible(false);
			panelAficionado.setVisible(true);
			panelAficionado.setBounds(0, 59, 158, 356);
			
			
			
		}
		
	}
}
