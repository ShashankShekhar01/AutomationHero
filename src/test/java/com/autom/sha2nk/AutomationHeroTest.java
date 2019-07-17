package com.autom.sha2nk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class AutomationHeroTest {

	private static final String TEST_FILE1_TXT = "test-file-1.txt";
	private static final String TEST_FILE2_TXT = "test-file-2.txt";
	private static final String FILE1_OUTPUT = "file1Output.txt";
	private static final String FILE2_OUTPUT = "file2Output.txt";

//	@Ignore

	@Test
	public void noInputFileSpecified() {
		System.out.println("Test for system not crashing due to invalid inputfile");
		AutomationHero.breakSortAndMerge(null, "myTestOutput");

	}

//@Ignore

	@Test
	public void testSmallFile1Sort() throws Exception {
		System.out.println("Test for small file sort");
		ClassLoader classLoader = new AutomationHeroTest().getClass().getClassLoader();

		AutomationHero.breakSortAndMerge((classLoader.getResource(TEST_FILE1_TXT).getFile()), "myTestOutput");
		try {
			assertReaders((classLoader.getResource(FILE1_OUTPUT).getFile()), "myTestOutput");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testBigFile2Sort() throws Exception {
		System.out.println("Testing with a big File. This Test takes some time to execute.");
		try {
			Thread.sleep(200l);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ClassLoader classLoader = new AutomationHeroTest().getClass().getClassLoader();

		AutomationHero.breakSortAndMerge((classLoader.getResource(TEST_FILE2_TXT).getFile()), "myTestOutput2");
		try {
			assertReaders((classLoader.getResource(FILE2_OUTPUT).getFile()), "myTestOutput2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private static void assertReaders(String testchunkComapreFile1, String testchunkComapreFile2) throws Exception {
		FileReader fra = new FileReader(testchunkComapreFile1);
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
