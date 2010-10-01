package sdar.manager.autentication;

import java.util.LinkedList;
import java.util.List;

import com.db4o.ObjectSet;

public class UCManterPessoaManager {
	
	/**
	 * Metodo que faz a insercao de um objeto Pessoa
	 * @param pessoa
	 */
	public void insert(Pessoa pessoa) {
		ColPessoa colPessoa = new ColPessoa();
		colPessoa.getDbConnection().connect();
		colPessoa.insert(pessoa);
		colPessoa.getDbConnection().disconnect();
	}
	
	/**
	 * Metodo de consulta que retorna uma lista com todos os objetos Pessoa inseridos no banco
	 * @return
	 */
	public List<Pessoa> retrieveAll() {
		ObjectSet<Pessoa> result = null;
		List<Pessoa> lista = null;
		
		ColPessoa colPessoa = new ColPessoa();
		colPessoa.getDbConnection().connect();
		
		result = colPessoa.retrieveAll();
		if (result.hasNext()) {
			lista = new LinkedList<Pessoa>();
			
			while (result.hasNext()) {
				Pessoa p = (Pessoa) result.next();
				lista.add(p);
			}
		}
		colPessoa.getDbConnection().disconnect();
		
		return lista;
	}
	
	/**
	 * Metodo que consulta um objeto Pessoa
	 * @param pessoa
	 * @return
	 */
	public List<Pessoa> retrieve(Pessoa pessoa) {
		ObjectSet<Pessoa> result = null;
		List<Pessoa> lista = null;
		
		ColPessoa colPessoa = new ColPessoa();
		colPessoa.getDbConnection().connect();
		
		result = colPessoa.retrieve(pessoa);
		if (result.hasNext()) {
			lista = new LinkedList<Pessoa>();
			
			while (result.hasNext()) {
				Pessoa p = (Pessoa) result.next();
				lista.add(p);
			}
		}
		colPessoa.getDbConnection().disconnect();
		
		return lista;
	}
	
	/**
	 * Metodo que remove um objeto Pessoa
	 * @param pessoa
	 */
	public void delete(Pessoa pessoa) {
		ColPessoa colPessoa = new ColPessoa();

		colPessoa.getDbConnection().connect();
		
		ObjectSet<Pessoa> result = colPessoa.retrieve(pessoa);
		
		if (result != null) {
			while (result.hasNext()) {
				Pessoa p = (Pessoa) result.next();
				colPessoa.delete(p);
			}
		}
		
		colPessoa.getDbConnection().disconnect();
	}
}