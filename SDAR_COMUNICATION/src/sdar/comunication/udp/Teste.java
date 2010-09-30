/**
 * Teste.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.udp
 */
package sdar.comunication.udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sdar.comunication.common.Packt;
import sdar.comunication.def.ComEspecification;

/**
 * @author cadi
 *
 */
public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			File f = new File("teste.txt");
			FileInputStream fi = new FileInputStream(f);
			Packt p = new Packt();
			byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
			UDPComunication udpCom = new UDPComunication();
			
			int i = fi.read(buf, 0, buf.length);
			
			System.out.println(new String(buf) + "\n" + i);
			
			p.setPayLoad(buf);
			udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);

			fi.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
