package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public class Principal {

	public static boolean autenticado;
	private XML gladeFile;
	private Window mainWindow;
	private ImageMenuItem menuConectar;
	private ImageMenuItem menuSair;
	private ImageMenuItem menuSobre;
	private Statusbar barraMensagem;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Principal() throws FileNotFoundException {
		gladeFile = Glade.parse("principal.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.gerenciaControles();
		this.gerenciaEventos();
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela principal
	 */
	public void gerenciaControles() {
		menuConectar = (ImageMenuItem) gladeFile.getWidget("menu_conectar");
		menuSair = (ImageMenuItem) gladeFile.getWidget("menu_sair");
		menuSobre = (ImageMenuItem) gladeFile.getWidget("menu_sobre");
		barraMensagem = (Statusbar) gladeFile.getWidget("barra_mensagem");
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
					new Login(autenticado);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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