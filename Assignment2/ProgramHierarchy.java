
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	private static final double RECT_HEIGHT = 50;
	private static final double RECT_WIDTH = 100;

	public void run() {

		double posX = getWidth() / 2; // middle coordinates of x
		double posY = getHeight() / 2; // middle coordinates of y
		int x = 3; // coefficient of distance in x axes
		int y = 2; // coefficient of distance in y axes
		getMainRect(posX, posY, y);
		getRectandGetLabel(posX, posY, x);
		getLines(posX, posY, x, y);
		getLabel("Program", posX - RECT_WIDTH / 2, posY - y * RECT_HEIGHT, 1, -1); // adds upper label
	}

	private void getRectandGetLabel(double posX, double posY, int x) { // draws 3 rectangle and proper labels
		for (int i = -1; i <= 1; i++) {
			GRect rect = new GRect(posX - (x * i + 1) * RECT_WIDTH / 2, posY, RECT_WIDTH, RECT_HEIGHT);
			add(rect);
			addLabels(posX, posY, x, i);
		}
	}

	private void getMainRect(double posX, double posY, int y) { // draws upper rectangle
		GRect rect = new GRect(posX - RECT_WIDTH / 2, posY - y * RECT_HEIGHT, RECT_WIDTH, RECT_HEIGHT);
		add(rect);

	}

	private void getLines(double posX, double posY, int x, int y) { // draws 3 line
		for (int i = -1; i <= 1; i++) {
			GLine line = new GLine(posX, posY - (y - 1) * RECT_HEIGHT, posX - x * i * RECT_WIDTH / 2, posY);
			add(line);
		}
	}

	private void getLabel(String programType, double posX, double posY, int x, int i) { // adds GLabels for proper
																						// Rectangles

		GLabel prog = new GLabel(programType);
		GLabel p = new GLabel(programType, posX - x * i * RECT_WIDTH / 2 - prog.getWidth() / 2,
				posY + RECT_HEIGHT / 2 + prog.getAscent() / 2);
		add(p);

	}

	private void addLabels(double posX, double posY, int x, int i) { // adds proper strings for proper lower three
																		// rectangle
		if (i == 1) {
			getLabel("GraphicsProgram", posX, posY, x, i);
		} else if (i == 0) {
			getLabel("ConsoleProgram", posX, posY, x, i);
		} else {
			getLabel("DialogProgram", posX, posY, x, i);
		}

	}
}
