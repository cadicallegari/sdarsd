package sdar.manager.autentication;

import java.io.Serializable;

/**
 * Classe Person referente a uma person
 * @author matheus
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String usuario;
	private String senha;

	/**
	 * Metodo construtor do objeto Person
	 */
	public Person() {
	}
	
	/**
	 * Metodo construtor do objeto Person
	 * @param nome
	 * @param usuario
	 * @param senha
	 */
	public Person(String nome, String usuario, String senha) {
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
	}
	
	/**
	 * Metodo que retorna o Nome da Person
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo que seta o Nome da Person
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Metodo que retorna o Usuario da Person
	 * @return
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * Metodo que seta o Usuario da Person
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Metodo que retorna a Senha da Person
	 * @return
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * Metodo que seta a Senha da Person
	 * @param senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
}