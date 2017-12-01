package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import support.BinarySearchTree;

public class FileReader {

	private File mainFile;
	private String path;

	public FileReader(String path) {
		mainFile = new File(path);
		this.path = path;
	}

	/**
	 * Saves medication to the list as "time:name:dose"
	 * 
	 * @param time
	 * @param name
	 * @param dose
	 * @throws IOException
	 */
	public void saveToFile(int time, String name, int dose) throws IOException {
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		printer.println(time + ":" + name + ":" + dose);
		printer.close();
	}

	/**
	 * Used to sort the list of medication.
	 * 
	 * @throws FileNotFoundException
	 *             - mainFile doesn't have a valid path
	 */
	public void sortAlphabetical() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		BinarySearchTree<String> x = new BinarySearchTree<String>();
		while (reader.hasNextLine()) {
			x.add(reader.nextLine());
		}
		reader.close();
	}

	public void sortTime() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		BinarySearchTree<String> x = new BinarySearchTree<String>();
		
	}

	public void sortType() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		BinarySearchTree<String> x = new BinarySearchTree<String>();
	}

	/**
	 * This method is used to delete medication from the list.
	 * 
	 * @param del
	 *            - The String that is checked for what to destroy
	 * @throws FileNotFoundException
	 *             - mainFile doesn't have a path
	 */
	public void deleteFromFile(String del) throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		List<String> fileLines = new ArrayList<String>();
		String x;
		while (reader.hasNextLine()) {
			x = reader.nextLine();
			if (!x.equals(del))
				fileLines.add(x);
		}
		reader.close();
		mainFile.delete();
		mainFile = new File(path);
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		while (fileLines.size() > 0) {
			printer.println(fileLines.remove(0));
		}
		printer.close();
	}
}
