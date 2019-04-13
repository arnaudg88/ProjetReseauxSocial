package fr.ul.miage.reseauxsocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Since extends Propriete {

	private int valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Since(String attribut, int valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}
}
