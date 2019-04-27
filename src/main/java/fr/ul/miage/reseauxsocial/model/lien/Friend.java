package fr.ul.miage.reseauxsocial.model.lien;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.Lien;
import fr.ul.miage.reseauxsocial.model.Propriete;

public class Friend extends Lien {

	public Friend(String nom, boolean sens, String nom2) {
		super(nom, sens, nom2);
	}

	public Friend(String nom, String sens, String nom2) {
		super(nom, sens, nom2);
	}

	public Friend(String nom, boolean sens, String nom2, ArrayList<Propriete> proprietes) {
		super(nom, sens, nom2, proprietes);
	}

	@Override
	public Lien getLienInverse() {
		return new Friend(this.getNoeudDestination(), this.isDoubleSens(), getNoeudDepart(), this.getProprietes());
	}
}
