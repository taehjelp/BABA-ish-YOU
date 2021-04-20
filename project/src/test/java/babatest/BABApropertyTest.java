package babatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import babagame.BABAproperty;

public class BABApropertyTest {
	private BABAproperty prop;
	
	@Test
	@DisplayName("testing setProperty")
	void testSetProperty() {
		prop = new BABAproperty();
		assertThrows(IllegalArgumentException.class, () -> {
			prop.setProperty('k');;
		}, "invalid property-name should throw IllegalArgumentException");
		prop.setProperty('p');
		assertEquals(true, prop.isMove(), "PUSH should be Move");
		assertEquals(true, prop.isSolid(), "PUSH should be Solid");
		assertEquals(false, prop.isYou(), "PUSH should not be You");
		assertEquals(false, prop.isWin(), "PUSH should not be Win");
	}	
}
