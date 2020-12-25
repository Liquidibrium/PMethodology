
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */
import acm.program.*;
import acm.graphics.*;
import acm.program.*;
import java.awt.*;

import com.sun.prism.paint.Color;

public class Target extends GraphicsProgram {
	// Radius of large circle in centimeters
	private static final double RADIUS1 = 14;
	// Radius of medium circle in centimeters
	private static final double RADIUS2 = 10;
	// Radius of small circle in centimeters
	private static final double RADIUS3 = 0.76;

	public void run() {
		double R1 = CentimeterToPixels(RADIUS1);
		double R2 = CentimeterToPixels(RADIUS2);
		double R3 = CentimeterToPixels(RADIUS3);
		double posX = getWidth() / 2;
		double posY = getHeight() / 2;
		GCircle(R1, posX, posY);
		GCircle(R2, posX, posY);
		GCircle(R3, posX, posY);
	}

	private void GCircle(double R, double posX, double posY) { // draws circle
		GOval circle = new GOval(posX - R, posY - R, 2 * R, 2 * R);
		double R2 = CentimeterToPixels(RADIUS2);
		if (R != R2) {
			circle.setFilled(true);
			circle.setColor(java.awt.Color.red);
		} else {
			circle.setFilled(true);
			circle.setColor(java.awt.Color.white);
		}
		add(circle);

	}

	private double CentimeterToPixels(double cm) {// converts centimeters to pixels
		double pix = cm * 72 / 2.54;
		return pix;
	}

}
