package sdar.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.Window;

import sdar.client.manager.UCManterArquivoManager;
import sdar.manager.autentication.Person;

public class Principal {

	private boolean autenticado;
	private Person person;
	private String filePath;
	
	private XML gladeFile;
	private Window mainWindow;
	private Statusbar statusbar;
	private MenuItem menuConectar;
	private MenuItem menuDesconectar;
	private MenuItem menuUsuarioAdicionar;
	private MenuItem menuUsuarioConsultar;
	private MenuItem menuSair;
	private MenuItem menuSobre;
	private FileChooserButton fcb_upload;
	private Label labelUpload;
	private Label labelDownload;
	private Button upload;
	private Button download;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Principal() throws FileNotFoundException {
		gladeFile = Glade.parse("xml/principal.glade", "janela");
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
		menuConectar = (MenuItem) gladeFile.getWidget("menu_conectar");
		menuDesconectar = (MenuItem) gladeFile.getWidget("menu_desconectar");
		menuUsuarioAdicionar = (MenuItem) gladeFile.getWidget("menu_usuario_adicionar");
		menuUsuarioConsultar = (MenuItem) gladeFile.getWidget("menu_usuario_consultar");
		menuSair = (MenuItem) gladeFile.getWidget("menu_sair");
		menuSobre = (MenuItem) gladeFile.getWidget("menu_sobre");
		statusbar = (Statusbar) gladeFile.getWidget("barra_mensagem");
		fcb_upload = (FileChooserButton) gladeFile.getWidget("fcb_upload");
		labelUpload = (Label) gladeFile.getWidget("txt_upload"); 
		labelDownload = (Label) gladeFile.getWidget("txt_download");
		upload = (Button) gladeFile.getWidget("btn_upload");
		download = (Button) gladeFile.getWidget("btn_download");
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
					person = new Person();
					new Login(autenticado, person, statusbar);
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
				person = new Person();
			}
		});
		
		//Evento do menu Usuario Adicionar
		menuUsuarioAdicionar.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new UsuarioAdicionar(false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Evento do menu Usuario Consultar
		menuUsuarioConsultar.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new UsuarioConsultar();
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
		
		//Evento do fileChooser de selecionar arquivo
		fcb_upload.connect(new FileChooserButton.FileSet() {
			@Override
			public void onFileSet(FileChooserButton arg0) {
				filePath = fcb_upload.getFilename();
				labelUpload.setLabel(filePath);
			}
		});
		
		//Evento do botao Upload
		upload.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				UCManterArquivoManager uc = new UCManterArquivoManager();
				try {
					
					uc.sendFile(filePath);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Download
		download.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				//TODO Efetuar Download
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
			this.setStatusBar("Usuário Conetado. Login: " + person.getUsuario());
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