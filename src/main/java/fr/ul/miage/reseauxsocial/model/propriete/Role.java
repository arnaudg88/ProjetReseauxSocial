package fr.ul.miage.reseauxsocial.model.propriete;

import java.util.Arrays;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Role extends Propriete {

	@Override
	public String toString() {
		return "Role [valeur=" + valeur + "]";
	}

	private String valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Role(String attribut, String valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		Role other = (Role) obj;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}

}
