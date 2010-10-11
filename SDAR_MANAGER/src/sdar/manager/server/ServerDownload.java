/**
 * ServerDownload.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.server
 */
package sdar.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.manager.handler.DownloadHandler;

/**
 * @author cadi
 *
 */
public class ServerDownload extends Thread {
	
	private Socket clientSocket; // Socket do cliente
	private ServerSocket serverSocket; // Servidor
	private boolean finish = false;
	private TCPComunication socketCommunication;
	
	/**
	 * 
	 */
	public ServerDownload(String threadName) {
		super(threadName);
	}


	/** 
	 * Método que ouve a porta esperando conexoes
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			//cria socket servidor
			System.out.println("Porta para servidor do Socket: " + ComEspecification.DOWNLOAD_PORT);
			this.serverSocket = new ServerSocket(ComEspecification.DOWNLOAD_PORT);
			
			while (!finish) {
				this.clientSocket = this.serverSocket.accept();
				this.socketCommunication = new TCPComunication(this.clientSocket);
				System.out.println("[Download]Connection received from " + this.clientSocket.getInetAddress().getHostName());
				new Thread(new DownloadHandler(this.socketCommunication), "SENDER_TCP").start();
			}
			
			this.serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Método que finaliza o processo servidor
	 */
	public void finish() {
		this.finish = true;
	}

}
