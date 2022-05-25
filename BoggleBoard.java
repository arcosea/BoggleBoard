import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;



public class BoggleBoard {
	
	// Create a 2D char array private field for the board, an integer score keeper, and board Size
	private char[][] board;
	private int points;
	private int boardSize;
	
	
	
	/**
	 * This method will read the text file 'fileName' that contains the characters that
	 * 		make up to board. The first line in the file is an integer that describes 
	 * 		the size of the board as n*n. The second line contains all the characters
	 * 		that make up the board. The first n characters are the first row in the board
	 * 		The second n characters in the line are those to be put in the second row. etc.
	 * 
	 * @param fileName: the file that is read
	 */
	public void readFile(String fileName) {
		// Initialize a File named file that reads in fileName
		File file = new File(fileName);
		try {
			
			// Initiate a Scanner 'scanFile' using 'file'
			Scanner scanFile = new Scanner (file);
					
			// Store the size of the board
			boardSize = Integer.parseInt(scanFile.nextLine());
			
			// Instantiate the size of the board
			board = new char[boardSize][boardSize];
			
			// Create a nested for loop that cycles through the row and column of the board
			for(int currentRow = 0; currentRow < boardSize; currentRow++) {
				for(int currentCol = 0; currentCol < boardSize; currentCol++) {
					
					// Store current character as a String and convert it to a char
					String current = scanFile.next();
					char character = current.charAt(0);
					
					// Set location in board to current character String
					board[currentRow][currentCol] = character;
					
				}
			}					
			// Close file
			scanFile.close();
			
		} catch(FileNotFoundException e) {		
				e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 	This method reads the input from the board and characterizes it in order for
	 * 		it to get displayed to the client.
	 * 		Format:
	 * 				+-------+
	 *				|A E H X|
	 *				|A I T N|
	 *				|S E C E|
	 *				|A W T B|
	 *				+-------+
	 *
	 * @return String format of board like shown above
	 * 		
	 */
	public String displayBoard() {
		// Create a String Builder
		StringBuilder display = new StringBuilder("+");
		
		//  place '-' in for one less than twice the size of the board for the top Border
		for(int cur = 0; cur < 2 * boardSize - 1; cur ++) {
			display.append("-");
		}
		
		// Display the corner and move to the next line
		display.append("+\n");
		
		
		// Create a next loop that cycles through the row and columns of the board except the last
		for(int row = 0; row < boardSize; row ++) {
			
			// Include the left border of the board
			display.append("|");
			
			for(int col = 0; col < boardSize -1 ; col++) {
				
				// Add the current character from the board to the display string
				display.append(board[row][col] + " ");
			}
			
			// Print the characters in last column and Include the right border of the board & move to the next line
			display.append(board[row][boardSize-1]);
			display.append("|\n");
			
		}
		
		// Finally, place the bottom border of the board
		display.append("+");
		for(int cur = 0; cur < 2 * boardSize -1 ; cur ++) {
			display.append("-");
		}
		display.append("+");
		
		System.out.print(display.toString());
		System.out.println();
		return display.toString();
	}
	
	
	/**
	 *  This method searches the board in order to determine if a word is present.
	 *  	The method cycles through each index of the boggle board and for each index
	 *  	it checks to see if character in that index is same as first character in the 
	 *  	word searched until it is and then looks at surrounding neighbors to see if it 
	 *  	can find/make the word searched for using the recursive method findWord
	 *  	
	 *  @param word: String of the word that is being searched for
	 *  
	 *  @return true if word is found on board. False, otherwise
	 */
 
	public boolean searchBoard(String word) {
		// Set the word to upper Case
		word = word.toUpperCase();
		
		// Loop through the whole board (rows and columns)
		for(int r = 0; r < boardSize; r ++) {
			for(int c = 0; c < boardSize; c++) {
				// Create a char 2d array of same size as boggle board to keep track of indexes checked
				char[][] explored = new char[boardSize][boardSize];
				
				// Search for the word beginning with the specified row and column coordinates & a clear 2d array
				if(findWord(word, r, c, explored)) {
					return true;
				}
			}
		}
		// if all fails, return false (word not in board)
		return false;
	}
	
	
	/**
	 * 	This method takes in a word, 2d array coordinates and another 2d array explored to keep track of explored locations
	 * 		The method uses takes in a coordinate then checks to see if current location on board contains the same character 
	 * 		as the words first character. If so, it checks all (8 with diagonals) surrounding neighbors with a substring
	 * 		of the word (no longer including the first character in word) until the word is empty
	 * 
	 * 
	 * 
	 * @param word: The word being searched for on the boggle board
	 * @param row: row index in 2d boggle board array
	 * @param col: column index in 2d boggle board array
	 * @param explored: 2d char array to keep track of explored locations.
	 * 			 Use '@' to keep track of visited locations
	 * 
	 * @return true if word is found, else otherwise
	 */
	
	private boolean findWord(String word, int row, int col, char[][] explored) {
		
		// Base case: word is empty and thus found
		if(word.length() < 1) {
			return true;
		}
		
		// Determine if row and column are invalid. 
		if(row < 0 || col < 0 || row > boardSize -1  || col > boardSize -1 ) {
					
			// Return false if invalid and backtrack
			return false;
		}
		
		// Check to see if current location on board is the first character of the word && current location is unexplored
		if(board[row][col] == word.charAt(0) && explored[row][col] != '@') {
			// Mark current Location as explored
			explored[row][col] = '@';	
			
			// Check Upper Left Diagonal with word not including first character and currently explored maze
			if(findWord(word.substring(1), row-1, col-1, explored)) {
				return true;
			}
			
			// Check Up (Above) coordinate with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row-1 , col, explored)) {
				return true;
			}
			
			// Check Upper Right Diagonal with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row-1 , col+1, explored)) {
				return true;
			}
			
			// Check Right coordinate with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row , col +1 , explored)) {
				return true;
			}
			
			// Check Bottom Right Diagonal with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row +1 , col+1, explored)) {
				return true;
			}
			
			// Check Down (Below) coordinate with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row +1 , col, explored)) {
				return true;
			}
			
			// Check Bottom Left Diagonal with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row +1 , col-1, explored)) {
				return true;
			}
			
			//Check Left coordinate with word not including first character and currently explored maze
			else if(findWord(word.substring(1), row, col-1, explored)) {
				return true;
			}
			
		}
		
		// If all fails, return false
		return false;
	}
	
	
}
