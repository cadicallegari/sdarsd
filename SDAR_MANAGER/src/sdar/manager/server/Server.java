package sdar.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sdar.comunication.tcp.TCPComunication;
import sdar.manager.client.ClientManager;


public class Server implements Runnable {

	public static int PORT = 4000;
	
	private Socket clientSocket; // Socket do cliente
	private ServerSocket serverSocket; // Servidor
	private boolean finish = false;
	private TCPComunication socketCommunication;
	
	/**
	 * 
	 */
	public Server() {
	}



	/** 
	 * Método que ouve a porta esperando conexoes
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			//cria socket servidor
			this.serverSocket = new ServerSocket(Server.PORT);
			
			while (!finish) {
				this.clientSocket = this.serverSocket.accept();
				this.socketCommunication = new TCPComunication(this.clientSocket);
				System.out.println("Connection received from " + this.clientSocket.getInetAddress().getHostName());
				new Thread(new ClientManager(this.socketCommunication), "RECEPTOR").start();
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
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}
	
	
}
