package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.lien.Author;
import fr.ul.miage.reseauxsocial.model.lien.Category;
import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.lien.Likes;

public class ConstructeurLien {

	private String depart;
	private String destination;
	private boolean sens;
	private ArrayList<Propriete> proprietes;


	public ConstructeurLien withParam(String nom, boolean sens, String nom2) {
		this.depart=nom;
		this.sens = sens;
		this.destination = nom2;
		return this;
	}


	public ConstructeurLien withPropriete(Propriete... args) {
		if(proprietes == null) {
			proprietes = new ArrayList<>();
		}
		
		for(Propriete p:args) {
			this.proprietes.add(p);
		}
		

		return this;

	}



	public EmployeeOf buildEmployee() {
		return new EmployeeOf(depart, sens, destination, proprietes );
	}

	public Friend buildFriend() {
		return new Friend(depart, sens, destination, proprietes);
	}
	
	public Author buildAuthor() {
		return new Author(depart, sens, destination, proprietes);
	}

	public Category buildCategory() {
		return new Category(depart, sens, destination, proprietes);
	}
	
	public Likes buildLikes() {
		return new Likes(depart, sens, destination, proprietes);
	}
}
