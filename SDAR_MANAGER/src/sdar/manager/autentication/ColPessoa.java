package sdar.manager.autentication;

import com.db4o.ObjectSet;

import sdar.manager.database.DBConnection;

/**
 * Classe que representa a Collection de Pessoa
 * @author matheus
 */
public class ColPessoa {
	
	private DBConnection dbConnection;
	
	/**
	 * Metodo construtor da classe Colection de Pessoa
	 */
	public ColPessoa() {
		this.dbConnection = new DBConnection();
	}
	
	/**
	 * Meotod que insere o objeto
	 * @param pessoa
	 */
	public void insert(Pessoa pessoa) {
		this.dbConnection.getObjectContainer().store(pessoa);
	}
	
	/**
	 * Metodo que retorna todos os objetos Pessoa
	 * @return
	 */
	public ObjectSet<Pessoa> retrieveAll() {
		ObjectSet<Pessoa> result = null;
		result = this.dbConnection.getObjectContainer().queryByExample(Pessoa.class);
		return result;
	}

	/**
	 * Metodo que retorna um objeto Pessoa
	 * @param pessoa
	 * @return
	 */
	public ObjectSet<Pessoa> retrieve(Pessoa pessoa) {
		ObjectSet<Pessoa> result = this.dbConnection.getObjectContainer().queryByExample(pessoa);
		return result;
	}
	
	/**
	 * Metodo que remove um objeto Pessoa
	 * @param pessoa
	 */
	public void delete(Pessoa pessoa) {
		this.dbConnection.getObjectContainer().delete(pessoa);
	}

	/**
	 * Metodo que retorna a conexao da base de dados
	 * @return
	 */
	public DBConnection getDbConnection() {
		return this.dbConnection;
	}

	/**
	 * Metodo que seta a conexao da base de dados
	 * @param dbConnection
	 */
	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
}