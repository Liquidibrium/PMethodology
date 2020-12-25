
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		initGame();
		for (int j = 1; j <= 13; j++) {
			for (int i = 1; i <= nPlayers; i++) {
				PlayersTurn(i);
			}
		}
		winner();
	}

	// initializes game
	private void initGame() {
		scores = new int[nPlayers][N_CATEGORIES];
		initScores();
	}

	// reveals winner of the game
	private void winner() {
		int tmp = 0;
		int x = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (totalScores(i + 1) > tmp) {
				tmp = totalScores(i + 1);
				x = i;
			}
		}
		display.printMessage(
				"Congratulations,  " + playerNames[x] + " , you're the winner with a total score of " + tmp + "!");
	}

	// this method is one player turn
	private void PlayersTurn(int player) {
		display.printMessage(playerNames[player - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice");
		display.waitForPlayerToClickRoll(player);
		rollDicesFisrtTime();
		display.displayDice(dices);
		for (int i = 1; i < nRolls; i++) {
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\" ");
			display.waitForPlayerToSelectDice();
			rollSelectedDices();
			display.displayDice(dices);
		}
		display.printMessage("Select the category for this roll");
		choosingCategory(player);
		updatesScores(player);
	}

	// updates upper, lower and total scores
	private void updatesScores(int player) {
		upperScores(player);
		lowerScores(player);
		totalScores(player);
	}

// this method is for player to choose category and then updates it with proper score 
	private void choosingCategory(int player) {
		category = display.waitForPlayerToSelectCategory();
		while (scores[player - 1][category - 1] != sentinel) {
			display.printMessage("Select another free category ");
			category = display.waitForPlayerToSelectCategory();
		}
		int x = score();
		refreshScore();
		display.updateScorecard(category, player, x);
		scores[player - 1][category - 1] = x;
	}

	// sums upper scores
	private void upperScores(int player) {
		int sumUpperScores = 0;
		boolean filled = true;
		for (int i = 0; i < SIXES; i++) {
			if (scores[player - 1][i] != sentinel) {
				sumUpperScores += scores[player - 1][i];
			} else
				filled = false;
		}
		if (filled) {
			display.updateScorecard(UPPER_SCORE, player, sumUpperScores);
			upperBonus(player, sumUpperScores);
		}
	}

	// return total score of player
	private int totalScores(int player) {
		int sum = 0;
		for (int i = 0; i < N_CATEGORIES; i++) {
			if (scores[player - 1][i] != sentinel) {
				sum += scores[player - 1][i];
			}
			display.updateScorecard(TOTAL, player, sum);
		}
		return sum;
	}

	// sums lower scores
	private void lowerScores(int player) {
		int sum = 0;
		boolean filled = true;
		for (int i = UPPER_BONUS; i < CHANCE; i++) {
			if (scores[player - 1][i] != sentinel) {
				sum += scores[player - 1][i];
			} else
				filled = false;
		}
		if (filled) {
			display.updateScorecard(LOWER_SCORE, player, sum);
		}
	}

	// updates upper bonus
	private void upperBonus(int player, int sumUpperScores) {
		if (sumUpperScores >= 63) {
			scores[player - 1][UPPER_BONUS - 1] = 35;
			display.updateScorecard(UPPER_BONUS, player, scores[player - 1][UPPER_BONUS - 1]);
		} else {
			scores[player - 1][UPPER_BONUS - 1] = 0;
			display.updateScorecard(UPPER_BONUS, player, scores[player - 1][UPPER_BONUS - 1]);
		}
	}

	// initializes all scores of player as sentinel
	private void initScores() {
		for (int i = 0; i < nPlayers; i++) {
			for (int j = 0; j < N_CATEGORIES; j++) {
				scores[i][j] = sentinel;
			}
		}
	}

	// saves number of same score in array
	private void diceScores() {
		for (int i = 0; i < N_DICE; i++) {
			int x = dices[i];
			diceScores[x - 1]++;
		}
	}

	// returns proper scores depending on category which was selected
	private int score() {
		diceScores();
		if (category == ONES) {
			return (ONES * diceScores[ONES - 1]);
		} else if (category == TWOS) {
			return (TWOS * diceScores[TWOS - 1]);
		} else if (category == THREES) {
			return (THREES * diceScores[THREES - 1]);
		} else if (category == FOURS) {
			return (FOURS * diceScores[FOURS - 1]);
		} else if (category == FIVES) {
			return (FIVES * diceScores[FIVES - 1]);
		} else if (category == SIXES) {
			return (SIXES * diceScores[SIXES - 1]);
		} else if ((category == THREE_OF_A_KIND) && isThreeOfAKind()) {
			return sumDices();
		} else if ((category == FOUR_OF_A_KIND) && isFourOfAKind()) {
			return sumDices();
		} else if ((category == FULL_HOUSE) && isFullHouse()) {
			return 25;
		} else if ((category == SMALL_STRAIGHT) && isSmallStraight()) {
			return 30;
		} else if ((category == LARGE_STRAIGHT) && isLargeStraight()) {
			return 40;
		} else if ((category == YAHTZEE) && isYAHTZEE()) {
			return 50;
		} else if (category == CHANCE) {
			return sumDices();
		}
		return 0;
	}

	// if combination is YAHTZEE returns true
	private boolean isYAHTZEE() {
		for (int i = 0; i < NScores; i++) {
			if (diceScores[i] == 5) {
				return true;
			}
		}
		return false;
	}

	// if combination is large straight returns true
	private boolean isLargeStraight() {
		int length = 5;
		return isStraight(length);
	}

// check's for straight with its length 
	private boolean isStraight(int length) {
		int numb = 0;
		for (int i = 0; i < NScores; i++) {
			if (diceScores[i] >= 1) {
				numb++;
				if (numb == length) {
					return true;
				}
			} else {
				numb = 0;
			}
		}
		return false;
	}

	// if combination is small straight returns true
	private boolean isSmallStraight() {
		int length = 4;
		return isStraight(length);
	}

// if combination is Full house return true 
	private boolean isFullHouse() {
		for (int i = 0; i < NScores; i++) {
			for (int j = 0; j < NScores; j++) {
				if (diceScores[i] == 3 && diceScores[j] == 2) {
					return true;
				}
			}
		}
		return false;
	}

	// if combination is four of a kind returns true
	private boolean isFourOfAKind() {
		for (int i = 0; i < NScores; i++) {
			if (diceScores[i] >= 4) {
				return true;
			}
		}
		return false;
	}

// if combination is three of a kind returns true 
	private boolean isThreeOfAKind() {
		for (int i = 0; i < NScores; i++) {
			if (diceScores[i] >= 3) {
				return true;
			}
		}
		return false;
	}

// sums scores on dices 
	private int sumDices() {
		int x = 0;
		for (int i = 0; i < N_DICE; i++) {
			x += dices[i];
		}
		return x;
	}

// all elements of array becomes zeroes 
	private void refreshScore() {
		for (int i = 0; i < NScores; i++) {
			diceScores[i] = 0;
		}
	}

	// gives selected element in dices array random number from 1 to 6 , if dice is
	// not selected the number on it remains same
	private void rollSelectedDices() {
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				dices[i] = rnd.nextInt(1, 6);
			}
		}
	}

	// gives all element in dices array random number from 1 to 6
	private void rollDicesFisrtTime() {
		for (int i = 0; i < N_DICE; i++) {
			dices[i] = rnd.nextInt(1, 6);
		}
	}

	// array of 5 dices with scores
	private int[] dices = new int[N_DICE];
	// number of possible score
	private int NScores = 6;
	// creates array of number of dice's scores
	private int[] diceScores = new int[NScores];
	// crates array of players and category with their proper scores
	private int[][] scores;
	// number of category
	private int category;
	// sentinel to fill scores array
	private final int sentinel = -1;

	private RandomGenerator rnd = RandomGenerator.getInstance();

	/* Private instance variables */
	private int nRolls = 3;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;

}
