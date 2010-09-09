package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public class Principal {

	private XML gladeFile;
	private Window mainWindow;
	private Entry usuario;
	private Entry senha;
	private Button entrar;
	private Button voltar;

	public Principal() throws FileNotFoundException {
		gladeFile = Glade.parse("login.glade", "janela_sobre");
		mainWindow = (Window) gladeFile.getWidget("janela_sobre");
		
		usuario = (Entry) gladeFile.getWidget("txt_login_usuario");
		senha = (Entry) gladeFile.getWidget("txt_login_senha");
		entrar = (Button) gladeFile.getWidget("bt_login_entrar");
		voltar = (Button) gladeFile.getWidget("bt_login_voltar");
		
		mainWindow.showAll();
	}
	
	public void imprimir() {
		System.out.println("BINGOOO");
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