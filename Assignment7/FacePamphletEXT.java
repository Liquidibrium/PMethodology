
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class FacePamphletEXT extends Program implements FacePamphletConstantsEXT, AdjustmentListener {

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
		BDateTextField = new JTextField(TEXT_FIELD_SIZE);
		schoolTextField = new JTextField(TEXT_FIELD_SIZE);
		chatField = new JTextField(TEXT_FIELD_SIZE);
		add(nameTextField, NORTH);
		nameTextField.addActionListener(this);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		add(new JButton("Message to"), NORTH);
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
		extraSpace();
		add(schoolTextField, WEST);
		schoolTextField.addActionListener(this);
		add(new JButton("add school"), WEST);
		extraSpace();
		add(BDateTextField, WEST);
		BDateTextField.addActionListener(this);
		add(new JButton("add Birth Date"), WEST);
		extraSpace(SOUTH);
		add(new JButton("Send"), SOUTH);
		add(chatField, SOUTH);
		add(new JButton("Up"), SOUTH);
		add(new JButton("Down"), SOUTH);
		chatField.addActionListener(this);
		scroll = new JScrollBar();
		scroll.setValue(90);
		add(scroll, EAST);
		scroll.addAdjustmentListener(this);
		addActionListeners();
		canvas = new FacePamphletCanvasEXT();
		add(canvas);
	}

	// this code is responsible for detecting when scroll is moved or clicked
	// and will move messages properly
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (receiver != null && pendingProfile != null) {
			firstMessageIndex = 90 - scroll.getValue();
			canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
		}
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
			chatTo(cmd);
		}
		changeStatus(e, cmd);
		changePicture(e, cmd);
		addFriends(e, cmd);
		changeSchool(e, cmd);
		changeBDate(e, cmd);
		chatFunctions(e, cmd);
	}

	// opens chat , so it possible to contact with chosen receiver
	private void chatTo(String cmd) {
		if (cmd.equals("Message to")) {
			if (datas.containsProfile(nameTextField.getText())) {
				receiver = datas.getProfile(nameTextField.getText());
				openMessenger();
				canvas.showMessage("Now you can Chat to " + receiver.getName());
			} else {
				canvas.showMessage(nameTextField.getText() + " doesn't exists");
			}
		}
	}

	// shows messages if they where already existed
	private void openMessenger() {
		if (pendingProfile.getMessages(receiver.getName()) != null) {
			canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
		}
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
							canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
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
				receiver = null;
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
						canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
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
					canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
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
				canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
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
				pendingProfile = new FacePamphletProfileEXT(nameTextField.getText());
				canvas.displayProfile(pendingProfile);
				canvas.showMessage("New profile created");
				datas.addProfile(pendingProfile);
			}
			receiver = null;
			nameTextField.setText("");
		}
	}

	// change school
	private void changeSchool(ActionEvent e, String cmd) {
		if (!schoolTextField.getText().equals("")) {
			if (e.getSource() == schoolTextField || cmd.equals("add school")) {
				if (pendingProfile != null) {
					pendingProfile.setSchool(schoolTextField.getText());
					canvas.displayProfile(pendingProfile);
					canvas.showMessage("school updated to " + schoolTextField.getText());
					schoolTextField.setText("");
					canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
				} else {
					canvas.showMessage("Please select a profile to add school");
				}
			}
		}
	}

	// change bDate
	private void changeBDate(ActionEvent e, String cmd) {
		if (!BDateTextField.getText().equals("")) {
			if (e.getSource() == BDateTextField || cmd.equals("add Birth Date")) {
				if (pendingProfile != null) {
					pendingProfile.setBDate(BDateTextField.getText());
					canvas.displayProfile(pendingProfile);
					canvas.showMessage("birth date updated to " + BDateTextField.getText());
					canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
					BDateTextField.setText("");
				} else {
					canvas.showMessage("Please selecr a profile to add  BDate");
				}
			}
		}
	}

	// makes additional free space
	private void extraSpace() {
		for (int i = 0; i < 1; i++) {
			add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		}
	}

	// makes additional free space at side
	private void extraSpace(String side) {
		for (int i = 0; i < 9; i++) {
			add(new JLabel("                 "), side);
		}
	}

	// defines chat button functions
	private void chatFunctions(ActionEvent e, String cmd) {
		if (e.getSource() == chatField || cmd.equals("Send")) {
			if (!chatField.getText().equals("") && receiver != null && pendingProfile != null) {
				pendingProfile.setMessage(receiver.getName(), pendingProfile.getName() + ":  " + chatField.getText());
				receiver.setMessage(pendingProfile.getName(), pendingProfile.getName() + ":  " + chatField.getText());
				canvas.showMessage("New message was sent to " + receiver.getName());
				canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
				chatField.setText("");
			} else {
				canvas.showMessage("Choose someone to send message or send text");
			}
		}
		if (cmd.equals("Up")) {
			firstMessageIndex++;
			canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
			scroll.setValue(90 - firstMessageIndex);
		}
		if (cmd.equals("Down") && firstMessageIndex > 0) {
			firstMessageIndex--;
			canvas.refreshChat(pendingProfile, receiver, firstMessageIndex);
			scroll.setValue(90 - firstMessageIndex);
		}
	}

// Instance variables 
	private JScrollBar scroll;
	private JTextField chatField;
	private int firstMessageIndex = 0;
	private FacePamphletProfileEXT receiver;
	private JTextField BDateTextField;
	private JTextField schoolTextField;
	private JTextField nameTextField;
	private JTextField statusTextField;
	private JTextField PictureTextField;
	private JTextField FriendTextField;
	private FacePamphletProfileEXT pendingProfile;
	private FacePamphletDataBaseEXT datas = new FacePamphletDataBaseEXT();
	private FacePamphletCanvasEXT canvas;
}