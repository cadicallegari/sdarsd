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
import sdar.comunication.common.Util;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.manager.handler.ManagerHandler;

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
		
//		File fTeste = new File("teste" + file.getName());
//		FileOutputStream fout = new FileOutputStream(fTeste);
		
		do {
			
			read = fi.read(buf);
			System.out.println(read);	
		
			pack = new Package();			
			pack.setFileName(file.getName());
			pack.setSequenceNumber(pakageNumber++);
			pack.setNext(pakageNumber);
			pack.setPayLoad(buf);

			if (read == ComEspecification.BUFFER_SIZE) {
				pack.setPool(true);
			}
			else {
				pack.setPool(false);
				pack.setPayLoad(Util.copyBytes(buf, read));
			}
			
//			fout.write(buf);
			
			com.sendObject(pack);

		} while (read == ComEspecification.BUFFER_SIZE);
		
		com.close();
		sock.close();
	}
	
	
	
	public void receiveFile(String fileName) {
		
		new Thread(new FileSender(fileName), "FILESENDER").start();
		
	}
	
	
}
