package com.autom.sha2nk;

import java.io.BufferedReader;
/**
* Automation Hero Problem.
*  
* @author Shashank
* @version 1.0
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class SortFileImpl implements SortFile {

	/** Checks if file present/accessible or not in system. 
	  * @param inputFile File name */
	public  boolean checkFile(String inputFile) {
		if(inputFile == null || inputFile.equals(""))
			return false;
		
		File f = new File(inputFile);
		return f.exists();
	}

	/** Writes the data to a file. 
	  * @param numbers List of number to be written to file
	  * @param pass an int value to keep record of temp files genertaed */
	public String writeChunk(ArrayList<Integer> numbers, int pass) {
		try {
			System.out.println(
					" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));
			File file = new File("tempFile" + pass + ".txt");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			Iterator<Integer> it = numbers.iterator();
			while (it.hasNext()) {
				writer.println(it.next() + "");
			}
			System.out.println(
					" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));
			writer.close();
			System.gc();
			return ("tempFile" + pass + ".txt");
		} catch (IOException e) {
			System.out.println("Failed");
		}
		return null;
	}
	
	/** Merge the data of two files. 
	  * @param one first file
	  * @param two second file
	  * @param compare compare files before merging to store in a sorted format
	  * @param round int value to keep record of files merged */
	public String mergeFiles(String one, String two, boolean compare, int round) {
		FileReader f1, f2;
		String n1 = "", n2 = "";
		PrintWriter writer = null;
		
		try {
			f1 = new FileReader(one);
			f2 = new FileReader(two);
			File file = new File("round" + round + ".txt");
			writer = new PrintWriter(file, "UTF-8");
			boolean movefirst = true, movesecond = true;
			BufferedReader br1 = new BufferedReader(f1), br2 = new BufferedReader(f2);

			try {
				while (true) {
					if (movefirst)
						if ((n1 = br1.readLine()) == null)
							break;
					if (movesecond)
						if ((n2 = br2.readLine()) == null)
							break;
					if (Integer.parseInt(n1)<Integer.parseInt(n2)) {
						writer.println(n1);
						movesecond = false;
						movefirst = true;
					} else {
						writer.println(n2);
						movefirst = false;
						movesecond = true;
					}
				}
				if (n1 != null) {
					writer.println(n1);
					while ((n1 = br1.readLine()) != null) {
						writer.println(n1);
					}
				}
				if (n2 != null) {
					writer.println(n2);
					while ((n2 = br2.readLine()) != null) {
						writer.println(n2);
					}
				}
				System.gc();
				System.out.println(
						" Memory MB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
								/ (1024 * 1024));
				writer.close();
				br1.close();
				br2.close();
				
				new File(one).delete();
				new File(two).delete();
			} catch (IOException e) {
				System.out.println("There is an error with the input files.");
				System.exit(1);
			}

		} catch (FileNotFoundException e) {
			System.out.println("The requested file was not found.");
			System.exit(1);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "round" + round + ".txt";
		
		}
	
	
}
