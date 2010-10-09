package sdar.manager.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import sdar.bo.Person;
import sdar.comunication.def.ComEspecification;

public class RemoteClient {
	
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			
			Person p = new Person();
			p.setName("Matheus");
			stub.checkAutentication(p);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}		
	}
}