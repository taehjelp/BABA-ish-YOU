package babagame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//TODO 2 filbehandling
//TODO 3 feilhandtering: try/catch, unntak v ugyldig input i load
public class BABAfile implements BABAfileInterface {

	public void save(String game, File file) { // saves current board as the BABAgame toString
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(game);
		} catch (IOException e) {
			System.out.println("SAVE is IO EXCEPTION");
		} catch (Exception e) {
			System.out.println("SAVE is EXCEPTION");
		}
	}

	public char[][] load(File file, int height, int width) { // reads from savefile to a 2D array
		if (height < 0 || width < 0) {
			throw new IllegalArgumentException("Height and width cannot be negative");
		}
		char[][] array = new char[height][width]; // size of array is decided by height & width:)
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			for (int y = 0; y < height; y++) {
				String line = reader.readLine();
				for (int x = 0; x < width; x++) {
					array[y][x] = line.charAt(x);
				}
			}
		} catch (IOException e) { //TODO 3 feilhandtering: einaste feil det er sannsynleg at brukaren møter på:
			System.out.println("LOAD is IO EXCEPTION");  //saveFile er tom:) return null får kontrollaren til 
			return null;                                 //å vise brukaren ei fin feilmelding:)
		} catch (Exception e) {
			System.out.println("LOAD is EXCEPTION");
			return null;
		}
		return array;
	}
}
