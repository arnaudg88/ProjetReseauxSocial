package fr.ul.miage.reseauxsocial.model.lien;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.Lien;
import fr.ul.miage.reseauxsocial.model.Propriete;

public class Likes extends Lien {

	public Likes(String nom, boolean sens, String nom2) {
		super(nom, sens, nom2);
	}

	public Likes(String nom, String sens, String nom2) {
		super(nom, sens, nom2);
	}

	public Likes(String nom, boolean sens, String nom2, ArrayList<Propriete> proprietes) {
		super(nom, sens, nom2, proprietes);
	}

	@Override
	public Lien getLienInverse() {
		return new Likes(this.getNoeudDestination(), this.isDoubleSens(), this.getNoeudDepart(),
				this.getProprietes());
	}
}
