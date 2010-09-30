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

	
	private String methodName;

	
	
	

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
	
}
