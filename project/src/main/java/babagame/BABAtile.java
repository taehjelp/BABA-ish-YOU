package babagame;

public class BABAtile {
	private char type = ' '; // current type of the tile
	private char underType = ' '; // memory:) stores current type if a move-obj moves on top of it
	private char text;
	private int x;
	private int y;
	private boolean solid = false;
	private boolean move = false;
	private boolean you = false;
	private boolean win = false;

	private String types = "BTFRW "; // BTFRW' ' = BABA, TEXT, FLAG, ROCK, WALL, EMPTY
	private String nouns = "btfrw"; // btfrw = baba, text, flag, rock, wall
	private String operators = "i"; // i = is
	private String properties = "yvps"; // yvps = YOU, WIN, PUSH, STOP
	
	BABAproperty prop = new BABAproperty();

//const
	public BABAtile(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Position cannot be negative");
		}
		this.x = x;
		this.y = y;
	}
//position
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

//type 
//	setters
	public void setType(char type) { // sets type if valid
		if (!types.contains(String.valueOf(type))) {
			throw new IllegalArgumentException("invalid type");
		} else if (type == 'T') {
			throw new IllegalArgumentException("text required");
		}
		this.type = type;
	}

	public void setType(char type, char text) { // if type = Text: sets type and text if valid
		if (type != 'T') {
			throw new IllegalArgumentException("type must be TEXT");
		} else if (!((isNoun(text) || isOperator(text) || isProperty(text)))) {
			throw new IllegalArgumentException("invalid text");
		}
		this.type = type;
		this.text = text;
		solid = true; // set TEXT is PUSH (solid + move) by default
		move = true;
	}
	
//	getter
	public char getType() {
		return type;
	}
	
//	translation
	public char typeToNoun() { // returns a type's corresponding noun
		return Character.toLowerCase(type);
	}
	
//	checks
	public boolean isBABA() {
		return type == 'B';
	}

	public boolean isTEXT() {
		return type == 'T';
	}

	public boolean isFLAG() {
		return type == 'F';
	}

	public boolean isROCK() {
		return type == 'R';
	}

	public boolean isWALL() {
		return type == 'W';
	}
	
//undertype 
//	setter
	public void setUnderType(char type) { // stores non-solid type if a move-obj moves on top of it
			underType = type;             // + some other stuff:)
	}
	
//	getter
	public char getUnderType() {
		return underType;
	}
	
//text 
//	getter
	public char getText() {
		return text;
	}
	
//text 
//	checks
	public boolean isNoun(char text) {
		return nouns.contains(String.valueOf(text));
	}

	public boolean isOperator(char text) {
		return operators.contains(String.valueOf(text));
	}

	public boolean isProperty(char text) {
		return properties.contains(String.valueOf(text));
	}
	
//bools/properties
//	setters
	public void setBools(char property) { // sets underlying properties of a tile,
		if (!isProperty(property)) {      // represented by bools
			throw new IllegalArgumentException("invalid property");
		}
		prop.setProperty(property);
		if (getType() != 'T') { // text is always solid and move
			solid = prop.isSolid();
			move = prop.isMove();
		}		
		you = prop.isYou();
		win = prop.isWin();
	}
	
	public void resetBools() { // resets bools of the tile to the default according to type
		if (getType() != 'T') { // TEXT is always PUSH (solid + move)
			solid = false;
			move = false;
			text = ' '; // removes text if a tile is no longer Text
		}
		you = false;
		win = false;
	}
	
//	getters
	public boolean isSolid() { // lowercase because they return the underlying properties:)
		return solid;
	}

	public boolean isMove() {
		return move;
	}

	public boolean isYou() {
		return you;
	}

	public boolean isWin() {
		return win;
	}

	@Override
	public String toString() {
		return Character.toString(type);
	}
}
