/**
 * MessageReceivedHandler.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.manager
 */
package sdar.repository.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import sdar.comunication.common.Packt;
import sdar.comunication.common.Solicitation;

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
		
		if (className.equals("Packt")) {
			try {
				
				Packt p = (Packt) obj;
				this.packtHandler(p);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (className.equals("Solicitation")) {
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
	private void packtHandler(Packt p) throws IOException {

		//TODO definir local dos arquivos do repositorio
		File f = new File(p.getFileName());
		
		FileOutputStream fo = new FileOutputStream(f);
		
		fo.write(p.getPayLoad());
		
		fo.close();
	}

}
