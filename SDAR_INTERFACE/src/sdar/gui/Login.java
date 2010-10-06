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
import org.gnome.gtk.Label;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.Window;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteServiceInterface;
import sdar.manager.autentication.Person;

public class Login {

	private boolean autenticado;
	private Person person;
	
	private XML gladeFile;
	private Window mainWindow;
	private Statusbar statusbar;
	private Entry usuario;
	private Entry senha;
	private Label mensagem;
	private Button entrar;
	private Button voltar;
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Login(boolean autenticado, Person person, Statusbar statusbar) throws FileNotFoundException {
		this.autenticado = autenticado;
		this.person = person;
		this.statusbar = statusbar;
		
		gladeFile = Glade.parse("xml/login.glade", "janela");
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
		usuario = (Entry) gladeFile.getWidget("txt_usuario");
		senha = (Entry) gladeFile.getWidget("txt_senha");
		entrar = (Button) gladeFile.getWidget("btn_entrar");
		voltar = (Button) gladeFile.getWidget("btn_voltar");
		mensagem = (Label) gladeFile.getWidget("lab_mensagem");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void gerenciaEventos() {
		//Evento do botao Entrar
		entrar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				person.setUsuario(usuario.getText());
				person.setSenha(senha.getText());
				
				try {
					Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
					RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
					setAutenticado(stub.checkAutentication(person));
					
					if (getAutenticado()) {
						mainWindow.hide();
					} else {
						setMensagemErro();
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
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
	
	/**
	 * Metodo que seta a mensagem de erro de login incorreto
	 */
	public void setMensagemErro() {
		mensagem.setLabel("Usuário ou Senha Incorretos");
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
	
	public boolean getAutenticado() {
		return this.autenticado;
	}
}