/**
 * UCManterSolicitationManager.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.solicitation
 */
package sdar.manager.solicitation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import sdar.bo.Archive;
import sdar.comunication.common.Solicitation;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.comunication.udp.UDPComunication;


/**
 * @author cadi
 *
 */
public class UCManterSolicitationManager {

	
	public List<Archive> listFile() throws IOException, ClassNotFoundException {
		ServerSocket serverSocket;
		Socket sock;
		UDPComunication comUdp = new UDPComunication();
		Solicitation solicitation = new Solicitation();
		
		solicitation.setCode(Solicitation.LIST_FILE);
		InetAddress inetAddress = InetAddress.getLocalHost();
		solicitation.setAddress(inetAddress.getHostAddress());
		solicitation.setPort(ComEspecification.UPLOAD_PORT);
		
		System.out.println("enviou");
		comUdp.sendObject(ComEspecification.GROUP, ComEspecification.UDP_PORT, solicitation);
		
		System.out.println("esperando resposta");
		serverSocket = new ServerSocket(ComEspecification.UPLOAD_PORT);
		sock = serverSocket.accept();
		
		System.out.println("recebendo retorno");
		List<Archive> f = this.receiveArchiveList(sock);
		
//		sock.close();
		serverSocket.close();
		
		return f; 
	}

	
	
	
	/**
	 * @param sock
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private List<Archive> receiveArchiveList(Socket sock) throws IOException, ClassNotFoundException {
		
		Archive archive;
		LinkedList<Archive> list = new LinkedList<Archive>();
		TCPComunication com = new TCPComunication(sock);
		
		do {
			
			archive = (Archive) com.readObject();
			list.add(archive);
			
		} while (archive.isPool());
		
		com.close();
		return list;
	}
	
	
	
}
