package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.gdk.Pixbuf;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public class Login {

	private String filename_logo_sdar = "/home/matheus/workspace/SDAR_INTERFACE/logo_sdar.png";
	private XML gladeFile;
	private Window mainWindow;
	private Entry usuario;
	private Entry senha;
	private Button entrar;
	private Button voltar;
	private Image logo_sdar;

	public Login() throws FileNotFoundException {
		gladeFile = Glade.parse("login.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.gerenciaControles();
		this.gerenciaEventos();
		
		Pixbuf image = new Pixbuf(filename_logo_sdar);		
		logo_sdar.setImage(image);
		logo_sdar.show();
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela de login
	 */
	public void gerenciaControles() {
		usuario = (Entry) gladeFile.getWidget("txt_usuario");
		senha = (Entry) gladeFile.getWidget("txt_senha");
		entrar = (Button) gladeFile.getWidget("btn_entrar");
		voltar = (Button) gladeFile.getWidget("btn_voltar");
		logo_sdar = (Image) gladeFile.getWidget("img_logo");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void gerenciaEventos() {
		//Evento do botao Entrar
		entrar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				System.out.println(usuario.getText());
				System.out.println(senha.getText());
			}
		});
		
		//Evento do botao Voltar
		voltar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				Gtk.mainQuit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		try {
			Gtk.init(args);
			new Login();
			Gtk.main();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}