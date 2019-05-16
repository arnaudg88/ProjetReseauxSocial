package fr.ul.miage.reseauxsocial.control;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.Requete;
import fr.ul.miage.reseauxsocial.model.Reseaux;

public class ControlRequete {

	Reseaux reseaux;
	Requete requete;

	public ControlRequete(Reseaux reseaux, Requete requete) {
		this.reseaux = reseaux;
		this.requete = requete;
	}

	public Reseaux getReseaux() {
		return reseaux;
	}

	public void setReseaux(Reseaux reseaux) {
		this.reseaux = reseaux;
	}

	public Requete getRequete() {
		return requete;
	}

	public void setRequete(Requete requete) {
		this.requete = requete;
	}

	public ArrayList<String> executeRequete() {
		return executeRequete(this.requete);
	}

	public ArrayList<String> executeRequete(Requete requete) {
		if (requete.getMode() == 0)
			return parcoursRequeteProfondeurNoeudGlobal(requete).getResultat();
		else if (requete.getMode() == 1)
			return parcoursRequeteLargeur(requete).getResultat();
		else
			return new ArrayList<String>();
	}

	public Requete parcoursRequeteProfondeurNoeudGlobal(Requete requeteCourante) {
		ArrayList<String> voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		for (String noeudVoisin : voisins) {
			if (possedeLiensAParcourir(requeteCourante.getNoeudDepart(), noeudVoisin, requete.getLiensAParcourir())
					&& filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin)) {
				requeteCourante.getResultat().add(noeudVoisin);
				requeteCourante.getDejaParcouruNoeud().add(noeudVoisin);
				
				Requete sousRequete = requeteCourante.requeteDuVoisin(noeudVoisin);
				sousRequete = parcoursRequeteProfondeurNoeudGlobal(sousRequete);
				
				//remettre les données de sousRequete dans requeteCourante
			}
		}
		return requeteCourante;
	}

	public Requete parcoursRequeteProfondeurLienGlobal(Requete requeteCourante) {
		ArrayList<String> voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		for (String noeudVoisin : voisins) {
			if (possedeLiensAParcourir(requeteCourante.getNoeudDepart(), noeudVoisin, requete.getLiensAParcourir())
					&& filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin)) {
				requeteCourante.getResultat().add(noeudVoisin);
				// ajout du lien dans deja parcouru
				Requete sousRequete = requeteCourante.requeteDuVoisin(noeudVoisin);
				sousRequete = parcoursRequeteProfondeurLienGlobal(sousRequete);
			}
		}
		return requeteCourante;
	}

	public Requete parcoursRequeteLargeur(Requete requeteCourante) {

		return requeteCourante;
	}

	// public boolean estDejaParcouru(String noeudDepart, String noeudArrive)

	public boolean possedeLiensAParcourir(String noeudDepart, String noeudArrive, ArrayList<String> liensAParcourir) {
		// change les paramètres si besoins
		// regarde les liensAParcourir avec ceux enregistré dans le réseaux
		// si l'un correspond on renvoie vrai
		return true;
	}

	public boolean filtrageDesliens(String noeudDepart, String noeudArrive) {
		return true;
	}
}
