package Models;

public class Song {
	private String song;
	private String artist;
	private String album;
	private String year;
	public Song(String song, String artist, String album, String year) {
		this.song = song;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	public String getSong() {
		return song;
	}
	public String getArtist() {
		return artist;
	}
	public String getAlbum() {
		return album;
	}
	public String getYear() {
		return year;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String toString() {
		String output = "";
		output += song;
		output += ", " + artist;
		output += " (";
		if(album == null) {
			output += "unknown album, ";
		}
		else {
			output += album + ", ";
		}
		if(year == null) {
			output += "unknown year)";
		}
		else {
			output += year + ")";
		}
		return output;
	}
}
