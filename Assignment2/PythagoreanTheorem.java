
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("enter two integer (a;b) to compute pythagoran theorem ");
		int a = readInt("a: ");
		int b = readInt("b: ");
		if (a >= 0 && b >= 0) { // check if inputed number is not negative
			println(" length Of Hypotenuse = " + lengthOfHypotenuse(a, b));
		} else {
			println(" ERROR ");
		}
	}

	private double lengthOfHypotenuse(double a, double b) { // get two numbers squares and sums then gets square root
															// from sum
		double d = Math.pow(a, 2) + Math.pow(b, 2);
		double c = Math.sqrt(d);
		return c;
	}

}
