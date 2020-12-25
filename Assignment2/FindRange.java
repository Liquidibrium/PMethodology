
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	public void run() {
		println("This program finds largest and smallest numbers");
		findRange();
	}

	private void findRange() {
		int a = readInt("- "); // a is The first number imputed
		if (a == SENTINEL) {
			println("NOT A PROPER INTEGER");
		}
		int min = a; // first number is both , min and max
		int max = a;
		while (a != SENTINEL) {
			int b = readInt("- ");
			if (b != SENTINEL) { // if b is not SENTINEL , this code gets finds if new integer is "new" max or
									// "new" min
				if (b < min) {
					min = b;
				} else if (b > max) {
					max = b;
				}
			}
			if (b == SENTINEL) {// if b is sentinel , console stops and prints
				println("max " + max);
				println("min " + min);
			}
		}
	}
}
