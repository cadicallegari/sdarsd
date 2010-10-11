package sdar.manager.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import sdar.bo.Archive;
import sdar.bo.Person;

/**
 * Classe que implementa a Interface dos serviços remotos 
 */
public interface RemoteServiceInterface extends Remote {

	
	/**
	 * Metodo remoto que verifica a autenticacao de um objeto Person 
	 */
	public boolean checkAutentication(Person person) throws RemoteException;

	
	/**
	 * Metodo remoto que inseri um objeto Person
	 */
	public void insertPerson(Person person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que remoto o objeto Person
	 */
	public void deletePerson(Person person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que consulta o objeto Person
	 */
	public List<Person> retrieveAllPerson(Person person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que retorna todos os objetos Persons
	 */
	public List<Person> retrieveAllPerson() throws RemoteException;

	
	/**
	 * Metodo remoto que retorna todos os objetos Archives
	 */
	public List<Archive> retrieveAllArchive() throws RemoteException;
}