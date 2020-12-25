
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		moveToNewspaper();
		pickingNewspaper();
		backToHome();
	}

	private void moveToNewspaper() {
		while (frontIsClear()) {
			move();
		}
		turnRight();
		move();
		turnLeft();
		move();
		/*
		 * Pre-condition : Facing East, at top corner of karel's house postcondition:
		 * facing East, while being on Newspaper
		 */
	}

	private void pickingNewspaper() {
		if (beepersPresent()) {
			pickBeeper();
			turnAround();
		}
		/*
		 * peeking beeper and turn around
		 */
	}

	private void backToHome() {
		while (frontIsClear()) {
			move();
		}
		turnRight();
		move();
		turnRight();
		/*
		 * precondition; facing West, out of karel's house post condition ; facing east,
		 * at top of karel's house
		 */

	}

}
