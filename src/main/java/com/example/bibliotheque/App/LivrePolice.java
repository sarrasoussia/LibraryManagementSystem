package com.example.bibliotheque.App;

public class LivrePolice extends Livre {
	String descriptif;
	String detectif;
	String victime;
	
	//constructors
	public LivrePolice() {
		super();		
	}
	public LivrePolice(String desc, String detec, String vict, String titre, String auteur, long isbn) {
		super(titre, auteur, isbn);
		this.descriptif = desc;
		this.detectif = detec;
		this.victime = vict;
	}
	public LivrePolice(String auteur, String titre) {
		super(auteur, titre);	
	}
	
	
	@Override
	public String toString() {
		return "LivrePolicier [descriptif=" + descriptif + ", detectif=" + detectif + ", victime=" + victime
				+ ", getIsbn()=" + getIsbn() + ", getCode()=" + getCode() + ", getAuteur()=" + getAuteur()
				+ ", getTitre()=" + getTitre() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
	//setters and getters
	public String getDescriptif() {
		return descriptif;
	}
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	public String getDetectif() {
		return detectif;
	}
	public void setDetectif(String detectif) {
		this.detectif = detectif;
	}
	public String getVictime() {
		return victime;
	}
	public void setVictime(String victime) {
		this.victime = victime;
	}
	
	
}
