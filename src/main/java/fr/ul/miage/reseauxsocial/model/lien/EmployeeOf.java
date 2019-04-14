package fr.ul.miage.reseauxsocial.model.lien;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.Lien;
import fr.ul.miage.reseauxsocial.model.Propriete;

public class EmployeeOf extends Lien {

	public EmployeeOf(String nom, boolean sens, String nom2) {
		super(nom, sens, nom2);
	}

	public EmployeeOf(String nom, String sens, String nom2) {
		super(nom, sens, nom2);
	}

	public EmployeeOf(String nom, boolean sens, String nom2, ArrayList<Propriete> proprietes) {
		// TODO Auto-generated constructor stub
		super(nom, sens, nom2, proprietes);
	}
	
	
}
