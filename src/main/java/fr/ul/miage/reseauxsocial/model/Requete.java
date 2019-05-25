package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

public class Requete {

	private String noeudDepart;
	private int mode; //0 profondeur, 1 largeur
	private int niveau;
	private int unicite; //0 noeudGlobal, 1 lienGlobal
	private ArrayList<String> liensAParcourir;
	private ArrayList<String> noeudsAParcourir;
	
	private ArrayList<String[]> listeTypesFiltres;
	
	private ArrayList<String[]> listeProprietes;
	
	
	private ArrayList<String> resultat;
	private ArrayList<String> dejaParcouruNoeud;
	private ArrayList<Lien> dejaParcouruLien;
	
	public Requete(String noeudDepart) {
		this.noeudDepart = noeudDepart;
		this.mode = 0;
		this.niveau = Integer.MAX_VALUE;
		this.unicite = 0;
		this.liensAParcourir = new ArrayList<>();
		this.noeudsAParcourir = new ArrayList<>();
		this.resultat = new ArrayList<>();
		this.dejaParcouruNoeud = new ArrayList<>();
		this.dejaParcouruLien = new ArrayList<>();

		
		this.listeTypesFiltres = new ArrayList<>();
		this.listeProprietes = new ArrayList<>();
	}
	
	public Requete(String noeudDepart, int mode, int niveau, int unicite, ArrayList<String> liensAParcourir, ArrayList<String> noeudsAParcourir, ArrayList<String> resultat, ArrayList<String> dejaParcouruNoeud, ArrayList<Lien> dejaParcouruLien, ArrayList<String[]> filtres, ArrayList<String[]> proprietes) {

		this.noeudDepart = noeudDepart;
		this.mode = mode;
		this.niveau = niveau;
		this.unicite = unicite;
		this.liensAParcourir = liensAParcourir;
		this.noeudsAParcourir = noeudsAParcourir;
		this.resultat = resultat;
		this.dejaParcouruNoeud = dejaParcouruNoeud;
		this.dejaParcouruLien = dejaParcouruLien;

		
		this.listeTypesFiltres = filtres;
		this.listeProprietes = proprietes;
	}

	public Requete requeteDuVoisin(String noeudVoisin) {
		return new Requete(noeudVoisin, this.getMode(), this.getNiveau()-1, this.getUnicite(), this.getLiensAParcourir(), this.getNoeudsAParcourir(), this.getResultat(), this.getDejaParcouruNoeud(), this.getDejaParcouruLien(), this.listeTypesFiltres, this.listeProprietes); 
	}

	public ArrayList<Lien> getDejaParcouruLien() {
		return dejaParcouruLien;
	}

	public void setDejaParcouruLien(ArrayList<Lien> dejaParcouruLien) {
		this.dejaParcouruLien = dejaParcouruLien;
	}

	public ArrayList<String> getDejaParcouruNoeud() {
		return dejaParcouruNoeud;
	}

	public void setDejaParcouruNoeud(ArrayList<String> dejaParcouru) {
		this.dejaParcouruNoeud = dejaParcouru;
	}

	public ArrayList<String> getNoeudsAParcourir() {
		return noeudsAParcourir;
	}

	public void setNoeudsAParcourir(ArrayList<String> noeudsAParcourir) {
		this.noeudsAParcourir = noeudsAParcourir;
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


	public ArrayList<String[]> getListeTypesFiltres() {
		return listeTypesFiltres;
	}

	public void setListeTypesFiltres(ArrayList<String[]> listeTypesFiltres) {
		this.listeTypesFiltres = listeTypesFiltres;
	}

	public ArrayList<String[]> getListeProprietes() {
		return listeProprietes;
	}

	public void setListeProprietes(ArrayList<String[]> listeProprietes) {
		this.listeProprietes = listeProprietes;
	}
	
	public static ArrayList<String[]> splitLiens(String filtre) {
		ArrayList<String[]> liensResultat = new ArrayList<>();
		
		if(filtre.length() > 0) {
			String[] liens = filtre.split(";");
			for(String lien:liens) {
				String[] infosLien = lien.trim().split(" ");
				String[] nomLien = new String[2];
				if(infosLien.length == 1) { // dans cas juste lien
					nomLien[0] = infosLien[0];
					nomLien[1] = "";
				} else if(infosLien.length == 2) { //dans cas lien avec direction
					nomLien[0] = infosLien[0];
					nomLien[1] = infosLien[1];
				}
				liensResultat.add(nomLien);
			}
		}
		return liensResultat;
	}
	
	public static ArrayList<String[]> splitProprietes(String filtre) {
		ArrayList<String[]> proprietesResultat = new ArrayList<>();
		
		if(filtre.length() > 0) {
			String[] proprietes = filtre.split(";"); 
			for(String propriete:proprietes) { //pour chaque liens
				String[] infosPropriete = propriete.trim().split("="); //split sur espace
				String[] nomPropriete = new String[2];
				nomPropriete[0] = infosPropriete[0];
				nomPropriete[1] = infosPropriete[1];
				
				proprietesResultat.add(nomPropriete);
			}
		}
		return proprietesResultat;
	}
	
}
