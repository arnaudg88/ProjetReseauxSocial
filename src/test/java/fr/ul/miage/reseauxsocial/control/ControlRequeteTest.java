package fr.ul.miage.reseauxsocial.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	
	@Test
	void requeteExemple1() {
		ArrayList<String> resultatPrevu = new ArrayList<>();
		resultatPrevu.add("Barbara");
		resultatPrevu.add("Anna");
		resultatPrevu.add("Carol");
		resultatPrevu.add("Dawn");
		resultatPrevu.add("Elizabeth");
		resultatPrevu.add("Jill");
		
		Requete requete = new ConstructeurRequete().withNoeudDepart("NoSQLDistilled").withNiveau(4).BuildRequete();
		ControlRequete controlRequeteSujet = new ControlRequete(irSujet.importReseau(), requete);
		ArrayList<String> actual = controlRequeteSujet.executeRequete();
		assertEquals(resultatPrevu, actual);
	}
	
	@Test
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
}
