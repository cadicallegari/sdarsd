package sdar.bo;

import java.io.Serializable;

/**
 * Classe que implementa o objeto File que representa um arquivo
 */
public class Archive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String filename;
	private long size;
	
	private boolean pool;
	
	
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
	public long getSize() {
		return size;
	}
	
	/**
	 * Metodo que seta o tamanho do arquivo
	 * @param size
	 */
	public void setSize(long size) {
		this.size = size;
	}
}