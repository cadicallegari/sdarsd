package sdar.bo;

import java.io.Serializable;

/**
 * Classe que implementa o objeto File que representa um arquivo
 */
public class File implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String filename;
	private int size;
	
	/**
	 * Metodo que retorna o nome do arquivo
	 * @return
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Metodo que seta o nome do arquivo
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Metodo que retorna o tamanho do arquivo
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Metodo que seta o tamanho do arquivo
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
}