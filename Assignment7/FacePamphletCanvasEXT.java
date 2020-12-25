
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

public class FacePamphletCanvasEXT extends GCanvas implements FacePamphletConstantsEXT {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvasEXT() {
		msgText = new ArrayList<>();
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
		add(message, getWidth() / 4 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfileEXT profile) {
		removeAll();
		if (profile != null) {
			add(new GLine(getWidth() / 2, 0, getWidth() / 2, getHeight()));
			name(profile);
			image(profile);
			status(profile);
			friendList(profile);
			school(profile);
			birthDate(profile);
		}
	}

	// draws label with name
	private void name(FacePamphletProfileEXT profile) {
		GLabel name = new GLabel(profile.getName(), LEFT_MARGIN, TOP_MARGIN);
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name);
	}

	// shows friend list
	private void friendList(FacePamphletProfileEXT profile) {
		Iterator<String> it = profile.getFriends();
		if (it.hasNext()) {
			GLabel label = new GLabel("FRIENDS :");
			label.setFont(PROFILE_FRIEND_LABEL_FONT);
			add(label, 2 * LEFT_MARGIN + IMAGE_WIDTH, TOP_MARGIN);
			for (int i = 1; it.hasNext(); i++) {
				GLabel lbl = new GLabel(it.next());
				lbl.setFont(PROFILE_FRIEND_FONT);
				add(lbl, 2 * LEFT_MARGIN + IMAGE_WIDTH, TOP_MARGIN + i * lbl.getHeight());
			}
		}
	}

	// shows status
	private void status(FacePamphletProfileEXT profile) {
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
	private void image(FacePamphletProfileEXT profile) {
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
		lbl.setColor(Color.red);
		lbl.setFont(PROFILE_IMAGE_FONT);
		add(lbl, LEFT_MARGIN + IMAGE_WIDTH / 2 - lbl.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT / 2);
	}

	// adds label of school
	private void school(FacePamphletProfileEXT profile) {
		String school;
		if (profile.getSchool().equals("")) {
			school = "No School ";
		} else {
			school = profile.getSchool();
		}
		GLabel sch = new GLabel(school);
		sch.setFont(PROFILE_STATUS_FONT);
		sch.setColor(Color.cyan);
		add(sch, LEFT_MARGIN, IMAGE_HEIGHT + TOP_MARGIN + IMAGE_MARGIN + 2 * STATUS_MARGIN);
	}

	// to draw updated version of birth date
	private void birthDate(FacePamphletProfileEXT profile) {
		String date;
		if (profile.getBirthDate() == null) {
			date = "No B-Date INFO";
		} else {
			date = profile.getBirthDate();
		}
		GLabel dt = new GLabel(date);
		dt.setColor(Color.green);
		add(dt, LEFT_MARGIN, IMAGE_HEIGHT + TOP_MARGIN + IMAGE_MARGIN + 3 * STATUS_MARGIN);
	}

	// refresh chat and adds GLabels of messages in canvas
	public void refreshChat(FacePamphletProfileEXT pendingProfile, FacePamphletProfileEXT receiver,
			int firstMessageIndex) {
		if (receiver != null && pendingProfile != null) {
			removeMessages();
			double height = getHeight() - BOTTOM_MESSAGE_MARGIN;
			for (int i = firstMessageIndex; i < pendingProfile.getMessages(receiver.getName()).size(); i++) {
				String messageLine = pendingProfile.getMessages(receiver.getName()).get(i);
				StringTokenizer tkn = new StringTokenizer(messageLine, ":");
				String name = tkn.nextToken();
				GLabel msg = new GLabel(tkn.nextToken());
				msg.setFont(MESSAGE_FONT);
				msgText.add(msg);
				if (name.equals(pendingProfile.getName())) {
					msg.setColor(Color.cyan);
					add(msg, getWidth() - msg.getWidth() - 5, height);
				} else {
					msg.setColor(Color.green);
					add(msg, getWidth() / 2, height);
				}
				height = height - msg.getHeight() / 1.5;
			}
		}
	}

	// removes messages
	public void removeMessages() {
		for (int i = 0; i < msgText.size(); i++) {
			remove(msgText.get(i));
		}
	}

	// arrayList of message GLabeles
	private ArrayList<GLabel> msgText;
	private GLabel message;

}
