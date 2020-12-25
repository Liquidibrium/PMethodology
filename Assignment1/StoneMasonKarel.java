
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	public void run() {
		stoneMason();
	}

	private void stoneMason() {
		while (frontIsClear()) {
			repearColumn();
			movingDown();
			movingFourTimes();
		}
		repearColumn();
		/*
		 * pre-condition: facing East, Karel is standing at 1X1 cell post condition:
		 * facing North, Karel is standing at top of last column
		 */
	}

	private void repearColumn() { // repears column
		turnLeft();
		while (frontIsClear()) {
			if (beepersPresent()) {
				move();
			} else {
				putBeeper();
				move();
			}
		}
		if (noBeepersPresent()) {
			putBeeper();
		}
		/*
		 * pre-condition facing East, standing at bottom block post condition facing
		 * North, standing at top of column
		 */
	}

	public void movingDown() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
		/*
		 * precondition : facing north, Karel is standing at top of the column
		 * postcondition : facing East , Karel is standing at bottom of the column
		 */
	}

	public void movingFourTimes() {
		for (int i = 0; i < 4; i++) {
			move();
		}
		/*
		 * facing East ,standing at bottom , moving 4 block ahead
		 */
	}

}
