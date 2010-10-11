package sdar.util.temporaryfile;

import java.util.LinkedList;

import sdar.comunication.common.Package;

/**
 * Classe que implementa um arquivo temporario
 */
public class TemporaryFile {

	private String fileName;
	private LinkedList<Package> packageList = new LinkedList<Package>();
	

	/**
	 * Metodo que adiciona um pacote no arquivo temporario
	 * @param newPackage
	 */
	public void add(Package newPackage) {
		this.packageList.add(newPackage);
	}
	
	
	/**
	 * Metodo que retorna o nome do arquivo temporario
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	
	/**
	 * Metodo que seta o nome do arquivo temporario
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	/**
	 * Metodo que retorna a lista de todos os pacotes que representa o arquivo temporario
	 * @return
	 */
	public LinkedList<Package> getPackgeList() {
		return this.packageList;
	}
}