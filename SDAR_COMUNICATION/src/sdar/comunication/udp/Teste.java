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
			
//			File f = new File("teste.txt");
//			File f = new File("teste2.txt");
			File f = new File("curso_linux.pdf");
			FileInputStream fi = new FileInputStream(f);
			Package p = new Package();
			byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
			UDPComunication udpCom = new UDPComunication();
			String fileName = f.getName();
			
			int s = fi.read(buf);
			System.out.println("lido " + s);
			
			
			
			//TODO verificar quando a ultima leitura do arquivo der exatamente buffer_size
			while (s == ComEspecification.BUFFER_SIZE) {
				p.setPayLoad(Util.copyBytes(buf, s));
				p.setPool(true);
				p.setFileName(fileName);
				udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				s = fi.read(buf);
				System.out.println("lido " + s);
			}
				
			p.setPayLoad(Util.copyBytes(buf, s));
			p.setFileName(fileName);
			p.setPool(false);
			udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);

			fi.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
