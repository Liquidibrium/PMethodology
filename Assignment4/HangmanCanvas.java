
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		add(hiddenWord(""));
		scaffold();
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		remove(hidden);
		add(hiddenWord(word));
	}

// adds label for hidden word 
	private GLabel hiddenWord(String word) {
		hidden = new GLabel(word, getWidth() / 2 - BEAM_LENGTH, getHeight() - labelOffsetFromBottom);
		hidden.setColor(Color.CYAN);
		hidden.setFont("Bernard MT Condensed-50");
		return hidden;
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(int numberOfAttempts, String incorrect) {
		drawing(numberOfAttempts);
		incorrectChars(incorrect);
	}

// adds label with incorrect chars 
	private void incorrectChars(String incorrect) {
		GLabel str = new GLabel(incorrect, getWidth() / 2 - BEAM_LENGTH, getHeight() - labelOffsetFromBottom / 3);
		str.setColor(Color.red);
		str.setFont("Bernard MT Condensed-30");
		add(str);
	}

	// draws scaffold
	private void scaffold() {
		double x = getWidth() / 2 - BEAM_LENGTH;
		double y = getHeight() / 2 - ROPE_LENGTH - 2 * HEAD_RADIUS - ARM_OFFSET_FROM_HEAD - offset;
		GLine scaffold = new GLine(x, y, x, y + SCAFFOLD_HEIGHT);
		GLine beam = new GLine(x, y, x + BEAM_LENGTH, y);
		GLine rope = new GLine(x + BEAM_LENGTH, y, x + BEAM_LENGTH, y + ROPE_LENGTH);
		add(scaffold);
		add(beam);
		add(rope);
	}

	// draws head
	private void head() {
		GOval head = new GOval(getWidth() / 2 - HEAD_RADIUS,
				getHeight() / 2 - 2 * HEAD_RADIUS - ARM_OFFSET_FROM_HEAD - offset, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	// draws body
	private void body() {
		GLine body = new GLine(getWidth() / 2, getHeight() / 2 - ARM_OFFSET_FROM_HEAD - offset, getWidth() / 2,
				getHeight() / 2 + BODY_LENGTH - ARM_OFFSET_FROM_HEAD - offset);
		add(body);
	}

	// draws Right arm
	private void rightArm() {
		GLine upperArm = new GLine(getWidth() / 2, getHeight() / 2 - offset, getWidth() / 2 - UPPER_ARM_LENGTH,
				getHeight() / 2 - offset);
		GLine lowerArm = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH, getHeight() / 2 - offset,
				getWidth() / 2 - UPPER_ARM_LENGTH, getHeight() / 2 + LOWER_ARM_LENGTH - offset);
		add(lowerArm);
		add(upperArm);
	}

	// draws left arm
	private void leftArm() {
		GLine upperArm = new GLine(getWidth() / 2, getHeight() / 2 - offset, getWidth() / 2 + UPPER_ARM_LENGTH,
				getHeight() / 2 - offset);
		GLine lowerArm = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH, getHeight() / 2 - offset,
				getWidth() / 2 + UPPER_ARM_LENGTH, getHeight() / 2 + LOWER_ARM_LENGTH - offset);
		add(lowerArm);
		add(upperArm);
	}

	// draws right leg
	private void rightLeg() {
		GLine rightHip = new GLine(getWidth() / 2, getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset,
				getWidth() / 2 + HIP_WIDTH, getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset);
		GLine rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset, getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset);
		add(rightHip);
		add(rightLeg);
	}

	// draws left leg
	private void leftLeg() {
		GLine leftHip = new GLine(getWidth() / 2, getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset,
				getWidth() / 2 - HIP_WIDTH, getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset);
		GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH - offset, getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset);
		add(leftHip);
		add(leftLeg);
	}

	// draws right foot
	private void rightFoot() {
		GLine foot = new GLine(getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset,
				getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset);
		add(foot);
	}

	// draws left foot
	private void leftFoot() {
		GLine foot = new GLine(getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset,
				getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
				getHeight() / 2 - ARM_OFFSET_FROM_HEAD + BODY_LENGTH + LEG_LENGTH - offset);
		add(foot);
	}

	// draws proper parts of the body when guess is incorrect
	private void drawing(int numberOfAttempts) {
		if (numberOfAttempts == 7) {
			head();
		} else if (numberOfAttempts == 6) {
			body();
		} else if (numberOfAttempts == 5) {
			rightArm();
		} else if (numberOfAttempts == 4) {
			leftArm();
		} else if (numberOfAttempts == 3) {
			rightLeg();
		} else if (numberOfAttempts == 2) {
			leftLeg();
		} else if (numberOfAttempts == 1) {
			rightFoot();
		} else if (numberOfAttempts == 0) {
			leftFoot();
		}
	}

	// hidden word GLabel
	private GLabel hidden;
	// string guessed word offset from bottom
	private int labelOffsetFromBottom = 50;
	// Human offset
	private int offset = 100;
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
}
