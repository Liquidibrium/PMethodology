

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Breakout_Ext extends GraphicsProgram {

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
	private static final int NBRICKS_PER_ROW = 8;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 8;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = BRICK_WIDTH;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 25;

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
	private double countBricks;
	private int hearts = NTURNS;
	private int countCollisions = 1;
	private int score;
	private AudioClip a = MediaTools.loadAudioClip("xylophone-a.wav");
	private AudioClip b = MediaTools.loadAudioClip("xylophone-b.wav");
	private AudioClip c = MediaTools.loadAudioClip("xylophone-c2.wav");
	private AudioClip d = MediaTools.loadAudioClip("xylophone-d1.wav");
	private AudioClip e = MediaTools.loadAudioClip("xylophone-e1.wav");
	private AudioClip f = MediaTools.loadAudioClip("xylophone-f.wav");
	private AudioClip g = MediaTools.loadAudioClip("xylophone-g.wav");

	// heart images
	private GObject img1, img2, img3;

	// score object
	private GLabel sc;

	// score image
	private GImage scoreSGN;

	public void run() {
		play();
	}

	private void play() {
		setBackground(Color.BLACK);
		gameStart();
		gameInitialization();
		while (hearts > 0) {
			ball();
			waitForClick();
			moveBall();
			removeHearts();
			if (countBricks == 0) { // win game
				removeAll();
				win();
				waitForClick();
				removeAll();
				play();
				// break;
			}
		}
		if (countBricks > 0) { // game over
			removeAll();
			lost();
			waitForClick();
			removeAll();
			hearts = 3;
			play();
		}
	}

	// start game start TEXT
	private void gameStart() {
		add(labelInTheMiddle(" CLICK TO START THE GAME "));
		waitForClick();
		removeAll();
	}

// draws initial game objects 
	private void gameInitialization() {
		countBricks = ((double) (NBRICK_ROWS + 1) / (double) 2) * NBRICK_ROWS; // NBRICKS_PER_ROW * NBRICK_ROWS;
		score = 0;
		bricks();
		drawPaddle();
		drawHearts();
		scoreLabel();
		scoreSign();
	}

	// draws bricks with proper color
	private void bricks() {
		int amount = NBRICK_ROWS;
		double x;
		x = WIDTH - BRICK_WIDTH - 2; // start coordinate of x
		for (int j = 0; j < NBRICK_ROWS; j++) {
			brickColumn(x, j, amount);
			amount--;
		}
	}

	// draws columns of brick
	private void brickColumn(double x, int j, int amount) {

		for (int i = 0; i < amount; i++) {
			Color color;
			int NRows = 1;// number of rows with same color
			if (j < NRows) {
				color = Color.RED;
			} else if (j < 2) {
				color = Color.ORANGE;
			} else if (j < 3) {
				color = Color.YELLOW;
			} else if (j < 4) {
				color = Color.GREEN;
			} else if (j < 5) {
				color = Color.BLUE;
			} else if (j < 6) {
				color = Color.getHSBColor(275 / 360F, 1F, 0.25F);// color violet
			} else {
				color = Color.getHSBColor(300 / 360f, 0.45f, 0.93f);// color indigo
			}
			brick(x, j, i, color);
		}
	}

// adds brick 
	private void brick(double x, int j, int i, Color color) {
		GRect rect = new GRect(x - j * (BRICK_HEIGHT + BRICK_SEP),
				BRICK_Y_OFFSET + (i + j / 2.0) * (BRICK_HEIGHT + BRICK_SEP), BRICK_HEIGHT, BRICK_HEIGHT);
		rect.setFilled(true);
		rect.setColor(color);
		add(rect);

	}

// draws heart that's number of tries left 
	private void drawHearts() {
		img1 = heart(1);
		img2 = heart(2);
		img3 = heart(3);
		add(img1);
		pause(20);
		add(img2);
		pause(20);
		add(img3);
	}

// removes heart when player miss the ball
	private void removeHearts() {
		if (hearts == 2) {
			remove(img3);
		}
		if (hearts == 1) {
			remove(img2);
		}
	}

// return image for heart
	private GObject heart(int i) {
		double heartSizeX = 30;
		double heartSizeY = 30;
		GImage heart = new GImage("14.gif", APPLICATION_WIDTH - (i + 0.3) * heartSizeX / 1.5, 1);
		heart.setSize(heartSizeX, heartSizeY);
		return heart;
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
			paddle.setColor(rnd.nextColor());
		}
	}

	// draws ball
	private void ball() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT * 2 / 3 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		changeBallColor();
		add(ball);
	}

	private void moveBall() {
		vy = 3; // initial velocity of ball on y axes
		vx = rnd.nextDouble(1, 3); // initial random velocity of ball on x axes
		if (rnd.nextBoolean(0.5)) {
			vx = -vx;
		}

		while (true) {
			ball.move(vx, vy); // ball movement
			increaseSpeed();
			pause(10);
			collisionToWalls();
			collisionToObject();
			scoreObjects();
			// paddle.setLocation(ball.getX(), paddlePosY);

			if (ball.getY() + BALL_RADIUS * 2 > HEIGHT) {// next attempt
				remove(ball);
				hearts--;
				break;
			}
			if (countBricks == 0) { // win
				break;
			}
		}
	}

