package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;

public class ConstructeurLien {

	private String depart;
	private String destination;
	private boolean sens;
	private ArrayList<Propriete> proprietes;


	ConstructeurLien withParam(String nom, boolean sens, String nom2) {
		this.depart=nom;
		this.sens = sens;
		this.destination = nom2;
		return this;
	}


	ConstructeurLien withPropriete(Propriete... args) {
		if(proprietes == null) {
			proprietes = new ArrayList<Propriete>();
		}
		
		for(Propriete p:args) {
			this.proprietes.add(p);
		}
		

		return this;

	}



	EmployeeOf BuildEmployee() {
		return new EmployeeOf(depart, sens, destination, proprietes );
	}

	Friend BuildFriend() {
		return new Friend(depart, sens, destination, proprietes);
	}

}
