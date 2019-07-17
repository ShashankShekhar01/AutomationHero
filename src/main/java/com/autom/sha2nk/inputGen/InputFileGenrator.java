package com.autom.sha2nk.inputGen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class InputFileGenrator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			File file = new File("testnewsort.txt");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			Random random = new Random();

			while (true) {

				writer.println(random.nextInt() + "");
				if (getFileSizeMegaBytes(file) >= 250) {
					break;
				}
			}

			writer.close();
			
		} catch (IOException e) {
			System.out.println("Failed");

		}

	}
	
	private static double getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) ;
	}

}
