package fr.ul.miage.reseauxsocial.vue;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static final Logger LOG = Logger.getLogger(App.class.getName());
	
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Vue.fxml"));
		} catch (IOException e) {
			LOG.severe(e.getMessage());
		}
		Scene scene = new Scene(root);
		primaryStage.setTitle("MiageBook - Le reseau social par excellence");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

