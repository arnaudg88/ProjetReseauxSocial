package fr.ul.miage.reseauxsocial.control;

import java.util.ArrayList;

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
		if (requete.getMode() == 0) {
			if(requete.getUnicite()==0) {
				return parcoursRequeteProfondeurNoeudGlobal(requete).getResultat();
			} else {
				return parcoursRequeteProfondeurLienGlobal(requete).getResultat();
			}
		} else if (requete.getMode() == 1) {
			if(requete.getUnicite() == 0) {
				return parcoursRequeteLargeurNoeudGlobal(requete).getResultat();
			} else  {
				return parcoursRequeteLargeurLienGlobal(requete).getResultat();
			}
		} else {
			return new ArrayList<>();

		}
	}

	public Requete parcoursRequeteProfondeurNoeudGlobal(Requete requeteCourante) {

		ArrayList<String> voisins;
		if(requeteCourante.getNiveau() == 0) { // gestion des niveaux
			voisins = new ArrayList<>();
		} else {
			voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		}

		for (String noeudVoisin : voisins) { //parcours ses voisins
			if (conditionsFiltre(requeteCourante, noeudVoisin))
			{ //filtrage et direction vers les liens voulus 

				requeteCourante.getResultat().add(noeudVoisin);
				requeteCourante.getDejaParcouruNoeud().add(noeudVoisin);

				Requete sousRequete = parcoursRequeteProfondeurNoeudGlobal(requeteCourante.requeteDuVoisin(noeudVoisin)); //execution de la requete vers les voisins 

				// recuperation des donn�es pour les sous requetes suivantes
				recupereNoeudParcouru(requeteCourante, sousRequete);

				recupereNoeudResultat(requeteCourante, sousRequete);

			}
		}
		return requeteCourante;
	}

	private void recupereNoeudResultat(Requete requeteCourante, Requete sousRequete) {
		for(String noeudResultat:sousRequete.getResultat()) {
			if(!requeteCourante.getResultat().contains(noeudResultat)) {
				requeteCourante.getResultat().add(noeudResultat);
			}
		}
	}

	private void recupereNoeudParcouru(Requete requeteCourante, Requete sousRequete) {
		for(String noeudParcouru:sousRequete.getDejaParcouruNoeud()) {
			if(!requeteCourante.getDejaParcouruNoeud().contains(noeudParcouru)) {
				requeteCourante.getDejaParcouruNoeud().add(noeudParcouru);
			}
		}
	}

	public Requete parcoursRequeteProfondeurLienGlobal(Requete requeteCourante) {
		
		ArrayList<String> voisins;
		if(requeteCourante.getNiveau() == 0) { // gestion des niveaux
			voisins = new ArrayList<>();
		} else {
			voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		}
		for (String noeudVoisin : voisins) {
			
			String typeLienTemp = possedeLiensAParcourirLienGlobal(requeteCourante.getNoeudDepart(), noeudVoisin);
			if (typeLienTemp != null && filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin, requeteCourante.getListeTypesFiltres(), requeteCourante.getListeProprietes())) {
				requeteCourante.getResultat().add(noeudVoisin);

				if (reseaux.findLien(requeteCourante.getNoeudDepart(), typeLienTemp, noeudVoisin) == null) {
					requeteCourante.getDejaParcouruLien()
							.add(reseaux.findLien(noeudVoisin, typeLienTemp, requeteCourante.getNoeudDepart()));
				} else {
					requeteCourante.getDejaParcouruLien()
							.add(reseaux.findLien(requeteCourante.getNoeudDepart(), typeLienTemp, noeudVoisin));
				}

				Requete sousRequete = parcoursRequeteProfondeurLienGlobal(requeteCourante.requeteDuVoisin(noeudVoisin));

				// recuperation des donn�es pour les sous requetes suivantes
				recupereLienParcouru(requeteCourante, sousRequete);

				recupereNoeudResultat(requeteCourante, sousRequete);
			}
		}
		return requeteCourante;
	}

	private void recupereLienParcouru(Requete requeteCourante, Requete sousRequete) {
		for(Lien lienParcouru:sousRequete.getDejaParcouruLien()) {
			if(!requeteCourante.getDejaParcouruLien().contains(lienParcouru)) {
				requeteCourante.getDejaParcouruLien().add(lienParcouru);
			}
		}
	}
	
	private Requete parcoursRequeteLargeurNoeudGlobal(Requete requeteCourante) {
		ArrayList<String> voisins;
		if(requeteCourante.getNiveau() == 0) { // gestion des niveaux
			voisins = new ArrayList<>();
		} else {
			voisins = reseaux.getVoisins(requeteCourante.getNoeudDepart());
		}
		
		recupereNoeudVoisin(requeteCourante, voisins);
		
		ArrayList<String> noeudsAParcourir = new ArrayList<>();
		noeudsAParcourir.addAll(requeteCourante.getNoeudsAParcourir());

		for (String noeudVoisin : noeudsAParcourir) { //parcours ses voisins
			if (conditionsFiltre(requeteCourante, noeudVoisin))
			{ //filtrage et direction vers les liens voulus 

				requeteCourante.getResultat().add(noeudVoisin);
				requeteCourante.getDejaParcouruNoeud().add(noeudVoisin);
			} else {
				requeteCourante.getNoeudsAParcourir().remove(noeudVoisin);
			}
		}
		
		ArrayList<String> voisinsAParcourir = new ArrayList<>();
		voisinsAParcourir.addAll(requeteCourante.getNoeudsAParcourir());
		
		for(String noeudVoisin:voisinsAParcourir) {
			requeteCourante.getNoeudsAParcourir().remove(noeudVoisin);
			Requete sousRequete = parcoursRequeteLargeurNoeudGlobal(requeteCourante.requeteDuVoisin(noeudVoisin)); //execution de la requete vers les voisins
			recupereNoeudParcouru(requeteCourante, sousRequete);

			recupereNoeudResultat(requeteCourante, sousRequete);

			recupereNoeudAParcourir(requeteCourante, sousRequete);
		}
		
		return requeteCourante;
	}

	private void recupereNoeudVoisin(Requete requeteCourante, ArrayList<String> voisins) {
		for(String noeudVoisin : voisins) {
			if(!requeteCourante.getNoeudsAParcourir().contains(noeudVoisin)) {
				requeteCourante.getNoeudsAParcourir().add(noeudVoisin);
			}
		}
	}

	private boolean conditionsFiltre(Requete requeteCourante, String noeudVoisin) {
		return possedeLiensAParcourir(requeteCourante.getNoeudDepart(), noeudVoisin)
				&& filtrageDesliens(requeteCourante.getNoeudDepart(), noeudVoisin, requeteCourante.getListeTypesFiltres(), requeteCourante.getListeProprietes())
				&& !estDejaParcouru(noeudVoisin, requeteCourante.getDejaParcouruNoeud());
	}

	private void recupereNoeudAParcourir(Requete requeteCourante, Requete sousRequete) {
		for(String noeudAParcourir:sousRequete.getNoeudsAParcourir()) {
			if(!requeteCourante.getNoeudsAParcourir().contains(noeudAParcourir) && !requeteCourante.getDejaParcouruNoeud().contains(noeudAParcourir)) {
				requeteCourante.getNoeudsAParcourir().add(noeudAParcourir);
			}
		}
	}

	public Requete parcoursRequeteLargeurLienGlobal(Requete requeteCourante) {
		return requeteCourante;
	}

	public boolean possedeLiensAParcourir(String noeudDepart, String noeudArrive) {
		boolean res = false;
		if(reseaux.paireExist(noeudDepart, noeudArrive)) {
			res=true;
		}
		if(reseaux.paireExist(noeudArrive, noeudDepart)) {
			res=true;
		}
		return res;
	}

	public String possedeLiensAParcourirLienGlobal(String noeudDepart, String noeudArrive) {

		ArrayList<Lien> listeLien1;
		if(reseaux.paireExist(noeudDepart, noeudArrive)) {
			listeLien1 = reseaux.getReseau().get(new Paire(noeudDepart, noeudArrive));
			for(Lien l:listeLien1) {
				if(!(requete.getDejaParcouruLien().contains(l))) {
					return l.getClass().getSimpleName();
				}
			}
		}else if(reseaux.paireExist(noeudArrive, noeudDepart)) {
			listeLien1 = reseaux.getReseau().get(new Paire(noeudArrive, noeudDepart));
			for(Lien l:listeLien1) {
				if(!(requete.getDejaParcouruLien().contains(l))) {
					return l.getClass().getSimpleName();
				}
			}
		}
		return null;
	}

	public boolean filtrageDesliens(String noeudDepart, String noeudArrive, ArrayList<String[]> listeTypeLien, ArrayList<String[]> listeProprietes) {
		boolean res = false;
		if(listeTypeLien.isEmpty()) {
			res=true;
		}else {


			ArrayList<Lien> listeLienDA = reseaux.getReseau().get(new Paire(noeudDepart, noeudArrive));

			ArrayList<Lien> listeLienAD = reseaux.getReseau().get(new Paire(noeudArrive, noeudDepart));

			for(String[] filtre:listeTypeLien) {

				if (listeLienDA != null) {
					res = parcoursLienDA(listeProprietes, res, listeLienDA, listeLienAD, filtre);
				}
				if (listeLienAD != null) {
					res = parcoursLienAD(listeProprietes, res, listeLienAD, filtre);
				}
			}
		}
		return res;
	}

	private boolean parcoursLienAD(ArrayList<String[]> listeProprietes, boolean res, ArrayList<Lien> listeLienAD,
			String[] filtre) {
		for (Lien l : listeLienAD) {
			ArrayList<Propriete> listeProprietesTemp = l.getProprietes();
			if (filtre[0].equals(l.getClass().getSimpleName()) && (filtre[1].equals("<")) || filtre[1].equals("")) {
				res = testCroisementPropriete(listeProprietes, res, listeProprietesTemp);
			}
		}
		return res;
	}

	private boolean parcoursLienDA(ArrayList<String[]> listeProprietes, boolean res, ArrayList<Lien> listeLienDA,
			ArrayList<Lien> listeLienAD, String[] filtre) {
		for (Lien lDA : listeLienDA) {
			if (filtre[0].equals((lDA.getClass()).getSimpleName())) {
				ArrayList<Propriete> listeProprietesTemp = lDA.getProprietes();

				if ((filtre[1].equals(">")) || (filtre[1].equals(""))) {
					res = testCroisementPropriete(listeProprietes, res, listeProprietesTemp);
				} else if ((filtre[1].equals("<>")) && listeLienAD != null) {
					res = parcoursLienDALienAD(listeProprietes, res, listeLienAD, lDA, listeProprietesTemp);
				}
			}
		}

		return res;
	}

	private boolean parcoursLienDALienAD(ArrayList<String[]> listeProprietes, boolean res,
			ArrayList<Lien> listeLienAD, Lien lDA, ArrayList<Propriete> listeProprietesTemp) {
		for (Lien lienAD : listeLienAD) {
			if ((lDA.getNoeudDepart().equals(lienAD.getNoeudDestination()))
					&& (lDA.getNoeudDestination().equals(lienAD.getNoeudDepart()))) {
				res = testCroisementPropriete(listeProprietes, res, listeProprietesTemp);
			}
		}
		return res;
	}

	private boolean testCroisementPropriete(ArrayList<String[]> listeProprietes, boolean res,
			ArrayList<Propriete> listeProprietesTemp) {
		if (listeProprietes.isEmpty()) {
			res = true;
		} else {
			for (String[] proprietes : listeProprietes) {
				for (Propriete p : listeProprietesTemp) {
					if (proprietes[0].equals(p.getClass().getSimpleName()) && proprietes[1].equals(p.getAttribut())) {
						res = true;
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