// changes ball color randomly  
	private void changeBallColor() {
		int i = rnd.nextInt(1, 7);
		Color COlor = null;
		if (i == 1) {
			COlor = Color.red;
		} else if (i == 2) {
			COlor = Color.ORANGE;

		} else if (i == 3) {
			COlor = Color.GREEN;

		} else if (i == 4) {
			COlor = Color.CYAN;

		} else if (i == 5) {
			COlor = Color.getHSBColor(275 / 360F, 1F, 0.25F);

		} else if (i == 6) {
			COlor = Color.getHSBColor(300 / 360f, 0.45f, 0.93f);

		} else {
			COlor = Color.YELLOW;
		}
		ball.setColor(COlor);
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
	 * checks for collisions if object is paddle changes direction of speed, if
	 * object is brick removes too and changes with proper speed and adds scores
	 * 
	 */
	private void collisionToObject() {
		if (ball.getY() > img1.getHeight() + img1.getY() + 1) {
			if (paddle == GObject2()) {
				ball.setLocation(ball.getX(), ball.getY() - 4);
				vy = -vy;
				countCollisions++;
			} else if (paddle == GObject5()) {
				if (vx > 0) {
					ball.setLocation(ball.getX() + 3, ball.getY() - 4);
					vy = -vy;
					vx = -vx;
				} else {
					vy = -vy;
				}
				countCollisions++;
			} else if (paddle == GObject7()) {
				if (vx < 0) {
					ball.setLocation(ball.getX() - 3, ball.getY() - 4);
					vy = -vy;
					vx = -vx;
				} else {
					vy = -vy;
				}
				countCollisions++;
			} else if (paddle == GObject1() || paddle == GObject3() || paddle == GObject4() || paddle == GObject6()
					|| paddle == GObject8()) {
				if (paddle.getX() > ball.getX()) {
					ball.setLocation(ball.getX() - 3, ball.getY() + 3);
					vx = -vx;
				} else if (paddle.getX() < ball.getX()) {
					ball.setLocation(ball.getX() + 3, ball.getY() + 3);
					vx = -vx;
				}
				countCollisions++;
			}

			// if object is brick
			else if (null != GObject1()) {
				vy = -vy;
				removeBrick(GObject1());
				changeBallColor();
			} else if (null != GObject2()) {
				vy = -vy;
				removeBrick(GObject2());
				changeBallColor();
			} else if (null != GObject3()) {
				vx = -vx;
				removeBrick(GObject3());
				changeBallColor();
			} else if (null != GObject4()) {
				vx = -vx;
				removeBrick(GObject4());
				changeBallColor();
			} else if (null != GObject5()) {
				vy = -vy;
				vx = -vx;
				removeBrick(GObject5());
				changeBallColor();
			} else if (null != GObject6()) {
				vy = -vy;
				vx = -vx;
				removeBrick(GObject6());
				changeBallColor();
			} else if (null != GObject7()) {
				vy = -vy;
				vx = -vx;
				removeBrick(GObject7());
				changeBallColor();
			} else if (null != GObject8()) {
				vy = -vy;
				vx = -vx;
				removeBrick(GObject8());
				changeBallColor();
			}

		}
	}

// increases speed of ball every 8 time with paddle interaction 

	private void increaseSpeed() {
		if (countCollisions % 9 == 0) {
			vx *= 1.2;
			vy *= 1.2;
			countCollisions = 1;
		}
	}

// score counter 
	private void scoreSystem(GObject x) {
		int i;
		if (x.getColor() == Color.CYAN) {
			c.play();
			i = 3;
			bonusScore(i, x);
		} else if (x.getColor() == Color.GREEN) {
			d.play();
			i = 4;
			bonusScore(i, x);
		} else if (x.getColor() == Color.YELLOW) {
			e.play();
			i = 5;
			bonusScore(i, x);
		} else if (x.getColor() == Color.ORANGE) {
			f.play();
			i = 6;
			bonusScore(i, x);
		} else if (x.getColor() == Color.red) {
			g.play();
			i = 7;
			bonusScore(i, x);
		} else if (x.getColor() == Color.getHSBColor(275 / 360F, 1F, 0.25F)) {
			b.play();
			i = 2;
			bonusScore(i, x);
		} else {
			a.play();
			i = 1;
			bonusScore(i, x);
		}
	}

// gives bonus score when ball has same color as collided brick
	private void bonusScore(int i, GObject x) {
		if (x.getColor() == ball.getColor()) {
			score += 5 * i;
		} else {
			score += 1 * i;
		}
	}

	// remove for bricks + adds proper scores
	private void removeBrick(GObject x) {
		remove(x);
		countBricks--;
		scoreSystem(x);
	}

	// object located in north side of the ball
	private GObject GObject1() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 0.1);
		return gobj;
	}

	// object in located south side of the ball
	private GObject GObject2() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + 0.1);
		return gobj;
	}

	// object located in east side of the ball
	private GObject GObject3() {
		GObject gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + BALL_RADIUS + 0.1);
		return gobj;
	}

	// object located in West side of the ball
	private GObject GObject4() {
		GObject gobj = getElementAt(ball.getX() - 0.1, ball.getY() + BALL_RADIUS);
		return gobj;
	}

	// object located in south-east side of the ball
	private GObject GObject5() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS + (BALL_RADIUS + 0.1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS + (BALL_RADIUS + 0.1) / Math.sqrt(2));
		return gobj;
	}

	// object located in north-east side of the ball
	private GObject GObject6() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS + (BALL_RADIUS + 0.1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS - (BALL_RADIUS + 0.1) / Math.sqrt(2));
		return gobj;
	}

	// object located in south-west side of the ball
	private GObject GObject7() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS - (BALL_RADIUS + 0.1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS + (BALL_RADIUS + 0.1) / Math.sqrt(2));
		return gobj;
	}

	// object located in north-west side of the ball
	private GObject GObject8() {
		GObject gobj = getElementAt(ball.getX() + BALL_RADIUS - (BALL_RADIUS + 0.1) / Math.sqrt(2),
				ball.getY() + BALL_RADIUS - (BALL_RADIUS + 0.1) / Math.sqrt(2));
		return gobj;
	}

	// draws label for winner
	private void win() {
		add(labelInTheMiddle(" YOU WIN WP !!! SCORE: " + score));
	}

	// draws label when game is over
	private void lost() {
		add(labelInTheMiddle("GAME OVER :(( CLICK TO TRY AGAIN"));
	}

// adds score  label and score sign and after changing removes it and adds again 

	private void scoreObjects() {
		remove(sc);
		scoreLabel();
		remove(scoreSGN);
		scoreSign();
	}

// returns label in the middle
	private GLabel labelInTheMiddle(String s) {
		GLabel label = new GLabel(s);
		label.setFont("Bernard MT -20");
		GLabel lb = new GLabel(s, WIDTH / 2 - label.getWidth() / 2, HEIGHT / 2 - label.getAscent() / 2);
		lb.setColor(Color.blue);
		lb.setFont("Bernard MT -20");
		return (lb);
	}

// add new score
	private void scoreLabel() {
		sc = new GLabel("SCORE " + score, 1, 24);
		sc.setColor(Color.CYAN);
		sc.setFont("Bernard MT Condensed-24");
		add(sc);
	}

// adds score sign 
	private void scoreSign() {
		double SizeX = 20;
		double SizeY = 30;
		scoreSGN = new GImage("x1.png", sc.getX() + sc.getWidth() + 2, 1);
		scoreSGN.setSize(SizeX, SizeY);
		add(scoreSGN);
	}
}
