package fr.ul.miage.reseauxsocial.control;

import fr.ul.miage.reseauxsocial.model.ExportReseau;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Reseaux;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class VueController {

	@FXML
	private ListView<String> listview;
	
	Reseaux reseaux;
	
	@FXML
    public void initialize() {
		listview.getItems().add("Affichage du reseau :");
		ImportReseau ir = new ImportReseau("(Thomas --Friend[Since=2000]--> Charles)\n(Thomas <--Friend[Since=2000]--> Arnaud)\n");
		this.reseaux = ir.importReseau();
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
	}
	
	public void afficherReseau() {
		
	}
	
	public void importReseau() {
		
	}
	
	public void exportReseau() {
		
	}
}
