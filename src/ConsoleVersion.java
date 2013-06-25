import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleVersion {
	private Scanner in = new Scanner(System.in);
	public Random rGen = new Random();
	public static final String FILE="input/dictionary.txt";

	public void run() {

		do {
			// create an array to store the random word so we don't generate
			// more than one word
			String phrase = getword();
			char[] randPhrase = phrase.toCharArray();
			// create guesses array with same size and the display word
			char[] guesses = new char[randPhrase.length];
			
			popGuesses(guesses);
			
			String counter = "";
			System.out.println(randPhrase);
						
			while(true) {
			
				if(Arrays.equals(guesses, randPhrase)){
					System.out.println("you won");
					break;
				}
					// display the guesses array with "-"
				display(guesses);
				System.out.println();
				String guess = getString("Guess");
				
				if(phrase.contains(guess))
				{
					processor(randPhrase, guesses, guess);
				}
				else
				{
					System.out.println("Sorry "+guess+" is not part o the word");
				}
								
				// pass the randPhrase array, guesses array and user input to be
				// process and displayed
				
				counter += guess + ",";
				System.out.println(counter);
				
				}
				
				
				
			

		} while (playAgain());//while loops  ends

	}

	public void popGuesses(char[] guesses) {
		// populate the guess array with "-"
					for (int i = 0; i < guesses.length; i++) {
						guesses[i] = '_';
					}
		
	}

	// passed randPhrase array, guesses array and guess string to compare and
	// populate guesses
	public void processor(char[] randPhrase, char[] guesses, String guess) {

		for (int i = 0; i < randPhrase.length; i++) {
			if (guess.charAt(0) == randPhrase[i]) {
				
				guesses[i] = guess.charAt(0);
			
			}
			
		}

	}

	

	// displays guesses array
	public static void display(char[] guesses) {

		for (int i = 0; i < guesses.length; i++) {

			System.out.print(guesses[i] + " ");

		}

	}

	// generate a random word and return via char array
	public String getword() {
		String[] words = textFile();

		int n = words.length;
		int r = rGen.nextInt(n);
		String word = words[r];
		
		return word;
	}

	// ask user from whether to play or not
	private boolean playAgain() {

		System.out.print("Play Again? ");
		String answer = in.nextLine().toLowerCase();

		if (answer.equals("y") || answer.equals("yes")) {
			return true;
		}

		return false;
	}

	// read from the file, store in string array,
	public String[] textFile() {
		// create a bufferedReader
		BufferedReader reader = null;
		// create a list array and store the values from the text file
		List<String> wordList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(FILE));
			String s = null;

			while ((s = reader.readLine()) != null) {

				wordList.add(s);

			}

		} catch (IOException e) {

			System.out.println(e.getMessage());
			System.exit(-1);
		} finally {
			try {
				// close the file
				reader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
		}
		// convert from arraylist to array and return
		return wordList.toArray(new String[wordList.size()]);

	}

	// This method returns user input
	public String getString(String question) {
		System.out.print(question);
		return in.nextLine();
	}

	public static void main(String[] args) {
		ConsoleVersion source = new ConsoleVersion();
		source.run();

	}

}
