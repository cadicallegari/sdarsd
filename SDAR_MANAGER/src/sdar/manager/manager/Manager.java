/**
 * Manager.java
 * SDAR_MANAGER
 * sdar.manager.manager
 */
package sdar.manager.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import sdar.comunication.common.Package;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.rmi.RemoteService;
import sdar.comunication.udp.UDPComunication;
import sdar.manager.server.ServerTCP;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * @author cadi
 *
 */
public class Manager {

	public static TemporaryFileList fileBuffer = new TemporaryFileList();
	private ServerTCP serverRep;
	private RemoteService serverRMI;
	
	
	
	public Manager() throws RemoteException {
		
		serverRMI = new RemoteService();
		serverRep = new ServerTCP();
		
	}


	
	public static void sendFile(String filePath) throws IOException, InterruptedException {

		TemporaryFile tmp = Manager.fileBuffer.remove(filePath);
		
		if (tmp != null) {
		
			UDPComunication com = new UDPComunication();
			LinkedList<Package> list = tmp.getPackgeList();
		
			for(Package pack : list) {
				System.out.println("enviado seq " + pack.getSequenceNumber());
				com.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, pack);
				Thread.sleep(ComEspecification.DELAY);
			}
		
		
		}
		
	}

	
	

//	public static void sendFile(String filePath) throws IOException {
//		//TODO aqui vai a logica de retirar do buffer de arquivos e enviar para os repositorios
//		File f = new File(filePath);
//		FileInputStream fi = new FileInputStream(f);
//		byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
//		UDPComunication udpCom = new UDPComunication();
//		Package p = null;
//		
//		int qtd = fi.read(buf);
//		
//		System.out.println("leu " + qtd);
//		
//		while (qtd == ComEspecification.BUFFER_SIZE) { 			//enquanto nao acabar de ler o arquivo
//			p = new Package();
//			p.setPayLoad(Util.copyBytes(buf, qtd));
//			p.setFileName(filePath);
//			p.setPool(true);
//			udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
//			
//			qtd = fi.read(buf);
//			
//			System.out.println("leu " + qtd);
//		}
//		
//		p = new Package();								//quando terminar de ler o arquivo
//		p.setPayLoad(Util.copyBytes(buf, qtd));
//		p.setFileName(filePath);
//		p.setPool(false);								//ultimo pacote
//		udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, p);
//	
//		fi.close();
//		
//	}

	
	
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



	/**
	 * @param fileName
	 * @throws IOException 
	 */
	public static void saveFile(String fileName) throws IOException {

		File f = new File(fileName);
		FileOutputStream fo = new FileOutputStream(f);
		TemporaryFile tempFile = Manager.fileBuffer.remove(fileName);
		LinkedList<Package> list = tempFile.getPackgeList();
		
		
		byte [] buf;
		
		for (Package p : list) {
			System.out.println("seq " + p.getSequenceNumber());
			buf = p.getPayLoad();
			fo.write(buf);
		}

		fo.close();
		
	}
	
	
}
