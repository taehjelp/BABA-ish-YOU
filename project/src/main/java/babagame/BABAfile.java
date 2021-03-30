package babagame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

public class BABAfile implements BABAfileInterface{

	public void save(String game, File file) { // saves current board as the BABAgame toString
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(game);
		}
		catch (IOException e) {
			System.out.println("sumn be IO wrong save");
		}
		catch (Exception e) {
			System.out.println("sumn be wrong save");
		}
	}
	
	public char[][] load(File file) { // reads from savefile to a 2D array
		char[][] array = new char[14][14];
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			for (int y = 0; y < 14; y++) {
				String line = reader.readLine();
				for (int x = 0; x < 14; x++) {
					array[y][x] = line.charAt(x);
				}
			}	
		}
		catch (IOException e) {
			System.out.println("sumn be IO wrong load");
		}
		catch (Exception e) {
			System.out.println("sumn be wrong load");
		}
		return array;
	}
}
