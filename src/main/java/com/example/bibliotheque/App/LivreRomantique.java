package com.example.bibliotheque.App;

public class LivreRomantique extends Livre {
	String descriptif, nomPersonnage;
	
	//constructors
	public LivreRomantique() {
		super();
		
	}

	public LivreRomantique(String titre, String auteur, long isbn,String des,String nom) {
		super(titre, auteur, isbn);
		this.descriptif = des;
		this.nomPersonnage = nom;
	}

	public LivreRomantique(String auteur, String titre) {
		super(auteur, titre);
	}

	@Override
	public String toString() {
		return "LivreRomance [descriptif=" + descriptif + ", nomPersonnage=" + nomPersonnage + ", getIsbn()="
				+ getIsbn() + ", getCode()=" + getCode() + ", getAuteur()=" + getAuteur() + ", getTitre()=" + getTitre()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

	//getters and setters
	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

	public String getNomPersonnage() {
		return nomPersonnage;
	}

	public void setNomPersonnage(String nomPersonnage) {
		this.nomPersonnage = nomPersonnage;
	}
	
}
