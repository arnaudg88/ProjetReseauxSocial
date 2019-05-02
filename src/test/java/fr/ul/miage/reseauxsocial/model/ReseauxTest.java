package fr.ul.miage.reseauxsocial.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.Lien;
import fr.ul.miage.reseauxsocial.model.Noeud;
import fr.ul.miage.reseauxsocial.model.Paire;
import fr.ul.miage.reseauxsocial.model.Reseaux;
import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import javafx.util.Pair;

class ReseauxTest {

	Reseaux reseaux0;
	Reseaux reseaux1;
	
	@BeforeEach
	void avantChaque() {
		HashMap<Paire, ArrayList<Lien>> valReseaux0 = new HashMap<>();
		ArrayList<Lien> listelien = new ArrayList<Lien>();
		listelien.add(new Friend("barbara", false, "clara"));
		valReseaux0.put(new Paire("barbara", "clara"), listelien);
		reseaux0 = new Reseaux();
		reseaux0.setReseau(valReseaux0);
		HashMap<String, Noeud> noeuds0 = new HashMap<String, Noeud>();
		noeuds0.put("barbara", new Noeud("barbara"));
		noeuds0.put("clara", new Noeud("clara"));
		reseaux0.setNoeuds(noeuds0);
		
		HashMap<Paire, ArrayList<Lien>> valReseaux1 = new HashMap<>();
		ArrayList<Lien> listeLien1 = new ArrayList<Lien>();
		ArrayList<Lien> listeLien2 = new ArrayList<Lien>();
		listeLien1.add(new Friend("barbara", false, "clara"));
		listeLien2.add(new EmployeeOf("barbara", false, "google"));
		valReseaux1.put(new Paire("barbara", "clara"), listeLien1);
		valReseaux1.put(new Paire("barbara", "google"), listeLien2);
		reseaux1 = new Reseaux();
		reseaux1.setReseau(valReseaux1);
		HashMap<String, Noeud> noeuds1 = new HashMap<>();
		noeuds1.put("barbara", new Noeud("barbara"));
		noeuds1.put("clara", new Noeud("clara"));
		noeuds1.put("google", new Noeud("google"));
		reseaux1.setNoeuds(noeuds1);
	}
	
	@Test
	void addLien1Fois() {
		Reseaux reseauxTmp = new Reseaux();
		reseauxTmp.addLien(new Friend("barbara", false, "clara"));
		assertEquals(reseaux0, reseauxTmp);
	}
	
	@Test
	void addLien2Fois() {
		Reseaux reseauxTmp = new Reseaux();
		reseauxTmp.addLien(new Friend("barbara", false, "clara"));
		reseauxTmp.addLien(new EmployeeOf("barbara", false, "google"));
		assertEquals(reseaux1, reseauxTmp);
	}
	
	@Test
	void paireExistVrai() {
		assertTrue(reseaux0.paireExist("barbara", "clara"));
	}
	
	@Test
	void paireExistInverseFaux() {
		assertFalse(reseaux0.paireExist("clara", "barbara"));
	}
	
	@Test
	void paireExistFaux() {
		assertFalse(reseaux0.paireExist("noeudnull", "noeudnullaussi"));
	}
	
	@Test
	void noeudExistVrai() {
		assertTrue(reseaux0.noeudExist("barbara"));
	}
	
	@Test
	void noeudExistFaux() {
		assertFalse(reseaux0.noeudExist("noeudnull"));
	}
	
