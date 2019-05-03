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
}
