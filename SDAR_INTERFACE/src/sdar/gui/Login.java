package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

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
				if (!usuario.getText().equals("matheusc") || !senha.getText().equals("123")) {
					setAutenticado(false);
					setMensagemErro();
				} else {
					person = new Person();
					person.setNome("Matheus Cristiano Barreto");
					person.setUsuario(usuario.getText());
					person.setSenha(senha.getText());
					setAutenticado(true);
					mainWindow.hide();
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
}