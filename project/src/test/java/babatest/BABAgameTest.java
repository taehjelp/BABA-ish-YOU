package babatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import babagame.BABAgame;

public class BABAgameTest {
	private BABAgame game;
	
	@BeforeEach
	void setUp() {
		game = new BABAgame(6,7);
		                   // 0   1   2   3   4   5
		char board[][] =   {{'B','B',' ','B','B','W'},// 0   // 1 ned: bryte biy (isLOSE=true) (og rip), laga rif
							{' ',' ','R','b','i','y'},// 1   // 1 høgre: teste BB' ' vs BBW, teste B mot Out Of Bounds
							{'F','B',' ',' ',' ',' '},// 2   // 1 venstre: B skubbe R, B til F => isWIN=true
							{' ','R','B',' ',' ','B'},// 3   // 2 opp: bryte rip, B går over R
							{' ',' ',' ',' ',' ','f'},// 4  
							{'f','i','v','r','i','p'},// 5
							{'w','i','s','B',' ',' '}};//6
		for (int y = 0; y < 7; y++) { // sets the type of each tile according to board[][]
			for (int x = 0; x < 6; x++) {
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
	@DisplayName("Testing constructor")
	void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			game = new BABAgame(-1,2);;
		}, "invalid height/width should throw IllegalArgumentException");
	}

	@Test
	@DisplayName("Testing getTile")
	void testGetTile() {
		assertThrows(IllegalArgumentException.class, () -> {
			game.getTile(-1,0);;
		}, "invalid coordinates should throw IllegalArgumentException");
		assertThrows(IllegalArgumentException.class, () -> {
			game.getTile(20,0);;
		}, "invalid coordinates should throw IllegalArgumentException");
		assertEquals('B', (game.getTile(0, 0)).getType(), "Tile should be of type BABA:)");
	}
	
	@Test
	@DisplayName("Testing w/ moveUp: underTypes, updateRules + resetAllProps")
	void testUp() { 
		game.moveUp();
		game.moveUp();
		game.moveUp();
		// Testing that non-solid items aren't obliterated if something moves over it:
		assertEquals(true, game.getTile(2, 1).isROCK(), "ROCK should still be there after getting trampled by BABA");
		// Testing that properies are removed if a rule that sets them are removed
		assertEquals(false, game.getTile(2, 1).isMove(), "ROCK should NOT be PUSH: !Move");
		assertEquals(false, game.getTile(2, 1).isSolid(), "ROCK should NOT be PUSH: !Solid");
	}
	
	@Test
	@DisplayName("Testing w/ moveDown: anyYOU + isLOSE, updateRules + setAllNouns")
	void testDown() {
		game.moveDown();
		// Testing that isLOSE is set to true if no item on the board is YOU
		assertEquals(true, game.isLOSE(), "Game should be LOSE since BABA is not YOU anymore");
		// Testing that items of one type get set to another if a rule dictates is
		assertEquals(true, game.getTile(2, 1).isFLAG(), "The previous ROCK should now be FLAG");
	}
	
	@Test
	@DisplayName("Testing w/ moveLeft: updateRules + setAllProps, moveYou + movePos, isWIN")
	void testLeft() {
		game.moveLeft();
		// Testing that the properties of items are set as dictated by the rules
		assertEquals(true, game.getTile(2, 1).isMove(), "ROCK should be Move (PUSH)");
		assertEquals(true, game.getTile(2, 1).isSolid(), "ROCK should be  Solid (PUSH)");
		// Testing that a YOU-item can push a PUSH-item as intended
		assertEquals(true, game.getTile(0, 3).isROCK(), "ROCK should be moved 1 tile left");
		assertEquals(true, game.getTile(1, 3).isBABA(), "BABA should be moved 1 tile left");
		// Testing that isWIN is set to true if a YOU-item overlaps with a WIN-item
		assertEquals(true, game.isWIN(), "Game should be WIN");
	}
	
	@Test
	@DisplayName("Testing w/ moveRight: canMove + moveYou + movePos")
	void testRight() {
		game.moveRight();
		// Testing that several YOU-items in a row will move if there is an empty/non-solid space for them to move into
		assertEquals(true, game.getTile(1, 0).isBABA(), "BABA should have moved right");
		assertEquals(true, game.getTile(2, 0).isBABA(), "BABA should have moved right");
		// Testing that several YOU-items in a row will NOT move if there is no empty/non-solid space for them to move into
		assertEquals(true, game.getTile(3, 0).isBABA(), "BABA should NOT have moved right");
		assertEquals(true, game.getTile(4, 0).isBABA(), "BABA should NOT have moved right");
		// Testing that an item does not move if next tile is oout of bounds
		assertEquals(true, game.getTile(5, 3).isBABA(), "BABA should not move out of bounds");
	}
}