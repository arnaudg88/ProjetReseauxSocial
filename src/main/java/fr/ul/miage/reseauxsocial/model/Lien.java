package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;

public abstract class Lien {

	private ArrayList<Propriete> proprietes;
	private boolean doubleSens; // true <>, false >
	private String noeudDepart;
	private String noeudDestination;

	public Lien(String noeudDepart, boolean sens, String noeudDestination) {
		this.doubleSens = sens;
		this.noeudDepart = noeudDepart;
		this.noeudDestination = noeudDestination;
	}

	public Lien(String noeudDepart, String sens, String noeudDestination) {
		this(noeudDepart, sens.equals("<>"), noeudDestination);
	}
	
	//Methode pour ConstructeurLien
	public Lien(String nom, boolean sens, String nom2, ArrayList<Propriete> proprietes) {
		this(nom, sens, nom2);
		this.proprietes=proprietes;
	}
	
	public abstract Lien getLienInverse();

	public void addPropriete(Propriete p) {
		if(proprietes == null) {
			proprietes = new ArrayList<>();
		}
		proprietes.add(p);
	}

	public ArrayList<Propriete> getProprietes() {
		return proprietes;
	}

	@Override
	public String toString() {
		return "Lien [proprietes=" + proprietes + ", doubleSens=" + doubleSens + ", noeudDepart=" + noeudDepart
				+ ", noeudDestination=" + noeudDestination + "]";
	}

	public void setProprietes(ArrayList<Propriete> proprietes) {
		this.proprietes = proprietes;
	}

	public boolean isDoubleSens() {
		return doubleSens;
	}

	public void setDoubleSens(boolean doubleSens) {
		this.doubleSens = doubleSens;
	}

	public String getNoeudDepart() {
		return noeudDepart;
	}

	public void setNoeudDepart(String noeudDepart) {
		this.noeudDepart = noeudDepart;
	}

	public String getNoeudDestination() {
		return noeudDestination;
	}

	public void setNoeudDestination(String noeudDestination) {
		this.noeudDestination = noeudDestination;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (doubleSens ? 1231 : 1237);
		result = prime * result + ((noeudDepart == null) ? 0 : noeudDepart.hashCode());
		result = prime * result + ((noeudDestination == null) ? 0 : noeudDestination.hashCode());
		result = prime * result + ((proprietes == null) ? 0 : proprietes.hashCode());
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
		Lien other = (Lien) obj;
		if (doubleSens != other.doubleSens)
			return false;
		if (noeudDepart == null) {
			if (other.noeudDepart != null)
				return false;
		} else 
			if (!noeudDepart.equals(other.noeudDepart))
				return false;
		if (noeudDestination == null) {
			if (other.noeudDestination != null)
				return false;
		} else 
			if (!noeudDestination.equals(other.noeudDestination))
				return false;
		if (proprietes == null) {
			if (other.proprietes != null)
				return false;
		} else 
			if (!proprietes.equals(other.proprietes))
				return false;
		return true;
	}

}
