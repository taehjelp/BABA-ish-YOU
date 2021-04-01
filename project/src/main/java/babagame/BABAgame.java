package babagame;

public class BABAgame {

	private int height;
	private int width;
	private BABAtile[][] board;
	private int youCount;
	private boolean isWIN = false;
	private boolean isLOSE = false;

//const
	public BABAgame(int width, int height) { // board setup
		this.height = height;
		this.width = width;
		board = new BABAtile[height][width];

		for (int y = 0; y < height; y++) { // fills board with BABAtiles
			for (int x = 0; x < width; x++) {
				board[y][x] = new BABAtile(x, y); // tile is EMPTY by default
			}
		}
	}

//board size
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
//tiles
	public BABAtile getTile(int x, int y) {
		if (!isTile(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bounds");
		}
		return board[y][x];
	}
	
	public boolean isTile(int x, int y) {
		return (x >= 0 && y >= 0 && x < getWidth() && y < getHeight());
	}
	
//type, text
//	getters
	public char tileType(int x, int y) {
		return getTile(x, y).getType();
	}

	public char tileText(int x, int y) {
		return getTile(x, y).getText();
	}
//	translation
	public char nounToType(char noun) { // returns a noun's corresponding tile type
		return Character.toUpperCase(noun);
	}
	
//WIN/LOSE
//	counter
	private void countYou() { // counts instances of YOU
		updateRules();
		youCount = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y).isYou()) {
					youCount++;
				}
			}
		}
		if (youCount == 0) { // game is LOSE if nothing on the board is YOU
			isLOSE = true;
		}
	}
//	getters
	public boolean isWIN() {
		return isWIN;
	}

	public boolean isLOSE() {
		return isLOSE;
	}

//gamerules	
	public void updateRules() { // sets gamerules according to text on the board (BABA is YOU, WALL is FLAG etc)
		resetAllProps(); // clearing current properties
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (board[y][x].isOperator(tileText(x, y))) { // if (x,y) == is-text:
					if (getTile(x - 1, y).isNoun(tileText(x - 1, y)) // horizontal sentence, noun is property
							&& getTile(x + 1, y).isProperty(tileText(x + 1, y))) {
						setAllProps(tileText(x - 1, y), tileText(x + 1, y)); 
					}
					if (getTile(x, y - 1).isNoun(tileText(x, y - 1)) // vertical sentence, noun is property
							&& getTile(x, y + 1).isProperty(tileText(x, y + 1))) {
						setAllProps(tileText(x, y - 1), tileText(x, y + 1));
					}
					if (getTile(x - 1, y).isNoun(tileText(x - 1, y)) // horizontal sentence, noun is noun
							&& getTile(x + 1, y).isNoun(tileText(x + 1, y))) {
						setAllNouns(tileText(x - 1, y), tileText(x + 1, y));
					}
					if (getTile(x, y - 1).isNoun(tileText(x, y - 1)) // vertical sentence, noun is noun
							&& getTile(x, y + 1).isNoun(tileText(x, y + 1))) {
						setAllNouns(tileText(x, y - 1), tileText(x, y + 1));
					}
				}
			}
		}
	}

//	property/noun setters
	private void resetAllProps() { // resets property-bools of all tiles (except Text)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x].resetBools();
			}
		}
	}

	private void setAllProps(char noun, char property) { // all tiles matching noun's Type are given the
		for (int y = 0; y < height; y++) {                                             // input property
			for (int x = 0; x < width; x++) {
				if (noun == board[y][x].typeToNoun()) {
					board[y][x].setBools(noun, property);
				}
			}
		}
	}

	private void setAllNouns(char startNoun, char endNoun) { // all tiles matching startNoun's type are
		for (int y = 0; y < height; y++) {                   				   // set to endNoun's type
			for (int x = 0; x < width; x++) {
				if (startNoun == board[y][x].typeToNoun()) {
					if (endNoun == 't') { // text is a special case
						board[y][x].setType(nounToType(endNoun), startNoun); // i.e: ROCK is TEXT turns all
					} else {                                               // rocks into text saying "rock"
						board[y][x].setType(nounToType(endNoun));
					}
				}
			}
		}
	}
	
//public movement
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
//private movement
//	coords
	private int newY(int y, char dir) { // y-position object will move to
		if (dir == 'u') {
			return (y - 1);
		} else if (dir == 'd') {
			return (y + 1);
		} else {
			return y;
		}
	}

	private int newX(int x, char dir) { // x-position object will move to
		if (dir == 'l') {
			return (x - 1);
		} else if (dir == 'r') {
			return (x + 1);
		} else {
			return x;
		}
	}
//	movement starter
	private void moveYou(char dir) { 
		countYou(); // sets isLOSE = true if necessary
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y).isYou()) {
					movePos(x, y, dir);
				}
			}
		}
	}
//	actual movement
	private void movePos(int x, int y, char dir) { // x,y=pos. of object to be moved, dir=direction of movement
		countYou(); // also runs updateRules
		if (!isLOSE && !isWIN) {
			BABAtile tile = getTile(x, y);
			char type = tile.getType(); // type of current tile
			int newY = newY(y, dir);
			int newX = newX(x, dir);
			if (isTile(newX, newY)) { // can't move out of bounds
				BABAtile nextTile = getTile(newX, newY);
				char nextType = nextTile.getType(); // type of next tile in movement direction
	
				if (nextType == ' ') { // ' ': empty
					if (type == 'T') { // text is a special case
						nextTile.setType(type, (tileText(x, y)));
						countYou(); // player can lose by moving text, needs to check if anything is still YOU:)
					} else {
						nextTile.setType(type);
					}
					tile.setType(tile.getUnderType()); // underType is EMPTY by default
				} 
				else if (nextTile.isSolid() && nextTile.isMove()) {
					if (type != 'T') {
						movePos(newX, newY, dir);
						nextTile.setType(type);
						tile.setType(tile.getUnderType());
					} else { 
						movePos(newX, newY, dir);
						nextTile.setType(type, tileText(x, y));
						tile.setType(tile.getUnderType());
					}
				} 
				else if (nextTile.isWin()) {
					nextTile.setType(type);
					tile.setType(tile.getUnderType());
					isWIN = true;
				} 
				else if (!nextTile.isSolid()) {
					nextTile.setUnderType(nextType); // stores type of the non-solid for later
					if (type != 'T') {
						nextTile.setType(type);
					} else {
						nextTile.setType(type, tileText(x, y));
					}
					tile.setType(tile.getUnderType());
				}
				// nothing happens if nextTile=solid and != move
			}
		}
	}
	
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
		return boardString;
	}
}
