package sdar.manager.autentication;

import java.util.LinkedList;
import java.util.List;

import com.db4o.ObjectSet;

public class UCManterAutenticationManager {
	
	/**
	 * Metodo que faz a insercao de um objeto Person
	 * @param person
	 */
	public void insert(Person person) {
		ColPerson colPerson = new ColPerson();
		colPerson.getDbConnection().connect();
		colPerson.insert(person);
		colPerson.getDbConnection().disconnect();
	}
	
	/**
	 * Metodo de consulta que retorna uma lista com todos os objetos Person inseridos no banco
	 * @return
	 */
	public List<Person> retrieveAll() {
		ObjectSet<Person> result = null;
		List<Person> lista = null;
		
		ColPerson colPerson = new ColPerson();
		colPerson.getDbConnection().connect();
		
		result = colPerson.retrieveAll();
		if (result.hasNext()) {
			lista = new LinkedList<Person>();
			
			while (result.hasNext()) {
				Person p = (Person) result.next();
				lista.add(p);
			}
		}
		colPerson.getDbConnection().disconnect();
		
		return lista;
	}
	
	/**
	 * Metodo que consulta um objeto Person
	 * @param person
	 * @return
	 */
	public List<Person> retrieve(Person person) {
		ObjectSet<Person> result = null;
		List<Person> lista = null;
		
		ColPerson colPerson = new ColPerson();
		colPerson.getDbConnection().connect();
		
		result = colPerson.retrieve(person);
		if (result.hasNext()) {
			lista = new LinkedList<Person>();
			
			while (result.hasNext()) {
				Person p = (Person) result.next();
				lista.add(p);
			}
		}
		colPerson.getDbConnection().disconnect();
		
		return lista;
	}
	
	/**
	 * Metodo que remove um objeto Person
	 * @param person
	 */
	public void delete(Person person) {
		ColPerson colPerson = new ColPerson();

		colPerson.getDbConnection().connect();
		
		ObjectSet<Person> result = colPerson.retrieve(person);
		
		if (result != null) {
			while (result.hasNext()) {
				Person p = (Person) result.next();
				colPerson.delete(p);
			}
		}
		
		colPerson.getDbConnection().disconnect();
	}
	
	/**
	 * Metodo que verifica se o objeto Person esta cadastrado
	 * @param person
	 * @return
	 */
	public boolean confirmAutentication(Person person) {
		List<Person> list = this.retrieve(person);
		
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}