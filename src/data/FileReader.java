package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

import ch05.queues.LinkedUnbndQueue;
import support.BinarySearchTree;
import support.Medication;
public class FileReader {

	private File mainFile;
	private String path;

	public FileReader(String path) {
		this.path = path;
		mainFile = new File(path);
		System.out.println(path);
		if(!mainFile.exists()) {
			try {
				mainFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Saves medication to the list as "time:name:dose"
	 * 
	 * @param time
	 * @param name
	 * @param dose
	 * @throws IOException
	 */
	public void saveToFile(Medication med) throws IOException {
		PrintWriter printer = new PrintWriter(new FileOutputStream(mainFile));
		String temp=med.getName()+"$"+med.getDoseage()+"$"+med.getUrgency()+"$"+med.getTimeSetting()+"$"+med.getDateSetting();
		if(med.getTimeSetting()==1) {
			temp=temp+"$"+med.getSpecificTime();
		}
		else if(med.getTimeSetting()==2) {
			int[] temp2=med.getMultipleTimes();
			for(int i=0;i<temp2.length;i++) {
				temp=temp+"$"+temp2[i];
			}
		}
		if(med.getDateSetting()==1) {
			temp=temp+"$"+med.getDayInterval();
		}
		else if(med.getDateSetting()==2) {
			int[] temp2=med.getSelectDays();
			for(int i=0;i<temp2.length;i++) {
				temp=temp+"$"+temp2[i];
			}
		}
		System.out.println(temp);
		printer.println(temp);
		printer.close();
	}
	public LinkedUnbndQueue<String> readFromFile() throws FileNotFoundException {
		File readFile = new File(path);
		Scanner reader = new Scanner(mainFile);
		LinkedUnbndQueue<String> fileLines = new LinkedUnbndQueue<String>();
		while(reader.hasNext()) {
			fileLines.enqueue(reader.nextLine());
		}
		reader.close();
		return fileLines;
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
