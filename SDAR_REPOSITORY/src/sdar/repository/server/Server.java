package sdar.repository.server;

import java.io.IOException;

import sdar.comunication.especification.Especification;
import sdar.comunication.udp.UDPComunication;
import sdar.repository.handler.MessageReceivedHandler;

/**
 * Classe que implementa o servidor do repositorio
 */
public class Server implements Runnable {
	
	private boolean finish = false;
	
	/**
	 * Construtor da classe
	 */
	public Server() {
	}


	/**
	 * Metodo que fica ouvindo o grupo UDP
	 */
	public void run() {
		//Cria canal de comunicação
		UDPComunication comunicationUDP = new UDPComunication();
		Object object;
		
		while (!finish) {
			try {
				//Lê um objeto do grupo
				object = comunicationUDP.readGroupObject(Especification.GROUP, Especification.UDP_PORT);
				//Instancia uma nova thread
				new Thread(new MessageReceivedHandler(object), "repository_server").start();
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
		System.out.println("Iniciando modulo de Repositorio (REPOSITORY)");
		server.run();
	}	
}