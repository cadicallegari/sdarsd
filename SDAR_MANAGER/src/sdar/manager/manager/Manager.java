/**
 * Manager.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.manager
 */
package sdar.manager.manager;

import java.io.IOException;

import sdar.comunication.udp.UDPComunication;

/**
 * @author cadi
 *
 */
public class Manager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			UDPComunication udp = new UDPComunication();
			
			String str = "ma oeeeee";
			byte[] buf = str.getBytes();
		
			udp.send("localhost", 2000, buf);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
