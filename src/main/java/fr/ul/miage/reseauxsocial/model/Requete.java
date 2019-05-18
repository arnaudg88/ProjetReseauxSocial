package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

public class Requete {

	private String noeudDepart;
	private int mode; //0 profondeur, 1 largeur
	private int niveau;
	private int unicite; //0 noeudGlobal, 1 lienGlobal
	private ArrayList<String> liensAParcourir;
	//manque filtre
	private ArrayList<String> resultat;
	private ArrayList<String> dejaParcouruNoeud;
	private ArrayList<Integer> dejaParcouruLien;
	
	public Requete(String noeudDepart) {
		this.noeudDepart = noeudDepart;
		this.mode = 0;
		this.niveau = Integer.MAX_VALUE;
		this.unicite = 0;
		this.liensAParcourir = new ArrayList<>();
		this.resultat = new ArrayList<>();
		this.dejaParcouruNoeud = new ArrayList<>();
		this.dejaParcouruLien = new ArrayList<>();
	}
	
	public Requete(String noeudDepart, int mode, int niveau, int unicite, ArrayList<String> liensAParcourir, ArrayList<String> resultat, ArrayList<String> dejaParcouruNoeud, ArrayList<Integer> dejaParcouruLien) {
		this.noeudDepart = noeudDepart;
		this.mode = mode;
		this.niveau = niveau;
		this.unicite = unicite;
		this.liensAParcourir = liensAParcourir;
		this.resultat = resultat;
		this.dejaParcouruNoeud = dejaParcouruNoeud;
		this.dejaParcouruLien = dejaParcouruLien;
	}
	
	public Requete requeteDuVoisin(String noeudVoisin) {
		return new Requete(noeudVoisin, this.getMode(), this.getNiveau()-1, this.getUnicite(), this.getLiensAParcourir(), this.getResultat(), this.getDejaParcouruNoeud(), this.getDejaParcouruLien()); 
	}

	public ArrayList<Integer> getDejaParcouruLien() {
		return dejaParcouruLien;
	}

	public void setDejaParcouruLien(ArrayList<Integer> dejaParcouruLien) {
		this.dejaParcouruLien = dejaParcouruLien;
	}

	public ArrayList<String> getDejaParcouruNoeud() {
		return dejaParcouruNoeud;
	}

	public void setDejaParcouruNoeud(ArrayList<String> dejaParcouru) {
		this.dejaParcouruNoeud = dejaParcouru;
	}

	public String getNoeudDepart() {
		return noeudDepart;
	}

	public void setNoeudDepart(String noeudDepart) {
		this.noeudDepart = noeudDepart;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public int getUnicite() {
		return unicite;
	}

	public void setUnicite(int unicite) {
		this.unicite = unicite;
	}

	public ArrayList<String> getLiensAParcourir() {
		return liensAParcourir;
	}

	public void setLiensAParcourir(ArrayList<String> liensAParcourir) {
		this.liensAParcourir = liensAParcourir;
	}

	public ArrayList<String> getResultat() {
		return resultat;
	}

	public void setResultat(ArrayList<String> resulat) {
		this.resultat = resulat;
	}
	
}
