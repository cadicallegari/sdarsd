/**
 * Solicitation.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.common
 */
package sdar.comunication.common;

import java.io.Serializable;
import java.net.InetAddress;

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
	
	
	private String methodName;
	private int code;
	private InetAddress address;
	
	
	

	
	
	
	/**
	 * @return the address
	 */
	public InetAddress getAddress() {
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
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}


	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	/**
	 * @param address
	 */
	public void setAddress(InetAddress address) {
		this.address = address;		
	}
	
}
