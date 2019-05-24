package fr.ul.miage.reseauxsocial.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.ConstructeurRequete;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Requete;

public class ControlRequeteTest {

	ImportReseau irSujet;
	
	@BeforeEach
	void avantChaque() {
		irSujet = new ImportReseau();
		irSujet.importFile("src/test/ressource/reseauSujetRequete");
	}
	
	//@Test
	void requeteExemple1() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Barbara");
		resultatPrevu.add("Anna");
		resultatPrevu.add("Carol");
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Elizabeth");
		resultatPrevu.add("Jill");
		
		String[] filtre1 = {"Likes", "<"};
		String[] filtre2 = {"Friend", ""};
		
		Requete requete = new ConstructeurRequete().withNoeudDepart("NoSQLDistilled").withNiveau(4).withFiltre(filtre1, filtre2).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	//@Test
	void filtreTypeFriend() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Barbara");
		String[] str = {"Friend",""};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void filtreTypeFriendSensDroite() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Barbara");
		String[] str = {"Friend",""};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void filtreTypeLien() {
		String[] str = {"Friend",""};
		ArrayList<String[]> arr1 = new ArrayList<>();
		arr1.add(str);
		
		ArrayList<String[]> arr2 = new ArrayList<>();
		Requete requete = new ConstructeurRequete().BuildRequete();
		
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertTrue(controlRequeteSujet.filtrageDesliens("Carol","Dawn", arr1, arr2));
	}
	
	
}
