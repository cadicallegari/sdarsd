package sdar.comunication.encryption;

import sdar.bo.Person;

/**
 * Classe que contem os algoritmos de criptografia do projeto
 */
public class Encryption {

	
    /**
     * Metodo que faz a criptografia de uma mensagem
     * @param person - Objeto Person a ser criptografado
     * @return Objeto Encriptografado
     */
    public Person encrypt(Person person) {
    	Person personReturn = new Person();
    	int valor;
    	char auxiliar;
    	String message, messageReturn;
    	
    	//Encriptografa Nome da Pessoa
    	message = person.getName();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor++;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setName(messageReturn);
    	}
    	
    	//Encriptografa Usuario da Pessoa
    	message = person.getUser();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor++;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setUser(messageReturn);	
    	}
    	
    	//Encriptografa Senha da Pessoa
    	message = person.getPassword();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor++;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setPassword(messageReturn);
    	}
    	
    	return personReturn;
    }
    
    
    /**
     * Metodo que faz a descriptografia de uma mensagem
     * @param person - Objeto Person a ser descriptografado
     * @return Objeto descriptografado
     */
    public Person decrypt(Person person) {
    	Person personReturn = new Person();
    	int valor;
    	char auxiliar;
    	String message, messageReturn;
    	
    	//Descriptografa Nome da Pessoa
    	message = person.getName();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor--;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setName(messageReturn);	
    	}
    	
    	//Descriptografa Usuario da Pessoa
    	message = person.getUser();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor--;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setUser(messageReturn);
    	}
    	
    	//Descriptografa Senha da Pessoa
    	message = person.getPassword();
    	if (message != null) {
    		messageReturn = "";
        	for (int i = 0; i < message.length(); i++) {
        		valor = (int) message.charAt(i);
        		valor--;
        		auxiliar = (char) valor;
        		messageReturn = messageReturn.concat(String.valueOf(auxiliar));
        	}
        	personReturn.setPassword(messageReturn);	
    	}
    	
    	return personReturn;
    }
}