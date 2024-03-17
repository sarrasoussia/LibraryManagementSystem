package com.example.bibliotheque.App;

public class LivreSciFi extends Livre{

	Integer date;
	String espace;
	
	public LivreSciFi() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LivreSciFi(String titre, String auteur, long isbn, Integer date, String espace) {
		super(titre, auteur, isbn);
		this.date = date;
		this.espace = espace;
	}
	public LivreSciFi(String auteur, String titre) {
		super(auteur, titre);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "LivreSciFi [date=" + date + ", espace=" + espace + ", getIsbn()=" + getIsbn() + ", getCode()="
				+ getCode() + ", getAuteur()=" + getAuteur() + ", getTitre()=" + getTitre() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getEspace() {
		return espace;
	}
	public void setEspace(String espace) {
		this.espace = espace;
	}
	
	
	
	
}
