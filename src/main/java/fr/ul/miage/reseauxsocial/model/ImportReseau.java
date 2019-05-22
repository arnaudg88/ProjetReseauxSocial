package fr.ul.miage.reseauxsocial.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import fr.ul.miage.reseauxsocial.model.propriete.Since;

public class ImportReseau {

	public String reseau;

	public ImportReseau(String reseau) {
		this.reseau = reseau;
	}

	public Reseaux importReseau() {
		String[] relations = this.reseau.split("\n");
		Reseaux reseaux = new Reseaux();
		HashMap<Paire, ArrayList<Lien>> valReseaux = new HashMap<>();
		for (int i = 0; i < relations.length; i++) {
			relations[i] = relations[i].trim();
			if (relations[i] != "") {
				// INIT
				ArrayList<Lien> listeLien = new ArrayList<Lien>();
				Boolean direction = false;

				String firstNoeud = relations[i].substring(relations[i].indexOf("(") + 1, relations[i].indexOf(" "));
				// System.out.println(firstNoeud);
				relations[i] = relations[i].substring(relations[i].indexOf(" ") + 1, relations[i].indexOf(")") + 1);
				// System.out.println("New string : " + relations[i]);
				if (relations[i].charAt(0) == '<') {
					direction = true;
					System.out.println("direction true");
				}
				relations[i] = relations[i].replace("<", "");
				relations[i] = relations[i].replace(">", "");
				relations[i] = relations[i].replace("-", "");
				// System.out.println("New string : " + relations[i]);
				String secondNoeud = relations[i].substring(relations[i].indexOf(" ") + 1, relations[i].indexOf(")"));
				// System.out.println(secondNoeud);
				relations[i] = relations[i].substring(0, relations[i].indexOf(" "));
				// System.out.println("New string : " + relations[i]);
				String lien = relations[i].substring(0, relations[i].indexOf("["));
				relations[i] = relations[i].substring(relations[i].indexOf("[") + 1, relations[i].indexOf("]"));
				// System.out.println(lien);
				// System.out.println("New string : " + relations[i]);
				String[] properties = relations[i].split(",");

				ArrayList<Propriete> myProps = new ArrayList<Propriete>();

				for (int y = 0; y < properties.length; y++) {
					String propertie = properties[y].substring(0, relations[i].indexOf("="));
					String value = properties[y].substring(relations[i].indexOf("=") + 1, relations[i].length());
					switch (propertie) {
					case "Hired":
						Hired h = new Hired("Hired", new Date(value));
						myProps.add(h);
						break;
					case "Role":
						Role r = new Role("Role", value);
						myProps.add(r);
						break;
					case "Share":
						// Share sh = new Share("Share",value);
						break;
					case "Since":
						Since si = new Since("Since", Integer.parseInt(value));
						myProps.add(si);
						break;
					}
				}

				Propriete[] myPropsArray = new Propriete[myProps.size()];
				myPropsArray = myProps.toArray(myPropsArray);

				Paire p = new Paire(firstNoeud, secondNoeud);

				switch (lien) {
				case "Friend":
					Friend f = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud)
							.withPropriete(myPropsArray).BuildFriend();
					if (valReseaux.containsKey(p)) {
						listeLien = valReseaux.get(p);
						listeLien.add(f);
						valReseaux.put(p, listeLien);
					} else {
						listeLien.add(f);
						valReseaux.put(p, listeLien);
					}
					break;
				case "EmployeeOf":
					EmployeeOf e = new ConstructeurLien().withParam(firstNoeud, direction, secondNoeud).BuildEmployee();
					if (valReseaux.containsKey(p)) {
						listeLien = valReseaux.get(p);
						listeLien.add(e);
						valReseaux.put(p, listeLien);
					} else {
						listeLien.add(e);
						valReseaux.put(p, listeLien);
					}
					break;
				default:
					// code block
				}
			}
		}
		reseaux.setReseau(valReseaux);
		return reseaux;
	}

	public String importFile(String filename) {
		String dataReseau = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

			String line = null;
			while ((line = br.readLine()) != null) {
				dataReseau += line;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataReseau;
	}
}