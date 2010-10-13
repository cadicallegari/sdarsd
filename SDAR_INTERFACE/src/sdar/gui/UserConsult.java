package sdar.gui;

import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreePath;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.Window;

import sdar.bo.Person;
import sdar.comunication.especification.Especification;
import sdar.manager.rmi.RemoteServiceInterface;

/**
 * Classe que implementa a janela de consulta de usuarios
 */
public class UserConsult {
	
	
	private Person person;
	
	private XML gladeFile;
	private Window mainWindow;
	private Button add;
	private Button remove;
	private Button exit;
	private TreeView listUsers;
	private ListStore model;
	private DataColumnString name;
	private DataColumnString user;
	
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public UserConsult() throws FileNotFoundException {
		gladeFile = Glade.parse("src/sdar/xml/usuario-consult.glade", "janela");
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
		listUsers = (TreeView) gladeFile.getWidget("tabela");
		add = (Button) gladeFile.getWidget("btn_adicionar");
		remove = (Button) gladeFile.getWidget("btn_remover");
		exit = (Button) gladeFile.getWidget("btn_fechar");
		
		this.setListUsers();
	}
	
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void manageEvents() {
		
		//Evento do botao Adicionar
		add.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				try {
					mainWindow.hide();
					new UserAdd(true);
				} catch (Exception e) {
					new Error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Remover
		remove.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				if (person != null && !person.getName().trim().equals("")) {
					try {
						Registry reg = LocateRegistry.getRegistry("localhost", Especification.RMI_PORT);
						RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
						stub.deletePerson(person);
						person = null;
					} catch (Exception e) {
						new Error(e.getMessage());
						e.printStackTrace();
					}
					updateListUsers();
				}
			}
		});
		
		//Evento da ativacao de uma linha da tabela
		listUsers.connect(new TreeView.RowActivated() {
			@Override
            public void onRowActivated(TreeView treeView, TreePath treePath, TreeViewColumn treeViewColumn) {
				TreeIter row = model.getIter(treePath);
                person = new Person();
                person.setName(model.getValue(row, name));
                person.setUser(model.getValue(row, user));
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
	 * Metodo que seta os dados do banco de dados na lista de usuarios
	 */
	public void setListUsers() {
        TreeIter row = null;
        CellRendererText renderer = null;
        TreeViewColumn column = null;
		List<Person> listPersons = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Person
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", Especification.RMI_PORT);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
			listPersons = stub.retrieveAllPerson();
		} catch (Exception e) {
			new Error(e.getMessage());
			e.printStackTrace();
		}
		
		//Transforma de Lista para Vector de Person
		Person[] persons = new Person[0];
		if (listPersons != null) {
			persons = new Person[listPersons.size()];
			for (int i = 0; i < listPersons.size(); i++) {
				persons[i] = listPersons.get(i);
			}
		}
		
		//Preenche a tabela com os dados
        model = new ListStore(new DataColumn[] {name = new DataColumnString(), user = new DataColumnString()});
        for (Person person : persons) {
            row = model.appendRow();
            model.setValue(row, name, person.getName());
            model.setValue(row, user, person.getUser());
        }
        listUsers.setModel(model);

        
        //Seta as propriedades das colunas
        column = listUsers.appendColumn();
        column.setTitle("Name");
        column.setMinWidth(300);
        renderer = new CellRendererText(column);
        renderer.setText(name);

        column = listUsers.appendColumn();
        column.setTitle("Usuário");
        renderer = new CellRendererText(column);
        renderer.setText(user);
	}
	
	
	/**
	 * Metodo que seta os dados do banco de dados na tabela
	 */
	public void updateListUsers() {
        TreeIter row = null;
		List<Person> listPersons = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Person
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", Especification.RMI_PORT);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
			listPersons = stub.retrieveAllPerson();
		} catch (Exception e) {
			new Error(e.getMessage());
			e.printStackTrace();
		}
		
		//Transforma de Lista para Vector de Person
		Person[] persons = new Person[0];
		if (listPersons != null) {
			persons = new Person[listPersons.size()];
			for (int i = 0; i < listPersons.size(); i++) {
				persons[i] = listPersons.get(i);
			}
		}
		
		//Preenche a tabela com os dados
        model = new ListStore(new DataColumn[] {name = new DataColumnString(), user = new DataColumnString()});
        for (Person person : persons) {
            row = model.appendRow();
            model.setValue(row, name, person.getName());
            model.setValue(row, user, person.getUser());
        }
        listUsers.setModel(model);
	}
}