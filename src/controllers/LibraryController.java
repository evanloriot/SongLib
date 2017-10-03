//Evan Loriot and Joseph Klaszky
package controllers;

import java.io.IOException;
import java.util.ArrayList;

import build.SongUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Song;

public class LibraryController {
	@FXML
	public ListView<Song> library;
	public Button addSong;
	public Text description;
	
	public ObservableList<Song> obsList;
	
	public void start(Stage mainStage, ObservableList<Song> obsList) {
		this.obsList = obsList;
		
		//set list in ui
		library.setItems(obsList);
		if(obsList.size() != 0) {
			populateDescription(obsList.get(0));
		}
		
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
				else if(click.getClickCount() == 1) {
					populateDescription(song);
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
					addSong.start(mainStage, obsList, library.getSelectionModel().getSelectedItem());
					
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
	
	public void populateDescription(Song song) {
		description.setText(song.fullToString());
	}
	
	public void addSong(Song song) {
		obsList.add(song);
		selectSong(song);
	}
	
	public void selectSong(Song song) {
		int index = 0;
		for(int i = 0; i < obsList.size(); i++) {
			if(obsList.get(i).toString().equals(song.toString())) {
				index = i;
				break;
			}
		}
		library.getSelectionModel().select(index);
		populateDescription(song);
	}
	
	public void changeSong(Song song, int index) {
		Song s = obsList.get(index);
		s.setSong(song.getSong());
		s.setArtist(song.getArtist());
		s.setAlbum(song.getAlbum());
		s.setYear(song.getYear());
		selectSong(song);
	}
	
	public Song getSong(int index){
		return obsList.get(index);
	}
	
	public void deleteSong(int index) {
		obsList.remove(index);
		if(index >= obsList.size()) {
			library.getSelectionModel().select(index - 1);
		}
		else {
			library.getSelectionModel().select(index);
		}
		if(obsList.size() == 0) {
			description.setText("");
		}
	}
	
	public void sortList(Song song){
		this.obsList = SongUtils.sort(obsList);
		selectSong(song);
	}
	
	
    protected void showAddError(Stage mainStage) {                
    	Alert alert = 
			   new Alert(AlertType.INFORMATION);
	   alert.initOwner(mainStage);
	   alert.setTitle("Library");
	   alert.setHeaderText("Canceling Last User Operation");

	   String content = "Can't add a song with the same name and artist as another song already in the library"; 

	   alert.setContentText(content);
	   alert.showAndWait();
   }
    
   protected void showEditError(Stage mainStage) {                
    	Alert alert = 
			   new Alert(AlertType.INFORMATION);
	   alert.initOwner(mainStage);
	   alert.setTitle("Library");
	   alert.setHeaderText("Canceling Last User Operation");

	   String content = "Can't edit a song to have the same name and artist as another song already in the library"; 

	   alert.setContentText(content);
	   alert.showAndWait();
   }
}
