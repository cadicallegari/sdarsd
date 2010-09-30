/**
 * MessageReceivedHandler.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.manager
 */
package sdar.repository.manager;

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
			System.out.println("packt");
		}
		else if (className.equals("Solicitation")) {
			System.out.println("solicitation");
		}
		
	}

}
