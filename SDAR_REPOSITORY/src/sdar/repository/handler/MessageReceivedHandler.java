/**
 * MessageReceivedHandler.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.manager
 */
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
import sdar.comunication.def.ComEspecification;
import sdar.comunication.tcp.TCPComunication;
import sdar.repository.especification.Especification;
import sdar.repository.server.Server;
import sdar.util.temporaryfile.TemporaryFile;

/**
 * @author cadi
 *
 */
public class MessageReceivedHandler implements Runnable {

	private Object obj;
	
	/**
	 * @param obj
	 */
	public MessageReceivedHandler(Object obj) {
		this.obj = obj;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		String className = obj.getClass().getSimpleName();
		System.out.println(className);
		
		if (className.equals("Package")) {
			try {
				Package p = (Package) obj;
				this.packageHandler(p);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (className.equals("Solicitation")) {
			Solicitation s = (Solicitation) obj;
			
			try {
				this.solicitation(s);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Retorno do metodo Run");
		
	}


	
	
	/**
	 * @param s
	 * @throws IOException 
	 */
	private void solicitation(Solicitation s) throws IOException {
		
		int code = s.getCode();
		System.out.println(code);
		if (code == Solicitation.LIST_FILE) {
			this.sendListFile(s);
		}
		else if (code == Solicitation.DOWNLOAD) {
			this.sendFile(s);
		}
		
		System.out.println("Retorno do metodo Solicitation");
		
	}


	
	/**
	 * @param s
	 */
	private void sendFile(Solicitation s) {
		System.out.println("Metodo de SendFile");
		try {
			//TENTA conectar ao remetente da solicitaçao
			//Se nao der provavelmente alguem ja conectou entao ta tudo certo
			Socket sock = new Socket(s.getAddress(), s.getPort());
			TCPComunication com = new TCPComunication(sock);
			File file = new File(s.getArchiveName());
			FileInputStream fi = new FileInputStream(file);
			Package pack;
			int read;
			int packageNumber = 1;
			byte [] buf = new byte[ComEspecification.BUFFER_SIZE];
			
			do {
				read = fi.read(buf);

				pack = new Package();			
				pack.setFileName(file.getName());
				pack.setSequenceNumber(packageNumber++);
				pack.setNext(packageNumber);
				pack.setPayLoad(buf);

				if (read == ComEspecification.BUFFER_SIZE) {
					pack.setNotLast(true);
				}
				else {
					pack.setNotLast(false);
					pack.setPayLoad(Util.copyBytes(buf, read));
				}
				
				System.out.println("Enviando PACKAGE from REP to MAN" + pack.getSequenceNumber());
				com.sendObject(pack);
				
			} while (read == ComEspecification.BUFFER_SIZE);
			System.out.println("Terminou de enviar os pacotes");
			com.close();
			sock.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * @param s
	 * @throws IOException 
	 */
	private void sendListFile(Solicitation s) throws IOException {
		Socket sock = new Socket(s.getAddress(), s.getPort());
		TCPComunication com = new TCPComunication(sock);
		Archive arq;
		File [] fileList = this.getFileList();
		
		for (int i = 0; i < fileList.length - 1; i++) {
			File f = fileList[i];
			
			arq = new Archive();
			arq.setName(f.getName());
			arq.setSize(f.length());
			arq.setNotLast(true);
			
			com.sendObject(arq);
		}
		
		File f = fileList[fileList.length-1];
		arq = new Archive();
		arq.setName(f.getName());
		arq.setSize(f.length());
		arq.setNotLast(false);
		
		com.sendObject(arq);
		
		com.close();
		sock.close();
	}


	
	/**
	 * @return
	 */
	private File[] getFileList() {
		File [] list;
		File directory = new File(Especification.REP_DIRECTORY);
		
		list = directory.listFiles();
		
		return list;
	}


	/**
	 * @param p
	 * @throws IOException 
	 */
	private void packageHandler(Package p) throws IOException {
		
		if (p.isNotLast()) { 						//se nao for ultimo pacote do arquivo
			int i = Server.tmpFileList.add(p);
			System.out.println("poll " + i);
		}
		else {
			
			int pos = Server.tmpFileList.hasTmpFile(p);
			
			System.out.println("not poll " + pos);
			
			if (pos == -1) {				//se arquivos temporarios nao contem o arquivo do pacote
				this.saveFile(p);							//cria arquivo pequenino
			}
			else {
				TemporaryFile tmp = Server.tmpFileList.remove(pos);
				tmp.add(p);										//adiciona ultimo pacote
				this.saveFile(tmp);
			}
		}
	
	}


	/**
	 * @param tmp
	 * @throws IOException 
	 */
	private void saveFile(TemporaryFile tmp) throws IOException {
		
		File f = new File(tmp.getFileName());
		FileOutputStream fo = new FileOutputStream(f);
		LinkedList<Package> list = tmp.getPackgeList();
		
		byte [] buf;
		
		for (Package p : list) {
			System.out.println("seq " + p.getSequenceNumber());
			buf = p.getPayLoad();
			fo.write(buf);
		}

		fo.close();
		
	}


	/**
	 * @param p
	 * @throws IOException 
	 */
	private void saveFile(Package p) throws IOException {
		
		File f = new File(p.getFileName());
		
		FileOutputStream fo = new FileOutputStream(f);
		
		fo.write(p.getPayLoad());
		
		fo.close();	

	}
}
