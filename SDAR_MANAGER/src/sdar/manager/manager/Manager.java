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
import sdar.comunication.udp.UDPComunication;
import sdar.manager.handler.DownloadHandler;
import sdar.manager.rmi.RemoteService;
import sdar.manager.server.ServerDownload;
import sdar.manager.server.ServerUpload;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * @author cadi
 *
 */
public class Manager {

	public static TemporaryFileList uploadBuffer = new TemporaryFileList();
	public static TemporaryFileList downloadBuffer = new TemporaryFileList();
	private ServerUpload serverUp;
	private ServerDownload serverDown;
	private RemoteService serverRMI;
	
	
	
	public Manager() throws RemoteException {
		this.serverRMI = new RemoteService();
//		new Thread(new ServerUpload(), "upload_server").start();
//		new Thread(new ServerDownload(), "download_server").start();
		this.serverUp = new ServerUpload();
		this.serverDown = new ServerDownload();
	}


	
	
	
	/**
	 * Método utilizado para enviar arquivo para so repositorios quando ele chega
	 * por completo ao buffer de Upload
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void sendFile(String filePath) throws IOException, InterruptedException {

		TemporaryFile tmp = Manager.uploadBuffer.remove(filePath);
		
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


	
	
	
	/**
	 * 
	 * Metodo de teste para verificar arquivos que chegam ao Manager
	 * @param fileName
	 * @throws IOException 
	 */
	public static void saveFile(String fileName) throws IOException {

		File f = new File(fileName);
		FileOutputStream fo = new FileOutputStream(f);
		TemporaryFile tempFile = Manager.uploadBuffer.remove(fileName);
		LinkedList<Package> list = tempFile.getPackgeList();
		
		
		byte [] buf;
		
		for (Package p : list) {
			System.out.println("seq " + p.getSequenceNumber());
			buf = p.getPayLoad();
			fo.write(buf);
		}

		fo.close();
		
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
//			Manager manager = new Manager();
			System.out.println("Iniciando modulo MANAGER             [OK]");
			new Manager();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
