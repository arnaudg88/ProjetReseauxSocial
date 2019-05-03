package fr.ul.miage.reseauxsocial.model;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import fr.ul.miage.reseauxsocial.model.propriete.Share;
import fr.ul.miage.reseauxsocial.model.propriete.Since;

class LienTest {

	@Test
	void testContructeurLienEmployee() {
		Date date = new Date();
		EmployeeOf employeur = (new ConstructeurLien().withParam("Charles",false,"CHRU").withPropriete((new Hired("Oui",date)),(new Role("Oui","Dev")))).BuildEmployee();
		EmployeeOf lien1 = new EmployeeOf("Charles",false,"CHRU");
		Hired h1 = new Hired("Oui",date);
		Role r1 = new Role("Oui","Dev");
		lien1.addPropriete(h1);
		lien1.addPropriete(r1);
		
		System.out.println(employeur.getNoeudDestination());
		System.out.println(employeur.toString());
		System.out.println(lien1.toString());
		assertEquals(lien1, employeur);
	}
	
	@Test
	void testContructeurLienFriend() {
		List<String> liste = Arrays.asList("Sport");
		
		Friend ami = (new ConstructeurLien().withParam("Charles",false,"Arnaud").withPropriete(new Share("Oui",liste),new Since("Oui",1))).BuildFriend();
		Friend lien1 = new Friend("Charles",false,"Arnaud");
		Share h1 = new Share("Oui",liste);
		Since r1 = new Since("Oui",1);
		lien1.addPropriete(h1);
		lien1.addPropriete(r1);
		
		assertEquals(lien1, ami);
	}

}
