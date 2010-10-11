/**
 * ManagerHandler.java
 * cadi
 * SDAR_MANAGER
 * sdar.manager.handler
 */
package sdar.manager.handler;

import java.io.IOException;

import sdar.comunication.common.Package;
import sdar.comunication.tcp.TCPComunication;
import sdar.manager.manager.Manager;

/**
 * @author cadi
 *
 */
public class UploadHandler implements Runnable {

	
	private TCPComunication comunication;
	
	/**
	 * @param socketCommunication
	 */
	public UploadHandler(TCPComunication socketCommunication) {
		this.comunication = socketCommunication;
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		Package pack;
		
		try {
			
			do {
				
				pack = (Package) this.comunication.readObject();
				Manager.uploadBuffer.add(pack);

				System.out.println(pack.isNotLast());
				
			} while (pack.isNotLast());
			
			Manager.sendFile(pack.getFileName());
			
			this.comunication.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
