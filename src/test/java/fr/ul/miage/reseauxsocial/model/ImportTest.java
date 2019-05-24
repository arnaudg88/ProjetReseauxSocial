package fr.ul.miage.reseauxsocial.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.propriete.Since;

class ImportTest {
	
	Reseaux reseaux;
	HashMap<Paire, ArrayList<Lien>> valReseaux;
	HashMap<String,Noeud> listeNoeuds;
	ArrayList<Lien> listeLien;

	@BeforeEach
	void initialisation() {
		this.reseaux = new Reseaux();
		this.valReseaux = new HashMap<>();
		this.listeLien = new ArrayList<Lien>();
		this.listeNoeuds = new HashMap<String,Noeud>();
	}
	
	@Test
	void importationSimple() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").withPropriete(new Since("Ami",2000)).BuildFriend();
		this.listeLien.add(ami);
		
		this.listeNoeuds.put("Thomas", new Noeud("Thomas"));
		this.listeNoeuds.put("Charles", new Noeud("Charles"));
		this.reseaux.setNoeuds(listeNoeuds);
		
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		
		ImportReseau importRes = new ImportReseau("(Thomas --Friend[Since=2000]--> Charles)");
		Reseaux resTemp = importRes.importReseau();
		
		assertEquals(this.reseaux, resTemp);
	}
	
	@Test
	void importationDouble() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").withPropriete(new Since("Ami",2000)).BuildFriend();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		
		Paire paire2 = new Paire("Thomas","Arnaud");
		Friend ami2 = new ConstructeurLien().withParam("Thomas",true,"Arnaud").withPropriete(new Since("Ami",2000)).BuildFriend();
		this.listeLien = new ArrayList<Lien>();
		this.listeLien.add(ami2);
		this.valReseaux.put(paire2, this.listeLien);
		Paire paire3 = new Paire("Arnaud","Thomas");
		Friend ami3 = new ConstructeurLien().withParam("Arnaud",true,"Thomas").withPropriete(new Since("Ami",2000)).BuildFriend();
		this.listeLien = new ArrayList<Lien>();
		this.listeLien.add(ami3);
		this.valReseaux.put(paire3, this.listeLien);
		this.listeNoeuds.put("Thomas", new Noeud("Thomas"));
		this.listeNoeuds.put("Charles", new Noeud("Charles"));
		this.listeNoeuds.put("Arnaud", new Noeud("Arnaud"));
		this.reseaux.setNoeuds(listeNoeuds);
		this.reseaux.setReseau(this.valReseaux);
		
		ImportReseau importRes = new ImportReseau("(Thomas --Friend[Since=2000]--> Charles)\n(Thomas <--Friend[Since=2000]--> Arnaud)\n");
		Reseaux resTemp = importRes.importReseau();
		
		assertEquals(this.reseaux, resTemp);
	}

}