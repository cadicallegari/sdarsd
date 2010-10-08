package sdar.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.CellRendererText;
import org.gnome.gtk.DataColumn;
import org.gnome.gtk.DataColumnString;
import org.gnome.gtk.FileChooserButton;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Label;
import org.gnome.gtk.ListStore;
import org.gnome.gtk.MenuItem;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.TreeIter;
import org.gnome.gtk.TreePath;
import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

import sdar.bo.Archive;
import sdar.bo.Person;
import sdar.client.manager.UCManterArquivoManager;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteServiceInterface;

/**
 * Classe que implementa a janela principal da interface
 */
public class Main {

	private boolean authentication;
	private Person person;
	private String filePath;
	
	private XML gladeFile;
	private Window mainWindow;
	private Statusbar statusBar;
	private MenuItem menuConnect;
	private MenuItem menuDisconnect;
	private MenuItem menuUserAdd;
	private MenuItem menuUserConsult;
	private MenuItem menuExit;
	private MenuItem menuAbout;
	
	
	private Label labelUpload;
	private Label labelDownload;
	private Button upload;
	private Button listar;
	private Button download;
	private FileChooserButton fileChooserUpload;
	private TreeView listArchivesRepository;
	private ListStore model;
	private DataColumnString fileName;
	private DataColumnString fileSize;

