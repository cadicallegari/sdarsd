package sdar.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import sdar.client.manager.UCHandlerArchiveManager;
import sdar.comunication.especification.Especification;
import sdar.manager.rmi.RemoteServiceInterface;

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
	private Label labelListar;
	private Button upload;
	private Button download;
	private Button listar;
	private FileChooserButton fileChooserUpload;
	private FileChooserButton fileChooserDownload;
	private TreeView listArchivesRepository;
	private ListStore model;
	private DataColumnString archiveName;
	private DataColumnString archiveSize;

	
	/**
	 * Construtor da Classe
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public Main() throws IOException, ClassNotFoundException {
		gladeFile = Glade.parse("src/sdar/xml/main.glade", "janela");
		mainWindow = (Window) gladeFile.getWidget("janela");
		
		this.manageControls();
		this.manageEvents();
		this.setAuthentication(false);
		
		UCHandlerArchiveManager.locateManager();
		
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
		fileChooserDownload = (FileChooserButton) gladeFile.getWidget("fcb_download");
		labelUpload = (Label) gladeFile.getWidget("txt_upload"); 
		labelDownload = (Label) gladeFile.getWidget("txt_download");
		labelListar = (Label) gladeFile.getWidget("txt_listar");
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
					new Login(authentication, person, statusBar, fileChooserUpload, fileChooserDownload, upload, download, listar, listArchivesRepository);
				} catch (Exception e) {
					new Error(e.getMessage());
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
				} catch (Exception e) {
					new Error(e.getMessage());
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
				UCHandlerArchiveManager uc = new UCHandlerArchiveManager();
				try {
					uc.sendFile(filePath);
				} catch (Exception e) {
					new Error(e.getMessage());
					e.printStackTrace();
				}
				labelUpload.setLabel("");
			}
		});
		
		//Evento do botao Download
		download.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				UCHandlerArchiveManager uc = new UCHandlerArchiveManager();
				uc.receiveFile(labelDownload.getText(), fileChooserDownload.getFilename());
				labelDownload.setLabel("");
			}
		});
		
		//Evento do botao Listar
		listar.connect(new Button.Clicked() {
			@Override
			public void onClicked(Button arg0) {
				updateListArchives();
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
		
		//Evento da seleção de uma linha da tabela
		listArchivesRepository.connect(new TreeView.RowActivated() {
			@Override
            public void onRowActivated(TreeView treeView, TreePath treePath, TreeViewColumn treeViewColumn) {
				TreeIter row = model.getIter(treePath);
                Archive archive = new Archive();
                archive.setName(model.getValue(row, archiveName));
                archive.setSize(Integer.valueOf(model.getValue(row, archiveSize)));
                labelDownload.setAlignment(0, Float.valueOf("0.5"));
                labelDownload.setLabel(archive.getName());
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
			setSensitive(true);
		} else {
			this.setStatusBar("Usuário Desconectado.");
			setSensitive(false);
		}
 		this.authentication = authentication;
	}

	
	/**
	 * Metodo que seta a sensibilidade dos botoes da janela principal
	 * @param sensitive
	 */
	public void setSensitive(boolean sensitive) {
		this.fileChooserUpload.setSensitive(sensitive);
		this.fileChooserDownload.setSensitive(sensitive);
		this.upload.setSensitive(sensitive);
		this.download.setSensitive(sensitive);
		this.listar.setSensitive(sensitive);
		this.listArchivesRepository.setSensitive(sensitive);
		if (!sensitive) {
			this.labelUpload.setLabel("");
			this.labelDownload.setLabel("");
			this.clearListFiles();
		}
	}
	
	
	/**
	 * Metodo que seta os dados na tabela
	 */
	public void updateListArchives() {
		TreeIter row = null;
        CellRendererText renderer = null;
        TreeViewColumn column = null;
		List<Archive> listArchives = null;
		
		//Conexao RMI onde invoca metodo remoto para retornar todos os objetos Archives
		try {
			Registry reg = LocateRegistry.getRegistry(Especification.MANAGER_ADDR, Especification.RMI_PORT);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(Especification.RMI_NAME);
			listArchives = stub.retrieveAllArchive();
		} catch (Exception e) {
			new Error(e.getMessage());
			e.printStackTrace();
		}

		//Preenche a lista de arquivos com os dados
        model = new ListStore(new DataColumn[] {archiveName = new DataColumnString(), archiveSize = new DataColumnString()});
        for (Archive archive : listArchives) {
            row = model.appendRow();
            model.setValue(row, archiveName, archive.getName());
            model.setValue(row, archiveSize, String.valueOf(archive.getSize()));
        }
        listArchivesRepository.setModel(model);
        
        //Seta as propriedades das colunas
        if (listArchivesRepository.getColumns().length != 2) {
	        column = listArchivesRepository.appendColumn();
	        column.setTitle("Nome do Arquivo");
	        column.setMinWidth(300);
	        renderer = new CellRendererText(column);
	        renderer.setText(archiveName);
	
	        column = listArchivesRepository.appendColumn();
	        column.setTitle("Tamanho do Arquivo");
	        renderer = new CellRendererText(column);
	        renderer.setText(archiveSize);
        }
        if (listArchives != null) {
        	labelListar.setLabel("Total de Arquivos: " + listArchives.size());
        }
	}
	
	
	/**
	 * Metodo que limpa os dados da tabela
	 */
	public void clearListFiles() {
		//Instancia um model com nenhum valor
        model = new ListStore(new DataColumn[] {archiveName = new DataColumnString(), archiveSize = new DataColumnString()});
        listArchivesRepository.setModel(model);
        labelListar.setLabel("");
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}