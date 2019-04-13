package fr.ul.miage.reseauxsocial.model.lien;

import fr.ul.miage.reseauxsocial.model.Lien;

public class Friend extends Lien {

	public Friend(String nom, boolean sens, String nom2) {
		super(nom, sens, nom2);
	}

	public Friend(String nom, String sens, String nom2) {
		super(nom, sens, nom2);
	}
}
