package sdar.repository.server;

import java.io.IOException;

import sdar.comunication.udp.UDPComunication;



public class Server implements Runnable {

	
	
	private boolean finish = false;

	
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

	
	public void run() {
		
		UDPComunication udp = new UDPComunication();
		udp.setBufferSize(8000);
		byte[] buffer = null;
		
		while (!finish) {
			
			try {
				
				buffer = udp.read(2000);
				//TODO Tratar buffer lido
				String str = new String(buffer);
				
				System.out.println(str);
				
			} catch (IOException e) {
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
		System.out.println("inicizando...");
		server.run();
	}
	
	
}
