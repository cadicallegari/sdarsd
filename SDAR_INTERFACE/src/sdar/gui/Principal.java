package sdar.gui;

import java.io.FileNotFoundException;

import org.gnome.gtk.*;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;

public final class Principal {
	
	 final XML glade;
	 final Window top;
	 final Button confirm;
	 

	 
	public Principal() throws FileNotFoundException
	{
		 glade = Glade.parse("sobre.glade", "janela_sobre");
		 
		 top = (Window) glade.getWidget("janela_sobre");
		 confirm = (Button) glade.getWidget("button4");
		 
		 top.showAll();
	}
	 
	 
	public static void main(String[] args)
	{
		try {
			Gtk.init(args);
			new Principal();
			Gtk.main();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			Gtk.init(args);
//			new Principal();
//			Gtk.main();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
}
