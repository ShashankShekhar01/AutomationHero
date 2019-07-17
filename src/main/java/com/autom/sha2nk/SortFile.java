package com.autom.sha2nk;
/**
* Automation Hero Problem.
*  
* @author Shashank
* @version 1.0
*/
import java.util.ArrayList;

public interface SortFile {

	
	public boolean checkFile(String inputFile);
	
	public String writeChunk(ArrayList<Integer> numbers, int pass);
	
	public String mergeFiles(String one, String two, boolean compare, int round); 


}
