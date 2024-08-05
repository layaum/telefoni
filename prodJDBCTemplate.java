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
}
