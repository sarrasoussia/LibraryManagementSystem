package com.example.bibliotheque.App;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.time.Period;

public class Bibliotheque {

	private ArrayList<Livre> liste_livres = new ArrayList<>();
	private ArrayList<Lecteur> lecteurs = new ArrayList<>();
	Map<Long, DetailEmprunt> mapEmprunts = new HashMap<Long, DetailEmprunt>();
	Map<Long, Integer> mapLivres = new HashMap<Long, Integer>();
	/**************/
	
    //ex8 tp8
    public void getNbLivres() {
    	
//    	    liste_livres.stream()
//                  .filter(livre -> livre.getAuteur().equalsIgnoreCase("Victor Hugo"))
//                  .peek(System.out::println) //kima for each
//                  .filter(livre -> livre.getTitre().startsWith("L") )
//                  .peek(System.out::println)
//                  .count();
    	    
    	    getListe_livres().stream()
    	       .filter(livre -> livre.getAuteur().equalsIgnoreCase("Victor Hugo"))
    	       .filter(livre -> livre.getTitre().startsWith("L"))
    	       .forEach(System.out::println);

    }
    public void triLec() {
    	getLecteurs().stream()
        .sorted(Comparator.comparingInt(Lecteur::getAge).thenComparing(Lecteur::getNom));
      
    	
    }
    
	/**************/
	//methode pour ajouter un livre a la liste
	public void ajouter_livre(Livre livre) {
		if (!getListe_livres().contains(livre))
			getListe_livres().add(livre);
		
		if (mapLivres.containsKey(livre.getIsbn())) {
            int nombreExemplaires = mapLivres.get(livre.getIsbn());
            mapLivres.put(livre.getIsbn(), nombreExemplaires + 1);
        } else {
            mapLivres.put(livre.getIsbn(), 1);
        }
		System.out.println("Nouveau livre ajouté");
	}

	//methode pour emprunter un livre
	public void emprunter(long cin, Livre l)  {
		//verifier si le livre existe dans liste_livres
		if (!getListe_livres().contains(l)) {
			System.out.println("Le livre n'existe pas!");
			return;
		}
		
		//verifier si le lecteur existe dans lecteurs
		boolean lecteur_existe = false;
		for(Lecteur lect: getLecteurs()) {
			if(lect.getCin()==cin) {
				lecteur_existe = true;
				break;
			}
		}
		if(!lecteur_existe) {
			System.out.println("Le lecteur n'existe pas!");
			return;
		}
		
		//verifier le nombre d'exemplaires de ce livre non empruntes
	    int nb_exemplaires = mapLivres.get(l.getIsbn());
	    int nb_total = 0;
	    for(Livre item: getListe_livres()) {
	    	if (item != null && item.equals(l)) {
	    		nb_total++;
	    	}
	    }
	    if(nb_exemplaires < nb_total) {
	    	System.out.println("Tous les exemplaires du livre <"+ l.getTitre() +"> ont été empruntés!");
	    	return;
	    }
	    
		
		//verifier si le lecteur a deja emprunte un livre
	    if (mapEmprunts.containsKey(cin)) {
	        System.out.println("Ce lecteur a déjà emprunté un livre.");
	        return;
	    }
	    
	    
	    //livre et lecteur disponibles
	    DetailEmprunt nouv = new DetailEmprunt(l);
	    mapEmprunts.put(cin, nouv);
	    //decrementer le nb d'exemplaires de ce livre dans mapLivres
  		long cle_livre = l.getIsbn();
  		if(mapLivres.containsKey(cle_livre)) {
  			int nb_actuel = mapLivres.get(cle_livre);
  			nb_actuel--;
  			mapLivres.put(cle_livre,nb_actuel);
  		}
	    System.out.println("Emprunt effectué!");
		
	}
	
