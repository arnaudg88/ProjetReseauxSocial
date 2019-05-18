package fr.ul.miage.reseauxsocial.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

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
			initialisePaire(depart, destination); //initialise la paire
		
		getLiens(depart, destination).add(l);
		if(l.isDoubleSens()) {
			if(!paireExist(destination, depart))
				initialisePaire(destination, depart);
			getLiens(destination, depart).add(l.getLienInverse());
		}
		
		if(!noeudExist(depart))
			noeuds.put(depart, new Noeud(depart)); //ajoute le noeud depart
		
		if(!noeudExist(destination))
			noeuds.put(destination, new Noeud(destination)); //ajoute le noeud destination
	}
	
	
	public int getDistEntreDeuxNoeuds(String noeud1, String noeud2) {
		if(noeud1.equals(noeud2)) {
			return 0;
		}
		ArrayList<String> fils = getFils(noeud1);
		int tmpMinimum = Integer.MAX_VALUE;
		for(String f:fils) {
			int tmp = getDistEntreDeuxNoeuds(f, noeud2);
			if(tmp!=-1 && tmp<tmpMinimum) {
				tmpMinimum = tmp;
			}
		}
		if(tmpMinimum != Integer.MAX_VALUE)
			return 1+tmpMinimum;
		else
			return -1;
	}
	
	public ArrayList<String> getFils(String noeud) {
		ArrayList<String> result = new ArrayList<String>();
		for(String n:noeuds.keySet()) {
			if(paireExist(noeud, n)) {
				result.add(n);
			}
		}
		return result;
	}
	
	public ArrayList<String> getVoisins(String noeud) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> listeNoeudsFils = getFils(noeud);
		result.addAll(getParents(noeud));
		for(String n:listeNoeudsFils) {
			if(!result.contains(n)) {
				result.add(noeud);
			}
		}
		return result;
	}
	
	//@Charles
	//a tester
	//voir si péter le noeud suffit ou s'il faut egalement defoncer le lien ou les liens associés au noeud
	public void supprimerNoeud(String noeud) {
		ArrayList<String> listeLien = new ArrayList<String>();
		ArrayList<String> listTemp = new ArrayList<String>();
		listTemp.add(noeud);
		
		listeLien.addAll(getVoisins(noeud));
		for(String n:listeLien) {
			if(nombreDeLien(n)==1) {
				//noeuds.remove(n);
				listTemp.add(n);
			}
		}
		
		for(String s:listTemp) {
			for(String n: noeuds.keySet()) {
				if(paireExist(s, n)) {
					reseau.remove(new Paire(s,n));
				}
				if(paireExist(n, s)) {
					reseau.remove(new Paire(n,s));
				}
			}
		}
		
		for(String s: listTemp) {
			noeuds.remove(s);
		}
		
	}
	
	
	//@Charles
	public ArrayList<String> getParents(String noeud) {
		ArrayList<String> result = new ArrayList<String>();
		for(String n:noeuds.keySet()) {
			if(paireExist(n, noeud)) {
				result.add(n);
			}
		}
		return result;
	}
	
	//@Charles
	public int nombreDeLien(String noeud) {
		return getParents(noeud).size() + getFils(noeud).size();
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
