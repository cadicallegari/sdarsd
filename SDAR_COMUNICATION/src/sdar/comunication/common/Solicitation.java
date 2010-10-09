/**
 * Solicitation.java
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
public class Solicitation implements Serializable {

	
	/**
	 * long
	 */
	private static final long serialVersionUID = 4706811657142766361L;

	public static int LIST_FILE		= 1;
	public static int DOWNLOAD		= 2;
	
	String archiveName;
	private int code;
	private String address;
	private int port;
	
	
	

	
	/**
	 * @return the archive
	 */
	public String getArchiveName() {
		return archiveName;
	}


	/**
	 * @param archive the archive to set
	 */
	public void setArchiveName(String archive) {
		this.archiveName = archive;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}


	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;		
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
	
}
