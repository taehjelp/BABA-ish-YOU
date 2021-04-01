package babagame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BABAfile implements BABAfileInterface{

	public void save(String game, File file) { // saves current board as the BABAgame toString
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(game);
		}
		catch (IOException e) {
			System.out.println("SAVE is IO EXCEPTION");
		}
		catch (Exception e) {
			System.out.println("SAVE is EXCEPTION");
		}
	}
	
	public char[][] load(File file, int height, int width) { // reads from savefile to a 2D array
		char[][] array = new char[height][width];            // size of array is decided by height & width:)
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			for (int y = 0; y < height; y++) {
				String line = reader.readLine();
				for (int x = 0; x < width; x++) {
					array[y][x] = line.charAt(x);
				}
			}	
		}
		catch (IOException e) {
			System.out.println("LOAD is IO EXCEPTION");
		}
		catch (Exception e) {
			System.out.println("LOAD is EXCEPTION");
		}
		return array;
	}
}
