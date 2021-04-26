package babagame;

//TODO 1 grunnklasse
//TODO 3 feilhandtering: ugyldig input
public class BABAproperty {
	private char property;

	public void setProperty(char property) {
		if (!"yvps".contains(String.valueOf(property))) // yvps = YOU, WIN, PUSH, STOP
			throw new IllegalArgumentException("invalid property, must be y/v/p/s");
		else
			this.property = property;
	}

//	The following bools represent the underlying properties of YOU, WIN, PUSH and STOP
	public boolean isSolid() {
		return (property != 'v'); // only WIN is non-solid
	}

	public boolean isMove() {
		return (property == 'y' || property == 'p'); // YOU and PUSH is move
	}

	public boolean isYou() {
		return (property == 'y'); // YOU is you
	}

	public boolean isWin() {
		return (property == 'v'); // WIN is win
	}

}
