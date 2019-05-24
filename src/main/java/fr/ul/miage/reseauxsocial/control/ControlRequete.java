package fr.ul.miage.reseauxsocial.control;

import java.util.ArrayList;

import fr.ul.miage.reseauxsocial.model.Paire;
import fr.ul.miage.reseauxsocial.model.Lien;
import fr.ul.miage.reseauxsocial.model.Paire;
import fr.ul.miage.reseauxsocial.model.Propriete;
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

		ArrayList<String> voisins;
		if(requeteCourante.getNiveau() == 0) { // gestion des niveaux
			voisins = new ArrayList<>();
		} else {
			voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		}

		for (String noeudVoisin : voisins) { //parcours ses voisins
			if (possedeLiensAParcourir(requeteCourante.getNoeudDepart(), noeudVoisin, requete.getLiensAParcourir())
					&& filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin, requeteCourante.getListeFiltres(), requeteCourante.getListeProprietes())
					&& !estDejaParcouru(noeudVoisin, requeteCourante.getDejaParcouruNoeud()))
			{ //filtrage et direction vers les liens voulus 

				requeteCourante.getResultat().add(noeudVoisin);
				requeteCourante.getDejaParcouruNoeud().add(noeudVoisin);

				Requete sousRequete = parcoursRequeteProfondeurNoeudGlobal(requeteCourante.requeteDuVoisin(noeudVoisin)); //execution de la requete vers les voisins 

				// recuperation des donn�es pour les sous requetes suivantes
				for(String noeudParcouru:sousRequete.getDejaParcouruNoeud()) {
					if(!requeteCourante.getDejaParcouruNoeud().contains(noeudParcouru)) {
						requeteCourante.getDejaParcouruNoeud().add(noeudParcouru);
					}
				}

				for(String noeudResultat:sousRequete.getResultat()) {
					if(!requeteCourante.getResultat().contains(noeudResultat)) {
						requeteCourante.getResultat().add(noeudResultat);
					}
				}

			}
		}
		return requeteCourante;
	}

	public Requete parcoursRequeteProfondeurLienGlobal(Requete requeteCourante) {
		ArrayList<String> voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		for (String noeudVoisin : voisins) {
			if (possedeLiensAParcourir(requeteCourante.getNoeudDepart(), noeudVoisin, requete.getLiensAParcourir())
					&& filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin, requeteCourante.getListeFiltres(), requeteCourante.getListeProprietes())) {
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
		boolean res = false;
		if(reseaux.paireExist(noeudDepart, noeudArrive)) {
			res=true;
		}
		if(reseaux.paireExist(noeudArrive, noeudDepart)) {
			res=true;
		}
		return res;
	}

	public boolean filtrageDesliens(String noeudDepart, String noeudArrive, ArrayList<String[]> listeTypeLien, ArrayList<String[]> listeProprietes) {
		boolean res = false;
		if(listeTypeLien.size()==0) {
			res=true;
		}else {
			
		
			ArrayList<Lien> listeLienDA = reseaux.getReseau().get(new Paire(noeudDepart, noeudArrive));
	
			ArrayList<Lien> listeLienAD = reseaux.getReseau().get(new Paire(noeudArrive, noeudDepart));
			
			for(String[] filtre:listeTypeLien) {
				
				if (listeLienDA != null) {
					for (Lien l : listeLienDA) {
						if (filtre[0].equals(l.getClass().getSimpleName())) {
				
							ArrayList<Propriete> listeProprietesTemp = l.getProprietes();

							if ((filtre[1].equals(">")) || (filtre[1].equals(""))) {
								if (listeProprietes.size() == 0) {
									res = true;
								} else {
									for (String[] proprietes : listeProprietes) {
										for (Propriete p : listeProprietesTemp) {
											if (proprietes[0].equals(p.getClass().getSimpleName())
													&& proprietes[1].equals(p.getAttribut())) {
												res = true;
											}
										}
									}
								}

							} else if ((filtre[1].equals("<>"))) {

								if (listeProprietes.size() == 0) {
									res = true;
								} else {
									for (String[] proprietes : listeProprietes) {
										for (Propriete p : listeProprietesTemp) {
											if (proprietes[0].equals(p.getClass().getSimpleName())
													&& proprietes[1].equals(p.getAttribut())) {
												res = true;
											}
										}
									}
								}
							}
						}
					}
				}
				if (listeLienAD != null) {
					for (Lien l : listeLienAD) {
						ArrayList<Propriete> listeProprietesTemp = l.getProprietes();
						if (filtre[0].equals(l.getClass().getSimpleName())) {
							if ((filtre[1].equals("<"))) {

								if (listeProprietes.size() == 0) {
									res = true;
								} else {
									for (String[] proprietes : listeProprietes) {
										for (Propriete p : listeProprietesTemp) {
											if (proprietes[0].equals(p.getClass().getSimpleName())
													&& proprietes[1].equals(p.getAttribut())) {
												res = true;
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		}
		return res;
	}

	public boolean estDejaParcouru(String noeud, ArrayList<String> dejaParcouru) {
		return dejaParcouru.contains(noeud);
	}
}
