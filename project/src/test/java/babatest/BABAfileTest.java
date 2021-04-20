package babatest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import babagame.BABAfile;
import babagame.BABAgame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BABAfileTest {
	private BABAgame game = new BABAgame(3,3);
	private BABAfile babaFile = new BABAfile();
	String rootPath = new File("").getAbsolutePath(); 
	private File testSaveFile = new File(rootPath + "\\testSaveFile.txt");
	private File testEmptyFile = new File(rootPath + "\\testEmptyFile.txt");
	
	@BeforeAll
	public void setUp() {
		char board[][] = {{' ', 'b', 'B'},
						  {'W', 'i', 'w'},
						  {'F', 'y', ' '}};
		for (int y = 0; y < 3; y++) { // sets the type of each tile according to board[][]
			for (int x = 0; x < 3; x++) {
				char chara = board[y][x];
				if (Character.isLowerCase(chara)) { // text
					game.getTile(x, y).setType('T', chara);
				} else if (Character.isUpperCase(chara)) {
					game.getTile(x, y).setType(chara); // type != text
				} // tile defaults to ' ' (EMPTY) if chara != alpha
			}
		}
	}
	
	@Test
	@DisplayName("Testing save")
	void testSave() {
		babaFile.save(game.toString(), testSaveFile);
		String saved = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(testSaveFile))) {
			for (int i = 0; i < 3; i++) {
				saved += reader.readLine() + "\n";	
			}
		}
		catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}
		catch (Exception e) {
			System.out.println("EXCEPTION");
		}
		assertEquals(game.toString(), saved, "Saved string should match toString of game:)");
	}
	
	@Test
	@DisplayName("Testing load")
	void testLoad() {
		char[][] board = {{' ','b','B'},{'W','i','w'},{'F','y',' '}};
		assertThrows(IllegalArgumentException.class, () -> {
			babaFile.load(testSaveFile,-1,3);;
		}, "invalid height/width should throw IllegalArgumentException");
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testSaveFile))) {
			writer.write(game.toString());
		}
		catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}
		catch (Exception e) {
			System.out.println("EXCEPTION");
		}
		assertArrayEquals((babaFile.load(testSaveFile, 3, 3)), board, "loaded string should match board:)");
		try {
			babaFile.load(testEmptyFile, 3, 3);
		}
		catch (Exception e) {
			fail("should not be able to load empty file");
		}
	}
	
	@AfterAll
	void teardown() {
		testSaveFile.delete();
		testEmptyFile.delete();
	}
}
