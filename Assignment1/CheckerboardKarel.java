
/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		getCheckeBoarded();
	}

	private void getCheckeBoarded() { // this code makes karel to get checker board done for every condition
		if (frontIsClear()) {
			getChekerBoardedVol1();
		} else {
			getChekerBoardedVol2();
		}
	}

	private void getChekerBoardedVol1() { // this code works in the world when number of columns is more than one
		putBeepersOnRow();
		while (leftIsClear()) {
			moveUpIfEastIsBlocked();
			putBeepersOnRow();
			moveUpIfWestIsBlocked();
			putBeepersOnRow();
		}

	}

	private void putBeepersOnRow() { // Karel puts Beeper on a row
		while (frontIsClear()) {
			putBeeper();
			move();
			if (frontIsClear()) {
				move();
				if (frontIsBlocked()) {
					putBeeper();
				}
			}
		}
	}

	private void moveUpIfEastIsBlocked() { // if beeper presents moves up, faces West and moves one block forward ,
											// if beeper not presents just moves up and faces West
		if (facingEast()) {
			if (frontIsBlocked()) {
				turnLeft();
				if (beepersPresent()) {
					moveUpFromEastSide();
					move();

				} else {
					moveUpFromEastSide();
				}

			}
		}
	}

	private void moveUpFromEastSide() {

		// faces north, moves one block forward and faces west side
		if (frontIsClear()) {
			move();
			turnLeft();
		}
	}

	private void moveUpIfWestIsBlocked() { // if beeper presents moves up, faces East and moves one block forward ,
											// if beeper not presents just moves up and faces east
		while (facingWest()) {
			if (frontIsBlocked()) {
				turnRight();
				if (beepersPresent()) {
					moveUpFromWestSide();
					move();
				} else {
					moveUpFromWestSide();
				}
			}
		}
	}

	private void moveUpFromWestSide() {
		// faces north, moves one block forward and faces East side
		if (frontIsClear()) {
			move();
			turnRight();
		}
	}

	private void getChekerBoardedVol2() {
		/*
		 * pre condition : facing East and front Is Blocked , standing at 1X1 post, puts
		 * beepers on column condition: standing at top of column , facing north
		 */
		turnAround();
		if (frontIsBlocked()) {
			turnRight();
			while (frontIsClear()) {
				putBeeper();
				move();
				if (frontIsClear()) {
					move();
					if (frontIsBlocked()) {
						putBeeper();
					}
				}
			}
		}
	}
}
