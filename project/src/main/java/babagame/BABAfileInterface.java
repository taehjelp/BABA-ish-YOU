package babagame;

import java.io.File;

public interface BABAfileInterface {
	
	public void save(String content, File filename);
	
	public char[][] load(File filename, int height, int width);
}
