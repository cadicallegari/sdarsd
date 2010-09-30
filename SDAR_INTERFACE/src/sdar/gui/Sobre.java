package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

public class Sobre {

	private XML gladeFile;
	private Window mainWindow;
	private Button voltar;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Sobre() throws FileNotFoundException {
		gladeFile = Glade.parse("sobre.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.gerenciaControles();
		this.gerenciaEventos();
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela sobre
	 */
	public void gerenciaControles() {
		voltar = (Button) gladeFile.getWidget("btn_voltar");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela sobre
	 */
	public void gerenciaEventos() {
		//Evento do botao Voltar
		voltar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				mainWindow.hide();
			}
		});
	}
}