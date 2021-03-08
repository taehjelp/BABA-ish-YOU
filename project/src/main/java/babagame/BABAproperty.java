package babagame;

public class BABAproperty {

	private char property;

	public BABAproperty(char property) {
		if (!"yvps".contains(String.valueOf(property))) { // hhv YOU, WIN, PUSH, STOP
			throw new IllegalArgumentException("invalid noun or property");
		} else {
			this.property = property;
		}
	}

	public boolean isSolid() {
		return (property != 'v'); // WIN er einaste ikkje-solid
	}

	public boolean isMove() {
		return (property == 'y' | property == 'p'); // YOU og PUSH er move
	}

	public boolean isYou() {
		return (property == 'y'); // YOU er you
	}

	public boolean isWin() {
		return (property == 'v'); // WIN er win
	}
}
