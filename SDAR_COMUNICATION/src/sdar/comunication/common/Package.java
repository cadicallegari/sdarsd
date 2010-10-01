/**
 * Packt.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.common
 */
package sdar.comunication.common;

import java.io.Serializable;

/**
 * @author cadi
 *
 */
public class Package implements Serializable {

	/**
	 * long
	 */
	private static final long serialVersionUID = -9147464380706799772L;
	
	private String fileName;
	private int sequencieNumber;
	private String payLoad; 
	private int type;
	private int next;
	
	//indica se é um frame do meio do arquivo
	private boolean pool;

	
	
	/**
	 * @return the payLoad
	 */
	public byte[] getPayLoad() {
		return payLoad.getBytes();
	}
	/**
	 * @param payLoad the payLoad to set
	 */
	public void setPayLoad(byte[] payLoad) {
		this.payLoad = new String(payLoad);
	}	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the sequencieNumber
	 */
	public int getSequencieNumber() {
		return sequencieNumber;
	}
	/**
	 * @param sequencieNumber the sequencieNumber to set
	 */
	public void setSequencieNumber(int sequencieNumber) {
		this.sequencieNumber = sequencieNumber;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the next
	 */
	public int getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(int next) {
		this.next = next;
	}
	/**
	 * @return the pool
	 */
	public boolean isPool() {
		return pool;
	}
	/**
	 * @param pool the pool to set
	 */
	public void setPool(boolean pool) {
		this.pool = pool;
	}

}
