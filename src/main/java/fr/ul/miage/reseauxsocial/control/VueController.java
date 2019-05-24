package fr.ul.miage.reseauxsocial.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fr.ul.miage.reseauxsocial.model.ConstructeurLien;
import fr.ul.miage.reseauxsocial.model.ConstructeurRequete;
import fr.ul.miage.reseauxsocial.model.ExportReseau;
import fr.ul.miage.reseauxsocial.model.ImportReseau;
import fr.ul.miage.reseauxsocial.model.Noeud;
import fr.ul.miage.reseauxsocial.model.Propriete;
import fr.ul.miage.reseauxsocial.model.Requete;
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
import javafx.fxml.FXML;
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
	
	private static final Logger LOG = Logger.getLogger(VueController.class.getName());

	@FXML
	private ListView<String> listview;
	
	@FXML
	private ChoiceBox<String> choiceLien;
	
	@FXML
	private ChoiceBox<String> choiceMode;
	
	@FXML
	private ChoiceBox<String> choiceUnicite;
	
	@FXML
	private ChoiceBox<String> choiceNode;
	
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
	private TextField props;
	
	@FXML 
	private BorderPane window;
	
	@FXML 
	private Spinner<Integer> spinner;
	
	Reseaux reseaux;
	
	String filepath;
	
	ConstructeurRequete cr;
	
	private static String employeeOf = "EmployeeOf";
	private static String friend = "Friend";
	private static String author = "Author";
	private static String category = "Category";
	private static String likes = "Likes";
	private static String lienAjoute = "Lien ajouté !";
	private static String verifieVosParametre = "Vérifiez vos paramètres !";
	
	@FXML
    public void initialize() {
		this.filepath = "";
		listview.getItems().add("Reseau :");
		ImportReseau ir = new ImportReseau();
		ir.importFile("test");
		this.reseaux = ir.importReseau();
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
		this.choiceLien.setItems(FXCollections.observableArrayList(employeeOf, friend,author,category,likes));
		this.choiceLien.getSelectionModel().selectFirst();
		SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        spinner.setValueFactory(valueFactory);
        this.choiceMode.setItems(FXCollections.observableArrayList("Profondeur", "Largeur"));
        this.choiceMode.getSelectionModel().selectFirst();
        this.choiceUnicite.setItems(FXCollections.observableArrayList("Noeud Global", "Lien Global"));
        this.choiceUnicite.getSelectionModel().selectFirst();
		this.selectNoeud();
		this.newRelationChoice();
	}
	
	public void newRelationChoice() {
		if(this.choiceLien.getValue() == employeeOf) {
			this.shareBool.setDisable(true);
			this.sinceBool.setDisable(true);
			this.hiredBool.setDisable(false);
			this.roleBool.setDisable(false);
		}else if(this.choiceLien.getValue() == friend) {
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
		String choice = this.choiceLien.getValue();
		switch(choice) {
		case "EmployeeOf" : 
			this.ajouterLienEmployeeOf();
		break;
		case "Friend" :
			this.ajouterLienFriend();
		break;
		case "Author" :
			this.ajouterLienAuthor();
		break;
		case "Likes" :
			this.ajouterLienLikes();
		break;
		case "Category" :
			this.ajouterLienCategory();
		break;
		default:
			break;
		}
	}
	
	
	
	public void supprimerNoeud() {
		if(!this.suppInput.getText().equals("")) {
			this.reseaux.supprimerNoeud(this.suppInput.getText());
			listview.getItems().add("Noeud supprimé !");
			this.afficherReseau();
			this.selectNoeud();
		}else {
			listview.getItems().add(verifieVosParametre);
		}
	}
	
	public boolean checkNodes(String source, String destination) {
		if(source.equals("") || destination.equals("")){
			return false;
		}
		return true;
	}
	
	public void ajouterLienCategory() {
		if(this.checkNodes(this.noeudSource.getText(), this.noeudDestination.getText())) {
			Category c = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).buildCategory();
			this.reseaux.addLien(c);
			listview.getItems().add("Lien ajouté !");
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void ajouterLienAuthor() {
		if(this.checkNodes(this.noeudSource.getText(), this.noeudDestination.getText())) {
			Author a = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).buildAuthor();
			this.reseaux.addLien(a);
			listview.getItems().add("Lien ajouté !");
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void ajouterLienLikes() {
		if(this.checkNodes(this.noeudSource.getText(), this.noeudDestination.getText())) {
			Likes a = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).buildLikes();
			this.reseaux.addLien(a);
			listview.getItems().add("Lien ajouté !");
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void ajouterLienFriend() {
		ArrayList<Propriete> myProps = new ArrayList<Propriete>();
		Boolean error = false;
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
		
		if(this.checkNodes(this.noeudSource.getText(), this.noeudDestination.getText()) && error == false) {
			Propriete[] myPropsArray = new Propriete[myProps.size()];
			myPropsArray = myProps.toArray(myPropsArray);
			Friend e = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).withPropriete(myPropsArray).buildFriend();
			this.reseaux.addLien(e);
			listview.getItems().add("Lien ajouté !");
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void ajouterLienEmployeeOf() {
		ArrayList<Propriete> myProps = new ArrayList<Propriete>();
		Boolean error = false;
		if(this.hiredBool.isSelected()) {
			if(this.hiredInput.getValue() == null) {
				error = true;
			}else {
				
				try {
					LocalDate localDate = this.hiredInput.getValue();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					String date = localDate.format(formatter);
					SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy"); 
					Date date1 = formatter2.parse(date);
					Hired h = new Hired("Hired",date1);
					myProps.add(h);
				} catch (ParseException e) {
					LOG.severe(e.getMessage());
				}
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
		
		if(this.checkNodes(this.noeudSource.getText(), this.noeudDestination.getText()) && error == false) {
			Propriete[] myPropsArray = new Propriete[myProps.size()];
			myPropsArray = myProps.toArray(myPropsArray);
			EmployeeOf e = new ConstructeurLien().withParam(this.noeudSource.getText(),this.doubleSensBool.isSelected(),this.noeudDestination.getText()).withPropriete(myPropsArray).buildEmployee();
			this.reseaux.addLien(e);
			listview.getItems().add("Lien ajouté !");
		}else {
			listview.getItems().add("Vérifiez vos paramètres !");
		}
	}
	
	public void afficherReseau() {
		listview.getItems().add("Reseau :");
		ExportReseau er = new ExportReseau(this.reseaux);
		listview.getItems().add(er.exportReseau());
	}
	
	public void searchFile() {
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
		if(this.filepath.equals("")) {
			ImportReseau ir = new ImportReseau();
			ir.importFile(this.filepath);
			this.reseaux = ir.importReseau();
			listview.getItems().add("Importation terminée !");
			this.afficherReseau();
			this.selectNoeud();
		}else {
			listview.getItems().add("Aucun fichier selectionné");
		}
	}
	
	public void exportReseau() {
		new ExportReseau(this.reseaux);
		listview.getItems().add("Exportation terminée !");
	}
	
	public void selectNoeud() {
		HashMap<String, Noeud> noeuds = this.reseaux.getNoeuds();
		ArrayList<String> listeNoeuds = new ArrayList<>();
		for(Map.Entry<String, Noeud> entry : noeuds.entrySet()) {
		    listeNoeuds.add(entry.getKey());
		}
		this.choiceNode.setItems(FXCollections.observableArrayList(listeNoeuds));
        this.choiceNode.getSelectionModel().selectFirst();
	}
	
	public void lancerRequete() {
		String source = this.choiceNode.getValue();
		int mode = 0;
		if(this.choiceMode.getValue() == "Largeur") {
			mode = 1;
		}
		int niveau = this.spinner.getValue();
		int unicite = 0;
		if(this.choiceMode.getValue() == "Lien Global") {
			unicite = 1;
		}
		
		if(this.props.getText() != null && this.filter.getText() != null) {
			String properties = this.props.getText().trim();
			String filtres = this.filter.getText().trim();
			this.cr = new ConstructeurRequete().withNoeudDepart(source).withMode(mode).withNiveau(niveau).withUnicite(unicite).withPropriete(Requete.splitProprietes(properties)).withLiens(Requete.splitLiens(filtres));
			
		}else if(this.props.getText() != null) {
			String properties = this.props.getText().trim();
			this.cr = new ConstructeurRequete().withNoeudDepart(source).withMode(mode).withNiveau(niveau).withUnicite(unicite).withPropriete(Requete.splitProprietes(properties));
			
		}else if(this.filter.getText() != null) {
			String filtres = this.filter.getText().trim();
			this.cr = new ConstructeurRequete().withNoeudDepart(source).withMode(mode).withNiveau(niveau).withUnicite(unicite).withLiens(Requete.splitLiens(filtres));
		}else {
			this.cr = new ConstructeurRequete().withNoeudDepart(source).withMode(mode).withNiveau(niveau).withUnicite(unicite);	
		}
		
		ControlRequete crr = new ControlRequete(this.reseaux,cr.buildRequete());
		ArrayList<String> res = crr.executeRequete();
		listview.getItems().add("Résultat :");
		for(int i = 0; i < res.size();i++) {
			listview.getItems().add(res.get(i));
		}
	}
}
