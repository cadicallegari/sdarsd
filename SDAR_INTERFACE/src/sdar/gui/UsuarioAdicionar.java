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
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteServiceInterface;
import sdar.manager.autentication.Person;

public class UsuarioAdicionar {
	
	private XML gladeFile;
	private Window mainWindow;
	private Entry nome;
	private Entry usuario;
	private Entry senha;
	private Button adicionar;
	private Button voltar;
	
	private Person person;
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public UsuarioAdicionar() throws FileNotFoundException {
		gladeFile = Glade.parse("xml/usuario-add.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.gerenciaControles();
		this.gerenciaEventos();
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela de login
	 */
	public void gerenciaControles() {
		nome = (Entry) gladeFile.getWidget("txt_nome");
		usuario = (Entry) gladeFile.getWidget("txt_usuario");
		senha = (Entry) gladeFile.getWidget("txt_senha");
		adicionar = (Button) gladeFile.getWidget("btn_adicionar");
		voltar = (Button) gladeFile.getWidget("btn_voltar");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void gerenciaEventos() {
		//Evento do botao Adicionar
		adicionar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				carregaPerson();
				
				Registry reg;
				
				try {
					reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
				
					RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
				
					stub.insertPerson(person);
					
					mainWindow.hide();
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mainWindow.hide();
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
	
	/**
	 * Metodo que carrega os dados da Interface para o objeto Person
	 */
	public void carregaPerson() {
		person = new Person();
		person.setNome(nome.getText());
		person.setUsuario(usuario.getText());
		person.setSenha(senha.getText());
	}
}