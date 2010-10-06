package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.FileChooser;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

public class Upload {

	private XML gladeFile;
	private Window mainWindow;
	private Button voltar;
	private FileChooser arquivo;
	private String filename;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Upload(String filename) throws FileNotFoundException {
		this.filename = filename;
		
		gladeFile = Glade.parse("xml/upload.glade", "janela");
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
		arquivo = (FileChooser) gladeFile.getWidget("arquivo");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela sobre
	 */
	public void gerenciaEventos() {
		//Evento do botao Voltar
		voltar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				filename = arquivo.getFilename();
				mainWindow.hide();
			}
		});
	}
}