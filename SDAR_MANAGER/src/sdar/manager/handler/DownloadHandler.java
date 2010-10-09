/**
 * DownloadHandler.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.handler
 */
package sdar.manager.handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import sdar.comunication.common.Package;
import sdar.comunication.common.Solicitation;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.comunication.udp.UDPComunication;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * @author cadi
 *
 */
public class DownloadHandler implements Runnable {

	
	private TCPComunication comunication;
	private TCPComunication comRep;
	private TemporaryFileList buffer = new TemporaryFileList();
	
	/**
	 * @param socketCommunication
	 */
	public DownloadHandler(TCPComunication socketCommunication) {
		this.comunication = socketCommunication;
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			
			Solicitation sol = (Solicitation) this.comunication.readObject();
			
			this.connectToRepository(sol);
			
			this.receiveArchive();
			this.comRep.close();
			
			this.sendArchive();
			this.comunication.close();
			
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
	private void sendArchive() throws IOException {

		TemporaryFile tempFile = this.buffer.remove(0);
		LinkedList<Package> list = tempFile.getPackgeList();
		
		for (Package pack : list) {
			this.comunication.sendObject(pack);
		}
	
	}




	/**
	 * 
	 */
	private void receiveArchive() {
		Package pack;
		
		try {
			do {

				pack = (Package) this.comunication.readObject();
				this.buffer.add(pack);
				
			} while (pack.isPool());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




	/**
	 * @param sol
	 * @throws IOException 
	 */
	private void connectToRepository(Solicitation sol) throws IOException {
		//TODO Tratar timeout
		ServerSocket serverSocket = new ServerSocket(ComEspecification.DOWNLOAD_RECEIVE_PORT);
		Socket clientSockt;
		
		sol.setAddress(InetAddress.getLocalHost().getHostAddress());
		sol.setPort(ComEspecification.DOWNLOAD_RECEIVE_PORT);
		this.sendSolicitation(sol);
		
		System.out.println("aguardando conexao de algum repositorio");
		clientSockt = serverSocket.accept();
		this.comRep = new TCPComunication(clientSockt);
		
		serverSocket.close();
	}




	/**
	 * @param sol
	 * @throws IOException 
	 */
	private void sendSolicitation(Solicitation sol) throws IOException {
		UDPComunication udpCom = new UDPComunication();
		udpCom.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, sol);
	}

}
