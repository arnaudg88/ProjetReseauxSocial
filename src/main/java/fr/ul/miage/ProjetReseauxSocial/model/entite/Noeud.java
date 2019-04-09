package fr.ul.miage.ProjetReseauxSocial.model.entite;

import java.util.ArrayList;

import fr.ul.miage.ProjetReseauxSocial.model.Entite;
import fr.ul.miage.ProjetReseauxSocial.model.Lien;

public class Noeud extends Entite {

	private ArrayList<Lien> liens;
	private String nom;
	
	public Noeud(String nom, ArrayList<Lien> liens) {
		super();
		this.liens = liens;
		this.nom = nom;
	}

}
