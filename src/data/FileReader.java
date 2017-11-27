package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
}