	/**************/
	public void retourner_livre(Lecteur lect, Livre liv) {
		//verifier si le lecteur a deja emprunte
		if (!mapEmprunts.containsKey(lect.getCin()) ) {
			System.out.println("Le lecteur n'a pas emprunté un livre!");
			return;
		}
		
		//incrementer le nb d'exemplaires de ce livre dans mapLivres
		long cle_livre = liv.getIsbn();
		if(mapLivres.containsKey(cle_livre)) {
			int nb_actuel = mapLivres.get(cle_livre);
			nb_actuel++;
			mapLivres.put(cle_livre,nb_actuel);
		}
		
	}
	
	/**************/
	public int nombre_total_livres() {
		return getListe_livres().size();
	}
	
	/**************/
	public int nombre_livres_empruntes() {
		return mapEmprunts.size();
	}
	
	/**************/
	//j'ai ajouté des getters dans la classe DetailEmprunt
	public int nombre_livre_retours() {
		int nb = 0;
		//LocalDate limite_inf = LocalDate.now();
		/***** pour afficher 4 livres de retour dans les 7 jours suiv ***/
		//LocalDate limite_inf = LocalDate.of(2023, 10, 15);
		LocalDate limite_inf = LocalDate.now().plusDays(1);
		LocalDate limite_sup = limite_inf.plusDays(7);
		for( DetailEmprunt detail: mapEmprunts.values() ) {
			LocalDate dateRetour = detail.getDateRetour();
			if( dateRetour.isBefore(limite_sup) ) {
				nb++;
			}
		}
		
		return nb;
		
	}
	
	public ArrayList<ClientFidele> lecteurs_fideles() {
		ArrayList<ClientFidele> liste = new ArrayList<ClientFidele>();
		
		for(Lecteur lec: getLecteurs()) {
			LocalDate dateexpir = lec.abonnement.getDateexpiration();
			//Period p = Period.between(dateexpir, LocalDate.now());
			
			if(dateexpir.isBefore(LocalDate.now())) {
				System.out.println("Abonnement expiré");
			}
			else {
				//nb total emprunts durant l'annee
				int nbTotal = lec.abonnement.getListeEmprunts().size();
				if( nbTotal >= 2) {
					lec = new ClientFidele(lec.getCin(), lec.getNom(), lec.getPrenom());
					liste.add((ClientFidele) lec);
				}
				
			}
		}
		
		
		return liste;
	}


	public void categories_livres() {
		int nbRom = 0, nbPol = 0, nbSc = 0;
		for(Livre liv: getListe_livres()) {
			//System.out.println(liv.getClass());
			if (liv instanceof LivreRomantique) {
			    nbRom++;
			}	
			if (liv instanceof LivrePolice) {
			    nbPol++;
			}
			if (liv instanceof LivreSciFi) {
			    nbSc++;
			}
//			if(liv.getClass().descriptorString().contains("Police")) {
//				nbPol++;
//			}

		}
		
		System.out.println("Nb de livres romantiques:"+ nbRom + "\nNb de livres policiers: "+nbPol+"\nNb de livres Sci-Fi:"+nbSc);
		
	}


	public ArrayList <Lecteur> abonnements_epuises() {
		ArrayList <Lecteur> lecteur_expire = new ArrayList<Lecteur>();
		for(Lecteur lec: getLecteurs()) {
			LocalDate dateexpir = lec.abonnement.getDateexpiration();
			if(dateexpir.isBefore(LocalDate.now())) {
				lecteur_expire.add(lec);
			}
		}
		return lecteur_expire;
		
	}
	

	

public ArrayList<Lecteur> getLecteurs() {
	return lecteurs;
}
public void setLecteurs(ArrayList<Lecteur> lecteurs) {
	this.lecteurs = lecteurs;
}
public ArrayList<Livre> getListe_livres() {
	return liste_livres;
}
public void setListe_livres(ArrayList<Livre> liste_livres) {
	this.liste_livres = liste_livres;
}


}