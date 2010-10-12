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
import sdar.comunication.especification.Especification;
import sdar.comunication.tcp.TCPComunication;
import sdar.repository.server.Server;
import sdar.util.temporaryfile.TemporaryFile;

/**
 * Classe que gerencia os objetos recebidos no servidor do repositorio atraves de uma thread
 */
public class MessageReceivedHandler implements Runnable {

	private Object object;
	
	/**
	 * Construtor da classe
	 * @param object
	 */
	public MessageReceivedHandler(Object object) {
		this.object = object;
	}

	
	/**
	 * Metodo que executa as funcoes da thread
	 */
	@Override
	public void run() {
		//Verifica se é uma solicitação ou um pacote
		String className = object.getClass().getSimpleName();
		
		
		//Caso seja um pacote
		if (className.equals("Package")) {
			try {
				Package newPackage = (Package) object;
				System.out.println("[Modulo Repository] - Recebido pacote nº: " + newPackage.getSequenceNumber());
				this.packageHandler(newPackage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { 
			//Caso seja uma solicitação
			if (className.equals("Solicitation")) {
				Solicitation solicitation = (Solicitation) object;
				try {
					this.solicitation(solicitation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Metodo que executa caso seja uma solicitação, verificando qual o tipo da solicitação
	 * @param solicitation
	 * @throws IOException
	 */
	private void solicitation(Solicitation solicitation) throws IOException {
		
		int code = solicitation.getCode();
		
		//Caso seja uma solicitação de listar arquivos 
		if (code == Solicitation.LIST_FILE) {
			System.out.println("[Modulo Repository] - Recebido solicitação do tipo: Listar Arquivos");
			this.sendListFile(solicitation);
		}
		else {
			//Caso seja uma solicitação de download
			if (code == Solicitation.DOWNLOAD) {
				System.out.println("[Modulo Repository] - Recebido solicitação do tipo: Download");
				this.sendFile(solicitation);
			}
		}
	}


	
	/**
	 * Metodo que envia o arquivo ao modulo de gerenciamento
	 * @param s
	 */
	private void sendFile(Solicitation s) {
		try {
			//Cria canais de comunicação
			Socket sock = new Socket(s.getAddress(), s.getPort());
			TCPComunication com = new TCPComunication(sock);
			
			//Instancia arquivo do repositorio
			File file = new File(s.getArchiveName());
			FileInputStream fileIn = new FileInputStream(file);
			Package newPackage;
			int read;
			int packageNumber = 1;
			byte [] buf = new byte[Especification.BUFFER_SIZE];
			
			System.out.println("[Modulo Repository] - Enviando arquivo ao modulo de Gerenciamento");
			do {
				read = fileIn.read(buf);

				newPackage = new Package();			
				newPackage.setFileName(file.getName());
				newPackage.setSequenceNumber(packageNumber++);
				newPackage.setNext(packageNumber);
				newPackage.setPayLoad(buf);

				if (read == Especification.BUFFER_SIZE) {
					newPackage.setNotLast(true);
				}
				else {
					newPackage.setNotLast(false);
					newPackage.setPayLoad(Util.copyBytes(buf, read));
				}
				
				System.out.println("[Modulo Repository] - Nº pacote: " + newPackage.getSequenceNumber());
				com.sendObject(newPackage);
				
			} while (read == Especification.BUFFER_SIZE);
			System.out.println("[Modulo Repository] - Arquivo enviado ao modulo de Gerenciamento");
			System.out.println();

			//Fecha canais de comunicação
			com.close();
			sock.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Metodo que envia lista de arquivos ao modulo de gerenciamento
	 * @param solicitation
	 * @throws IOException
	 */
	private void sendListFile(Solicitation solicitation) throws IOException {
		//Cria canais de comunicação
		Socket sock = new Socket(solicitation.getAddress(), solicitation.getPort());
		TCPComunication com = new TCPComunication(sock);
		
		//Envia todos os arquivos que estão no repositorio
		Archive archive;
		File [] fileList = this.getFileList();
		System.out.println("[Modulo Repository] - Enviando lista de arquivos ao modulo de Gerenciamento");
		for (int i = 0; i < fileList.length - 1; i++) {
			File f = fileList[i];
			
			archive = new Archive();
			archive.setName(f.getName());
			archive.setSize(f.length());
			archive.setNotLast(true);
			
			com.sendObject(archive);
			System.out.println("[Modulo Repository] - Nome arquivo: " + archive.getName());
		}
		
		File f = fileList[fileList.length-1];
		archive = new Archive();
		archive.setName(f.getName());
		archive.setSize(f.length());
		archive.setNotLast(false);
		
		com.sendObject(archive);
		System.out.println("[Modulo Repository] - Nome arquivo: " + archive.getName());
		System.out.println("[Modulo Repository] - Lista de arquivos enviado ao modulo de Gerenciamento");
		System.out.println();
		
		//Fecha canais de comunicação
		com.close();
		sock.close();
	}


	/**
	 * Metodo que retorna uma lista de arquivos que estão no diretorio do repositorio
	 * @return
	 */
	private File[] getFileList() {
		File [] list;
		File directory = new File(sdar.repository.especification.Especification.REP_DIRECTORY);
		
		list = directory.listFiles();
		
		return list;
	}


	/**
	 * Metodo que gerencia o recebimento de pacotes
	 * @param newPackage
	 * @throws IOException
	 */
	private void packageHandler(Package newPackage) throws IOException {
		
		//Caso não seja o ultimo pacote
		if (newPackage.isNotLast()) {
			Server.tmpFileList.add(newPackage);
		} else {
			int pos = Server.tmpFileList.hasTmpFile(newPackage);
			System.out.println("[Modulo Repository] - Arquivo salvo no modulo de Repositorio");
			System.out.println();
			
			//Se arquivos temporarios nao contem o arquivo do pacote, entao cria arquivo pequeno
			if (pos == -1) {
				this.saveFile(newPackage);
			} else {
				TemporaryFile tmp = Server.tmpFileList.remove(pos);
				tmp.add(newPackage);
				this.saveFile(tmp);
			}
		}
	}


	/**
	 * Metodo que salva arquivo no repositorio
	 * @param temp
	 * @throws IOException
	 */
	private void saveFile(TemporaryFile temp) throws IOException {
		//Instancia novo arquivo
		File file = new File(temp.getFileName());
		FileOutputStream fileOut = new FileOutputStream(file);
		LinkedList<Package> list = temp.getPackgeList();
		
		byte [] buf;
		//Escreve arquivo
		for (Package newPackage : list) {
			buf = newPackage.getPayLoad();
			fileOut.write(buf);
		}
		fileOut.close();
	}


	/**
	 * Metodo que salva arquivo no repositorio
	 * @param newPackage
	 * @throws IOException
	 */
	private void saveFile(Package newPackage) throws IOException {
		File file = new File(newPackage.getFileName());
		FileOutputStream fileOut = new FileOutputStream(file);
		fileOut.write(newPackage.getPayLoad());
		fileOut.close();	
	}
}