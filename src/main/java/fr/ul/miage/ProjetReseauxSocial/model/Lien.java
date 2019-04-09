package fr.ul.miage.ProjetReseauxSocial.model;

import java.util.ArrayList;

import fr.ul.miage.ProjetReseauxSocial.model.entite.Noeud;

public abstract class Lien {

	private ArrayList<Propriete> proprietes;
	private String sens; //0 > 1 < 2 <>
	private Noeud noeudDepart;
	private Noeud noeudDestination;
	
	
	public Lien(Noeud noeudDepart, String sens, Noeud noeudDestination) {
		super();
		this.sens = sens;
		this.noeudDepart = noeudDepart;
		this.noeudDestination = noeudDestination;
	}
	
	public void addPropriete(Propriete p) {
		proprietes.add(p);
	}

	public ArrayList<Propriete> getProprietes() {
		return proprietes;
	}

	public void setProprietes(ArrayList<Propriete> proprietes) {
		this.proprietes = proprietes;
	}

	public String getSens() {
		return sens;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public Noeud getNoeudDepart() {
		return noeudDepart;
	}

	public void setNoeudDepart(Noeud noeudDepart) {
		this.noeudDepart = noeudDepart;
	}

	public Noeud getNoeudDestination() {
		return noeudDestination;
	}

	public void setNoeudDestination(Noeud noeudDestination) {
		this.noeudDestination = noeudDestination;
	}
}
