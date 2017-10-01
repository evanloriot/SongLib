package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Song;

public class LibraryController {
	@FXML
	public ListView<Song> library;
	public Button addSong;
	
	public ObservableList<Song> obsList;
	
	public void start(Stage mainStage, ObservableList<Song> obsList) {
		this.obsList = obsList;
		
		//set list in ui
		library.setItems(obsList);  
		
		//double click event to edit song
		library.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click){
				Song song = library.getSelectionModel().getSelectedItem();
				if(click.getClickCount() == 2) {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditSong.fxml"));
						Parent root = (Parent) loader.load();
						
						EditSongController editSong = loader.getController();
						editSong.songObj = song;
						editSong.index = obsList.indexOf(song);
						editSong.start(mainStage, obsList);
						
						Scene scene = new Scene(root);
						mainStage.setScene(scene);
					}
					catch(Exception e) {
						System.out.println("Exception occurred.");
						e.printStackTrace();
					}
				}
			}
		});
		
		addSong.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddSong.fxml"));
					Parent root = (Parent) loader.load();
					
					AddSongController addSong = loader.getController();
					addSong.start(mainStage, obsList);
					
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
	
	public void addSong(Song song) {
		obsList.add(song);
	}
	
	public void changeSong(Song song, int index) {
		Song s = obsList.get(index);
		s.setSong(song.getSong());
		s.setArtist(song.getArtist());
		s.setAlbum(song.getAlbum());
		s.setYear(song.getYear());
	}
	
	public void deleteSong(int index) {
		obsList.remove(index);
	}
}
