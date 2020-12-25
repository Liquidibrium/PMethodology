
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		add(new JLabel("Name"), NORTH);
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		statusTextField = new JTextField(TEXT_FIELD_SIZE);
		PictureTextField = new JTextField(TEXT_FIELD_SIZE);
		FriendTextField = new JTextField(TEXT_FIELD_SIZE);
		add(nameTextField, NORTH);
		nameTextField.addActionListener(this);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		add(statusTextField, WEST);
		statusTextField.addActionListener(this);
		add(new JButton("Change Status"), WEST);
		extraSpace();
		add(PictureTextField, WEST);
		PictureTextField.addActionListener(this);
		add(new JButton("Change Picture"), WEST);
		extraSpace();
		add(FriendTextField, WEST);
		FriendTextField.addActionListener(this);
		add(new JButton("Add Friend"), WEST);
		addActionListeners();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (!nameTextField.getText().equals("")) {
			addProfile(cmd);
			deleteProfile(cmd);
			lookupProfile(cmd);
		}
		changeStatus(e, cmd);
		changePicture(e, cmd);
		addFriends(e, cmd);
	}

	// add friends
	private void addFriends(ActionEvent e, String cmd) {
		if (!FriendTextField.getText().equals("")) {
			if (e.getSource() == FriendTextField || cmd.equals("Add Friend")) {
				if (pendingProfile != null) {
					if (datas.containsProfile(FriendTextField.getText())
							&& datas.getProfile(FriendTextField.getText()) != pendingProfile) {
						if (pendingProfile.addFriend(FriendTextField.getText())) {
							datas.getProfile(FriendTextField.getText()).addFriend(pendingProfile.getName());
							canvas.displayProfile(pendingProfile);
							canvas.showMessage(FriendTextField.getText() + " add as a friend");
						}
					} else {
						canvas.showMessage("A profile with the name " + FriendTextField.getText() + " doesn't exists");
					}
					FriendTextField.setText("");
				} else {
					canvas.showMessage("Please select a profile to add friend");
				}
			}
		}
	}

	// lookup profile
	private void lookupProfile(String cmd) {
		if (cmd.equals("Lookup")) {
			if (datas.containsProfile(nameTextField.getText())) {
				pendingProfile = datas.getProfile(nameTextField.getText());
				canvas.displayProfile(pendingProfile);
				canvas.showMessage("Displaying " + nameTextField.getText());
			} else {
				canvas.showMessage("A profile with the name " + nameTextField.getText() + " doesn't exists");
			}
			nameTextField.setText("");
		}
	}

	// change picture
	private void changePicture(ActionEvent e, String cmd) {
		if (!PictureTextField.getText().equals("")) {
			if (e.getSource() == PictureTextField || cmd.equals("Change Picture")) {
				if (pendingProfile != null) {
					GImage image = null;
					try {
						image = new GImage(PictureTextField.getText());
						pendingProfile.setImage(image);
						canvas.displayProfile(pendingProfile);
						canvas.showMessage("Picture updated");
					} catch (ErrorException ex) {
						// Code that is executed if the filename cannot be opened.
						canvas.showMessage("Unable to open image file: " + PictureTextField.getText());
					}
					PictureTextField.setText("");
				} else {
					canvas.showMessage("Please select a profile to add image");
				}
			}
		}
	}

	// change status
	private void changeStatus(ActionEvent e, String cmd) {
		if (!statusTextField.getText().equals("")) {
			if (e.getSource() == statusTextField || cmd.equals("Change Status")) {
				if (pendingProfile != null) {
					pendingProfile.setStatus(statusTextField.getText());
					canvas.displayProfile(pendingProfile);
					canvas.showMessage("Status updated to " + statusTextField.getText());
					statusTextField.setText("");
				} else {
					canvas.showMessage("Please selecr a profile to change status");
				}
			}
		}
	}

	// delete profile
	private void deleteProfile(String cmd) {
		if (cmd.equals("Delete")) {
			if (datas.containsProfile(nameTextField.getText())) {
				if (pendingProfile == datas.getProfile(nameTextField.getText())) {
					pendingProfile = null;
				}
				datas.deleteProfile(nameTextField.getText());
				canvas.displayProfile(pendingProfile);
				canvas.showMessage("Profile of " + nameTextField.getText() + " deleted");
			} else {
				canvas.showMessage("Profile of " + nameTextField.getText() + " doesnt exists");
			}
			nameTextField.setText("");
		}
	}

	// add profile
	private void addProfile(String cmd) {
		if (cmd.equals("Add")) {
			if (datas.containsProfile(nameTextField.getText())) {
				pendingProfile = datas.getProfile(nameTextField.getText());
				canvas.displayProfile(pendingProfile);
				canvas.showMessage("this profile already exists");
			} else {
				pendingProfile = new FacePamphletProfile(nameTextField.getText());
				canvas.displayProfile(pendingProfile);
				canvas.showMessage("New profile created");
			}
			datas.addProfile(pendingProfile);
			nameTextField.setText("");
		}
	}

	// makes additional free space
	private void extraSpace() {
		for (int i = 0; i < 3; i++) {
			add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		}
	}

// Instance variables 
	private	JTextField nameTextField;
	private	JTextField statusTextField;
	private	JTextField PictureTextField;
	private	JTextField FriendTextField;
	private FacePamphletProfile pendingProfile;
	private FacePamphletDatabase datas = new FacePamphletDatabase();
	private FacePamphletCanvas canvas;

}