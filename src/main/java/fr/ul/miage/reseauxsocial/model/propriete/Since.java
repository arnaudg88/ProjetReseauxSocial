package fr.ul.miage.reseauxsocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Since extends Propriete {

	@Override
	public String toString() {
		return "Since [valeur=" + valeur + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + valeur;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Since other = (Since) obj;
		if (valeur != other.valeur)
			return false;
		return true;
	}

	private int valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Since(String attribut, int valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}
}
