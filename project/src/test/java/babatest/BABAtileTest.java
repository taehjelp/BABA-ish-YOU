package babatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import babagame.BABAtile;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BABAtileTest {
	private BABAtile tile;

	@BeforeAll
	public void setUp() {
		tile = new BABAtile(0, 0);
	}

	@Test
	@DisplayName("Testing constructor")
	void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			tile = new BABAtile(-1, 2);
			;
		}, "invalid position should throw IllegalArgumentException");
	}

	@Test
	@DisplayName("Testing setType()")
	void testSetType() {		
		// 1 arg
		assertThrows(IllegalArgumentException.class, () -> {
			tile.setType('T');
			;
		}, "type text without yknow actual text should throw IllegalArgumentException");
		assertThrows(IllegalArgumentException.class, () -> {
			tile.setType('t');
			;
		}, "invalid type should throw IllegalArgumentException");
		tile.setType('B');
		assertEquals('B', tile.getType(), "BABA is BABA:)");

		// 2 args
		assertThrows(IllegalArgumentException.class, () -> {
			tile.setType('B', 'i');
			;
		}, "wrong type (not text) should throw IllegalArgumentException");
		assertThrows(IllegalArgumentException.class, () -> {
			tile.setType('T', 'q');
			;
		}, "invalid text should throw IllegalArgumentException");
		tile.setType('T', 'i');
		assertEquals('i', tile.getText(), "is is is:)");
	}

	@Test
	@DisplayName("Testing setBools()")
	void testSetBools() {
		assertThrows(IllegalArgumentException.class, () -> {
			tile.setBools('d');
			;
		}, "invalid property should throw IllegalArgumentException");
		tile.setBools('p');
		assertEquals(true, tile.isSolid(), "PUSH is solid:)");
		assertEquals(true, tile.isMove(), "PUSH is move:)");
		assertEquals(false, tile.isYou(), "PUSH is not you:)");
		assertEquals(false, tile.isWin(), "PUSH is not win:)");
	}

	@Test
	@DisplayName("Testing resetBools()")
	void testResetBools() {
		tile.setType('T', 'y');
		tile.setBools('v');
		assertEquals(true, tile.isWin(), "should be win:)");
		tile.resetBools();
		assertEquals(false, tile.isWin(), "should no longer be win:)");
		assertEquals(true, tile.isMove(), "text should always be move:)");

		tile.setType('B');
		tile.setBools('y');
		assertEquals(true, tile.isMove(), "should be move:)");
		tile.resetBools();
		assertEquals(false, tile.isMove(), "should no longer be move:)");
	}
}
