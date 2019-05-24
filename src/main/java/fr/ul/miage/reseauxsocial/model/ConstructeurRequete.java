package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

public class ConstructeurRequete {

	private String noeudDepart = "";
	private int mode = 0; //0 profondeur, 1 largeur
	private int niveau = Integer.MAX_VALUE;
	private int unicite = 0; //0 noeudGlobal, 1 lienGlobal
	private ArrayList<String> liensAParcourir = new ArrayList<>();
	private ArrayList<String[]> listeFiltres = new ArrayList<>();
	private ArrayList<String[]> listeProprietes = new ArrayList<>();
	private ArrayList<String> resultat = new ArrayList<>();
	private ArrayList<String> dejaParcouruNoeud = new ArrayList<>();
	private ArrayList<Integer> dejaParcouruLien = new ArrayList<>();

	public ConstructeurRequete withNoeudDepart(String noeud) {
		noeudDepart = noeud;
		return this;
	}
	
	public ConstructeurRequete withMode(int mode) {
		this.mode = mode;
		return this;
	}
	
	public ConstructeurRequete withProfondeurMode() {
		this.mode = 0;
		return this;
	}
	
	public ConstructeurRequete withLargeurMode() {
		this.mode = 1;
		return this;
	}
	
	public ConstructeurRequete withNiveau(int niveau) {
		this.niveau = niveau;
		return this;
	}
	
	public ConstructeurRequete withUnicite(int unicite) {
		this.unicite = unicite;
		return this;
	}
	
	public ConstructeurRequete withNoeudGlobal() {
		this.unicite = 0;
		return this;
	}
	
	public ConstructeurRequete withLienGlobal() {
		this.unicite = 1;
		return this;
	}
	
	public ConstructeurRequete withFiltre(String[]... filtres) {
		for(String[] filtre:filtres) {
			this.listeFiltres.add(filtre);
		}
		return this;
	}
	
	public ConstructeurRequete withLiens(ArrayList<String[]> liens) {
		this.listeFiltres = liens;
		return this;
	}
	
	public ConstructeurRequete withPropriete(ArrayList<String[]> props) {
		this.listeProprietes = props;
		return this;
	}

	/*ConstructeurLien withLien(String... args) {
		if(proprietes == null) {
			proprietes = new ArrayList<Propriete>();
		}
		
		for(Propriete p:args) {
			this.proprietes.add(p);
		}
		

		return this;

	}*/



	public Requete BuildRequete() {
		return new Requete(noeudDepart, mode, niveau, unicite, liensAParcourir, resultat, dejaParcouruNoeud, dejaParcouruLien, listeFiltres, listeProprietes);
	}
}
