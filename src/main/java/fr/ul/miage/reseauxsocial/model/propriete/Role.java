package fr.ul.miage.reseauxsocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Role extends Propriete {

	private String valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Role(String attribut, String valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}

}
