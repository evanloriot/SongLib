package build;

import javafx.collections.ObservableList;
import models.Song;

public class SongUtils {
	private SongUtils(){}
	public static ObservableList<Song> Sort(ObservableList<Song> obsList){
		Song temp;
		int lowest;
		
		for(int i = 0; i < obsList.size() ; i++){
			temp = obsList.get(i);
			lowest = i;

			for(int j = i+1; j < obsList.size(); j++){
				if(trimLower(obsList.get(j).toString()).compareTo(trimLower(obsList.get(lowest).toString())) < 0){
					lowest = j;
				}
			}

			obsList.set(i, obsList.get(lowest)); 
			obsList.set(lowest, temp);
		}
		return obsList;
	}
	
	public static boolean isDuplicate(ObservableList<Song> obsList, Song s){
		for(int i = 0; i < obsList.size(); i++){
			if(trimLower(s.getSong()).equals(trimLower(obsList.get(i).getSong())) && 
					trimLower(s.getArtist()).equals(trimLower(obsList.get(i).getArtist()))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDuplicate(ObservableList<Song> obsList, Song s, int index){
		for(int i = 0; i < obsList.size(); i++){
			if(i == index){
				continue;
			}
			if(trimLower(s.getSong()).equals(trimLower(obsList.get(i).getSong())) && 
					trimLower(s.getArtist()).equals(trimLower(obsList.get(i).getArtist()))){
				return true;
			}
		}
		return false;
	}
	
	public static String trimLower(String in){
		return in.toLowerCase().trim();
	}
}

