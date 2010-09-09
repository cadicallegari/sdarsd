package sdar.comunication.encryption;

public class Encryption {
	
    /**
     * Metodo que faz a criptografia de uma mensagem
     * @param mensagem - Mensagem a ser criptografada
     * @return Mensagem criptografada
     */
    public String encrypt(String mensagem) {
    	String mensagemRetorno = new String();
    	int valor;
    	char auxiliar;
    	
    	for (int i = 0; i < mensagem.length(); i++) {
    		valor = (int) mensagem.charAt(i);
    		valor++;
    		auxiliar = (char) valor;
    		mensagemRetorno = mensagemRetorno.concat(String.valueOf(auxiliar));
    	}
    	return mensagemRetorno;
    }
    
    /**
     * Metodo que faz a descriptografia de uma mensagem
     * @param mensagem - Mensagem a ser descriptografada
     * @return Mensagem criptografada
     */
    public String decrypt(String mensagem) {
    	String mensagemRetorno = new String();
    	int valor;
    	char auxiliar;
    	
    	for (int i = 0; i < mensagem.length(); i++) {
    		valor = (int) mensagem.charAt(i);
    		valor--;
    		auxiliar = (char) valor;
    		mensagemRetorno = mensagemRetorno.concat(String.valueOf(auxiliar));
    	}
    	return mensagemRetorno;
    }
}