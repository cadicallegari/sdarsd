/**
 * SDARRemoteServiceImpl.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.rmi
 */
package sdar.comunication.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sdar.comunication.def.ComEspecification;
import sdar.manager.autentication.Person;
import sdar.manager.autentication.UCManterAutenticationManager;

/**
 * @author cadi
 *
 */
public class RemoteService extends UnicastRemoteObject implements RemoteServiceInterface {

	

	/**
	 * long
	 */
	private static final long serialVersionUID = -5432021798076668288L;

	
	private Registry registryServer;
	
	
	/**
	 * @throws RemoteException
	 */
	public RemoteService() throws RemoteException {
		super();
		this.initService();
	}

	
	
	private void initService() {
		
		try {
			
			this.registryServer = LocateRegistry.createRegistry(ComEspecification.RMI_PORT_SERVER);
			
			this.registryServer.rebind(ComEspecification.RMI_NAME, this);
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see sdar.comunication.rmi.SDARRemoteService#getAutentication()
	 */
	@Override
	public boolean getAutentication(Person p) throws RemoteException {
		UCManterAutenticationManager uc = new UCManterAutenticationManager();
		
		return uc.confirmAutentication(p);
	}

	/* (non-Javadoc)
	 * @see sdar.comunication.rmi.SDARRemoteService#uploadFile()
	 */
	@Override
	public boolean uploadFile() throws RemoteException {
		System.out.println("upload muito loco");
		return false;
	}

	/* (non-Javadoc)
	 * @see sdar.comunication.rmi.SDARRemoteService#downloadFile()
	 */
	@Override
	public boolean downloadFile() throws RemoteException {
		System.out.println("download muito loco");
		return false;
	}

	
	
	
	
	public static void main(String[] args) {
		
		try {
			
			new RemoteService();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
