package sdar.repository.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import sdar.bo.Archive;
import sdar.comunication.common.Package;
import sdar.comunication.common.Solicitation;
import sdar.comunication.common.Util;
import sdar.comunication.tcp.TCPComunication;
import sdar.comunication.especification.Especification;
import sdar.util.temporaryfile.TemporaryFile;
import sdar.util.temporaryfile.TemporaryFileList;

/**
 * Classe que gerencia o repositorio
 */
public class MessageReceivedHandler implements Runnable {

	public TemporaryFileList buffer = new TemporaryFileList();
	private Object object;
	
	
	/**
	 * Construtor da classe
	 * @param obj
	 */
	public MessageReceivedHandler(Object obj) {
		this.object = obj;
	}

	
	/**
	 * Metodo que verifica se é um pacote ou uma solicitação e faz os devidos eventos
	 */
	@Override
	public void run() {
		String className = object.getClass().getSimpleName();
		
		if (className.equals("Package")) {
			//Caso seja um pacote
			try {
				Package p = (Package) object;
				this.packageHandler(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (className.equals("Solicitation")) {
			//Caso seja uma solicitação
			try {
				Solicitation s = (Solicitation) object;
				this.solicitation(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Metodo que gerencia uma solicitação
	 * @param solicitation
	 * @throws IOException
	 */
	private void solicitation(Solicitation solicitation) throws IOException {
		//Codigo da solicitação
		int code = solicitation.getCode();

		if (code == Solicitation.LIST_FILE) {
			//Caso esteja solicitando a lista de arquivos
			this.sendListFile(solicitation);
		}
		else if (code == Solicitation.DOWNLOAD) {
			//Caso esteja solicitando download de um arquivo
			this.sendFile(solicitation);
		}
	}
	
	
	/**
	 * Metodo que envia um arquivo do modulo de repositorio para o modulo de gerenciamento
	 * @param solicitation
	 */
	private void sendFile(Solicitation solicitation) {
		try {
			//Cria canais de comunicacao
			Socket socket = new Socket(solicitation.getAddress(), solicitation.getPort());
			TCPComunication comunicationTCP = new TCPComunication(socket);

			//Instancia arquivo a ser lido do repositorio 
			File file = new File(solicitation.getArchiveName());
			FileInputStream fi = new FileInputStream(file);
			Package newPackage;
			int read;
			int packageNumber = 1;
			byte [] buffer = new byte[Especification.BUFFER_SIZE];
			
			//Lê arquivo do repositorio e envia para o modulo de gerenciamento
			System.out.println("[Modulo Repository] - Enviando arquivo ao modulo de Gerenciamento");
			do {
				read = fi.read(buffer);

				newPackage = new Package();			
				newPackage.setFileName(file.getName());
				newPackage.setSequenceNumber(packageNumber++);
				newPackage.setNext(packageNumber);
				newPackage.setPayLoad(buffer);

				if (read == Especification.BUFFER_SIZE) {
					newPackage.setNotLast(true);
				}
				else {
					newPackage.setNotLast(false);
					newPackage.setPayLoad(Util.copyBytes(buffer, read));
				}
				
				System.out.println("[Modulo Repository] - Enviando pacote ao modulo de Gerenciamento. Nº pacote: " + newPackage.getSequenceNumber());
				comunicationTCP.sendObject(newPackage);
				
			} while (read == Especification.BUFFER_SIZE);
			
			//Fecha canais de comunicação
			comunicationTCP.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Metodo que envia todos os arquivos do modulo de repositorio para o modulo de gerenciamento
	 * @param solicitation
	 * @throws IOException
	 */
	private void sendListFile(Solicitation solicitation) throws IOException {
		//Cria canais de comunicação
		Socket socket = new Socket(solicitation.getAddress(), solicitation.getPort());
		TCPComunication comunicationTCP = new TCPComunication(socket);

		//Metodo que efetua a leitura do diretorio, retornando todos os arquivos que existe
		File [] fileList = this.getFileList();
		
		//Loop que envia todos os arquivos do repositorio, exceto o ultimo
		Archive archive;
		File file;
		for (int i = 0; i < fileList.length - 1; i++) {
			file = fileList[i];
			
			archive = new Archive();
			archive.setName(file.getName());
			archive.setSize(file.length());
			archive.setNotLast(true);
			
			comunicationTCP.sendObject(archive);
		}
		
		//Envia o ultimo arquivo do repositorio
		file = fileList[fileList.length-1];
		archive = new Archive();
		archive.setName(file.getName());
		archive.setSize(file.length());
		archive.setNotLast(false);
		
		comunicationTCP.sendObject(archive);
		
		//Fecha os canais de comunicação
		comunicationTCP.close();
		socket.close();
	}


	/**
	 * Metood que efetua a leitura do diretorio, retornando todos os arquivos que existem no diretorio
	 * @return
	 */
	private File[] getFileList() {
		File [] list;
		File directory = new File(sdar.repository.especification.Especification.REP_DIRECTORY);
		
		list = directory.listFiles();
		
		return list;
	}


	/**
	 * Metodo que gerencia os pacotes que chegam ao repositorio
	 * @param newPackage
	 * @throws IOException
	 */
	private void packageHandler(Package newPackage) throws IOException {
		
		//Caso não seja o ultimo pacote do arquivo
		System.out.println("[Modulo Repository] - Recebendo arquivo do modulo de Gerenciamento");
		System.out.println("[Modulo Repository] - Recebendo arquivo do modulo de Gerenciamento. Nº pacote: " + newPackage.getSequenceNumber());
		if (newPackage.isNotLast()) {
			this.buffer.add(newPackage);
		} else {
			int pos = this.buffer.hasTmpFile(newPackage);
			
			//Se arquivos temporarios não contem o arquivo do pacote, cria novo arquivo
			if (pos == -1) {
				this.saveFile(newPackage);
			} else {
				TemporaryFile temp = this.buffer.remove(pos);
				//Adiciona ultimo pacote
				temp.add(newPackage);
				this.saveFile(temp);
			}
		}
	}


	/**
	 * Metodo que salva o arquivo
	 * @param temp
	 * @throws IOException
	 */
	private void saveFile(TemporaryFile temp) throws IOException {
		//Cria novo arquivo no diretorio do repositorio 
		File file = new File(temp.getFileName());
		FileOutputStream fileOut = new FileOutputStream(file);
		LinkedList<Package> list = temp.getPackgeList();
		
		//Escreve no arquivo tudo que esta armazenado no buffer
		byte [] buffer;
		for (Package p : list) {
			buffer = p.getPayLoad();
			fileOut.write(buffer);
		}

		//Fecha o arquivo
		fileOut.close();
	}


	/**
	 * Metodo que salva o arquivo
	 * @param newPackage
	 * @throws IOException
	 */
	private void saveFile(Package newPackage) throws IOException {
		//Cria novo arquivo no diretorio do repositorio
		File file = new File(newPackage.getFileName());
		FileOutputStream fileOut = new FileOutputStream(file);
		fileOut.write(newPackage.getPayLoad());
		fileOut.close();	
	}
}