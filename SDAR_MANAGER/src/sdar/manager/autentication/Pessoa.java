package sdar.manager.autentication;

/**
 * Classe Pessoa referente a uma pessoa
 * @author matheus
 */
public class Pessoa {

	private String nome;
	private String usuario;
	private String senha;
	
	/**
	 * Metodo que retorna o Nome da Pessoa
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo que seta o Nome da Pessoa
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Metodo que retorna o Usuario da Pessoa
	 * @return
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * Metodo que seta o Usuario da Pessoa
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Metodo que retorna a Senha da Pessoa
	 * @return
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * Metodo que seta a Senha da Pessoa
	 * @param senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
}