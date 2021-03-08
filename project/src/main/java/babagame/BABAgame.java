package babagame;

public class BABAgame {

	private int height;
	private int width;
	private BABAtile[][] board;
	private boolean isWIN = false;
	private boolean isLOSE = false;
	private int youCount = 0; // telle kor mange som e you:)

//  sette opp brettet
	public BABAgame(int width, int height) {
		this.height = height;
		this.width = width;

		this.board = new BABAtile[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x] = new BABAtile(x, y); // blir automatisk empty ' ' ya
			}
		}
	}

//  getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BABAtile getTile(int x, int y) {
		if (!isTile(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bounds");
		}
		return board[y][x];
	}

//	mindre pain lmao
	public char tileType(int x, int y) {
		return getTile(x, y).getType();
	}

	public char tileText(int x, int y) {
		return getTile(x, y).getText();
	}

	public char nounToType(char noun) {
//    	if (!isNoun(noun)) {
//    		throw new IllegalArgumentException("text should be noun");
//    	}
		return Character.toUpperCase(noun);
	}

//  check   
	public boolean isTile(int x, int y) {
		return (x >= 0 && y >= 0 && x < getWidth() && y < getHeight());
	}

//	set shit bro
	private void resetAllProps() { // set alle BABAtile-bools til false (unntak:text)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x].resetBools();
			}
		}
	}

	private void setAllProps(char noun, char property) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (noun == board[y][x].typeToNoun()) {
					board[y][x].setBools(noun, property); // brukar ikkje getTile(x,y) fordi loopen ikkje går out of
															// bounds
				}
			}
		}
	}

	private void setAllNouns(char startNoun, char endNoun) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (startNoun == board[y][x].typeToNoun()) {
					if (endNoun == 't') { // text be special bruv
						board[y][x].setType(nounToType(endNoun), startNoun);
					} else {
						board[y][x].setType(nounToType(endNoun));
					}
				}
			}
		}
	}

//	rule time	
	public void updateRules() {
		resetAllProps(); // clearing the properties:)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (board[y][x].isOperator(tileText(x, y))) { // dersom (x,y) == is-tekst:
					if (getTile(x - 1, y).isNoun(tileText(x - 1, y))
							&& getTile(x + 1, y).isProperty(tileText(x + 1, y))) {
						setAllProps(tileText(x - 1, y), tileText(x + 1, y));
					} // ikkje else if, fordi fleire kan vere tilfelle:))
					if (getTile(x, y - 1).isNoun(tileText(x, y - 1))
							&& getTile(x, y + 1).isProperty(tileText(x, y + 1))) {
						setAllProps(tileText(x, y - 1), tileText(x, y + 1));
					}
					if (getTile(x - 1, y).isNoun(tileText(x - 1, y)) && getTile(x + 1, y).isNoun(tileText(x + 1, y))) {
						setAllNouns(tileText(x - 1, y), tileText(x + 1, y));
					}
					if (getTile(x, y - 1).isNoun(tileText(x, y - 1)) && getTile(x, y + 1).isNoun(tileText(x, y + 1))) {
						setAllNouns(tileText(x, y - 1), tileText(x, y + 1));
					}
				}
			}
		}
	}

//	public movement
	public void moveUp() {
		moveYou('u');
	}

	public void moveDown() {
		moveYou('d');
	}

	public void moveLeft() {
		moveYou('l');
	}

	public void moveRight() {
		moveYou('r');
	}

//	private movement
	private int newY(int y, char dir) {
		if (dir == 'u') {
			return (y - 1);
		} else if (dir == 'd') {
			return (y + 1);
		} else {
			return y;
		}
	}

	private int newX(int x, char dir) {
		if (dir == 'l') {
			return (x - 1);
		} else if (dir == 'r') {
			return (x + 1);
		} else {
			return x;
		}
	}

//	CODE is TRASH
	
