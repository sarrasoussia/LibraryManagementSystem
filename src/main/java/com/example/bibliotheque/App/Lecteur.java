package com.example.bibliotheque.App;

import java.util.Scanner;

public class Lecteur {

	private long cin;
    private String nom;
    private String prenom;
    protected Abonnement abonnement;
    private double somme_cumule;
    private int age;

	double calculer_credit(double somme) throws CreditNegatifException{
    	double frais = abonnement.getFrais();
    	double credit = frais - (somme_cumule + somme);
    	if( credit < 0) throw new CreditNegatifException("Credit negatif");
    	somme_cumule += somme;
    	return credit;
    }

    public static void main(String[] args){
		try {
			Abonnement a = new Abonnement(100);
			Lecteur lec = new Lecteur(a,130);
			double credit = lec.calculer_credit(20);
			System.out.println(credit);
		}
		catch (CreditNegatifException e) {
			System.out.println(e.getMessage());
		}
    }
//    emprunter_livre() throws EmpruntInterdit {
//
//    }

	public Lecteur(long cin, String nom, String pre, int ag, double fee) {
		this.cin = cin;
		this.nom = nom;
		this.prenom = pre;
		this.abonnement= new Abonnement(fee);
		this.age = ag;
		this.somme_cumule = 0;
	}

    public Lecteur(long cin, String nom, String prenom, Abonnement a1, double i) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.abonnement = a1;
        this.abonnement.setFrais(i);
    }
    
    public Lecteur(long cin, String nom, String prenom) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }
    public Lecteur(Abonnement a1, double i) {
        this.abonnement = a1;
        this.somme_cumule = i;
    }
    
    public Lecteur() {
    	Scanner sc = new Scanner(System.in);
    	String ch = sc.nextLine();
		
		String[] tab = ch.split(" ");
		
		nom = tab[0];
		prenom = tab[1];
		cin = Long.parseLong(tab[2]);
		
		System.out.println(nom + " " + prenom + " "+ cin);
		sc.close();
    }
    
  
    
	public long getCin() {
		return cin;
	}
	public void setCin(long cin) {
		this.cin = cin;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	

	public double getSomme_cumule() {
		return somme_cumule;
	}

	public void setSomme_cumule(double somme_cumule) {
		this.somme_cumule = somme_cumule;
	}

	@Override
	public String toString() {
		return "Lecteur [cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
	public double frais_Abonnement() {
		return (abonnement.getFrais());
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
    

    
    //emprunter un livre a un lecteur
    public void emprunterLivre(Livre livre) throws EmpruntInterdit {
        // Check if the lecteur is allowed to borrow more books (you can implement your own logic)
//        if (abonnement.getListeEmprunts().size() >= MAX_ALLOWED_BOOKS) {
//            throw new EmpruntInterdit("Le lecteur a atteint le nombre maximal d'emprunts autorisés.");
//        }

        if (!livre.estDisponible()) {
            throw new EmpruntInterdit("Le livre n'est pas disponible pour l'emprunt.");
        }

        DetailEmprunt emprunt = new DetailEmprunt(livre);
        abonnement.getListeEmprunts().add(emprunt);

        System.out.println("Emprunt effectué!");
    }

}

    

