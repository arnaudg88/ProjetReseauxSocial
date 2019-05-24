package fr.ul.miage.reseauxsocial.control;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import fr.ul.miage.reseauxsocial.model.ConstructeurLien;
import fr.ul.miage.reseauxsocial.model.ExportReseau;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Paire;
import fr.ul.miage.reseauxsocial.model.Propriete;
import fr.ul.miage.reseauxsocial.model.Reseaux;
import fr.ul.miage.reseauxsocial.model.lien.Author;
import fr.ul.miage.reseauxsocial.model.lien.Category;
import fr.ul.miage.reseauxsocial.model.lien.EmployeeOf;
import fr.ul.miage.reseauxsocial.model.lien.Friend;
import fr.ul.miage.reseauxsocial.model.lien.Likes;
import fr.ul.miage.reseauxsocial.model.propriete.Hired;
import fr.ul.miage.reseauxsocial.model.propriete.Role;
import fr.ul.miage.reseauxsocial.model.propriete.Share;
import fr.ul.miage.reseauxsocial.model.propriete.Since;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VueController {

	@FXML
	private ListView<String> listview;
	
	@FXML
	private ChoiceBox<String> choiceLien;
	
	@FXML
	private ChoiceBox<String> choiceMode;
	
	@FXML
	private ChoiceBox<String> choiceUnicite;
	
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
	
	@FXML
	private TextField sinceInput;
	
	@FXML
	private TextField shareInput;
	
	@FXML
	private TextField suppInput;
	
	@FXML
	private TextField noeudSource1;
	
	@FXML
	private TextField filter;
	
	@FXML 
	private BorderPane window;
	
	@FXML 
	private Spinner<Integer> spinner;
	
	Reseaux reseaux;
	
	String filepath;
	
	@FXML
    public void initialize() {
		this.filepath = "";
		listview.getItems().add("Reseau :");
		ImportReseau ir = new ImportReseau("(Thomas --Friend[Since=2000]--> Charles)\n(Thomas <--Friend[Since=2000]--> Arnaud)\n");
		this.reseaux = ir.importReseau();
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
		this.choiceLien.setItems(FXCollections.observableArrayList("EmployeeOf", "Friend","Author","Category","Likes"));
		this.choiceLien.getSelectionModel().selectFirst();
		SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
 
        spinner.setValueFactory(valueFactory);
        this.choiceMode.setItems(FXCollections.observableArrayList("Profondeur", "Largeur"));
        this.choiceMode.getSelectionModel().selectFirst();
        this.choiceUnicite.setItems(FXCollections.observableArrayList("Noeud Global", "Lien Global"));
        this.choiceUnicite.getSelectionModel().selectFirst();
		
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
		ArrayList<Propriete> myProps = new ArrayList<Propriete>();
		Boolean error = false;
		String choice = this.choiceLien.getValue();
		switch(choice) {
		case "EmployeeOf" : 
			if(this.hiredBool.isSelected()) {
				if(this.hiredInput.getValue() == null) {
					error = true;
				}else {
					Date date = Date.from(this.hiredInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					Hired h = new Hired("Hired",date);
					myProps.add(h);
				}
			}
			if(this.roleBool.isSelected()) {
				String role = this.roleInput.getText();
				if(role == null) {
					error = true;
				}else {
					Role r = new Role("Role",role);
					myProps.add(r);
				}
			}
			if(!(this.noeudSource.getText() == null) && !(this.noeudDestination.getText() == null) && error == false) {
				Propriete[] myPropsArray = new Propriete[myProps.size()];
				myPropsArray = myProps.toArray(myPropsArray);
				EmployeeOf e = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).withPropriete(myPropsArray).BuildEmployee();
				this.reseaux.addLien(e);
				listview.getItems().add("Lien ajouté !");
			}else {
				listview.getItems().add("Vérifiez vos paramètres !");
			}
		break;
		case "Friend" :
			if(this.sinceBool.isSelected()) {
				int since = Integer.parseInt(this.sinceInput.getText());
				Since r = new Since("Since",since);
				myProps.add(r);
			}
			if(this.shareBool.isSelected()) {
				String share = this.shareInput.getText();
				if(share == null) {
					error = false;
				}else {
					Share s = new Share("Share",Arrays.asList(share.split(";")));
					myProps.add(s);
				}
			}
			if(!(this.noeudSource.getText() == null) && !(this.noeudDestination.getText() == null) && error == false) {
				Propriete[] myPropsArray = new Propriete[myProps.size()];
				myPropsArray = myProps.toArray(myPropsArray);
				Friend e = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).withPropriete(myPropsArray).BuildFriend();
				this.reseaux.addLien(e);
				listview.getItems().add("Lien ajouté !");
			}else {
				listview.getItems().add("Vérifiez vos paramètres !");
			}
		break;
		case "Author" :
			if(!(this.noeudSource.getText() == null) && !(this.noeudDestination.getText() == null)) {
				Author a = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).BuildAuthor();
				this.reseaux.addLien(a);
				listview.getItems().add("Lien ajouté !");
			}else {
				listview.getItems().add("Vérifiez vos paramètres !");
			}
		break;
		
		case "Likes" :
			if(!(this.noeudSource.getText() == null) && !(this.noeudDestination.getText() == null)) {
				Likes a = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).BuildLikes();
				this.reseaux.addLien(a);
				listview.getItems().add("Lien ajouté !");
			}else {
				listview.getItems().add("Vérifiez vos paramètres !");
			}
		break;
		case "Category" :
			if(!(this.noeudSource.getText() == null) && !(this.noeudDestination.getText() == null)) {
				Category c = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).BuildCategory();
				this.reseaux.addLien(c);
				listview.getItems().add("Lien ajouté !");
			}else {
				listview.getItems().add("Vérifiez vos paramètres !");
			}
		break;
		}
	}
	
	public void supprimerNoeud() {
		if(this.suppInput.getText() != null) {
			this.reseaux.supprimerNoeud(this.suppInput.getText());
			listview.getItems().add("Noeud supprimé !");
			this.afficherReseau();
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void afficherReseau() {
		listview.getItems().add("Reseau :");
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
	}
	
	public void searchFile(ActionEvent e) {
		Stage stage = (Stage) window.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".txt", "*.txt")
         );
		fileChooser.setTitle("Selectionnez votre fichier");
		try {
			this.filepath = fileChooser.showOpenDialog(stage).getAbsolutePath();
		}catch(NullPointerException e1) {
			listview.getItems().add("Erreur lors de la sélection du fichier");
		}
	}
	
	public void importReseau() {
		if(this.filepath != "") {
			ImportReseau ir = new ImportReseau();
			ir.importFile(this.filepath);
			this.reseaux = ir.importReseau();
			listview.getItems().add("Importation terminée !");
			this.afficherReseau();
		}else {
			listview.getItems().add("Aucun fichier selectionné");
		}
	}
	
	public void exportReseau() {
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add("Exportation terminée !");
	}
	
	public void lancerRequete() {
		boolean error = false;
		String source = this.noeudSource1.getText();
		int mode = 0;
		if(this.choiceMode.getValue() == "Largeur") {
			mode = 1;
		}
		int niveau = this.spinner.getValue();
		int unicite = 0;
		if(this.choiceMode.getValue() == "Lien Global") {
			unicite = 1;
		}
		String filtres = this.filter.getText();
	}
}
