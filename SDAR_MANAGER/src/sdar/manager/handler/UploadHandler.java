package sdar.manager.handler;

import java.io.IOException;
import java.util.LinkedList;

import sdar.comunication.common.Package;
import sdar.comunication.especification.Especification;
import sdar.comunication.tcp.TCPComunication;
import sdar.comunication.udp.UDPComunication;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * Classe que gerencia a thread de Upload de um arquivo
 */
public class UploadHandler implements Runnable {
	
	private TCPComunication comunicationTCP;
	private TemporaryFileList buffer = new TemporaryFileList();
	
	/**
	 * Construtor da classe
	 * @param socketCommunication
	 */
	public UploadHandler(TCPComunication socketCommunication) {
		this.comunicationTCP = socketCommunication;
	}
	
	
	/**
	 * Metodo que executa as funcoes da thread
	 */
	@Override
	public void run() {
		//Instancia variavel de nome do arquivo a ser feito upload
		String fileName;
		
		try {
			//Recebe arquivo do cliente
			fileName = this.receiveArchive();
			
			//Envia arquivo para o repositorio
			this.sendFile(fileName);
			
			//Fecha canal de comunicação
			this.comunicationTCP.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo que recebe arquivo do modulo do cliente
	 * @return
	 */
	private String receiveArchive() {
		Package newPackage = new Package();
		
		try {
			//Recebe todos os pacotes do client
			System.out.println("[Modulo Manager] - Recebendo arquivo do modulo do Cliente");
			do {
				newPackage = (Package) this.comunicationTCP.readObject();
				System.out.println("[Modulo Manager] - Nº pacote: " + newPackage.getSequenceNumber());
				this.buffer.add(newPackage);
			} while (newPackage.isNotLast());
			System.out.println("[Modulo Manager] - Arquivo recebido do modulo do Cliente");
			System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new String(newPackage.getFileName());
	}
	
	
	/**
	 * Metodo que envia o arquivo para o modulo do Repositorio
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendFile(String filePath) throws IOException, InterruptedException {
		//Instacia arquivo temporario
		TemporaryFile tempFile = this.buffer.remove(filePath);
		LinkedList<Package> list = tempFile.getPackgeList();
		
		//Inicia canal de comunicacao TCP
		UDPComunication com = new UDPComunication();
	
		//Envia pacotes para o modulo do repositorio
		System.out.println("[Modulo Manager] - Enviando arquivo ao modulo de Repositorio");
		for(Package newPackage : list) {
			System.out.println("[Modulo Manager] - Nº pacote: " + newPackage.getSequenceNumber());
			com.sendObject(Especification.GROUP, Especification.UDP_PORT, newPackage);
			Thread.sleep(Especification.DELAY);
		}
		System.out.println("[Modulo Manager] - Arquivo enviado ao modulo de Repositorio");
		System.out.println();
	}
}