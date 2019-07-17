package com.autom.sha2nk;

/**
 Main class for Automation Hero Problem.
*  
* @author Shashank
* @version 1.0
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AutomationHero {
	private static ArrayList<String> filesround;
	private static ArrayList<String> filesmergeround;

	private static SortFile sortFile = new SortFileImpl();

	/**
	 * Main method
	 */
	public static void main(String[] args) {
		System.out.println("Automation Hero");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Input File name with file extension (complete Path)");
		String inputfilename = scanner.nextLine();

		if (!sortFile.checkFile(inputfilename)) {
			System.out.println("Invalid input file");
			System.exit(1);
		}

		System.out.println(
				"Enter Your desired output File name with file extension (complete path) or press enter to use default name for output file");
		String outputfilename = scanner.nextLine();

		if (outputfilename == null || outputfilename.equals("")) {
			outputfilename = "SortedOutputFIle.txt";
		}

		double init = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
		System.out.println(" Start MB: " + init);
		breakSortAndMerge(inputfilename, outputfilename);
		scanner.close();
		double fin = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
		System.out.println(Math.ceil(fin - init) + "MB");

	}

	/**
	 * Breaks The file into parts, sort the elements and save the temporary file.
	 * 
	 * @param inputfilename  Input file name to be sorted
	 * @param outputfilename File name in which sorted output is required
	 */
	public static void breakSortAndMerge(String inputfilename, String outputfilename) {
		if (!sortFile.checkFile(inputfilename)) {
			System.out.println("Invalid input file");
			return;
		}
		filesround = new ArrayList<String>();
		filesmergeround = new ArrayList<String>();

		if (!breakInput(inputfilename)) {
			System.out.println("Error Occured while reading Input file");
			System.exit(1);
		}

		int round = 0;
		while (filesround.size() > 1) {
			System.out.println(
					" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));
			int a = 0, b = 1;
			for (int i = 0; i < filesround.size() / 2; i++) {
				filesmergeround.add(sortFile.mergeFiles(filesround.get(a), filesround.get(b), true, round++));
				a = a + 2;
				b = b + 2;
			}
			if (filesround.size() % 2 == 1) {

				filesmergeround.add(filesround.get(filesround.size() - 1));

			}
			filesround = filesmergeround;
			System.out.println(
					" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));
			filesmergeround = new ArrayList<String>();
			System.gc();
		}
		try {
			File f = new File(outputfilename);
			if (f.exists()) {
				f.delete();
			}
			boolean fOutput = new File(filesround.get(0)).renameTo(new File(outputfilename));
			System.out.println(fOutput ? " File Sorted and saved" : "Operation Failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Output FIle : " + filesround.get(0));
		}

		System.out.println(" Final MB: "
				+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));

	}

	private static boolean breakInput(String inputFile) {
		File file = new File(inputFile);
		System.out.println(" Memory MB: "
				+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
		try {
			Scanner input = new Scanner(file);
			// int chunk = 50, pass = 0;
			int chunk = 50000000, pass = 0;
			while (input.hasNextLine()) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				int count = 0;
				while (input.hasNextLine() && count <= chunk
						&& (((double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
								/ (1024 * 1024)) < 70)) {
					temp.add(Integer.parseInt(input.nextLine()));
					count++;
				}
				Collections.sort(temp);
				String tempfilename = sortFile.writeChunk(temp, pass);
				if (tempfilename == null) {

					System.out.println("Unable to generate temp file");
					System.exit(1);

				}
				filesround.add(tempfilename);
				pass++;
				System.gc();
			}
			System.out.println(
					" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));
			input.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("The requested file was not found.");
			System.exit(1);
		}
		return false;
	}

}
