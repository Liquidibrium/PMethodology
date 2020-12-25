
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		message = new GLabel(" ");
		add(message);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		remove(message);
		message.setFont(MESSAGE_FONT);
		message = new GLabel(msg);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			name(profile);
			image(profile);
			status(profile);
			friendList(profile);
		}
	}

	// draws label with name
	private void name(FacePamphletProfile profile) {
		GLabel name = new GLabel(profile.getName(), LEFT_MARGIN, TOP_MARGIN);
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name);
	}

	// shows friend list
	private void friendList(FacePamphletProfile profile) {
		Iterator<String> it = profile.getFriends();
		if (it.hasNext()) {
			GLabel label = new GLabel("FRIENDS :");
			label.setFont(PROFILE_FRIEND_LABEL_FONT);
			add(label, getWidth() / 2, TOP_MARGIN);
			for (int i = 1; it.hasNext(); i++) {
				GLabel lbl = new GLabel(it.next());
				lbl.setFont(PROFILE_FRIEND_FONT);
				add(lbl, getWidth() / 2, TOP_MARGIN + i * lbl.getHeight());
			}
		}
	}

	// shows status
	private void status(FacePamphletProfile profile) {
		String str;
		if (profile.getStatus().equals("")) {
			str = "No current status";
		} else {
			str = profile.getName() + " is " + profile.getStatus();
		}
		GLabel lbl = new GLabel(str);
		lbl.setFont(PROFILE_STATUS_FONT);
		add(lbl, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
	}

	// draws image of profile
	private void image(FacePamphletProfile profile) {
		if (profile.getImage() != null) {
			GImage img = profile.getImage();
			img.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
		} else {
			noImage();
		}
	}

	// draws rectangle instead of image and label in it
	private void noImage() {
		String str = "No Image";
		GRect img = new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
		add(img);
		GLabel lbl = new GLabel(str);
		lbl.setFont(PROFILE_IMAGE_FONT);
		add(lbl, LEFT_MARGIN + IMAGE_WIDTH / 2 - lbl.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT / 2);
	}

private	GLabel message;

}
