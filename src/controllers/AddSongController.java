package controllers;


import build.CSVUtils;
import build.SongUtils;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.Song;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AddSongController {
	@FXML
	public TextField song;
	public TextField artist;
	public TextField album;
	public TextField year;
	
	public Text songTitle;
	public Text artistTitle;
	
	public Button cancel;
	public Button add;
	
	public void start(Stage mainStage, ObservableList<Song> obsList) {
		//set cancel event
		cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Library.fxml"));
					Parent root = (Parent) loader.load();
					
					LibraryController library = loader.getController();
					library.start(mainStage, obsList);
					
					Scene scene = new Scene(root);
					mainStage.setScene(scene);
				}
				catch(Exception e) {
					System.out.println("An exception occurred.");
					e.printStackTrace();
				}
			}
		});
		
		//set Add event
		add.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				try {
					if(song.getText() == null || song.getText().length() == 0 || artist.getText() == null || artist.getText().length() == 0) {
						songTitle.setFill(Color.RED);
						artistTitle.setFill(Color.RED);
						return;
					}
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Library.fxml"));
					Parent root = (Parent) loader.load();
					
					LibraryController library = loader.getController();
					Song s = new Song(song.getText(), artist.getText(), album.getText(), year.getText());
					library.start(mainStage, obsList);
					//TODO: Add an alert to tell the user that their song couldn't be added, it is a duplicate
					if(!SongUtils.isDuplicate(obsList, s)){
						library.addSong(s);
						library.sortList();
						CSVUtils.writeSongToFile(s);
					}
					
					Scene scene = new Scene(root);
					mainStage.setScene(scene);
				}
				catch(Exception e) {
					System.out.println("An exception occurred.");
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
