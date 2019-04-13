package fr.ul.miage.ProjetReseauxSocial.model.propriete;

import java.util.Arrays;
import java.util.Date;

import fr.ul.miage.ProjetReseauxSocial.model.Propriete;

public class Hired extends Propriete {

	private Date valeur;
	private final static String[] TYPE_POSSIBLE = {};
	
	public Hired(String attribut, Date valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}

}
