package sdar.gui;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteServiceInterface;
import sdar.manager.autentication.Person;

public class UsuarioConsultar {
	
	private Person person;
	
	private XML gladeFile;
	private Window mainWindow;
	private Button adicionar;
	private Button remover;
	private Button voltar;
	private TreeView tabela;
	private ListStore model;
	private DataColumnString nome;
	private DataColumnString usuario;
	
	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public UsuarioConsultar() throws FileNotFoundException {
		gladeFile = Glade.parse("xml/usuario-src.glade", "janela");
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
		tabela = (TreeView) gladeFile.getWidget("tabela");
		adicionar = (Button) gladeFile.getWidget("btn_adicionar");
		remover = (Button) gladeFile.getWidget("btn_remover");
		voltar = (Button) gladeFile.getWidget("btn_voltar");
		
		this.setTabela();
	}
	
	/**
	 * Metodo que gerencia os eventos da janela de login
	 */
	public void gerenciaEventos() {
		
		//Evento do botao Adicionar
		adicionar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				try {
					mainWindow.hide();
					new UsuarioAdicionar(true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Remover
		remover.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				if (!person.getNome().trim().equals("")) {
					try {
						Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
						RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
						stub.deletePerson(person);
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NotBoundException e) {
						e.printStackTrace();
					}
					updateTabela();
				}
			}
		});
		
		//Evento da ativacao de uma linha da tabela
		tabela.connect(new TreeView.RowActivated() {
			@Override
            public void onRowActivated(TreeView treeView, TreePath treePath, TreeViewColumn treeViewColumn) {
				TreeIter row = model.getIter(treePath);
                person = new Person();
                person.setNome(model.getValue(row, nome));
                person.setUsuario(model.getValue(row, usuario));
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
	 * Metodo que seta os dados do banco de dados na tabela
	 */
	private void setTabela() {
        TreeIter row = null;
        CellRendererText renderer = null;
        TreeViewColumn column = null;
		List<Person> listPersons = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Person
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			listPersons = stub.retrieveAllPerson();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
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
        model = new ListStore(new DataColumn[] {nome = new DataColumnString(), usuario = new DataColumnString()});
        for (Person person : persons) {
            row = model.appendRow();
            model.setValue(row, nome, person.getNome());
            model.setValue(row, usuario, person.getUsuario());
        }
        tabela.setModel(model);

        
        //Seta as propriedades das colunas
        column = tabela.appendColumn();
        column.setTitle("Name");
        column.setMinWidth(300);
        renderer = new CellRendererText(column);
        renderer.setText(nome);

        column = tabela.appendColumn();
        column.setTitle("Usuário");
        renderer = new CellRendererText(column);
        renderer.setText(usuario);
	}
	
	/**
	 * Metodo que seta os dados do banco de dados na tabela
	 */
	private void updateTabela() {
        TreeIter row = null;
		List<Person> listPersons = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Person
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			listPersons = stub.retrieveAllPerson();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
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
        model = new ListStore(new DataColumn[] {nome = new DataColumnString(), usuario = new DataColumnString()});
        for (Person person : persons) {
            row = model.appendRow();
            model.setValue(row, nome, person.getNome());
            model.setValue(row, usuario, person.getUsuario());
        }
        tabela.setModel(model);
	}
}