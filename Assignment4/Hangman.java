
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	// number of attempts
	private static final int NGUESSES = 8;
	// instance parameter of inputed string
	private String inputString = "";
	// instance parameter of hidden string
	private String hidden;
	// instance parameter of secret word
	private String secretWord = "";
	private RandomGenerator rnd = RandomGenerator.getInstance();
	// number of attempts left
	private int numberOfAttempts;
	// adds Hangman lexicon
	private HangmanLexicon hangman = new HangmanLexicon();
	// adds Hangman canvas
	private HangmanCanvas canvas = new HangmanCanvas();
	// unguessed chars
	private String incorrect;

	// adds canvas and HangmanLexicon
	public void init() {
		hangman.HangmanLexicon();
		add(canvas);
	}

	public void run() {
		while (true) {
			playHangman();
			if (!playAgain()) {
				break;
			}
		}
	}

// main code 
	private void playHangman() {
		gameInitialization();
		while (true) {
			activeGame();
			if (hidden.equals(secretWord)) {
				win();
				break;
			} else if (numberOfAttempts == 0) {
				lose();
				break;
			}
			attempts();
		}
	}

// if player want to play again return true 
	private Boolean playAgain() {
		String str = readLine("If you wanna play again enter YES : ");
		str = str.toUpperCase();
		if (str.equals("YES")) {
			return true;
		}
		return false;
	}

	// creates initial objects for the game
	private void gameInitialization() {
		numberOfAttempts = NGUESSES;
		hidden = "";
		incorrect = "";
		println("WELCOME TO HANGMAN!!! ");
		secretWord();
		hidden();
		canvas.reset();
	}

	// this code does active phase
	private void activeGame() {
		showWord();
		input();
		CheckInput();
		canvas.displayWord(hidden);
	}

	// chooses secret word from HangmanLexicon
	private void secretWord() {
		int i = rnd.nextInt(0, HangmanLexicon.getWordCount() - 1);
		secretWord = HangmanLexicon.getWord(i);
		secretWord = secretWord.toUpperCase();
	}

// gives player text  to input character , if the character is not proper it gives note to try again
	private void input() {
		String str = readLine("Your guess: ");
		if (str.length() != 1
				|| ('A' > str.charAt(0) || str.charAt(0) > 'Z') && ('a' > str.charAt(0) || str.charAt(0) > 'z')) {
			println("Try angain ");
			input();
		} else {
			inputString = str;
			inputString = inputString.toUpperCase();
		}
	}

// show what guessed word looks like 
	private void showWord() {
		println("The word now looks like this : " + hidden);
	}

// creates hidden same length word with '-'
	private void hidden() {
		char icon = '-';
		for (int i = 0; i < secretWord.length(); i++) {
			hidden = hidden + icon;
		}
	}

// if inputed char matches with secret word's character returns true  and also changes hidden word
	private Boolean ifMatches() {
		int count = 0;
		for (int i = 0; i < secretWord.length(); i++) {
			if (inputString.charAt(0) == secretWord.charAt(i)) {
				openGuessed(i, inputString.charAt(0));
				count++;
			}
		}
		return count > 0;
	}

// if inputed char is correct print proper text and if not decreases number of attempts
	private void CheckInput() {
		if (ifMatches()) {
			println("That guess is correct ");
		} else {
			println("There are no " + inputString.charAt(0) + "'s in the word");
			numberOfAttempts--;
			incorrect = incorrect + inputString.charAt(0);
			canvas.noteIncorrectGuess(numberOfAttempts, incorrect);
		}
	}

// print attempts left 
	private void attempts() {
		println("your have " + numberOfAttempts + " attempts left");
	}

// changes hidden word with guessed char 
	private void openGuessed(int i, char inputted) {
		hidden = hidden.substring(0, i) + inputted + hidden.substring(i + 1);
	}

// prints text if player wins 
	private void win() {
		println("You guessed : " + secretWord);
		println("You win ");
	}

	// prints text if player loses
	private void lose() {
		println("The word was : " + secretWord);
		println("You lose ");
	}
}
