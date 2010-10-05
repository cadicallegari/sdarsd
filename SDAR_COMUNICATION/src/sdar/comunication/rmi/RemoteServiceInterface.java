/**
 * SDARRemoteService.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.rmi
 */
package sdar.comunication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sdar.manager.autentication.Person;

/**
 * @author cadi
 *
 */
public interface RemoteServiceInterface extends Remote {

	//TODO Assinatura provisoria dos metodos
	
	boolean getAutentication(Person p) throws RemoteException;
	void insertPerson(Person p) throws RemoteException;
	
	boolean uploadFile() throws RemoteException;
	boolean downloadFile() throws RemoteException;
	
}
