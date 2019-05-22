package fr.ul.miage.reseauxsocial.control;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import fr.ul.miage.reseauxsocial.model.ConstructeurLien;
import fr.ul.miage.reseauxsocial.model.ExportReseau;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Paire;
import fr.ul.miage.reseauxsocial.model.Propriete;
import fr.ul.miage.reseauxsocial.model.Reseaux;
import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class VueController {

	@FXML
	private ListView<String> listview;
	
	@FXML
	private ChoiceBox<String> choiceLien;
	
	@FXML
	private CheckBox sinceBool;
	
	@FXML
	private CheckBox shareBool;
	
	@FXML
	private CheckBox hiredBool;
	
	@FXML
	private CheckBox roleBool;
	
	@FXML
	private CheckBox doubleSensBool;
	
	@FXML
	private DatePicker hiredInput;
	
	@FXML
	private TextField noeudSource;
	
	@FXML
	private TextField noeudDestination;
	
	@FXML
	private TextField roleInput;
	
	Reseaux reseaux;
	
	@FXML
    public void initialize() {
		listview.getItems().add("Affichage du reseau :");
		ImportReseau ir = new ImportReseau("(Thomas --Friend[Since=2000]--> Charles)\n(Thomas <--Friend[Since=2000]--> Arnaud)\n");
		this.reseaux = ir.importReseau();
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
		this.choiceLien.setItems(FXCollections.observableArrayList("EmployeeOf", "Friend","Author","Category","Likes"));
		this.choiceLien.getSelectionModel().selectFirst();
		this.newRelationChoice();
	}
	
	public void newRelationChoice() {
		if(this.choiceLien.getValue() == "EmployeeOf") {
			this.shareBool.setDisable(true);
			this.sinceBool.setDisable(true);
			this.hiredBool.setDisable(false);
			this.roleBool.setDisable(false);
		}else if(this.choiceLien.getValue() == "Friend") {
			this.shareBool.setDisable(false);
			this.sinceBool.setDisable(false);
			this.hiredBool.setDisable(true);
			this.roleBool.setDisable(true);
		}else {
			this.hiredBool.setDisable(true);
			this.roleBool.setDisable(true);
			this.shareBool.setDisable(true);
			this.sinceBool.setDisable(true);
		}
	}
	
	public void ajouterLien() {
		Boolean error = false;
		String choice = this.choiceLien.getValue();
		switch(choice) {
		case "EmployeeOf" : 
			ArrayList<Propriete> myProps = new ArrayList<Propriete>();
			if(this.hiredBool.isSelected()) {
				Date date = Date.from(this.hiredInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Hired h = new Hired("Hired",date);
				myProps.add(h);
			}
			if(this.roleBool.isSelected()) {
				String role = this.roleInput.getText();
				if(role == "") {
					error = true;
				}else {
					Role r = new Role("Role",role);
					myProps.add(r);
				}
			}
			if(this.noeudSource.getText() != "" && this.noeudDestination.getText() != "" && error == false) {
				Propriete[] myPropsArray = new Propriete[myProps.size()];
				myPropsArray = myProps.toArray(myPropsArray);
				Paire p = new Paire(this.noeudSource.getText(),this.noeudDestination.getText());
				EmployeeOf e = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).BuildEmployee();
			}	
		break;
		case "Friend" :
			
		break;
		case "Author" :
			
		break;
		
		case "Likes" :
			
		break;
		case "Category" :
			
		break;
		}
	}
	
	public void afficherReseau() {
		
	}
	
	public void importReseau() {
		
	}
	
	public void exportReseau() {
		
	}
}
