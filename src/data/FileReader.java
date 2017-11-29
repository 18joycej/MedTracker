package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

	private File mainFile;
	private String path;

	public FileReader(String path) {
		mainFile = new File(path);
		this.path = path;
	}

	public void saveToFile(int time, String name, int dose) throws IOException {
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		printer.println(time + ":" + name + ":" + dose);
		printer.close();
	}

	public void sort() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		String x = "";
		while (reader.hasNext()) {
			x = x + reader.next();
		}
	}

	public void deleteFromFile(String del) throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		List<String> fileLines = new ArrayList<String>();
		String x;
		while(reader.hasNextLine()) {
			x = reader.nextLine();
			if(!x.equals(del));
			fileLines.add(x);
		}
		mainFile.delete();
		mainFile = new File(path);
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		while(fileLines.size()>0) {
			fileLines
		}
	}
}
