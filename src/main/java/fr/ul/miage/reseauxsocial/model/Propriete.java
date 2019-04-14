package fr.ul.miage.reseauxsocial.model;

import java.util.List;

public abstract class Propriete {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribut == null) ? 0 : attribut.hashCode());
		result = prime * result + ((typePossible == null) ? 0 : typePossible.hashCode());
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
		Propriete other = (Propriete) obj;
		if (attribut == null) {
			if (other.attribut != null)
				return false;
		} else if (!attribut.equals(other.attribut))
			return false;
		if (typePossible == null) {
			if (other.typePossible != null)
				return false;
		} else if (!typePossible.equals(other.typePossible))
			return false;
		return true;
	}
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
