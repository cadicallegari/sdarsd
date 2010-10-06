package sdar.comunication.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import sdar.comunication.def.ComEspecification;
import sdar.manager.autentication.Person;

public class RemoteClient {
	
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.getRegistry("localhost", ComEspecification.RMI_PORT_SERVER);
			RemoteServiceInterface stub = (RemoteServiceInterface) reg.lookup(ComEspecification.RMI_NAME);
			
			Person p = new Person();
			p.setNome("Matheus");
			stub.checkAutentication(p);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}		
	}
}