package com.example.bibliotheque.App;

public class ClientFidele extends Lecteur {

	String mail, preference;
	
	public ClientFidele(long cin, String nom, String prenom, Abonnement a1, double frais, String mail, String preference) {
		super(cin, nom, prenom,a1,frais);
		this.mail = mail;
		this.preference = preference;
		
	}
	
	public ClientFidele(long cin, String nom, String prenom, String mail, String preference) {
		super(cin, nom, prenom);
		this.mail = mail;
		this.preference = preference;
		
	}
	
	
	public ClientFidele(long cin, String nom, String prenom, Abonnement a1, double i) {
		super(cin, nom, prenom, a1, i);
		// TODO Auto-generated constructor stub
	}

	public ClientFidele(long cin, String nom, String prenom) {
		super(cin, nom, prenom);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double frais_Abonnement() {
		if(this.abonnement != null) {
			double frais = this.abonnement.getFrais();
			frais*=0.85;
			this.abonnement.setFrais(frais);
			return frais;
		}
		
		return 0;
		
	}


	@Override
	public String toString() {
		return "ClientFidele [mail=" + mail + ", preference=" + preference + ", getCin()=" + getCin() + ", getNom()="
				+ getNom() + ", getPrenom()=" + getPrenom() + ", toString()=" + super.toString()
				+ ", frais_Abonnement()=" + frais_Abonnement() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
	
	
	

}
