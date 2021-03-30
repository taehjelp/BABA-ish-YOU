package babagame;

import java.io.File;
//import java.util.ArrayList;

public interface BABAfileInterface {
// save og load smilefjes
	public void save(String content, File filename);
	public char[][] load(File filename);
}
