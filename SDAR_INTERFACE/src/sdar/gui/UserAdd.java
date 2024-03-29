package sdar.gui;

import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Entry;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

import sdar.bo.Person;
import sdar.comunication.encryption.Encryption;
import sdar.comunication.especification.Especification;
import sdar.manager.rmi.RemoteServiceInterface;

/**
 * Classe que implementa a janela de adicionar usuario
 */
public class UserAdd {
	
	
	private boolean previousConsult;
	private Person person;
	
	private XML gladeFile;
	private Window mainWindow;
	private Entry name;
	private Entry user;
	private Entry password;
	private Button add;
	private Button exit;
	
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public UserAdd(boolean previousConsult) throws FileNotFoundException {
		this.previousConsult = previousConsult;
		gladeFile = Glade.parse("src/sdar/xml/usuario-add.glade", "janela");
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
		name = (Entry) gladeFile.getWidget("txt_nome");
		user = (Entry) gladeFile.getWidget("txt_usuario");
		password = (Entry) gladeFile.getWidget("txt_senha");
		add = (Button) gladeFile.getWidget("btn_adicionar");
		exit = (Button) gladeFile.getWidget("btn_fechar");
	}
	
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void manageEvents() {
		
		//Evento do botao Adicionar
		add.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				person = new Person();
				person.setName(name.getText());
				person.setUser(user.getText());
				person.setPassword(password.getText());
				
				if (!person.getName().trim().equals("") &&
						!person.getUser().trim().equals("") &&
						!person.getPassword().trim().equals("")) {
					//Encriptografa o objeto 
					Encryption encryption = new Encryption();
					Person personSend = encryption.encrypt(person);
					
					try {
						Registry reg = LocateRegistry.getRegistry(Especification.MANAGER_ADDR, Especification.RMI_PORT);
						RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
						stub.insertPerson(personSend);
						
						mainWindow.hide();
						if (previousConsult) {
							new UserConsult();
						}
					} catch (Exception e) {
						e.printStackTrace();
						new Error(e.getMessage());
					}
					mainWindow.hide();
				}
			}
		});
		
		//Evento do botao Voltar
		exit.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				mainWindow.hide();
			}
		});
	}
}