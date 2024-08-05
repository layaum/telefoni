package com.example.demo;

public class prodotto {
	
	String nome;
	String tipologia;
	double prezzo;
	int quantita;
    String url;
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "prodotto [nome=" + nome + ", tipologia=" + tipologia + ", prezzo=" + prezzo + ", quantita=" + quantita
				+ ", url=" + url + "]";
	}
	
}
	

	
	
	