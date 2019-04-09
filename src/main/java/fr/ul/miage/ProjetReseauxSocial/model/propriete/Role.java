package fr.ul.miage.ProjetReseauxSocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.ProjetReseauxSocial.model.Propriete;

public class Role extends Propriete {

	private String valeur;
	private final static String[] TYPE_POSSIBLE = {};
	
	public Role(String attribut, String valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}

}
