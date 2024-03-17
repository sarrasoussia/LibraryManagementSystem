package com.example.bibliotheque.App;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;



public class Livre {
    private static int nbLivres = 0;
    private long code;
    private String auteur, titre;
    private long isbn;

    //constructors
    public Livre() {
        nbLivres++;
        code = nbLivres;
    }
    public Livre(String auteur, String titre) {
        nbLivres++;
        code = nbLivres;
        this.auteur = auteur;
        this.titre = titre;
    }

    public Livre(String titre, String auteur, long isbn) {
        this.auteur = auteur;
        this.titre = titre;
        this.isbn = isbn;
        nbLivres++;
    }


    //getters and setters
    public long getIsbn() {
        return isbn;
    }
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public long getCode() {
        return code;
    }
    public void setCode(long code) {
        this.code = code;
    }

    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }


    //redefinition
    @Override
    public String toString() {
        return "Livre [code=" + code + ", auteur=" + auteur + ", titre=" + titre + "]";
    }


    //methods
    public int compare(Livre l) {
        return this.titre.compareToIgnoreCase(l.titre) ;
    }

    //better implementation of int compare
//	public int compare(Livre l) {
//	    return Objects.equals(this.titre, l.titre) ? 0 : this.titre.compareToIgnoreCase(l.titre);
//	}

    public static int compare(Livre l1, Livre l2) {
        return(l1.titre.compareToIgnoreCase(l2.titre) );
    }

    public void ajouter(String[][] t) {
        if ("Mussot".equals(this.auteur)) {
            for(int i = 0; i< t.length; i++) {
                if(t[i][1]!="Mussot") {
                    t[i][1] = this.auteur;
                    t[i][0] = this.titre;
                    return;

                }
            }
        }
    }

    public boolean estDisponible() {
        // TODO Auto-generated method stub
        return false;
    }





}
