
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {

		int amount = BRICKS_IN_BASE;
		double x, y;
		x = getWidth() / 2 - amount / 2.0 * BRICK_WIDTH; // start coordinate of x
		y = getHeight() - BRICK_HEIGHT; // start coordinate of y

		pyramidBuilder(amount, x, y);

	}

	private void pyramidBuilder(int amount, double x, double y) { // pyramid with rows
		for (int j = 0; j < BRICKS_IN_BASE; j++) {
			BuildingPyramidsRow(amount, x, y, j);
			amount--;

		}
	}

	private void BuildingPyramidsRow(int amount, double x, double y, int j) { // builds row of pyramid
		for (int i = 0; i < amount; i++) {
			GRect rect = new GRect(x + (i + j / 2.0) * BRICK_WIDTH, y - j * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
			add(rect);
		}
	}
}
