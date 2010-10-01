/**
 * Util.java
 * cadi
 * SDAR_COMUNICATION
 * sdar.comunication.common
 */
package sdar.comunication.common;

/**
 * @author cadi
 *
 */
public class Util {

	
	
	/**
	 * Método utilizado para copiar de buf, os qtd bytes
	 * Util para criar um pacote com o payload do tamanho correto
	 * @param buf
	 * @param qtd
	 * @return o byte array com os qtd primeiros bytes de buf
	 */
	public static byte[] copyBytes(byte[] buf, int qtd) {
		byte [] ret = new byte[qtd];
		
		if (qtd > buf.length) {
			return buf;
		}
		
		for (int i = 0; i < qtd; i++) {
			ret[i] = buf[i];
		}
		
		return ret;
	}
	
}
