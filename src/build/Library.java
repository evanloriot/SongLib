package build;
	
import java.util.ArrayList;

import Models.Song;
import controllers.LibraryController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class Library extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Create an ArrayList to contain songs
			ArrayList<Song> songs = new ArrayList<Song>();
			
			//TODO: load array list from file
			songs.add(new Song("International Love", "Pitbull", null, null));
			
			//populate observable list (does it even need to be observable?)
			ObservableList<Song> obsList = FXCollections.observableArrayList(songs);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/views/Library.fxml"));
			
			GridPane root = (GridPane) loader.load();
			
			LibraryController libraryController = loader.getController();
			libraryController.start(primaryStage, obsList);
			
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}