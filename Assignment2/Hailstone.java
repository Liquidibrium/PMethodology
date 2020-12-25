
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	private static final int FINAL_NUMBER = 1;

	public void run() {
		hailStone();
	}

	private void hailStone() {
		int count = 0; // step counter
		int n = readInt("Enter a number : ");
		while (n != FINAL_NUMBER) {
			if (n % 2 == 0) {// this code divides n by 2 if n is even and increases int count by 1
				int b = n / 2;
				println(n + " is even so I take half :" + b);
				n = b;
				count++;
			} else { // this code multiplies n by 3 and adds 1 if n is odd and increases int count by
						// 1
				int c = n * 3;
				int b = c + 1;
				println(n + " is odd so I make 3*n+1 :" + b);
				n = b;
				count++;
			}

		}
		println("The process took " + count + "to reach" + FINAL_NUMBER);
	}
}
