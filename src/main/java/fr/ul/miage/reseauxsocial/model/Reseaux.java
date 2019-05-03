package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Reseaux {

	private HashMap<Paire, ArrayList<Lien>> reseau;
	private HashMap<String, Noeud> noeuds;
	
	public Reseaux() {
		reseau = new HashMap<>();
		noeuds = new HashMap<>();
	}
	
	public void addLien(Lien l) {
		String depart = l.getNoeudDepart();
		String destination = l.getNoeudDestination();
		if(!paireExist(depart, destination))
			initialisePaire(depart, destination);
		
		getLiens(depart, destination).add(l);
		if(!noeudExist(depart))
			noeuds.put(depart, new Noeud(depart));
		
		if(!noeudExist(destination))
			noeuds.put(destination, new Noeud(destination));
	}
	
	public void initialisePaire(String noeud1, String noeud2) {
		reseau.put(new Paire(noeud1, noeud2), new ArrayList<Lien>());
	}
	
	public boolean paireExist(String noeud1, String noeud2) {
		return reseau.containsKey(new Paire(noeud1, noeud2));
	}
	
	public ArrayList<Lien> getLiens(String noeudDepart, String noeudDestination) {
		return reseau.get(new Paire(noeudDepart, noeudDestination));
	}
	
	public boolean noeudExist(String noeud) {
		return noeuds.containsKey(noeud);
	}

	public HashMap<Paire, ArrayList<Lien>> getReseau() {
		return reseau;
	}

	public void setReseau(HashMap<Paire, ArrayList<Lien>> reseau) {
		this.reseau = reseau;
	}

	public HashMap<String, Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(HashMap<String, Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((noeuds == null) ? 0 : noeuds.hashCode());
		result = prime * result + ((reseau == null) ? 0 : reseau.hashCode());
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
		Reseaux other = (Reseaux) obj;
		if (noeuds == null) {
			if (other.noeuds != null)
				return false;
		} else {
			if (!noeuds.equals(other.noeuds))
				return false;
		}
		if (reseau == null) {
			if (other.reseau != null)
				return false;
		} else { 
			if (!reseau.equals(other.reseau))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Reseaux [reseau=" + reseau + ", noeuds=" + noeuds + "]";
	}
}
