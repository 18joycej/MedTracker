package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileReader {

	private File mainFile;

	public FileReader(String path) {
		mainFile = new File(path);
	}

	public void saveToFile(int time, String name, int dose) throws IOException {
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		printer.println(time + "    " + name + "    " + dose);
		printer.close();
	}
	
	public void sort() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
	}
	
	public void deleteFromFile() {
		
	}
}
