package fr.ul.miage.ProjetReseauxSocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.ProjetReseauxSocial.model.Propriete;

public class Since extends Propriete {

	private int valeur;
	private final static String[] TYPE_POSSIBLE = {};
	
	public Since(String attribut, int valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}
}
