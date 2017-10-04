//Evan Loriot and Joseph Klaszky
package build;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import models.Song;

public class CSVUtils{

	private CSVUtils(){}
	
	public static void writeSongToFile(Song s){
		String filePath = new File("").getAbsolutePath();
		filePath += "\\src\\build\\songs.csv";
		File file = new File(filePath);

		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.write(CSVUtils.getCSVString(s));
			bufferWriter.close();
		} catch(IOException e) {
			System.out.println("Error writing the file");
		}
	}

	//Returns an arrayList of Song objects pulled from songs.csv
	public static ArrayList<Song> getSongs() throws Exception{
		ArrayList<Song> toReturn = new ArrayList<>();
		List<String> line = new ArrayList<>();
		try {
			String filePath = new File("").getAbsolutePath();
			filePath += "\\src\\build\\songs.csv";
			Song currentSong;
	
			Scanner scanner = new Scanner(new File(filePath));
	
			while(scanner.hasNext()){
				line = parseCSVLine(scanner.nextLine());
				currentSong = new Song(line.get(0), line.get(1), line.get(2), line.get(3));
				toReturn.add(currentSong);
			}
			
			scanner.close();
		} catch (FileNotFoundException e){
			return toReturn;
		}

		return toReturn;
	}

	//Can't handle stings with ", in the middle of them.
	//It won't break the program, but it will introduce errors in song storage
	public static List<String> parseCSVLine(String csvString){
		List<String> toReturn = new ArrayList<>();

		if(csvString == null || csvString.length() == 0){
			return null;
		}

		char[] csvStringArray = csvString.toCharArray();
		String currentString = "";
		boolean quoteStarted = false;
		boolean quoteEnded = false;

		for(char character : csvStringArray){
			if(character == '"' && !quoteStarted){
				quoteStarted = true;
			} else if(character == '"' && quoteStarted) {
				quoteEnded = true;
			} else if(character == ',' && quoteEnded || character == '\n' && quoteEnded){
				toReturn.add(currentString);
				currentString = "";
				quoteStarted = false;
				quoteEnded = false;
			}else {
				currentString += character;
			}
		}
		
		return toReturn;
	}
	
	public static void deleteSongFromFile(Song s){
		String filePath = new File("").getAbsolutePath();
		String finalFilePath = filePath + "\\src\\build\\songs.csv";
		String tempFilePath = filePath + "\\src\\build\\temp.csv";
		try{
			File inputFile = new File(finalFilePath);
			File tempFile = new File(tempFilePath);
			Song tempSong;
			List<String> line;
			String currentLine;
	
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	
	
			while((currentLine = reader.readLine()) != null) {
				line = CSVUtils.parseCSVLine(currentLine);
				if(line == null){
					continue;
				}
				tempSong = new Song(line.get(0),line.get(1),line.get(2),line.get(3));
				//This is ugly, but I just want to make sure that they're equal regardless of whitespace or case
				if(trimLower(s.getSong()).equals(trimLower(tempSong.getSong())) && 
						trimLower(s.getArtist()).equals(trimLower(tempSong.getArtist()))){
					continue;
				}else{
					writer.write(currentLine + System.getProperty("line.separator"));
				}
			}
			
			writer.close(); 
			reader.close();
			try{
				inputFile.delete();
			} catch(Exception e){
				System.out.println("Couldn't delete file.");
			}
			tempFile.renameTo(inputFile);
		} catch (Exception e){
			return;
		}
	}
	
	public static String trimLower(String in){
		return in.toLowerCase().trim();
	}
	
	public static String getCSVString(Song s){
		return 
			"\"" + s.getSong() + "\"" + "," +
			"\"" + s.getArtist() + "\"" + "," +
			"\"" + s.getAlbum() + "\"" + "," +
			"\"" + s.getYear() + "\"" + ",\n";
	}
}