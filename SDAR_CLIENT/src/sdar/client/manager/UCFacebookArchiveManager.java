package sdar.client.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import sdar.comunication.common.Package;
import sdar.comunication.common.Util;
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;

/**
 * Classe que gerencia o Arquivo no modulo do Cliente (Client)
 */
public class UCFacebookArchiveManager {

	
	/**
	 * Metodo que efetua o upload do arquivo para os repositorios
	 * Envia arquivo passado como parametro para o modulo de Gerenciamento (Manager)
	 * @param filePath - Arquivo a ser enviado para repositorios
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendFile(String filePath) throws UnknownHostException, IOException {
		System.out.println("[Modulo Client] - Enviando arquivo ao modulo de Gerenciamento");
		
		//Cria Socket de comunicação com o modulo Manager
		Socket socket = new Socket(ComEspecification.MANAGER_ADDR, ComEspecification.TCP_PORT);
		
		//Carrega arquivo 
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		
		//Estabelece o canal TCP
		TCPComunication comunicationTCP = new TCPComunication(socket);
		
		//Instancia variaveis
		byte [] buffer = new byte[ComEspecification.BUFFER_SIZE];
		int read;
		int pakageNumber = 1;
		Package pack;
		
		//Efetua toda a leitura do arquivo e envia todos os pacotes
		do {
			read = fileInputStream.read(buffer);
		
			pack = new Package();			
			pack.setFileName(file.getName());
			pack.setSequenceNumber(pakageNumber++);
			pack.setNext(pakageNumber);
			pack.setPayLoad(buffer);

			if (read == ComEspecification.BUFFER_SIZE) {
				pack.setNotLast(true);
			}
			else {
				pack.setNotLast(false);
				pack.setPayLoad(Util.copyBytes(buffer, read));
			}
			
			System.out.println("[Modulo Client] - Enviando pacote ao modulo de gerenciamento. Nº pacote: " + pack.getSequenceNumber());
			comunicationTCP.sendObject(pack);

		} while (read == ComEspecification.BUFFER_SIZE);
		
		//Fecha os canais de comunicação
		comunicationTCP.close();
		socket.close();
		System.out.println("[Modulo Client] - Arquivo enviado ao modulo de Gerenciamento");
	}
	
	
	/**
	 * Metodo que efetua o download do arquivo dos os repositorios
	 * Solicita arquivo passado como parametro para o modulo de Gerenciamento (Manager)
	 * @param fileName - Nome do arquivo a ser requisitado
	 * @param path - Caminho a ser gravado o arquivo
	 */
	public void receiveFile(String fileName, String path) {
		System.out.println("[Modulo Client] - Solicitando arquivo ao modulo de Gerenciamento");
		new Thread(new FileReceiver(fileName, path), "FILESENDER").start();
	}
}