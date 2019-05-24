package fr.ul.miage.reseauxsocial.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.ConstructeurRequete;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Requete;

public class ControlRequeteTest {

	ImportReseau irSujet;
	ImportReseau irPropriete;
	
	@BeforeEach
	void avantChaque() {
		irPropriete = new ImportReseau();
		irPropriete.importFile("src/test/ressource/reseauSujet");
		irSujet = new ImportReseau();
		irSujet.importFile("src/test/ressource/reseauSujetRequete");
		
		
	}
	
	@Test
	void requeteExemple1() {
		ArrayList<String> resultatPrevu = new ArrayList<>();

		resultatPrevu.add("Carol");
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Jill");
		resultatPrevu.add("Elizabeth");
		resultatPrevu.add("Barbara");
		resultatPrevu.add("Anna");
		
		
		

		String[] filtre1 = {"Likes", "<"};
		String[] filtre2 = {"Friend", ""};

		Requete requete = new ConstructeurRequete().withNoeudDepart("NoSQLDistilled").withNiveau(4).withFiltre(filtre1, filtre2).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void requeteExemple1Largeur() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Carol");
		resultatPrevu.add("Elizabeth");
		resultatPrevu.add("Barbara");
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Jill");
		resultatPrevu.add("Anna");

		String[] filtre1 = {"Likes", "<"};
		String[] filtre2 = {"Friend", ""};

		Requete requete = new ConstructeurRequete().withNoeudDepart("NoSQLDistilled").withNiveau(4).withFiltre(filtre1, filtre2).withLargeurMode().BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void requeteExemple3() {
		ArrayList<String> resultatPrevu = new ArrayList<>();

		resultatPrevu.add("Dawn");
		resultatPrevu.add("Jill");
		String[] filtre2 = {"Friend", ">"};
		
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(2).withFiltre(filtre2).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void requeteExemple3Largeur() {
		ArrayList<String> resultatPrevu = new ArrayList<>();

		resultatPrevu.add("Dawn");
		resultatPrevu.add("Jill");
		String[] filtre2 = {"Friend", ">"};
		
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(2).withFiltre(filtre2).withUnicite(1).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
	void filtreTypeFriend() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Dawn");
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
	
	
	
	@Test
	void testFiltreSimple() {
		String[] str = {"Friend",""};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertTrue(controlRequeteSujet.filtrageDesliens("Carol", "Dawn", lienFiltre, new ArrayList<>()));
	}
	
	@Test
	void testFiltreLienExistePas() {
		String[] str = {"Likes",""};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertFalse(controlRequeteSujet.filtrageDesliens("Carol", "Dawn", lienFiltre, new ArrayList<>()));
	}
	
	@Test
	void testDoubleFiltre() {
		String[] str2 = {"Friend", ""};
		String[] str = {"Likes",""};
		
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		lienFiltre.add(str2);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertTrue(controlRequeteSujet.filtrageDesliens("Carol", "Dawn", lienFiltre, new ArrayList<>()));
	}
	
	@Test
	void testFiltreDirection() {
		String[] str = {"Friend",">"};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertTrue(controlRequeteSujet.filtrageDesliens("Carol", "Dawn", lienFiltre, new ArrayList<>()));
	}
	
	@Test
	void testFiltreDirectionFaux() {
		String[] str = {"Friend","<"};
		ArrayList<String[]> lienFiltre = new ArrayList<>();
		lienFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		assertFalse(controlRequeteSujet.filtrageDesliens("Carol", "Dawn", lienFiltre, new ArrayList<>()));
	}

	@Test
	void requeteExemple21() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Barbara");
		resultatPrevu.add("Anna");
		resultatPrevu.add("Carol");
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Elizabeth");
		resultatPrevu.add("Jill");

		String[] filtre1 = {"Friend", ""};

		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withFiltre(filtre1).withUnicite(1).withProfondeurMode().BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}

	@Test
	void requeteExemple22() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Barbara");
		
		String[] filtre1 = {"Friend", ""};

		Requete requete = new ConstructeurRequete().withNoeudDepart("Carol").withFiltre(filtre1).withNiveau(1).withUnicite(1).withProfondeurMode().BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}


	
	@Test
	void testProprieteSimple() {
		String[] str = {"Friend",""};
		ArrayList<String[]> proprieteFiltre = new ArrayList<>();
		proprieteFiltre.add(str);
		Requete requete = new ConstructeurRequete().withNoeudDepart("Barbara").withNiveau(1).withFiltre(str).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irPropriete.importReseau(), requete);
		assertTrue(controlRequeteSujet.filtrageDesliens("Barbara", "Carol", new ArrayList<>(), proprieteFiltre));
	}
}
