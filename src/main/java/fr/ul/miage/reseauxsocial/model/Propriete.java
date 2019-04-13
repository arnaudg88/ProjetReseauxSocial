package fr.ul.miage.reseauxsocial.model;

import java.util.List;

public abstract class Propriete {

	protected String attribut;
	protected List<String> typePossible;
	
	public Propriete(String attribut, List<String> typePossible) {
		super();
		this.attribut = attribut;
		this.typePossible = typePossible;
	}
	
	public String getAttribut() {
		return attribut;
	}
	public void setAttribut(String attribut) {
		this.attribut = attribut;
	}
	public List<String> getTypePossible() {
		return typePossible;
	}
	public void setTypePossible(List<String> typePossible) {
		this.typePossible = typePossible;
	}
	
	
}
