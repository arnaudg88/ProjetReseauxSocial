package fr.ul.miage.ProjetReseauxSocial.model.propriete;

import java.util.Arrays;
import java.util.List;

import fr.ul.miage.ProjetReseauxSocial.model.Propriete;

public class Share extends Propriete {

	private List<String> valeur;
	private final static String[] TYPE_POSSIBLE = {};
	
	public Share(String attribut, List<String> valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}
}
