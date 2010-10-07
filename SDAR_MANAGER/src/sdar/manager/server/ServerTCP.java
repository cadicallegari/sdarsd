package sdar.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.manager.handler.ManagerHandler;


//aguarda conexao do cliente

public class ServerTCP implements Runnable {

	
	private Socket clientSocket; // Socket do cliente
	private ServerSocket serverSocket; // Servidor
	private boolean finish = false;
	private TCPComunication socketCommunication;
	
	/**
	 * 
	 */
	public ServerTCP() {
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
				System.out.println("Connection received from " + this.clientSocket.getInetAddress().getHostName());
				new Thread(new ManagerHandler(this.socketCommunication), "RECEPTOR_TCP").start();
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
		ServerTCP server = new ServerTCP();
		System.out.println("Iniciando modulo MANAGER             [OK]");
		server.run();
	}
	
	
}
