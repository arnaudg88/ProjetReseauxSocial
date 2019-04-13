package fr.ul.miage.reseauxsocial.model.lien;

import fr.ul.miage.reseauxsocial.model.Lien;

public class EmployeeOf extends Lien {

	public EmployeeOf(String nom, boolean sens, String nom2) {
		super(nom, sens, nom2);
	}

	public EmployeeOf(String nom, String sens, String nom2) {
		super(nom, sens, nom2);
	}
}
