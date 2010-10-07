package sdar.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.FileChooser;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

import sdar.client.manager.UCManterArquivoManager;

public class Upload {

	private XML gladeFile;
	private Window mainWindow;
	private Button enviar;
	private Button voltar;
	private FileChooser arquivo;
	private String filename;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Upload() throws FileNotFoundException {
		
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
		enviar = (Button) gladeFile.getWidget("btn_enviar");
		voltar = (Button) gladeFile.getWidget("btn_fechar");
		arquivo = (FileChooser) gladeFile.getWidget("arquivo");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela sobre
	 */
	public void gerenciaEventos() {
		
		//Evento do botao Voltar
		enviar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				filename = arquivo.getFilename();

				UCManterArquivoManager uc = new UCManterArquivoManager();
				try {
					
					uc.sendFile(filename);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Voltar
		voltar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				mainWindow.hide();
			}
		});
		
	}
}