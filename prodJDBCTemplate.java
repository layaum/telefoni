package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class prodJDBCTemplate {

	private JdbcTemplate jdbcTemplateObject;

    @Autowired
    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }
    
    
    public int insertprodotto(String nome ,String tipologia,  double prezzo, int quantita, String url) {
        String query = "INSERT INTO telefoni (nome, tipologia, prezzo, quantita, url) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplateObject.update(query,nome, tipologia, prezzo, quantita, url);
    }
    public int updatePrezzo(double prezzo, String nome) {
        String query = "UPDATE telefoni SET prezzo = ? WHERE nome = ?";
        return jdbcTemplateObject.update(query, prezzo, nome);
    }

	public int deleteNome(String nome) {
		String query = "DELETE FROM telefoni WHERE nome = ?";
		return jdbcTemplateObject.update(query, nome);
	}
	
	public ArrayList <prodotto> getLista(){
		// seleziona tutti i record da telefoni
		String query = "SELECT * FROM telefoni";
 
		// il metodo esegue la query e come secondo parametro crea un result set extractor
		 return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<prodotto>>() {
            // l'oggetto resultSetExtractor ha il metodo extractData che deve essere obbligatoriamente implementato
			@Override
			public ArrayList<prodotto> extractData(ResultSet rs) throws SQLException, DataAccessException {
 
				// creiamo un arraylist di prodotto che ci servirà come valore di ritorno del metodo
				ArrayList <prodotto> listaP = new ArrayList<>();
 
				// andiamo a iterare il resulta set
				while (rs.next()) {
 
					prodotto p1 = new prodotto();
					// con i risultati del result set abbiamo instanziato un oggetto prodotto e lo abbiamo
					// aggiunto alla lista
					p1.setNome(rs.getString("nome"));
					p1.setTipologia(rs.getString("tipologia"));
					p1.setPrezzo(rs.getDouble("prezzo"));
					p1.setQuantita(rs.getInt("quantita"));
					p1.setUrl(rs.getString("url"));
					listaP.add(p1);
 
				}
 
				return listaP;
			}
 
	});
 
	}
	   
	  public int updateprodottoOrdinato(String nome, int qnt) {
		   
		   String query = "UPDATE eventi SET postiD = postiD - ? WHERE nome = ?";
		   return jdbcTemplateObject.update(query, qnt, nome);
		   
	   }
	  
	  
	  //inizio registrazione utente
	  public int insertutente(String nome , String cognome,  String citta, String indirizzo, int telefono, String mail) {
	        String query = "INSERT INTO newsletter (nome, cognome, citta, indirizzo, telefono, mail) VALUES (?, ?, ?, ?, ?, ?)";
	        return jdbcTemplateObject.update(query,nome, cognome, citta, indirizzo, telefono, mail);
	    }
	    public int updatemail(String mail, String cognome, String nome) {
	        String query = "UPDATE newsletter SET mail = ? WHERE cognome = ? WHERE nome";
	        return jdbcTemplateObject.update(query, mail, cognome, nome);
	    }

		public int deleteMail(String mail) {
			String query = "DELETE FROM telefoni WHERE mail = ?";
			return jdbcTemplateObject.update(query, mail);
		}
		
		public ArrayList <utente> getListaU(){
			// seleziona tutti i record da telefoni
			String query = "SELECT * FROM newsletter";
	 
			// il metodo esegue la query e come secondo parametro crea un result set extractor
			 return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<utente>>() {
	            // l'oggetto resultSetExtractor ha il metodo extractData che deve essere obbligatoriamente implementato
				@Override
				public ArrayList<utente> extractData(ResultSet rs) throws SQLException, DataAccessException {
	 
					// creiamo un arraylist di prodotto che ci servirà come valore di ritorno del metodo
					ArrayList <utente> listaU2 = new ArrayList<>();
	 
					// andiamo a iterare il resulta set
					while (rs.next()) {
	 
						utente u1 = new utente();
						// con i risultati del result set abbiamo instanziato un oggetto prodotto e lo abbiamo
						// aggiunto alla lista
						u1.setNome(rs.getString("nome"));
						u1.setCognome(rs.getString("cognome"));
						u1.setCitta(rs.getString("citta"));
						u1.setIndirizzo(rs.getString("indirizzo"));
						u1.setTelefono(rs.getInt("telefono"));
						u1.setMail(rs.getString("mail"));
						listaU2.add(u1);
	 
					}
	 
					return listaU2;
				}
	 
		});
	 
		}
		   
		 /* public int updateprodottoOrdinato(String nome, int qnt) {
			   
			   String query = "UPDATE eventi SET postiD = postiD - ? WHERE nome = ?";
			   return jdbcTemplateObject.update(query, qnt, nome);*/
}

