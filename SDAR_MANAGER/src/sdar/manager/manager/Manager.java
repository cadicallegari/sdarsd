package sdar.manager.manager;

import java.io.IOException;
import java.rmi.RemoteException;

import sdar.manager.rmi.RemoteService;
import sdar.manager.server.ServerDiscovery;
import sdar.manager.server.ServerDownload;
import sdar.manager.server.ServerUpload;

/**
 * Classe que gerencia o modulo de Gerenciamento
 */
public class Manager {

	
	/**
	 * Construtor da classe
	 * @throws RemoteException
	 */
	public Manager() throws RemoteException {
		//Inicia serviço RMI
		new RemoteService();
		//Inicia Serviço de Upload
		new ServerUpload("upload_server").start();
		//Inicia Serviço de Download
		new ServerDownload("download_server").start();
		//Inicia Serviço de Descobrimento dinamico de modulo Manager
		new ServerDiscovery("discovery_server").start();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Iniciando modulo de Gerenciamento (MANAGER)          [OK]");
			new Manager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}