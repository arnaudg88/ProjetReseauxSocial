package fr.ul.miage.ProjetReseauxSocial.model.entite;

import java.util.ArrayList;

import fr.ul.miage.ProjetReseauxSocial.model.Entite;

public class Graphe extends Entite {

	private ArrayList<Entite> contients;

	public Graphe(ArrayList<Entite> contients) {
		super();
		this.contients = contients;
	}

	public Graphe() {
		super();
	}
	
	public void addEntite(Entite e) {
		contients.add(e);
	}
}
