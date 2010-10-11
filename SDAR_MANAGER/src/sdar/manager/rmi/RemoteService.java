package sdar.manager.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import sdar.bo.Archive;
import sdar.bo.Person;
import sdar.comunication.especification.Especification;
import sdar.manager.autentication.UCHandlerAuthenticationManager;
import sdar.manager.solicitation.UCHandlerSolicitationManager;

/**
 * Classe que implementa os serviços remotos
 * @author matheus
 *
 */
public class RemoteService extends UnicastRemoteObject implements RemoteServiceInterface {

	/**
	 * 
	 */
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
			this.registryServer = LocateRegistry.createRegistry(Especification.RMI_PORT);
			this.registryServer.rebind(Especification.RMI_NAME, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Metodo remoto que verifica a autenticacao de um objeto Person 
	 */
	@Override
	public boolean checkAutentication(Person person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();
		return uCManterAutenticationManager.checkAutentication(person);
	}
	
	
	/**
	 * Metodo remoto que inseri um objeto Person
	 */
	@Override
	public void insertPerson(Person person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();
		uCManterAutenticationManager.insert(person);
	}
	
	
	/**
	 * Metodo remoto que remoto o objeto Person
	 */
	@Override
	public void deletePerson(Person person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();
		uCManterAutenticationManager.delete(person);
	}
	
	
	/**
	 * Metodo remoto que consulta o objeto Person
	 */
	@Override
	public List<Person> retrieveAllPerson(Person person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();
		return uCManterAutenticationManager.retrieve(person);
	}
	
	
	/**
	 * Metodo remoto que retorna todos os objetos Persons
	 */
	@Override
	public List<Person> retrieveAllPerson() throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();
		return uCManterAutenticationManager.retrieveAll();
	}

	
	/**
	 * Metodo remoto que retorna todos os objetos Archives
	 */
	public List<Archive> retrieveAllArchive() throws RemoteException {
		UCHandlerSolicitationManager uCHandlerSolicitationManager = new UCHandlerSolicitationManager();
		List<Archive> list = null;

		try {
			list = uCHandlerSolicitationManager.listArchives();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}