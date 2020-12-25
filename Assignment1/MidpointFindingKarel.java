
/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		movingLikeAChessHorse();
		movingDown();
		putBeeper();
	}

	private void movingLikeAChessHorse() {// moves two blocks up, one block left if possible
		turnLeft();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
			}
			turnRight();
			if (frontIsClear()) {
				move();
			}
			turnLeft();
		}
		/*
		 * pre-condition : facing East, standing at 1X1 post condition : facing North ,
		 * standing at top and middle of world
		 */
	}

	private void movingDown() {
		turnAround();
		while (frontIsClear()) {
			move();
		}

	}
	/*
	 * pre condition : facing North , standing at top of column post condition :
	 * facing South , standing at bottom of column
	 */
}