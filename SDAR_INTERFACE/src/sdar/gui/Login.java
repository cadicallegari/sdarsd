package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public class Login {

	private boolean autenticado;
	private XML gladeFile;
	private Window mainWindow;
	private Entry usuario;
	private Entry senha;
	private Label mensagem;
	private Button entrar;
	private Button voltar;
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Login(boolean autenticado) throws FileNotFoundException {
		this.autenticado = autenticado;
		gladeFile = Glade.parse("login.glade", "janela");
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
					setMensagemErro();
				} else {
					autenticado = true;
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
}