//	private int prevY(int y, char dir) { // idk om dessa e naudsynte bro
//		if (dir == 'u') {
//			return (y+1);
//		}
//		else if (dir == 'd') {
//			return (y-1);
//		}	
//		else {
//			return y;
//		}
//	}
//	private int prevX(int x, char dir) {
//			if (dir == 'l') {
//				return (x+1);
//			}
//			else if (dir == 'r') {
//				return (x-1);
//			}	
//			else {
//				return x;
//			}
//	}
	private void countYou() {
		updateRules();
		youCount = 0; // telle kor mange som e you:)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y).isYou()) {
					youCount++;
				}
			}
		}
		if (youCount == 0) { // game is lose om ingenting på brettet er you yea:)
			isLOSE = true;
		}
	}

	private void moveYou(char dir) {
//		CODE is TRASH
		
//		youCount = 0; // telle kor mange som e you:)
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (getTile(x,y).isYou()) {
//					youCount++;
////					System.out.println(youCount); // berre for testing ya
//					movePos(x,y,dir);
//				}
//			}
//		}
		countYou();
		if (youCount == 0) { // game is lose om ingenting på brettet er you yea:)
			isLOSE = true;
		} else {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (getTile(x, y).isYou()) {
						movePos(x, y, dir);
					}
				}
			}
		}
	}

	private void movePos(int x, int y, char dir) { // pos er posisjonen til greia ein vil flytte
		countYou(); // denna køyre updateRules i tillegg:)
		if (youCount == 0) {
			isLOSE = true;
		}
		if (isLOSE || isWIN) {
		} else {
			BABAtile tile = getTile(x, y);
			char type = tile.getType();
			int newY = newY(y, dir);
			int newX = newX(x, dir);
			BABAtile nextTile = getTile(newX, newY); // hugs: getTile sjekke if it be valid:)
			char nextType = nextTile.getType();

			if (nextType == ' ') { // ' ': empty
				if (type == 'T') { // text krever spesialbehanling
					nextTile.setType(type, (tileText(x, y)));
				} else {
					nextTile.setType(type);
				}
				tile.setType(tile.getUnderType());
			} else if (nextTile.isSolid() && nextTile.isMove()) {
				if (type != 'T') {
					movePos(newX, newY, dir);
					nextTile.setType(type);
					tile.setType(tile.getUnderType());
				} else { // text krever spesialbehanling
					movePos(newX, newY, dir);
					nextTile.setType(type, tileText(x, y));
					tile.setType(tile.getUnderType());
				}
			} else if (nextTile.isWin()) {
				nextTile.setType(type);
				tile.setType(tile.getUnderType());
				isWIN = true;
			} else if (!nextTile.isSolid()) {
				nextTile.setUnderType(nextType); // stores type of the non-solid for later
				if (type != 'T') {
					nextTile.setType(type);
				} else {
					nextTile.setType(type, tileText(x, y));
				}
				tile.setType(tile.getUnderType()); // empty med mindre underType har blitt satt
			}
			// dersom nextTile er solid og !move skjer ingenting
		}
	}

//	tostring time
	@Override
	public String toString() {
		String boardString = "";
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (tileType(x, y) == 'T') {
					boardString += tileText(x, y);
				} else {
					boardString += tileType(x, y);
				}
			}
			boardString += "\n";
		}
		boardString += "\n----------------\n"; // kun for tydelegheit
		if (isWIN) {
			boardString += "\n\nGAME is WIN!";
		} else if (isLOSE) {
			boardString = "\n\nGAME is LOSE!";
		}
		return boardString;
	}

	public boolean isWIN() {
		return isWIN;
	}

	public boolean isLOSE() {
		return isLOSE;
	}

//	test
	public static void main(String[] args) {
		BABAgame game = new BABAgame(14, 14);

		game.getTile(11, 2).setType('T', 'f');
		game.getTile(11, 3).setType('T', 'i');
		game.getTile(11, 4).setType('T', 'v');
//		setting board:
//		game.getTile(6,2).setType('W');
//		game.getTile(6,6).setType('B');
		game.getTile(5, 5).setType('B');
//		game.getTile(6,5).setType('R');
		game.getTile(8, 2).setType('T', 'b');
		game.getTile(8, 3).setType('T', 'i');
		game.getTile(8, 4).setType('T', 'y');
//		game.getTile(10,2).setType('T','r');
//		game.getTile(10,3).setType('T','i');
//		game.getTile(10,4).setType('T','p');
//		game.getTile(11,2).setType('T','w');
//		game.getTile(11,3).setType('T','i');
//		game.getTile(11,4).setType('T','r');
		System.out.println(game);
////		gemgem
//		game.updateRules();
		game.moveUp();
		System.out.println(game);
////		game.updateRules();
//		game.moveRight();
//		System.out.println(game);
//		System.out.println(game.tileType(6, 4));
//		System.out.println(game.getTile(6, 4).isYou()); // e baba you?
//		System.out.println(game.tileType(7, 4));
//		System.out.println(game.getTile(7, 4).isMove()); // e rock push?
//		game.moveRight();
//		System.out.println(game);
//		System.out.println(game.tileType(7, 4));
//		System.out.println(game.getTile(7, 4).isYou()); // e baba you?
//		System.out.println(game.tileType(8, 4));
//		System.out.println(game.getTile(8, 4).isMove()); // e rock push?
//		game.moveRight();
//		System.out.println(game);
//		System.out.println(game.tileType(8, 4));
//		System.out.println(game.getTile(8, 4).isYou()); // e baba you?

	}

}
