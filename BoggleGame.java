/*
 * Name: Erick Arcos
 * Course: CS 270
 * Assignment: Lab08 & Boggle Game
 * Date: 11/15/21
 * Sources consulted: Textbook
 * Known Bugs: May have some case sensitive methods
 * Creativity:  Keep track of words guessed that are in the board (inspired by different student - told by prof)
 *  
 * 
 */


import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileNotFoundException;


public class BoggleGame {
	
	
	
	public static void main(String[] args) {
		// Create a list with Alphabet 
		ArrayList<String> alphabet = new ArrayList<String>();
		addAlphabet(alphabet);
		
		// Make a random board
		makeBoard(5, alphabet, "randomBoard.bac");
		
		// Create a score keeper and initialize it at zero & a list to keep track of words guessed
		int totalScore = 0;
		ArrayList<String> guessedOnBoard = new ArrayList<>();
		
		// Instantiate dictionary and Boggle board objects
		Dictionary myDict = new Dictionary();
		BoggleBoard myBoard = new BoggleBoard();
		
		// Read in the files and size of dictionary
		//myBoard.readFile("board.txt");
		myBoard.readFile("randomBoard.bac");
		myDict.readFile("words.txt");
		
		// Instantiate a scanner
		Scanner keyboard = new Scanner(System.in);
		
		// Display the board
		myBoard.displayBoard();
		
		// Prompt user for a word and store it as upper case
		System.out.print("Enter a word: ");
		String word = keyboard.nextLine();
		
		// While the word read is not empty
		while(!(word.equals(""))) {
			
			// Store the number of points based on word length and add it to totalScore earned
			int points = pointsEarned(word);
			totalScore += points;
			
			// Search dictionary to see if word is in it and store it
			boolean onDict = myDict.searchDictionary(word);
			
			// Determine if the word was already guessed
			boolean checked = alreadyChecked(guessedOnBoard, word);
			
			// Search word in boggle board. 
			boolean onBoard = myBoard.searchBoard(word);
			
			// If not present OR if the word has already been guessed, remove points from totalScore
			if(onBoard == false || checked == true)
				totalScore -=points;
			
			
			// Put word, points, onDict and onBoard booleans into formating string and print
			String results = BoggleGame.toString(word, points, onDict, onBoard, checked);
			System.out.println(results);
			
		
			// Display board and prompt user for a new word and store it
			myBoard.displayBoard();
			System.out.print("Enter a word: ");
			word = keyboard.nextLine();	
		}	
		
		// Display total points earned
		System.out.println("Total score: " + totalScore);
		
		
	} // End of main
	
	
/*****************************************************************************************************/
	
	/**	
	 * 	This method reads in the word, number of points earned based on word length, the boolean for whether
	 * 		or not the word searched is in the dictionary and boggle board and then returns a
	 * 		string based on the input
	 * 
	 * @param word: word being checked
	 * @param points: the number of points earned based on word length
	 * @param inDict: boolean to see whether or not the word is in the dictionary
	 * @param inBoggle: boolean to see whether or not word is in the boggle board
	 * @param gueesed: boolean to see if the word was already guessed
	 * 
	 * @return String format based on values of the parameters
	 */
	private static String toString(String word, int points, boolean inDict, boolean inBoggle, boolean guessed) {
		// Case 1: word is too short (length is less than zero)
		if(points == 0) {
			return "The word " + word + " is too short!";
		}
		
		// Case 2: word is valid (in dictionary) but already guessed
		else if(inDict == true && guessed == true) {
			return "The word " + word + " was already guessed. You score 0 points.";
		}
			
		// Case 3: The word is long enough but not in dictionary
		else if(points > 0 && inDict == false) {
			return "The word " + word + " is not a valid word(not in the dictionary).";
		}
		
		// Case 4: word is long enough and in dictionary but not in boggle board
		else if(points > 0 && inDict == true && inBoggle == false) {
			return "The word " + word + " is a valid word, but is not on the board.";
		}
		
		// Final case: word is long enough and is in both the dictionary and boggle board
		else 
			return "The word " + word + " is good! You score " + points + " points";
	}
	
	/**
	 * 	This method reads a word, determines the length of the word and based on the length,
	 * 	certain points are associated with it
	 * 
	 * @return the points earned based on the word length
	 * @param word: a string whose length is checked
	 * 
	 * Note: these are the rules assuming the board is 4x4
	 */
	private static int pointsEarned(String word) {
		int length = word.length();
		
		// If the length of the word is less than 3. Not a valid. 
		if( length < 3) {
			// Zero points returned
			return 0;
		}
		
		// If length of word is between 3 and 4 characters
		else if(length>= 3 && length <= 4) {
			// 1 point is returned
			return 1;
		}
		
		// If length of word is 5
		else if(length == 5) {
			// 2 points are earned
			return 2;
		}
		
		// If length of word is 6
		else if(length == 6) {
			// 3 points are earned
			return 3;
		}
		// If length of word is 7
		else if(length == 7) {
			// 5 points are earned
			return 5;
		}
		
		// If length is 8 or more, 11 points are earned
		else 
			return 11;
	}
	
	
	/**
	 * 	This method reads in a list of valid words (in dictionary) that have already been guessed and 
	 * 		adds it to the list if it has not been guessed already, in order to prevent duplicate points from being added
	 * 
	 * @param list: list of valid words that have been guessed
	 * @param word: word being checked
	 * @return true if already guessed, false if not in list
	 */
	private static boolean alreadyChecked(ArrayList<String> list, String word) {
		// Convert word to lowerCase
		word = word.toLowerCase(); 
		
		// Loop through list
		for(int curr = 0; curr < list.size(); curr++) {
			// Compare current to word and if its already in the list, return true
			String currentWord = list.get(curr);
			if(word.equals(currentWord)) {
				return true;
			}
		}
		// If not in list, add word to list and return false
		list.add(word);
		return false;
	}
	
	/**
	 * 	This method gets passed an empty Array list and it gets filled with letters in alphabetic order
	 * @param alph: array list that is to be filled with alphabet
	 */
	private static void addAlphabet(ArrayList<String> alph) {
		// Add all the letters in alphabetic order
		for(char letter = 'A'; letter <= 'Z'; letter++) {
			alph.add(Character.toString(letter));
		}
	}
	
	
	/**
	 * This method generates text file with random letters of the alphabet to simulate
	 * 		a random board
	 * 
	 * @param size of board (nxn)
	 * @param alph: array of alphabet
	 * @param boardFile prints to file of this name
	 */
	private static void makeBoard(int size, ArrayList<String> alph, String boardFile) {
		// Instantiate a random object
		Random rand = new Random();
		
		// Try-catch for board
		try {
			// Create a writer for file named board
			PrintWriter randomBoard = new PrintWriter(boardFile);
			
			// Print size of board
			randomBoard.println(size);
			
			// Loop through size squared number 
			for(int curr = 0; curr < size*size; curr++) {
				
				// Get a random index from 0 to 25
				int randIndex = rand.nextInt(26);
				
				// Store letter from alphabet at that random index
				String randLetter = alph.get(randIndex);
				
				// Write it to the file
				randomBoard.print(randLetter + " ");
				
			}	
			// Close file
			randomBoard.close();
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
