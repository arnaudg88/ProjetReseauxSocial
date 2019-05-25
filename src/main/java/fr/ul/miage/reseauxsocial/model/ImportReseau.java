package fr.ul.miage.reseauxsocial.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import fr.ul.miage.reseauxsocial.model.lien.Author;
import fr.ul.miage.reseauxsocial.model.lien.Category;
import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.lien.Likes;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import fr.ul.miage.reseauxsocial.model.propriete.Share;
import fr.ul.miage.reseauxsocial.model.propriete.Since;

public class ImportReseau {
	
	private static final Logger LOG = Logger.getLogger(ImportReseau.class.getName());

	private String reseau;

	public ImportReseau(String reseau) {
		this.reseau = reseau;
	}
	
	public ImportReseau() {
		this.reseau = "";
	}

	public String getReseau() {
		return reseau;
	}

	public void setReseau(String reseau) {
		this.reseau = reseau;
	}

	public Reseaux importReseau() {
		String[] relations = this.reseau.split("\n");
		Reseaux reseaux = new Reseaux();
		for (int i = 0; i < relations.length; i++) {
			relations[i] = relations[i].trim();
			if (!relations[i].equals("")) {
				// INIT
				Boolean direction = false;

				String firstNoeud = relations[i].substring(relations[i].indexOf('(') + 1, relations[i].indexOf(' '));
				relations[i] = relations[i].substring(relations[i].indexOf(' ') + 1, relations[i].indexOf(')') + 1);
				if (relations[i].charAt(0) == '<') {
					direction = true;
				}
				relations[i] = relations[i].replace("<", "");
				relations[i] = relations[i].replace(">", "");
				relations[i] = relations[i].replace("-", "");
	
				String secondNoeud = relations[i].substring(relations[i].indexOf(' ') + 1, relations[i].indexOf(')'));
				
				relations[i] = relations[i].substring(0, relations[i].lastIndexOf(' '));
				
				String lien = relations[i].substring(0, relations[i].indexOf('['));
				relations[i] = relations[i].substring(relations[i].indexOf('[') + 1, relations[i].indexOf(']'));
				
				String[] properties = relations[i].split(",");

				ArrayList<Propriete> myProps = new ArrayList<>();

				for (int y = 0; y < properties.length; y++) {
					String propertie = "";
					if(relations[i].indexOf('=') != -1)
						propertie = properties[y].substring(0, properties[y].indexOf('='));
					String value = properties[y].substring(properties[y].indexOf('=') + 1, properties[y].length());
					switch (propertie) {
						case "Hired":
							SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy"); 
							try {
								Date date1 =formatter.parse(value);
								Hired h = new Hired("Hired", date1);
								myProps.add(h);
							} catch (ParseException e1) {
								LOG.severe(e1.getMessage());
							}  
							break;
						case "Role":
							Role r = new Role("Role", value);
							myProps.add(r);
							break;
						case "Share":
							Share sh = new Share("Share",Arrays.asList(value.split(";")));
							myProps.add(sh);
							break;
						case "Since":
							Since si = new Since("Since", Integer.parseInt(value));
							myProps.add(si);
							break;
						default:
							break;
					}
				}

				Propriete[] myPropsArray = new Propriete[myProps.size()];
				myPropsArray = myProps.toArray(myPropsArray);

				switch (lien) {
				case "Friend":
					Friend friend = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud)
							.withPropriete(myPropsArray).buildFriend();
					reseaux.addLien(friend);
					break;
				case "EmployeeOf":
					EmployeeOf e = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud).withPropriete(myPropsArray).buildEmployee();
					reseaux.addLien(e);
					break;
				case "Author":
					Author author = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud).withPropriete(myPropsArray).buildAuthor();
					reseaux.addLien(author);
					break;
				case "Category":
					Category category = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud).withPropriete(myPropsArray).buildCategory();
					reseaux.addLien(category);
					break;
				case "Likes":
					Likes likes = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud).withPropriete(myPropsArray).buildLikes();
					reseaux.addLien(likes);
					break;
				default:
					// code block
				}
			}
		}
		return reseaux;
	}

	public void importFile(String filename) {
		String dataReseau = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			try {
				String line = null;
				while ((line = br.readLine()) != null) {
					dataReseau += line + "\n";
				}
			} finally {
				br.close();
			}
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}

		reseau = dataReseau;
	}
}