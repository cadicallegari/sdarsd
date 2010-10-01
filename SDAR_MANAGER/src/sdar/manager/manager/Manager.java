/**
 * Manager.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.manager
 */
package sdar.manager.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import sdar.comunication.common.Package;
import sdar.comunication.common.Util;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteService;
import sdar.comunication.udp.UDPComunication;
import sdar.manager.server.Server;

/**
 * @author cadi
 *
 */
public class Manager {

	private Server serverRep;
	private RemoteService serverRMI;
	
	
	
	public Manager() throws RemoteException {
		
		serverRMI = new RemoteService();
		serverRep = new Server();
		
	}



	public void sendFile(String filePath) throws IOException {
		
		File f = new File(filePath);
		FileInputStream fi = new FileInputStream(f);
		byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
		UDPComunication udpCom = new UDPComunication();
		Package p = null;
		
		int qtd = fi.read(buf);
		
		System.out.println("leu " + qtd);
		
		while (qtd == ComEspecification.BUFFER_SIZE) { 			//enquanto nao acabar de ler o arquivo
			p = new Package();
			p.setPayLoad(Util.copyBytes(buf, qtd));
			p.setFileName(filePath);
			p.setPool(true);
			udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
			
			qtd = fi.read(buf);
			
			System.out.println("leu " + qtd);
		}
		
		p = new Package();								//quando terminar de ler o arquivo
		p.setPayLoad(Util.copyBytes(buf, qtd));
		p.setFileName(filePath);
		p.setPool(false);								//ultimo pacote
		udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
	
		fi.close();
		
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			Manager manager = new Manager();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
