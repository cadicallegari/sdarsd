/**
 * TempFile.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.manager
 */
package sdar.repository.temporaryfile;

import java.util.LinkedList;

import sdar.comunication.common.Package;

/**
 * @author cadi
 *
 */
public class TemporaryFile {

	private String fileName;
	private LinkedList<Package> packageList = new LinkedList<Package>();
	
	
	
	

	/**
	 * @param pkg
	 */
	public void add(Package pkg) {
		//TODO possivel tratamento de ordem com o sequence number
		this.packageList.add(pkg);
		
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
	
	public LinkedList<Package> getPackgeList() {
		return this.packageList;
	}
	
}
