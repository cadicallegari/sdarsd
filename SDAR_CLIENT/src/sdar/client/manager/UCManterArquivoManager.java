/**
 * UCManterArquivo.java
 * cadi
 * SDAR_CLIENT
 * sdar.client.manager
 */
package sdar.client.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import sdar.comunication.common.Package;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;

/**
 * @author cadi
 *
 */
public class UCManterArquivoManager {

	
	/**
	 * Envia arquivo passado como parametro para o modulo de gerenciamento (Manager)
	 * @param filePath
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendFile(String filePath) throws UnknownHostException, IOException {
		
		Socket sock = new Socket(ComEspecification.TCP_ADDR, ComEspecification.TCP_PORT);
		File file = new File(filePath);
		FileInputStream fi = new FileInputStream(file);
		TCPComunication com = new TCPComunication(sock);
		byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
		int read;
		int pakageNumber = 1;
		Package pack;
		
		read = fi.read(buf);
		System.out.println(read);
		
		while (read == ComEspecification.BUFFER_SIZE) {
		
			pack = new Package();			
			pack.setFileName(file.getName());
			pack.setPool(true);
			pack.setSequenceNumber(pakageNumber++);
			pack.setNext(pakageNumber);
			pack.setPayLoad(buf);
			
			com.sendObject(pack);
			
			read = fi.read(buf);
			System.out.println("dentro " + read);
		}
		
		pack = new Package();			
		pack.setFileName(file.getName());
		pack.setPool(false);
		pack.setSequenceNumber(pakageNumber++);
		pack.setNext(-1);
		pack.setPayLoad(buf);
		
		com.sendObject(pack);
		
		com.close();
		sock.close();
	}
	
	
}
