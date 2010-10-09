/**
 * FileSender.java
 * cadi
 * SDAR_CLIENT
 * sdar.client.manager
 */
package sdar.client.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import sdar.comunication.common.Package;
import sdar.comunication.common.Solicitation;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * @author cadi
 *
 */
public class FileReceiver implements Runnable {
	
	String fileName;
	Socket sock;
	TCPComunication com;
	TemporaryFileList tempFile = new TemporaryFileList();
	
	
	/**
	 * @param fileName
	 */
	public FileReceiver(String fileName) {
		this.fileName = fileName;
	}




	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
		
			this.connect();
			this.sendSolicitation(this.fileName);
			this.receiveFile();
			this.saveFile();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




	/**
	 * @throws IOException 
	 * 
	 */
	private void saveFile() throws IOException {
		
		TemporaryFile tempFile = this.tempFile.remove(this.fileName);
		LinkedList<Package> list = tempFile.getPackgeList();
		File file = new File(this.fileName);
		FileOutputStream fo = new FileOutputStream(file);
		
		for (Package pack : list) {
			fo.write(pack.getPayLoad());
		}
		
		fo.close();
		
	}




	/**
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * 
	 */
	private void receiveFile() throws IOException, ClassNotFoundException {
		
		Package pack;
		
		do {
			
			pack = (Package) this.com.readObject();
			this.tempFile.add(pack);
			
		} while (pack.isPool());
		
	}




	/**
	 * @param fileName2
	 * @throws IOException 
	 */
	private void sendSolicitation(String fileName2) throws IOException {
		
		Solicitation solicitation = new Solicitation();
		solicitation.setCode(Solicitation.DOWNLOAD);
		solicitation.setArchiveName(fileName2);
		this.com.sendObject(solicitation);
		
	}




	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 */
	private void connect() throws UnknownHostException, IOException {
		this.sock = new Socket(ComEspecification.MANAGER_ADDR, ComEspecification.DOWNLOAD_PORT);
		this.com = new TCPComunication(sock);
	}

}
