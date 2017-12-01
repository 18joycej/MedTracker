package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import ch05.queues.LinkedUnbndQueue;
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
	public void saveToFile(String time, String name, int dose) throws IOException {
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
		reader.close();
	}

	public void sortType() throws FileNotFoundException {
		Scanner reader = new Scanner(mainFile);
		BinarySearchTree<String> x = new BinarySearchTree<String>();
		reader.close();
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
		LinkedUnbndQueue<String> fileLines = new LinkedUnbndQueue<String>();
		String x;
		while (reader.hasNextLine()) {
			x = reader.nextLine();
			if (!x.equals(del))
				fileLines.enqueue(x);
		}
		reader.close();
		mainFile.delete();
		mainFile = new File(path);
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		while (!fileLines.isEmpty()) {
			printer.println(fileLines.dequeue());
		}
		printer.close();
	}
}
