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

import sdar.comunication.common.Package;
import sdar.comunication.common.Util;
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
			Package p = new Package();
			byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
			UDPComunication udpCom = new UDPComunication();
			
			int s = fi.read(buf);
			
			p.setPayLoad(Util.copyBytes(buf, s));
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
