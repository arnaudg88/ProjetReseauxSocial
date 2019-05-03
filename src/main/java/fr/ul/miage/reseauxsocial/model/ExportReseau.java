package fr.ul.miage.reseauxsocial.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExportReseau {
	
	Reseaux reseaux;
	
	public ExportReseau(Reseaux reseaux) {
		this.reseaux = reseaux;
		this.saveInFile("test");
	}

	public String exportReseau() {
		//String exemple = "(Barbara --friend[since=1999]--> Carol)";
		HashMap<Paire, ArrayList<Lien>> valReseaux = this.reseaux.getReseau();
		String tmp = "";
		String sens = "";
		String prop = "";
		for(Map.Entry<Paire, ArrayList<Lien>> entry : valReseaux.entrySet()) {
			Paire key = entry.getKey();
			ArrayList<Lien> value = entry.getValue();
			for (Lien lien : value) {
				sens = "";
				if(lien.isDoubleSens()) {
					sens = "<";
				}
				String separator = "";
				prop = "[";
				ArrayList<Propriete> listeProps = lien.getProprietes(); 
				if(listeProps != null && !listeProps.isEmpty()) {
					for (Propriete propriete : lien.getProprietes()) {
						prop += separator + propriete.toString();
						separator = ",";
					}
				}
				prop += "]";
				tmp = tmp + "(" + lien.getNoeudDepart() + " " + sens + "--" + lien.getClass().getSimpleName() + prop + "--" + ">" + " " + lien.getNoeudDestination() + ")" + "\n";
			}
		}
		return tmp;
	}
	
	public void saveInFile(String filename) {
		    String str = this.exportReseau();
		    BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(str);
			    writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}    
	}
}
