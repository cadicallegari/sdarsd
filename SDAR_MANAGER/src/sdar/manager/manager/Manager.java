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

import sdar.comunication.common.Packt;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.udp.UDPComunication;

/**
 * @author cadi
 *
 */
public class Manager {

	
	
	public void sendFile(String filePath) throws IOException {
		
		File f = new File(filePath);
		FileInputStream fi = new FileInputStream(f);
		Packt p = new Packt();
		byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
		UDPComunication udpCom = new UDPComunication();
		
		fi.read(buf);
		
		System.out.println(new String(buf));
		
		p.setPayLoad(buf);
		
		udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
		
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Manager manager = new Manager();
		
		try {
			
			manager.sendFile("teste.txt");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