	/**
	 * Construtor da Classe
	 * @throws FileNotFoundException
	 */
	public Main() throws FileNotFoundException {
		gladeFile = Glade.parse("src/sdar/xml/main.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.manageControls();
		this.manageEvents();
		
		this.setAuthentication(false);
		
		mainWindow.showAll();
		Gtk.main();
	}
	
	/**
	 * Metodo que gerencia os controles da janela principal
	 */
	public void manageControls() {
		menuConnect = (MenuItem) gladeFile.getWidget("menu_conectar");
		menuDisconnect = (MenuItem) gladeFile.getWidget("menu_desconectar");
		menuUserAdd = (MenuItem) gladeFile.getWidget("menu_usuario_adicionar");
		menuUserConsult = (MenuItem) gladeFile.getWidget("menu_usuario_consultar");
		menuExit = (MenuItem) gladeFile.getWidget("menu_sair");
		menuAbout = (MenuItem) gladeFile.getWidget("menu_sobre");
		statusBar = (Statusbar) gladeFile.getWidget("barra_mensagem");
		fileChooserUpload = (FileChooserButton) gladeFile.getWidget("fcb_upload");
		labelUpload = (Label) gladeFile.getWidget("txt_upload"); 
		labelDownload = (Label) gladeFile.getWidget("txt_download");
		upload = (Button) gladeFile.getWidget("btn_upload");
		download = (Button) gladeFile.getWidget("btn_download");
		listar = (Button) gladeFile.getWidget("btn_listar");
		listArchivesRepository = (TreeView) gladeFile.getWidget("lista_arquivos");
	}
	
	/**
	 * Metodo que gerencia os eventos da janela principal
	 */
	public void manageEvents() {
		
		//Evento do menu Conectar
		menuConnect.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					person = new Person();
					new Login(authentication, person, statusBar, fileChooserUpload, upload, download, listArchivesRepository);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do menu Desconectar
		menuDisconnect.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				setAuthentication(false);
				person = new Person();
			}
		});
		
		//Evento do menu Usuario Adicionar
		menuUserAdd.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new UserAdd(false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Evento do menu Usuario Consultar
		menuUserConsult.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new UserConsult();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do menu Sair
		menuExit.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				Gtk.mainQuit();
				System.exit(0);
			}
		});
		
		//Evento do menu Sobre
		menuAbout.connect(new MenuItem.Activate() {
			@Override
			public void onActivate(MenuItem arg0) {
				try {
					new About();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do fileChooser de selecionar arquivo
		fileChooserUpload.connect(new FileChooserButton.FileSet() {
			@Override
			public void onFileSet(FileChooserButton arg0) {
				filePath = fileChooserUpload.getFilename();
				labelUpload.setAlignment(0, Float.valueOf("0.5"));
				labelUpload.setLabel(filePath);				
			}
		});
		
		//Evento do botao Upload
		upload.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				UCManterArquivoManager uc = new UCManterArquivoManager();
				try {
					uc.sendFile(filePath);
					updateListFiles();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Evento do botao Download
		download.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				//TODO Efetuar Download
				updateListFiles();
			}
		});
		
		//Evento do botao Listar
		listar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				//TODO Efetuar Listamento
				try {
					Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
					RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);

					System.out.println("alou 1");
					List<Archive> l = stub.retrieveAllArchive();
					System.out.println("alou 1");
					
					System.out.println(l.size());
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
				
				
			}
		});
		
		//Evento relacionado ao fechamento da Janela
		mainWindow.connect(new Window.DeleteEvent() {
			@Override
			public boolean onDeleteEvent(Widget arg0, Event arg1) {
				Gtk.mainQuit();
				System.exit(0);
				return false;
			}
		});
		
		//Evento da ativacao de uma linha da tabela
		listArchivesRepository.connect(new TreeView.RowActivated() {
			@Override
            public void onRowActivated(TreeView treeView, TreePath treePath, TreeViewColumn treeViewColumn) {
				TreeIter row = model.getIter(treePath);
                Archive archive = new Archive();
                archive.setFilename(model.getValue(row, fileName));
                archive.setSize(Integer.valueOf(model.getValue(row, fileSize)));
                labelDownload.setAlignment(0, Float.valueOf("0.5"));
                labelDownload.setLabel(archive.getFilename());
            }
		});
	}
	
	/**
	 * Metodo que seta a mensagem do StatusBar
	 * @param mensagem
	 */
	public void setStatusBar(String mensagem) {
		this.statusBar.setMessage(mensagem);
	}
	
	/**
	 * Metodo que seta o atributo autenticado
	 * @param authentication
	 */
	public void setAuthentication(boolean authentication) {
		if (authentication) {
			this.setStatusBar("Usuário Conetado. Login: " + person.getUser());
			//setSensitive(true);
		} else {
			this.setStatusBar("Usuário Desconectado.");
			//setSensitive(false);
		}
 		this.authentication = authentication;
	}

	/**
	 * Metodo que seta a sensibilidade dos botoes da janela principal
	 * @param sensitive
	 */
	public void setSensitive(boolean sensitive) {
		this.fileChooserUpload.setSensitive(sensitive);
		this.upload.setSensitive(sensitive);
		this.download.setSensitive(sensitive);
		this.listArchivesRepository.setSensitive(sensitive);
	}
	
	/**
	 * Metodo que seta os dados do banco de dados na lista de arquivos
	 */
	public void setListFiles() {
        TreeIter row = null;
        CellRendererText renderer = null;
        TreeViewColumn column = null;
		List<Archive> listArchives = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Files
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			listArchives = stub.retrieveAllArchive();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		//Transforma de Lista para Vector de Archive
		Archive[] archives = new Archive[0];
		if (listArchives != null) {
			archives = new Archive[listArchives.size()];
			for (int i = 0; i < listArchives.size(); i++) {
				archives[i] = listArchives.get(i);
			}
		}
		
		//Preenche a lista de arquivos com os dados
        model = new ListStore(new DataColumn[] {fileName = new DataColumnString(), fileSize = new DataColumnString()});
        for (Archive archive : archives) {
            row = model.appendRow();
            model.setValue(row, fileName, archive.getFilename());
            model.setValue(row, fileSize, String.valueOf(archive.getSize()));
        }
        listArchivesRepository.setModel(model);

        
        //Seta as propriedades das colunas
        column = listArchivesRepository.appendColumn();
        column.setTitle("Nome do Arquivo");
        column.setMinWidth(300);
        renderer = new CellRendererText(column);
        renderer.setText(fileName);

        column = listArchivesRepository.appendColumn();
        column.setTitle("Tamanho do Arquivo");
        renderer = new CellRendererText(column);
        renderer.setText(fileSize);
	}
	
	/**
	 * Metodo que seta os dados do banco de dados na tabela
	 */
	public void updateListFiles() {
		TreeIter row = null;
		List<Archive> listArchives = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Files
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			listArchives = stub.retrieveAllArchive();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		//Transforma de Lista para Vector de Archive
		Archive[] archives = new Archive[0];
		if (listArchives != null) {
			archives = new Archive[listArchives.size()];
			for (int i = 0; i < listArchives.size(); i++) {
				archives[i] = listArchives.get(i);
			}
		}
		
		//Preenche a lista de arquivos com os dados
        model = new ListStore(new DataColumn[] {fileName = new DataColumnString(), fileSize = new DataColumnString()});
        for (Archive archive : archives) {
            row = model.appendRow();
            model.setValue(row, fileName, archive.getFilename());
            model.setValue(row, fileSize, String.valueOf(archive.getSize()));
        }
        listArchivesRepository.setModel(model);
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Gtk.init(args);
			new Main();
			Gtk.main();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}