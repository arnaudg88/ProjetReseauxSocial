package fr.ul.miage.reseauxsocial.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ExportReseau {
	
	private static final Logger LOG = Logger.getLogger(ExportReseau.class.getName());
	Reseaux reseaux;
	
	public ExportReseau(Reseaux reseaux) {
		this.reseaux = reseaux;
		this.saveInFile("test");
	}
	
	public ExportReseau(Reseaux reseaux, String filename) {
		this.reseaux = reseaux;
		this.saveInFile(filename);
	}

	public String exportReseau() {
		HashMap<Paire, ArrayList<Lien>> valReseaux = this.reseaux.getReseau();
		String tmp = "";
		String sens = "";
		String prop = "";
		for(Map.Entry<Paire, ArrayList<Lien>> entry : valReseaux.entrySet()) {
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
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			try {
				writer.write(str);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}
	}
}
