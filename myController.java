package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.MessagingException;

@Controller
public class myController {
	private final prodJDBCTemplate prodJDBCTemp;
	
	 @Autowired
	 EmailService emailservice;
	 
	 ArrayList<prodottoOrdinato>	listaO = new ArrayList();
	 double sommaO = 0;
	 ArrayList<String> listaI = new ArrayList();
	  
	 @Autowired
	    public myController(prodJDBCTemplate prodJDBCTemp) {
	        this.prodJDBCTemp = prodJDBCTemp;
	    }
	 
	 @GetMapping("/negozio")
		public String getNegozio(Model model) {
	    	
	ArrayList<prodotto>	lista = prodJDBCTemp.getLista();
		    
		    for (prodotto p1: lista) {
		    	System.out.println(p1);
		    }
		    
		    
		    model.addAttribute("lista", lista);
		    	
		    	
			
			
			return "Store2";
		}
	
	
	@GetMapping("/private/gestore")
	  public String getGestore() {
		
		return "formG";
	 }
	
	
	@GetMapping("/registrati")
	  public String getRegistrati() {
		
		return "recUtente";
	 }
	
	//metodo per registrazione utente
	@PostMapping("/submit2")
	public ResponseEntity getSubmit(@RequestParam("nome") String nome,
			@RequestParam("cognome") String cognome, @RequestParam("citta") String citta,
			@RequestParam("indirizzo") String indirizzo, @RequestParam("telefono") int telefono, @RequestParam("mail") String mail, Model model) {
		
		System.out.println(mail);
		
		prodJDBCTemp.insertutente(nome, cognome, citta, indirizzo, telefono, mail);
		
		return ResponseEntity.ok("utente " + mail + " registrato con successo");
	}
	
	@PostMapping("/delete2")
	public ResponseEntity getDelete2(@RequestParam("mail") String mail) {
	
		prodJDBCTemp.deleteMail(mail);
			
	return ResponseEntity.ok("utente " + mail + " cancellato con successo");
	}
	/*@GetMapping("/")
	public String getrec(Model model) {
		
		ArrayList<utente> lista = prodJDBCTemp.getListaU();
		
		for (utente u1: lista) {
			
			System.out.println(u1);
		}
		
		model.addAttribute("listaU2", lista);
		
		
		
		return "index";
	}*/
	
	//fine registrazione utente metodo
	
	
	@PostMapping("/submit")
	public ResponseEntity getSubmit(@RequestParam("nome") String nome,
			@RequestParam("tipo") String tipologia, @RequestParam("prezzo") double prezzo,
			@RequestParam("quantita") int quantita, @RequestParam("url") String url, Model model) {
		
		System.out.println(prezzo);
		
		prodJDBCTemp.insertprodotto(nome, tipologia, prezzo, quantita, url);
		
		return ResponseEntity.ok("prodotto " + nome + " aggiunto con successo");
	}
	
	@PostMapping("/delete")
	public ResponseEntity getDelete(@RequestParam("nome") String nome) {
	
		prodJDBCTemp.deleteNome(nome);
			
	return ResponseEntity.ok("prodotto " + nome + " eliminato con successo");
	}
	
	@GetMapping("/")
	public String getStore(Model model) {
		
		ArrayList<prodotto> lista = prodJDBCTemp.getLista();
		
		for (prodotto p1: lista) {
			
			System.out.println(p1);
		}
		
		model.addAttribute("listaP", lista);
		
		
		
		return "index";
	}	
	
	
	
	
	
	
				
	 @PostMapping("/process")
	 public String getProcess(@RequestParam("qnts")String [] listaQnt, Model model) { 
		 ArrayList<prodottoOrdinato> listaOrdini = new ArrayList();
		 double somma = 0;
		 ArrayList<prodotto> lista = prodJDBCTemp.getLista();
		
		 for (int i = 0; i < lista.size(); i++) {
			 
			 if (!listaQnt[i].equals("0")) {
				 listaI.add(lista.get(i).url);
				 prodottoOrdinato p1 = new prodottoOrdinato();
				 p1.setNome(lista.get(i).nome);
				 p1.setQnt(Integer.parseInt(listaQnt[i]));
				 listaO.add(p1);
				 listaOrdini.add(p1);
				 
				somma += lista.get(i).prezzo * p1.getQnt();
				sommaO += lista.get(i).prezzo * p1.getQnt();
				
			 }
		 }
		 
		 model.addAttribute("lista", listaOrdini);
		 
		 model.addAttribute("somma", somma);
		 
	 return ("recap");
	 }
	 
	   @GetMapping("/show")
	    @ResponseBody
	    public ArrayList<prodotto> getLista(){
	    	
	    	ArrayList<prodotto>	lista = prodJDBCTemp.getLista();
	    	
	    	return (lista);
	    	
	    }
				
	
	    @PostMapping("/conferma")
	    public ResponseEntity<String> getConferma(@RequestParam("mail") String mail) throws MessagingException{
	   
	  	  String to = mail;
	  		 String subject = "ordine da Telefoni confermato";
	  		 String text = "";
	  		 text+= "Hai acquistato:  \n";
	  		 for (prodottoOrdinato p1 : listaO) {
	   
	  			 text+= "Telefono: " + p1.getNome() + "\n";
	  			 text+= "Quantità: " + p1.getQnt() + "\n";
	  			 prodJDBCTemp.updateprodottoOrdinato(p1.getNome(), (p1.getQnt()));
	  		 }
	  		 
	  		 
	   
	  		 text += "Il prezzo totale da pagare è " + sommaO + " euro";
	  		 emailservice.sendEmailWithImage(to, subject, text, listaI);
	   
	  		
	  		 
	  	  return ResponseEntity.ok("Conferma dell'avvenuto acquisto, guarda la tua mail");
	   
	    }

@GetMapping("/login")
public String getLogin() {
	
	return "login";
}


}
