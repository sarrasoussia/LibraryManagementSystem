package com.example.bibliotheque.App;

import java.time.LocalDate;

public class DetailEmprunt {

	Livre livre;
	LocalDate dateEmprunt;
	LocalDate dateRetour;
	
	
	public DetailEmprunt(Livre livre) {
		this.livre = livre;
		dateEmprunt = LocalDate.now();
		dateRetour = dateEmprunt.plusDays(7);
	}
	

	public DetailEmprunt(Livre livre, LocalDate dateEmprunt) {
		super();
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateEmprunt.plusDays(7);;
	}



	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}


	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}


	public LocalDate getDateRetour() {
		return dateRetour;
	}


	public Livre getLivre() {
		return livre;
	}


	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	
	


	
	
	
	
	
	
}
