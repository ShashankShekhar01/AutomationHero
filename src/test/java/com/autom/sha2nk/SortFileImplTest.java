package com.autom.sha2nk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class SortFileImplTest {

	private static final ArrayList<Integer> writeCHunk = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
	private static final String testchunkComapreFileExpected = "chunktempFile.txt";
	private static final String testmergeFileExpected = "mergeFile.txt";
	private static final String mergeFile2 = "mergeFile2.txt";
	private static final String mergeFile1 = "mergeFile1.txt";

	private SortFileImpl sortFileImpl = null;

	@Before
	public void init() {
		sortFileImpl = new SortFileImpl();
	}

	@Test
	public void mergeFiles() {
		System.out.println("Test for accurately merge of two files");
		ClassLoader classLoader = new SortFileImplTest().getClass().getClassLoader();

		// File file = new File(classLoader.getResource(fileName).getFile());

		String mergeFile1copy = "tempmergeFile1.txt";
		String mergeFile2copy = "tempmergeFile2.txt";

		try {
			copyFile(new File(classLoader.getResource(mergeFile1).getFile()), new File(mergeFile1copy));
			copyFile(new File(classLoader.getResource(mergeFile2).getFile()), new File(mergeFile2copy));

			String mergedFileNameActual = sortFileImpl.mergeFiles(mergeFile1copy, mergeFile2copy, true, 1);

			assertReaders(testmergeFileExpected, mergedFileNameActual);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	private static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		try (FileInputStream fis = new FileInputStream(sourceFile);
				FileChannel source = fis.getChannel();
				FileOutputStream fos = new FileOutputStream(destFile);
				FileChannel destination = fos.getChannel()) {
			destination.transferFrom(source, 0, source.size());
		}

	}

	@Test
	public void writeChunk() {
		System.out.println("Test for writing data to a file");
		String testchunkComapreFileActual = sortFileImpl.writeChunk(writeCHunk, 1);
		try {
			assertReaders(testchunkComapreFileExpected, testchunkComapreFileActual);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// fail("Not yet implemented");
	}

	private static void assertReaders(String testchunkComapreFile1, String testchunkComapreFile2) throws IOException {
		ClassLoader classLoader = new SortFileImplTest().getClass().getClassLoader();
		FileReader fra = new FileReader(classLoader.getResource(testchunkComapreFile1).getFile());
		BufferedReader actual = new BufferedReader(fra);

		FileReader fre = new FileReader(testchunkComapreFile2);
		BufferedReader expected = new BufferedReader(fre);

		String line;
		while ((line = expected.readLine()) != null) {

			assertEquals(line, actual.readLine());
		}
		actual.close();
		expected.close();

	}
}
