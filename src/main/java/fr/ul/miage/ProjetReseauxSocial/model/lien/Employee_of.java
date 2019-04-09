package fr.ul.miage.ProjetReseauxSocial.model.lien;

import fr.ul.miage.ProjetReseauxSocial.model.Lien;
import fr.ul.miage.ProjetReseauxSocial.model.entite.Noeud;

public class Employee_of extends Lien {

	public Employee_of(Noeud noeudDepart, String sens, Noeud noeudDestination) {
		super(noeudDepart, sens, noeudDestination);
	}

}
