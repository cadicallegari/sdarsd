package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public class Principal {

	private boolean autenticado;
	private XML gladeFile;
	private Window mainWindow;
	private Statusbar statusbar;
	private ImageMenuItem menuConectar;
	private ImageMenuItem menuDesconectar;
	private ImageMenuItem menuSair;
	private ImageMenuItem menuSobre;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Principal() throws FileNotFoundException {
		gladeFile = Glade.parse("principal.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.gerenciaControles();
		this.gerenciaEventos();
		
		this.setAutenticado(false);
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela principal
	 */
	public void gerenciaControles() {
		menuConectar = (ImageMenuItem) gladeFile.getWidget("menu_conectar");
		menuDesconectar = (ImageMenuItem) gladeFile.getWidget("menu_desconectar");
		menuSair = (ImageMenuItem) gladeFile.getWidget("menu_sair");
		menuSobre = (ImageMenuItem) gladeFile.getWidget("menu_sobre");
		statusbar = (Statusbar) gladeFile.getWidget("barra_mensagem");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela principal
	 */
	public void gerenciaEventos() {
		//Evento do menu Conectar
		menuConectar.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new Login(autenticado, statusbar);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do menu Desconectar
		menuDesconectar.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				setAutenticado(false);
			}
		});
		
		//Evento do menu Sair
		menuSair.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				Gtk.mainQuit();
				System.exit(0);
			}
		});
		
		//Evento do menu Sobre
		menuSobre.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new Sobre();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Metodo que seta a mensagem do StatusBar
	 * @param mensagem
	 */
	public void setStatusBar(String mensagem) {
		this.statusbar.setMessage(mensagem);
	}
	
	/**
	 * Metodo que seta o atributo autenticado
	 * @param autenticado
	 */
	public void setAutenticado(boolean autenticado) {
		if (autenticado) {
			this.setStatusBar("Usuário Conectado.");
		} else {
			this.setStatusBar("Usuário Desconectado.");
		}
 		this.autenticado = autenticado;
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Gtk.init(args);
			new Principal();
			Gtk.main();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}