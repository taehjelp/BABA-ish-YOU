package babagame;

public class BABAtile {
	private char type = ' '; // current type of the tile
	private char underType = ' '; // stores non-solid type if a move-obj moves on top of it
	private char text;
	private int x;
	private int y;
	private boolean solid = false;
	private boolean move = false;
	private boolean you = false;
	private boolean win = false;
	
	private String types = "BTFRW "; // BTFRW' '  = BABA, TEXT, FLAG, ROCK, WALL, EMPTY
//  text types
	private String nouns = "btfrw"; // btfrw = BABA, TEXT, FLAG, ROCK, WALL
	private String operators = "i"; // i = is
	private String properties = "yvps"; // yvps = YOU, WIN, PUSH, STOP

	public BABAtile(int x, int y) {
		this.x = x;
		this.y = y;
	}

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
			throw new IllegalArgumentException("invalid type");
		}
		else if (!((isNoun(text) || isOperator(text) || isProperty(text)))) {
			throw new IllegalArgumentException("invalid text");
		}
		this.type = type;
		this.text = text;
		solid = true; // set TEXT is PUSH and MOVE by default
		move = true;
	}

//	(!isNoun(text) && isOperator(text) && isProperty(text) true dersom text IKKJE noun og Er operator OG property			
	
	public void setUnderType(char type) {
		if (!solid == false) {
			throw new IllegalArgumentException("tile cannot be solid");
		}
		underType = type;
	}

//  eigenskap-setters
	public void setBools(char noun, char property) { // Bools: solid, move, you, win
		if (!isNoun(noun) || !isProperty(property)) {
			throw new IllegalArgumentException("invalid noun or property");
		}
		BABAproperty prop = new BABAproperty(property);
		solid = prop.isSolid();
		move = prop.isMove();
		you = prop.isYou();
		win = prop.isWin();
	}

	public void resetBools() {
		if (getType() != 'T') { // TEXT skal alltid vere push:)
			solid = false;
			move = false;
			text = ' '; // fjerne text dersom noko ikkje lenger e Text
		}
		you = false;
		win = false;

	}

	public char typeToNoun() {
		return Character.toLowerCase(type);
	}

//  text check
	public boolean isNoun(char text) {
		return nouns.contains(String.valueOf(text));
	}

	public boolean isOperator(char text) {
		return operators.contains(String.valueOf(text));
	}

	public boolean isProperty(char text) {
		return properties.contains(String.valueOf(text));
	}

//	posisjons-getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

//	gjenstand-samanlikning
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

//	text-getter
	public char getText() {
		return text;
	}

//  type-getter
	public char getType() {
		return type;
	}

	public char getUnderType() {
		return underType;
	}

//	eigenskap-getters
	public boolean isSolid() {
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

	@Override // idk om detta trengst
	public String toString() {
		return Character.toString(type);
	}
}
