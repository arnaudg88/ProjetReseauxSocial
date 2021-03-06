package fr.ul.miage.reseauxsocial.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import fr.ul.miage.reseauxsocial.model.propriete.Since;

class ExportTest {
	
	Reseaux reseaux;
	HashMap<Paire, ArrayList<Lien>> valReseaux;
	ArrayList<Lien> listeLien;
	
	@BeforeEach
	void initialisation() {
		this.reseaux = new Reseaux();
		this.valReseaux = new HashMap<>();
		this.listeLien = new ArrayList<Lien>();
	}

	@Test
	void reseauSansProprieteSimpleUnidirectionnel() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").buildFriend();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas --Friend[]--> Charles)\n";
		assertEquals(expected, test);
	}
	
	@Test
	void reseauAvecProprieteSimpleUnidirectionnel() {
		Date date = new Date();
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").withPropriete(new Since("Ami",2000)).buildFriend();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas --Friend[Since=2000]--> Charles)\n";
		assertEquals(expected, test);
	}
	
	@Test
	void reseauAvecMultipleProprieteSimple() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse(dateFormat.format(new Date()));
		String strDate = dateFormat.format(date);  
		Paire paire = new Paire("Thomas","Charles");
		EmployeeOf ami = new ConstructeurLien().withParam("Thomas",false,"Charles").withPropriete((new Hired("Oui",date)),(new Role("Oui","Dev"))).buildEmployee();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas --EmployeeOf[Hired=" + strDate + ",Role=Dev]--> Charles)\n";
		assertEquals(expected, test);
	}
	
	@Test
	void reseauSansProprieteSimpleBidirectionnel() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",true,"Charles").buildFriend();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas <--Friend[]--> Charles)\n";
		assertEquals(expected, test);
	}
	
	@Test
	void reseauAvecProprieteSimpleBidirectionnel() {
		Date date = new Date();
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",true,"Charles").withPropriete(new Since("Ami",2000)).buildFriend();
		this.listeLien.add(ami);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas <--Friend[Since=2000]--> Charles)\n";
		assertEquals(expected, test);
	}

	@Test
	void reseauSansProprieteMultiple() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").buildFriend();
		Friend ami2 = new ConstructeurLien().withParam("Thomas",false,"Arnaud").buildFriend();
		this.listeLien.add(ami);
		this.listeLien.add(ami2);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas --Friend[]--> Charles)\n(Thomas --Friend[]--> Arnaud)\n";
		assertEquals(expected, test);
	}
	
	@Test
	void reseauAvecProprieteMultiple() {
		Paire paire = new Paire("Thomas","Charles");
		Friend ami = new ConstructeurLien().withParam("Thomas",false,"Charles").withPropriete(new Since("Ami",2000)).buildFriend();
		Friend ami2 = new ConstructeurLien().withParam("Thomas",true,"Arnaud").withPropriete(new Since("Ami",2000)).buildFriend();
		this.listeLien.add(ami);
		this.listeLien.add(ami2);
		this.valReseaux.put(paire, this.listeLien);
		this.reseaux.setReseau(this.valReseaux);
		ExportReseau export = new ExportReseau(this.reseaux);
		String test = export.exportReseau();
		String expected = "(Thomas --Friend[Since=2000]--> Charles)\n(Thomas <--Friend[Since=2000]--> Arnaud)\n";
		assertEquals(expected, test);
	}
}
