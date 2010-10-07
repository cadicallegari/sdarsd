/**
 * TemporaryFileList.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.file
 */
package sdar.util.temporaryfile;

import java.util.LinkedList;

import sdar.comunication.common.Package;

/**
 * @author cadi
 *
 */
public class TemporaryFileList {

	
	private LinkedList<TemporaryFile> temporaryFileList = new LinkedList<TemporaryFile>();
	
	
	
	/**
	 * Verifica se existe algum arquivo temporario na lista de arquivos temporarios com o mesmo nome
	 * se existir retorna a posiçao em que o arquivo se encontra na lista
	 * se nao existir retorna -1
	 * @param pkg
	 * @return posiçao do arquivo temporario que tem o mesmo nome do pkg na lista
	 */
	public int hasTmpFile(Package pkg) {
		int ret = -1;
		int index = 0;
		
		String fileName = "";
		TemporaryFile tmpFile = null;
		
		if (!this.temporaryFileList.isEmpty()) {
			while ((ret == -1) && (index < this.temporaryFileList.size())) {
				tmpFile = this.temporaryFileList.get(index);
				fileName = tmpFile.getFileName();
				
				if (tmpFile.getFileName().equals(fileName)) {
					ret = index;
				}
				index++;
			}
		}
		return ret;
	}

	
	public int hasTmpFile(String fileName) {
		int ret = -1;
		int index = 0;
		
		TemporaryFile tmpFile = null;
		
		if (!this.temporaryFileList.isEmpty()) {
			while ((ret == -1) && (index < this.temporaryFileList.size())) {
				tmpFile = this.temporaryFileList.get(index);
				
				if (tmpFile.getFileName().equals(fileName)) {
					ret = index;
				}
				index++;
			}
		}
		return ret;
	}
	
	
	
	/**
	 * @param pkg
	 */
	private void insertTmpFile(Package pkg) {
		TemporaryFile tmp = new TemporaryFile();
		tmp.setFileName(pkg.getFileName());
		tmp.add(pkg);
		this.temporaryFileList.add(tmp);
	}
	
	
	
	/**
	 * 
	 * @param pkg
	 * @return
	 */
	public synchronized int add(Package pkg) {
		
		int index = this.hasTmpFile(pkg);
		
		if (index == -1) {  			//nao existe arquivo temporario com mesmo nome
			this.insertTmpFile(pkg);
			index = this.temporaryFileList.size() - 1;    // retorna posiçao de onde foi inserido
		}
		else {							//ja existe arquivo temporario incompleto na lista
			TemporaryFile tmp = this.temporaryFileList.get(index);
			tmp.add(pkg);
		}
		
		return index;
	}
	
	
	
	/**
	 * @param p
	 * @return
	 */
	public synchronized TemporaryFile remove(String fileName) {
		
		int pos = this.hasTmpFile(fileName);
		
		if (pos >= 0) {
			return this.temporaryFileList.remove(pos);
		}
		
		return null;
	}
	
	
	
	/**
	 * @param p
	 * @return
	 */
	public synchronized TemporaryFile remove(int pos) {
		
		return this.temporaryFileList.remove(pos);
		
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
