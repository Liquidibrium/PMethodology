
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	// y position of paddle
	private double paddlePosY = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
	/* Method: run() */
	/** Runs the Breakout program. */

	// paddle rectangle
	private GRect paddle;
	private GOval ball;

	// velocity of ball
	private double vx, vy;
	private RandomGenerator rnd = RandomGenerator.getInstance();
	private int countBricks = NBRICKS_PER_ROW * NBRICK_ROWS;

	public void run() {
		gameInitialization();
		gamePlay();
	}

	// draws bricks and paddle 
	public void gameInitialization() {
		bricks();
		drawPaddle();
	}
	// this is active part of the game 
	public void gamePlay() {
		for (int i = 0; i < NTURNS; i++) {
			ball();
			waitForClick();
			moveBall();
			if (countBricks == 0) { // win game
				removeAll();
				win();
				break;
			}

		}
		if (countBricks > 0) { // game over
			removeAll();
			lost();
		}
	}
	
	
	// draws bricks with proper color
	private void bricks() {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			Color color;
			int NRows = 2;// number of rows with same color
			if (i < NRows) {
				color = Color.RED;
			} else if (i < 2 * NRows) {
				color = Color.ORANGE;
			} else if (i < 3 * NRows) {
				color = Color.YELLOW;
			} else if (i < 4 * NRows) {
				color = Color.GREEN;
			} else {
				color = Color.CYAN;
			}
			rowOfBricks(BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP), color);
		}
	}

	// adds row of bricks
	private void rowOfBricks(double y, Color color) {
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			double posX = WIDTH / 2 - (BRICK_WIDTH * NBRICKS_PER_ROW + (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
			add(brick(posX + i * (BRICK_WIDTH + BRICK_SEP), y, color));
		}
	}

	// this code creates GRect object general brick
	private GRect brick(double x, double y, Color color) {
		GRect br = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		br.setFilled(true);
		br.setColor(color);
		return br;
	}

	// draws paddle
	private void drawPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, paddlePosY, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}

	// moves paddle in application window boarders with mouse move
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > PADDLE_WIDTH / 2 && e.getX() < WIDTH - PADDLE_WIDTH / 2) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, paddlePosY);
		}
	}

	// draws ball in center
	private void ball() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
// give initial velocity to ball and changes it if collied 
	private void moveBall() {
		vy = 3; // initial velocity of ball on y axes
		vx = rnd.nextDouble(1.0, 3.0); // initial random velocity of ball on x axes
		if (rnd.nextBoolean(0.5)) {
			vx = -vx;
		}
		while (true) {
			ball.move(vx, vy); // ball movement
			pause(10);
			collisionToWalls();
			collisionToObject();
			if (ball.getY() + BALL_RADIUS * 2 > HEIGHT) {// next atempt
				remove(ball);
				break;
			}
			if (countBricks == 0) { // win
				break;
			}
		}
	}

	// changes velocity if collides with wall
	private void collisionToWalls() {
		if (ball.getX() - 1 < 0 || ball.getX() + 2 * BALL_RADIUS + 1 > WIDTH) {
			vx = -vx;
		}
		if (ball.getY() < 0) {
			vy = -vy;
		}
	}

	/*
	 * checks for collisions if object is paddle changes direction of speed, 
	 * if object is brick removes it too
	 * 
	 */
	private void collisionToObject() {
		if (paddle == GObject2()) {
			ball.setLocation(ball.getX(), ball.getY() - 4);
			vy = -vy;
		} else if (paddle == GObject5()) {
			if (vx > 0) {
				ball.setLocation(ball.getX() + 3, ball.getY() - 3);
				vy = -vy;
				vx = -vx;
			} else {
				vy = -vy;
			}

		} else if (paddle == GObject7()) {
			if (vx < 0) {
				ball.setLocation(ball.getX() - 3, ball.getY() - 3);
				vy = -vy;
				vx = -vx;
			} else {
				vy = -vy;
			}
		} else if (paddle == GObject1() || paddle == GObject3() || paddle == GObject4() || paddle == GObject6()
				|| paddle == GObject8()) {
			if (paddle.getX() > ball.getX()) {
				ball.setLocation(ball.getX() - 3, ball.getY() + 3);
				vx = -vx;
			} else if (paddle.getX() < ball.getX()) {
				ball.setLocation(ball.getX() + 3, ball.getY() + 3);
				vx = -vx;
			}
		}

		// if object is brick
		else if (null != GObject1()) {
			vy = -vy;
			remove(GObject1());
			countBricks--;
		} else if (null != GObject2()) {
			vy = -vy;
			remove(GObject2());
			countBricks--;
		} else if (null != GObject3()) {
			vx = -vx;
			remove(GObject3());
			countBricks--;
		} else if (null != GObject4()) {
			vx = -vx;
			remove(GObject4());
			countBricks--;
		} else if (null != GObject5()) {
			vy = -vy;
			vx = -vx;
			remove(GObject5());
			countBricks--;
		} else if (null != GObject6()) {
			vy = -vy;
			vx = -vx;
			remove(GObject6());
			countBricks--;
		} else if (null != GObject7()) {
			vy = -vy;
			vx = -vx;
			remove(GObject7());
			countBricks--;
		} else if (null != GObject8()) {
			vy = -vy;
			vx = -vx;
			remove(GObject8());
			countBricks--;
		}
	}

	// object located in north side of the ball
	private GObject GObject1() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1);
		return gobj;
	}

	// object in located south side of the ball
	private GObject GObject2() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + 1);
		return gobj;
	}

	// object located in east side of the ball
	private GObject GObject3() {
		GObject gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + BALL_RADIUS + 1);
		return gobj;
	}

	// object located in West side of the ball
	private GObject GObject4() {
		GObject gobj = getElementAt(ball.getX() - 1, ball.getY() + BALL_RADIUS);
		return gobj;
	}

	// object located in south-east side of the ball
	private GObject GObject5() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS + (BALL_RADIUS + 1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS + (BALL_RADIUS + 1) / Math.sqrt(2));
		return gobj;
	}

	// object located in north-east side of the ball
	private GObject GObject6() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS + (BALL_RADIUS + 1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS - (BALL_RADIUS + 1) / Math.sqrt(2));
		return gobj;
	}

	// object located in south-west side of the ball
	private GObject GObject7() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS - (BALL_RADIUS + 1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS + (BALL_RADIUS + 1) / Math.sqrt(2));
		return gobj;
	}

	// object located in north-west side of the ball
	private GObject GObject8() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS - (BALL_RADIUS + 1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS - (BALL_RADIUS + 1) / Math.sqrt(2));
		return gobj;
	}

	// draws label for winner
	private void win() {
		add(labelInTheMiddle(" WINNER !!! WP "));
	}

	// draws label when game is over
	private void lost() {
		add(labelInTheMiddle(" GAME OVER :(( "));
	}

	// returns label in the middle
	private GLabel labelInTheMiddle(String s) {
		GLabel label = new GLabel(s);
		GLabel lb = new GLabel(s, WIDTH / 2 - label.getWidth() / 2, HEIGHT / 2 - label.getAscent() / 2);
		return (lb);
	}
}
