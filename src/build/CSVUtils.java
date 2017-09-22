package build;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVUtils{

	private CSVUtils(){}
	
	//Need to change how we catch this exception
	public static void writeSongToFile(String csvString){
		File f = new File("songs.csv");

		try {
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(csvString);
			bw.close();
		} catch(IOException e) {
			System.out.println("Error writing the file");
		}
	}

	public static List<Song> getSongs() throws Exception{
		List<Song> toReturn = new ArrayList<>();
		List<String> line = new ArrayList<>();
		String filePath = new File("").getAbsolutePath();
		filePath += "\\songs.csv";
		Song currentSong;

		Scanner s = new Scanner(new File(filePath));

		while(s.hasNext()){
			line = parseCSVLine(s.nextLine());
			currentSong = new Song(line.get(0), line.get(1), line.get(2), line.get(3));
			toReturn.add(currentSong);
		}
		
		s.close();

		return toReturn;
	}

	//Currently doesn't handle strings with double quotes in them
	//More of a rough out line, I'll refactor a bit once we get
	//things running
	public static List<String> parseCSVLine(String csvString){
		List<String> toReturn = new ArrayList<>();

		if(csvString == null || csvString.length() == 0){
			//I'll change this at some point
			return null;
		}

		char[] csvStringArray = csvString.toCharArray();
		String currentString = "";
		boolean quoteStarted = false;
		boolean quoteEnded = false;

		for(char ch : csvStringArray){
			if(ch == '"' && !quoteStarted){
				quoteStarted = true;
			} else if(ch == '"' && quoteStarted) {
				quoteEnded = true;
			} else if(ch == ',' && quoteEnded || ch == '\n' && quoteEnded){
				toReturn.add(currentString);
				currentString = "";
				quoteStarted = false;
				quoteEnded = false;
			}else {
				currentString += ch;
			}
		}

		for(String s : toReturn){
			System.out.println(s);
		}

		return toReturn;
	}
}