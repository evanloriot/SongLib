package build;

public class Song{
	private String name;
	private String artist;
	private String album;
	private String year;

	public Song(String name, String artist, String album, int year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = String.valueOf(year);
	}

	public Song(String name, String artist, String album, String year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public Song(String name, String artist, String album){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = "";
	}
	public Song(String name, String artist, int year){
		this.name = name;
		this.artist = artist;
		this.album = "";
		this.year = String.valueOf(year);
	}
	public Song(String name, String artist){
		this.name = name;
		this.artist = artist;
		this.album = "";
		this.year = "";
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getArtist(){
		return this.artist;
	}

	public void setArtist(String artist){
		this.artist = artist;
	}
	public String getAlbum(){
		return this.album;
	}

	public void setAlbum(String album){
		this.album = album;
	}
	public String getYear(){
		return this.year;
	}

	public void setYear(String year){
		this.year = year;
	}

	public void setYear(int year){
		this.year = String.valueOf(year);
	}

	public String getCSVString(){
		return 
			"\"" + this.name + "\"" + "," +
			"\"" + this.artist + "\"" + "," +
			"\"" + this.album + "\"" + "," +
			"\"" + this.year + "\"" + ",\n";
	}

	public String toString(){
		return "Song: " + this.name + "\nArtist: " + this.artist + "\n";
	}
}