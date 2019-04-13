package fr.ul.miage.ProjetReseauxSocial.model.lien;

import fr.ul.miage.ProjetReseauxSocial.model.Lien;
import fr.ul.miage.ProjetReseauxSocial.model.entite.Noeud;

public class Friend extends Lien {

	public Friend(Noeud noeudDepart, String sens, Noeud noeudDestination) {
		super(noeudDepart, sens, noeudDestination);
	}

}
