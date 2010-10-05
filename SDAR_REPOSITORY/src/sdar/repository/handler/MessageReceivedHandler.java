/**
 * MessageReceivedHandler.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.manager
 */
package sdar.repository.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import sdar.comunication.common.Package;
import sdar.comunication.common.Solicitation;
import sdar.repository.server.Server;
import sdar.repository.temporaryfile.TemporaryFile;

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
		
		if (className.equals("Package")) {
			try {
				System.out.println("Pakage");
				Package p = (Package) obj;
				this.packageHandler(p);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (className.equals("Solicitation")) {
			System.out.println("Solicitation");
			Solicitation s = (Solicitation) obj;
			this.solicitation(s);
		}
		
	}


	/**
	 * @param s
	 */
	private void solicitation(Solicitation s) {
		// TODO Auto-generated method stub
		System.out.println("tratar solicitaçao");
	}


	/**
	 * @param p
	 * @throws IOException 
	 */
	private void packageHandler(Package p) throws IOException {
		
		System.out.println(p.getPayLoad().length);
		
		if (p.isPool()) { 						//se nao for ultimo pacote do arquivo
			System.out.println("ma oee");
			Server.tmpFileList.add(p);
		}
		else {
			
			System.out.println("ma oee else");
			
			int pos = Server.tmpFileList.hasTmpFile(p);
			
			if (pos == -1) {				//se arquivos temporarios nao contem o arquivo do pacote
				this.saveFile(p);							//cria arquivo pequenino
			}
			else {
				TemporaryFile tmp = Server.tmpFileList.remove(pos);
				this.saveFile(tmp);
			}
		}
	
	}


	/**
	 * @param tmp
	 * @throws IOException 
	 */
	private void saveFile(TemporaryFile tmp) throws IOException {
		
		System.out.println("salvo grandao");
		
		File f = new File(tmp.getFileName());
		FileOutputStream fo = new FileOutputStream(f);
		LinkedList<Package> list = tmp.getPackgeList();
		
		int start = 0;
		int offset = 0;
		byte [] buf;
		
		for (Package p : list) {
			buf = p.getPayLoad();
			offset = buf.length;
			
			fo.write(buf, start, offset);
			
			start = offset;
		}

		fo.close();
		
	}


	/**
	 * @param p
	 * @throws IOException 
	 */
	private void saveFile(Package p) throws IOException {
		
		System.out.println("salvo pequeno");
		
		File f = new File(p.getFileName());
		
		FileOutputStream fo = new FileOutputStream(f);
		
		fo.write(p.getPayLoad());
		
		fo.close();	

	}
}
