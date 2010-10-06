package sdar.comunication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import sdar.manager.autentication.Person;

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
	 * Metodo remoto que executa a opcao de Upload do arquivo
	 */
	public boolean uploadFile() throws RemoteException;

	/**
	 * Metodo remoto que executa a opcao de Download do arquivo
	 */
	public boolean downloadFile() throws RemoteException;
}
