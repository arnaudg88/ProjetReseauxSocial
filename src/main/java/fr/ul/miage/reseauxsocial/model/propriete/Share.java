package fr.ul.miage.reseauxsocial.model.propriete;

import java.util.Arrays;
import java.util.List;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Share extends Propriete {

	private List<String> valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Share(String attribut, List<String> valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}
}