	@Test
	void distEntreDeuxMemeNoeud() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Roger"));
		assertEquals(0, reseaux.getDistEntreDeuxNoeuds("Sara", "Sara"));
	}
	
	@Test
	void distEntreDeuxNoeudExistePas() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Roger"));
		assertEquals(-1, reseaux.getDistEntreDeuxNoeuds("Google", "Apple"));
	}
	
	@Test
	void distEntreDeuxNoeudFils() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Roger"));
		assertEquals(1, reseaux.getDistEntreDeuxNoeuds("Sara", "Roger"));
	}
	
	@Test
	void distEntreDeuxNoeud2() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Sara", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		assertEquals(2, reseaux.getDistEntreDeuxNoeuds("Sara", "Patrice"));
	}
	
	@Test
	void distEntreDeuxNoeud3() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Flo"));
		reseaux.addLien(new Friend("Flo", false, "Patrice"));
		reseaux.addLien(new Friend("Sara", false, "Patrice"));
		assertEquals(1, reseaux.getDistEntreDeuxNoeuds("Sara", "Patrice"));
	}
	
	@Test
	void getFils() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", false, "Roger"));
		reseaux.addLien(new Friend("Sara", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		ArrayList<Noeud> expect = new ArrayList<>();
		expect.add(new Noeud("Roger"));
		expect.add(new Noeud("Nicole"));
		assertEquals(expect.size(), reseaux.getFils("Sara").size());
	}
	
	@Test
	void getFilsDoubleSens() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sara", true, "Roger"));
		reseaux.addLien(new Friend("Sara", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		ArrayList<Noeud> expect = new ArrayList<>();
		expect.add(new Noeud("Sara"));
		expect.add(new Noeud("Patrice"));
		assertEquals(expect.size(), reseaux.getFils("Roger").size());
	}
	
	
	@Test
	void supprimerNoeudSimpleLienSimple() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sarah", false, "Roger"));
		reseaux.addLien(new Friend("Sarah", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		HashMap<String, Noeud> noeudsTemp = new HashMap<String, Noeud>();
		noeudsTemp.put("Sarah", new Noeud("Sarah"));
		noeudsTemp.put("Roger", new Noeud("Roger"));
		noeudsTemp.put("Nicole",new Noeud("Nicole"));
		noeudsTemp.put("Patrice", new Noeud("Patrice"));
		reseaux0.setNoeuds(noeudsTemp);
		
		reseaux.supprimerNoeud("Patrice");
		
		assertTrue(reseaux.getNoeuds().containsKey("Sarah"));
		assertTrue(reseaux.getNoeuds().containsKey("Nicole"));
		assertTrue(reseaux.getNoeuds().containsKey("Roger"));
		assertFalse(reseaux.getNoeuds().containsKey("Patrice"));
		
	}
	
	@Test
	void supprimerNoeudSimpleLienDouble() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sarah", false, "Roger"));
		reseaux.addLien(new Friend("Sarah", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", true, "Patrice"));
		HashMap<String, Noeud> noeudsTemp = new HashMap<String, Noeud>();
		noeudsTemp.put("Sarah", new Noeud("Sarah"));
		noeudsTemp.put("Roger", new Noeud("Roger"));
		noeudsTemp.put("Nicole",new Noeud("Nicole"));
		noeudsTemp.put("Patrice", new Noeud("Patrice"));
		reseaux0.setNoeuds(noeudsTemp);
		
		reseaux.supprimerNoeud("Patrice");
		
		assertTrue(reseaux.getNoeuds().containsKey("Sarah"));
		assertTrue(reseaux.getNoeuds().containsKey("Nicole"));
		assertTrue(reseaux.getNoeuds().containsKey("Roger"));
		assertFalse(reseaux.getNoeuds().containsKey("Patrice"));
		
	}
	
	@Test
	void supprimerNoeudRepercussion() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sarah", false, "Roger"));
		reseaux.addLien(new Friend("Sarah", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		HashMap<String, Noeud> noeudsTemp = new HashMap<String, Noeud>();
		noeudsTemp.put("Sarah", new Noeud("Sarah"));
		noeudsTemp.put("Roger", new Noeud("Roger"));
		noeudsTemp.put("Nicole",new Noeud("Nicole"));
		noeudsTemp.put("Patrice", new Noeud("Patrice"));
		reseaux0.setNoeuds(noeudsTemp);
		
		reseaux.supprimerNoeud("Roger");
		
		
		assertTrue(reseaux.getNoeuds().containsKey("Sarah"));
		assertTrue(reseaux.getNoeuds().containsKey("Nicole"));
		assertFalse(reseaux.getNoeuds().containsKey("Roger"));
		assertFalse(reseaux.getNoeuds().containsKey("Patrice"));
		
	}
	
	@Test
	void supprimerNoeudAll() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sarah", false, "Roger"));
		reseaux.addLien(new Friend("Sarah", true, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		HashMap<String, Noeud> noeudsTemp = new HashMap<String, Noeud>();
		noeudsTemp.put("Sarah", new Noeud("Sarah"));
		noeudsTemp.put("Roger", new Noeud("Roger"));
		noeudsTemp.put("Nicole",new Noeud("Nicole"));
		noeudsTemp.put("Patrice", new Noeud("Patrice"));
		reseaux0.setNoeuds(noeudsTemp);
		
		reseaux.supprimerNoeud("Nicole");

		reseaux.supprimerNoeud("Roger");
		
		assertFalse(reseaux.getNoeuds().containsKey("Sarah"));
		assertFalse(reseaux.getNoeuds().containsKey("Nicole"));
		assertFalse(reseaux.getNoeuds().containsKey("Roger"));
		assertFalse(reseaux.getNoeuds().containsKey("Patrice"));
		
	}
	
	
	@Test
	void supprimerNoeudCreationSousReseau() {
		Reseaux reseaux = new Reseaux();
		reseaux.addLien(new Friend("Sarah", false, "Roger"));
		reseaux.addLien(new Friend("Sarah", false, "Nicole"));
		reseaux.addLien(new Friend("Nicole", false, "Roger"));
		reseaux.addLien(new Friend("Roger", false, "Patrice"));
		reseaux.addLien(new Friend("Ana", false, "Patrice"));
		HashMap<String, Noeud> noeudsTemp = new HashMap<String, Noeud>();
		noeudsTemp.put("Sarah", new Noeud("Sarah"));
		noeudsTemp.put("Roger", new Noeud("Roger"));
		noeudsTemp.put("Nicole",new Noeud("Nicole"));
		noeudsTemp.put("Patrice", new Noeud("Patrice"));
		noeudsTemp.put("Ana", new Noeud("Ana"));
		reseaux0.setNoeuds(noeudsTemp);
		
		reseaux.supprimerNoeud("Roger");
		
		assertTrue(reseaux.getNoeuds().containsKey("Sarah"));
		assertTrue(reseaux.getNoeuds().containsKey("Nicole"));
		assertFalse(reseaux.getNoeuds().containsKey("Roger"));
		assertTrue(reseaux.getNoeuds().containsKey("Patrice"));
		assertTrue(reseaux.getNoeuds().containsKey("Ana"));
		
	}
}
