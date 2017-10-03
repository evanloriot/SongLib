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

public class EditSongController {
	@FXML
	public TextField song;
	public TextField artist;
	public TextField album;
	public TextField year;
	
	public Button cancel;
	public Button change;
	public Button delete;
	
	public Text songTitle;
	public Text artistTitle;
	
	public Song songObj;
	public int index;
	
	public void start(Stage mainStage, ObservableList<Song> obsList) {
		song.setText(songObj.getSong());
		artist.setText(songObj.getArtist());
		album.setText(songObj.getAlbum());
		year.setText(songObj.getYear());
		
		//set cancel event
		cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Library.fxml"));
					Parent root = (Parent) loader.load();
					
					LibraryController library = loader.getController();
					library.start(mainStage, obsList);
					
					library.selectSong(songObj);
					
					Scene scene = new Scene(root);
					mainStage.setScene(scene);
				}
				catch(Exception e) {
					System.out.println("An exception occurred.");
					e.printStackTrace();
				}
			}
		});
		
		//set change event
		change.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
					//TODO: Add an alert to tell the user that their changes couldn't be saved if its a duplicate
					if(SongUtils.isDuplicate(obsList, s, index)){
						library.showEditError(mainStage);
						library.selectSong(songObj);
						Scene scene = new Scene(root);
						mainStage.setScene(scene);
					} else {
						CSVUtils.deleteSongFromFile(library.getSong(index));
						library.changeSong(s, index);
						CSVUtils.writeSongToFile(s);
						library.sortList(s);
						Scene scene = new Scene(root);
						mainStage.setScene(scene);
					}

				}
				catch(Exception e) {
					System.out.println("An exception occurred.");
					e.printStackTrace();
				}
			}
		});
		
		//set delete event
		delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Library.fxml"));
					Parent root = (Parent) loader.load();
					
					LibraryController library = loader.getController();
					library.start(mainStage, obsList);
					CSVUtils.deleteSongFromFile(library.obsList.get(index));
					library.deleteSong(index);
					
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
