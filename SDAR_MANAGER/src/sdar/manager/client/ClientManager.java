/**
 * ClientManager.java
 * cadi
 * SDAR_GERENCIADOR
 * sdar.manager.server
 */
package sdar.manager.client;

import sdar.comunication.tcp.TCPComunication;


public class ClientManager implements Runnable {
	
	TCPComunication socketCommunication;

	public ClientManager(TCPComunication socketCommunication) {
		this.socketCommunication = socketCommunication;
	}


	
	@Override
	public void run() {
		//TODO tratamento do cliente por enquanto desde o login até tudo
		System.out.println("uoooooooooooooowwww");
	}
}