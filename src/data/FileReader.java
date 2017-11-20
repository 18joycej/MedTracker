package data;

import java.io.File;
import java.io.IOException;

public class FileReader {
	
	private File mainFile;
	
	public FileReader() {
			mainFile = new File("C:");
	}
	
	public FileReader(String path) {
		mainFile = new File(path);
	}
	public void saveToFile(int time, String name, int dose) throws IOException {
		Scanner saver = new Scanner(mainFile);
	}
}
