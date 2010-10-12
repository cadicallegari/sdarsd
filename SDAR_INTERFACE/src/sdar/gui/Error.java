package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.Window;

/**
 * Classe que implementa a janela sobre da interface
 */
public class Error {

	
	private XML gladeFile;
	private Window mainWindow;
	private Button exit;
	private Label message;

	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Error(String messageError) throws FileNotFoundException {
		gladeFile = Glade.parse("src/sdar/xml/error.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.manageControls();
		this.manageEvents();
		
		this.message.setLabel(messageError);
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	
	/**
	 * Metodo que gerencia os controles da janela sobre
	 */
	public void manageControls() {
		message = (Label) gladeFile.getWidget("txt_erro");
		exit = (Button) gladeFile.getWidget("btn_fechar");
	}
	
	
	/**
	 * Metodo que gerencia os eventos da janela sobre
	 */
	public void manageEvents() {
		
		//Evento do botao Fechar
		exit.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				mainWindow.hide();
			}
		});
	}
}