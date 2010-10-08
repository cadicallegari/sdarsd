package sdar.comunication.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import sdar.bo.Archive;
import sdar.bo.Person;
import sdar.comunication.def.ComEspecification;
import sdar.manager.autentication.UCManterAutenticationManager;
import sdar.manager.solicitation.UCManterSolicitationManager;

public class RemoteService extends UnicastRemoteObject implements RemoteServiceInterface {

	private static final long serialVersionUID = -5432021798076668288L;

	private Registry registryServer;
	
	/**
	 * Metodo construtora da classe RemoteService
	 * @throws RemoteException
	 */
	public RemoteService() throws RemoteException {
		super();
		this.initService();
	}
	
	
	/**
	 * Metodo que inicializa o serviço remoto
	 */
	private void initService() {
		try {
			this.registryServer = LocateRegistry.createRegistry(ComEspecification.RMI_PORT_SERVER);
			this.registryServer.rebind(ComEspecification.RMI_NAME, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Metodo remoto que verifica a autenticacao de um objeto Person 
	 */
	@Override
	public boolean checkAutentication(Person person) throws RemoteException {
		UCManterAutenticationManager uCManterAutenticationManager = new UCManterAutenticationManager();
		return uCManterAutenticationManager.checkAutentication(person);
	}
	
	
	/**
	 * Metodo remoto que inseri um objeto Person
	 */
	@Override
	public void insertPerson(Person person) throws RemoteException {
		UCManterAutenticationManager uCManterAutenticationManager = new UCManterAutenticationManager();
		uCManterAutenticationManager.insert(person);
	}
	
	
	/**
	 * Metodo remoto que remoto o objeto Person
	 */
	@Override
	public void deletePerson(Person person) throws RemoteException {
		UCManterAutenticationManager uCManterAutenticationManager = new UCManterAutenticationManager();
		uCManterAutenticationManager.delete(person);
	}
	
	
	/**
	 * Metodo remoto que consulta o objeto Person
	 */
	@Override
	public List<Person> retrieveAllPerson(Person person) throws RemoteException {
		UCManterAutenticationManager uCManterAutenticationManager = new UCManterAutenticationManager();
		return uCManterAutenticationManager.retrieve(person);
	}
	
	
	/**
	 * Metodo remoto que retorna todos os objetos Persons
	 */
	@Override
	public List<Person> retrieveAllPerson() throws RemoteException {
		UCManterAutenticationManager uCManterAutenticationManager = new UCManterAutenticationManager();
		return uCManterAutenticationManager.retrieveAll();
	}
	

	/**
	 * Metodo remoto que executa a opcao de Upload do arquivo
	 */
	@Override
	public boolean uploadFile() throws RemoteException {
		System.out.println("Upload");
		return false;
	}

	/**
	 * Metodo remoto que executa a opcao de Download do arquivo
	 */
	@Override
	public boolean downloadFile() throws RemoteException {
		System.out.println("Download");
		return false;
	}
	
	/**
	 * Metodo remoto que retorna todos os objetos Archives
	 */
	public List<Archive> retrieveAllArchive() throws RemoteException {
		UCManterSolicitationManager uc = new UCManterSolicitationManager();
		List<Archive> list = null;

		try {
			
			list = uc.listFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
}