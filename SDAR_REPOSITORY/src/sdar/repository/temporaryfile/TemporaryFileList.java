/**
 * TemporaryFileList.java
 * cadi
 * SDAR_REPOSITORY
 * sdar.repository.file
 */
package sdar.repository.temporaryfile;

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
		
		while ((ret == -1) && (index < this.temporaryFileList.size()) && (!this.temporaryFileList.isEmpty())) {
			tmpFile = this.temporaryFileList.get(index);
			fileName = tmpFile.getFileName();
			
			if (tmpFile.getFileName().equals(fileName)) {
				ret = index;
			}
			index++;
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
			index = this.temporaryFileList.size();
		}
		else {							//ja existe arquivo temporario incompleto na lista
			TemporaryFile tmp = this.temporaryFileList.get(index);
			tmp.add(pkg);
		}
		
		
		return index;
	}
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}




	/**
	 * @param p
	 * @return
	 */
	public TemporaryFile remove(int pos) {
		
		return this.temporaryFileList.remove(pos);
		
	}

}
