package sdar.repository.server;

import java.io.IOException;

import sdar.comunication.especification.Especification;
import sdar.comunication.udp.UDPComunication;
import sdar.repository.handler.MessageReceivedHandler;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * Classe que gerencia a thread de servidor do repositorio
 */
public class Server implements Runnable {
	
	public static TemporaryFileList tmpFileList = new TemporaryFileList();
	private boolean finish = false;

	
	/**
	 * 	Metodo que executa as funcoes da thread
	 */
	public void run() {
		//Cria canal de comunicação
		UDPComunication comunicationUDP = new UDPComunication();
		Object object;
		
		while (!finish) {
			try {

				//Fica ouvindo o grupo
				object = comunicationUDP.readGroupObject(Especification.GROUP, Especification.UDP_PORT);
				//Cria uma nova thread assim que chega um objeto no grupo
				new Thread(new MessageReceivedHandler(object), "RECEPTORREP").start();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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
		System.out.println("Iniciando modulo de Repositorios (REPOSITORY)          [OK]");
		server.run();
	}	
}