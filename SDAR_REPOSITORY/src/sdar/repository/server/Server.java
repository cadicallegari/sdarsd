package sdar.repository.server;

import java.io.IOException;

import sdar.comunication.def.ComEspecification;
import sdar.comunication.udp.UDPComunication;
import sdar.repository.file.TemporaryFileList;
import sdar.repository.manager.MessageReceivedHandler;

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
				
				new Thread(new MessageReceivedHandler(obj), "RECEPTOR").start();
				
				System.out.println("RECEBIDO :" + obj.getClass().getSimpleName());
				
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
	

	
	
//	public void run() {
//		
//		try {
//			DatagramSocket serverSocket = new DatagramSocket(RepositoryEspecification.PORT);
//			byte[] receiveData = new byte[RepositoryEspecification.BUFFER_SIZE];
//	        DatagramPacket receivePacket = null;
//			
//			while (!finish) {
//		        //recebe o pacote UDP
//				receivePacket = new DatagramPacket(receiveData, receiveData.length);
//		        System.out.println("aguardando conexao");
//		        serverSocket.receive(receivePacket);
//		        System.out.println("conexao estabelecida");
//		        receiveData = receivePacket.getData();
//
//		        //TODO tratar receiveData
//		        System.out.println("doido velho");
//			}
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		System.out.println("inicizando...");
		server.run();
	}



	
}
