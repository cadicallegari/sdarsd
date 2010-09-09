package sdar.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Principal {

	private Window janelaPrincipal;
	private Label etiqueta;
	private ComboBox combo;
	
	public Principal() throws FileNotFoundException, GladeXMLException, IOException
	{
		//Carrega a interface a partir do arquivo glade
		LibGlade arvoreDeWidgets = new LibGlade("simple.glade", this);
		 
		//Carrega os Widgets em variaveis
		janelaPrincipal = (Window) arvoreDeWidgets.getWidget("janelaPrincipal");
		etiqueta = (Label) arvoreDeWidgets.getWidget("etiqueta");
		combo = (ComboBox) arvoreDeWidgets.getWidget("combo");
		//Define o valor padrao dentre os itens da lista combo box
		combo.setActive(0);
		 
		//Exibe toda interface
		janelaPrincipal.showAll();
		 
		//Inicia o loop principal de eventos
		Gtk.main();
	}
	 
	//Callbacks
	 
	public void mudaTamanho()
	{
		String text = combo.getActiveText();
		 
		if (text.equals("Pequeno")) {
			etiqueta.setMarkup("<small>Texto de exemplo</small>");
		} else if (text.equals("Normal")) {
			etiqueta.setMarkup("Texto de exemplo.");
		} else if (text.equals("Grande")) {
			etiqueta.setMarkup("<big>Texto de exemplo.</big>");
		}
	}
	 
	public void sair()
	{
		//Sai do loop principal de eventos
		Gtk.mainQuit();
		//Finaliza o programa
		System.exit(0);
	}
	 
	//Inicia a aplicacao
	public static void main(String[] args)
	{
		try {
			Gtk.init(args);
			new Principal();
			Gtk.main();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
