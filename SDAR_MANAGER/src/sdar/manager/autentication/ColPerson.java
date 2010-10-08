package sdar.manager.autentication;

import sdar.bo.Person;
import sdar.manager.database.DBConnection;

import com.db4o.ObjectSet;

/**
 * Classe que representa a Collection de Person
 * @author matheus
 */
public class ColPerson {
	
	private DBConnection dbConnection;
	
	/**
	 * Metodo construtor da classe Colection de Person
	 */
	public ColPerson() {
		this.dbConnection = new DBConnection();
	}
	
	/**
	 * Meotod que insere o objeto
	 * @param person
	 */
	public void insert(Person person) {
		this.dbConnection.getObjectContainer().store(person);
	}
	
	/**
	 * Metodo que retorna todos os objetos Person
	 * @return
	 */
	public ObjectSet<Person> retrieveAll() {
		ObjectSet<Person> result = null;
		result = this.dbConnection.getObjectContainer().queryByExample(Person.class);
		return result;
	}

	/**
	 * Metodo que retorna um objeto Person
	 * @param person
	 * @return
	 */
	public ObjectSet<Person> retrieve(Person person) {
		ObjectSet<Person> result = this.dbConnection.getObjectContainer().queryByExample(person);
		return result;
	}
	
	/**
	 * Metodo que remove um objeto Person
	 * @param person
	 */
	public void delete(Person person) {
		this.dbConnection.getObjectContainer().delete(person);
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