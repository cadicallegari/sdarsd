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
			System.out.println("Atributos da Solicitação");
			System.out.println("Address: " + sol.getAddress());
			System.out.println("FileName: " + sol.getArchiveName());
			System.out.println("Code: " + sol.getCode());
			System.out.println("Porta: " + sol.getPort());
			
			this.connectToRepository(sol);
			System.out.println("Comecando a receber arquivo do servidor");
			this.receiveArchive();
			this.comRep.close();
			System.out.println("Arquivo recebido do servidor");
			System.out.println("Enviando arquivo para client");
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
			System.out.println("Enviando pacote para client: " + pack.getSequenceNumber());
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

				pack = (Package) this.comRep.readObject();
				System.out.println("Pacote recebido: " + pack.getSequenceNumber());
				this.buffer.add(pack);
				
			} while (pack.isNotLast());
			
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
		System.out.println("Conexao aceito do repositorio");
		this.comRep = new TCPComunication(clientSockt);
		System.out.println("Instanciada Comunicacao TCP");
		serverSocket.close();
		System.out.println("Retorno metodo connectToRepository");
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
