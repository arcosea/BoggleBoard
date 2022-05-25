import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Dictionary {
	
	// Create a private ArrayList field of type String in order to store words from the dictionary
	private ArrayList<String> dictionary;

	
	
	/**
	 * This method reads a text file with words (from a dictionary) on their own line 
	 * 		and adds them to the ArrayList dictionary. This method essentially instantiates the array list
	 * 		
	 * 
	 * @param file: the file that is read 
	 */
	public void readFile(String fileName) {
		
		// Initialize a File named file that reads in fileName
		File file = new File(fileName);
		try {
			// Instantiate the array list
			dictionary = new ArrayList<String>();
			
			// Initiate a Scanner 'scanFile' using 'file'
			Scanner scanFile = new Scanner (file);
			
			// While the scanner has a next line (word)
			while(scanFile.hasNext()) {
				
				// Store word and add it to the dictionary array
				String word = scanFile.nextLine();
				dictionary.add(word);
			}
			
			// Close file
			scanFile.close();
			
			
		} catch(FileNotFoundException e) {		
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 *  This method takes in a word, converts it to lower case (dictionary contains words in lower case)
	 *  	and then returns the boolean result from the call to the dictionary recursive binary search  method
	 *  
	 * @param word: string of the word being searched
	 * @return true if word is in dictionary, false, otherwise
	 */
	
	public boolean searchDictionary(String word) {
		//convert the word to lower case
		word = word.toLowerCase();
		
		// Return with a call to searchDictionary
		return searchDictionary(word, 0, dictionary.size() -1);
		
	}
	
	/**
	 * This method reads in a word of type String and searches through the dictionary 
	 * 		in order to determine if it is a real/valid word. The method returns a boolean
	 * 		depending on whether the word is found or not. This method uses binary search 
	 * 		algorithm in order to locate the word
	 * 
	 * @param word: the word that is searched for in the dictionary
	 * @param first: index of first word in the dictionary
	 * @param last: index of last word in the dictionary
	 * 
	 * @return boolean: True if word is valid (found in dictionary, false otherwise.
	 * 
	 * Note: list must be in alphabetical order in order for binary search to work
	 * 		Also could possibly just turn this into a method with contains 
	 * 		*Dictionary only contains lower case words
	 * 
	 */
	private boolean searchDictionary( String word, int first, int last ) {
		
		// Unsuccessful Search: if first word greater than last word in dictionary (word is not in dict)
		if(first > last) {
			return false;
		}
		// Recursion
		else {
			// Determine index of the middle word
			int mid = (first + last)/2;
			
			// Determine the comparable Result from word to middle word
			int compResult = word.compareTo(dictionary.get(mid));
			
			// if the word searched for is the middle word return true
			if(compResult == 0) {
				return true;
			}	
			// if the word searched for less than (lower index in list) than the middle word
			else if(compResult < 0) {
				
				// Do a search again setting last word as middle word -1 index
				return searchDictionary( word, first, mid -1);
			}
			// If the word searched for is more than (higher index in list) than the middle word
			else {
				
				// Do a search again but with first word becoming the middle word +1 index
				return searchDictionary( word, mid+1, last);
			}
						
		}
	}
	
}
