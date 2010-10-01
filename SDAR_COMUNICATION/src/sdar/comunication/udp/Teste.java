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
			
			int s = fi.read(buf);
			
			byte [] msg = new byte[s];
			
			for (int i = 0; i < s; i++) {
				msg[i] = buf[i];
			}
			
			System.out.println(msg.length);
			
			p.setPayLoad(msg);
			p.setSequencieNumber(15);
			p.setFileName("doido velho");
			udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);

			fi.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
