package babagame;

enum TileTypeEnum { // for å minne meg om ei betre verd
	TEXT, BABA, FLAG, ROCK, WALL, EMPTY;
}

public class BABAtile {
	private char type = ' ';
	private char underType = ' ';
	private char text;
	private int x;
	private int y;
	private boolean solid = false;
	private boolean move = false;
	private boolean you = false;
	private boolean win = false;
//  types
	private String types = "TBFRW "; // hhv TEXT, BABA, FLAG, ROCK, WALL, EMPTY
//  text types
	private String nouns = "btfrw"; // hhv BABA, TEXT, FLAG, ROCK, WALL
	private String operators = "i"; // is
	private String properties = "yvps"; // hhv YOU, WIN, PUSH, STOP

	public BABAtile(int x, int y) {
		this.x = x;
		this.y = y;
	}

//	gjenstand-setters
	public void setType(char type) {
		if (!types.contains(String.valueOf(type))) {
			throw new IllegalArgumentException("invalid type");
		} else if (type == 'T') {
			throw new IllegalArgumentException("text required");
		}
		this.type = type;
	}

	public void setType(char type, char text) {
		if ((!types.contains(String.valueOf(type))) || (!isNoun(text) && isOperator(text) && isProperty(text))) {
			throw new IllegalArgumentException("invalid type or text");
		}
		this.type = 'T';
		this.text = text;
		solid = true; // set TEXT is PUSH by default
		move = true;
	}

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
