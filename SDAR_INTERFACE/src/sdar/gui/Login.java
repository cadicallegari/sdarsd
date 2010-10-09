package sdar.gui;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Entry;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.Window;

import sdar.bo.Person;
import sdar.comunication.def.ComEspecification;
import sdar.manager.rmi.RemoteServiceInterface;

/**
 * Classe que implementa a janela de login da interface
 */
public class Login {

	
	private boolean authentication;
	private Person person;
	
	private XML gladeFile;
	private Window mainWindow;
	private Statusbar statusbar;
	private Entry user;
	private Entry password;
	private Label message;
	private Button login;
	private Button exit;
	private FileChooserButton fileChooserUpload;
	private Button upload;
	private Button download;
	private Button listar;
	private TreeView listArchivesRepository;

	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Login(boolean authentication, 
					Person person, 
					Statusbar statusbar, 
					FileChooserButton fileChooserUpload,
					Button upload, 
					Button download, 
					Button listar,
					TreeView listFilesRepository) throws FileNotFoundException {
		this.authentication = authentication;
		this.person = person;
		this.statusbar = statusbar;
		this.fileChooserUpload = fileChooserUpload;
		this.upload = upload;
		this.download = download;
		this.listar = listar;
		this.listArchivesRepository = listFilesRepository;
		
		gladeFile = Glade.parse("src/sdar/xml/login.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.manageControls();
		this.manageEvents();
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	
	/**
	 * Metodo que gerencia os controles da janela de login
	 */
	public void manageControls() {
		user = (Entry) gladeFile.getWidget("txt_usuario");
		password = (Entry) gladeFile.getWidget("txt_senha");
		login = (Button) gladeFile.getWidget("btn_entrar");
		exit = (Button) gladeFile.getWidget("btn_fechar");
		message = (Label) gladeFile.getWidget("lab_mensagem");
	}
	
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void manageEvents() {
		
		//Evento do botao Entrar
		login.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				person.setUser(user.getText());
				person.setPassword(password.getText());
				
				try {
					Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
					RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
					setAuthentication(stub.checkAutentication(person));
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Fechar
		exit.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				mainWindow.hide();
			}
		});
	}
	
	
	/**
	 * Metodo que seta a mensagem de erro de login incorreto
	 */
	public void setMessageError() {
		message.setLabel("Usuário e/ou Senha Incorretos");
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
	 * @param authentication
	 */
	public void setAuthentication(boolean authentication) {
		if (authentication) {
			this.setStatusBar("Usuário Conetado. Login: " + person.getUser());
			setSensitive(true);
			mainWindow.hide();
		} else {
			this.setStatusBar("Usuário Desconectado.");
			setSensitive(false);
			setMessageError();
		}
 		this.authentication = authentication;
	}
	
	
	/**
	 * Metodo que retorna o atributo autenticado
	 * @return
	 */
	public boolean getAuthentication() {
		return this.authentication;
	}
	
	
	/**
	 * Metodo que seta a sensibilidade dos botoes da janela principal
	 * @param sensitive
	 */
	public void setSensitive(boolean sensitive) {
		this.fileChooserUpload.setSensitive(sensitive);
		this.upload.setSensitive(sensitive);
		this.download.setSensitive(sensitive);
		this.listar.setSensitive(sensitive);
		this.listArchivesRepository.setSensitive(sensitive);
	}
}