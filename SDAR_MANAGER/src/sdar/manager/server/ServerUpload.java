package sdar.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.manager.handler.UploadHandler;


//aguarda conexao do cliente

public class ServerUpload implements Runnable {

	
	private Socket clientSocket; // Socket do cliente
	private ServerSocket serverSocket; // Servidor
	private boolean finish = false;
	private TCPComunication socketCommunication;
	
	/**
	 * 
	 */
	public ServerUpload() {
		this.run();
	}



	/** 
	 * Método que ouve a porta esperando conexoes
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			//cria socket servidor
			this.serverSocket = new ServerSocket(ComEspecification.TCP_PORT);
			
			while (!finish) {
				this.clientSocket = this.serverSocket.accept();
				this.socketCommunication = new TCPComunication(this.clientSocket);
				System.out.println("[Upload]Connection received from " + this.clientSocket.getInetAddress().getHostName());
				new Thread(new UploadHandler(this.socketCommunication), "RECEPTOR_TCP").start();
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
