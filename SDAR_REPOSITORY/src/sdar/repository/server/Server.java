package sdar.repository.server;

import java.io.IOException;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.udp.UDPComunication;
import sdar.repository.handler.MessageReceivedHandler;
import sdar.util.temporaryfile.TemporaryFileList;

//aguarda conexao do manager

public class Server implements Runnable {

	
	public static TemporaryFileList tmpFileList = new TemporaryFileList();
	
	private boolean finish = false;

	
	/**
	 * 
	 */
	public Server() {
	}


	
	public void run() {
		
		UDPComunication udp = new UDPComunication();
		Object obj;
		
		while (!finish) {
			
			try {
				
				obj = udp.readGroupObject(ComEspecification.GROUP, ComEspecification.UDP_PORT);
				
				new Thread(new MessageReceivedHandler(obj), "RECEPTORREP").start();
				
				System.out.println("RECEBIDO: " + obj.getClass().getSimpleName());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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
		System.out.println("Iniciando modulo REPOSITORY             [OK]");
		server.run();
	}



	
}
