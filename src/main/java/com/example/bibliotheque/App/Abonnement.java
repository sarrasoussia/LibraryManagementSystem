package com.example.bibliotheque.App;

import java.time.LocalDate;
import java.util.ArrayList;

public class Abonnement {
	
	private LocalDate datecreation, dateexpiration;
	private double frais;
	private ArrayList <DetailEmprunt> listeEmprunts = new ArrayList<>();
	
	public Abonnement(ArrayList<DetailEmprunt> listeEmprunts, LocalDate datecreation) {
		super();
		this.datecreation = datecreation;
		this.listeEmprunts = listeEmprunts;
		this.setDateexpiration(datecreation.plusYears(1));
	}

	public Abonnement(double abonnement2) {
		this.frais = abonnement2;
	}
	
	public LocalDate getDatecreation() {
		return datecreation;
	}
	
	public double getFrais() {
		return frais;
	}
	public void setFrais(double frais) {
		this.frais = frais;
	}
	
	public ArrayList<DetailEmprunt> getListeEmprunts() {
		return listeEmprunts;
	}
	public void setListeEmprunts(ArrayList<DetailEmprunt> listeEmprunts) {
		this.listeEmprunts = listeEmprunts;
	}


	public LocalDate getDateexpiration() {
		return dateexpiration;
	}


	public void setDateexpiration(LocalDate dateexpiration) {
		this.dateexpiration = dateexpiration;
	}
	
	
	
}
