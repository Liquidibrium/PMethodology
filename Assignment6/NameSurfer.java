
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.Button;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		add(new JLabel("Name"), SOUTH);
		textField = new JTextField(15);
		add(textField, SOUTH);
		textField.addActionListener(this);
		add(new Button("Graph"), SOUTH);
		add(new Button("Clean"), SOUTH);
		addActionListeners();
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		// if clean button is pressed 
		if (cmd.equals("Clean")) {
			graph.clear();
			graph.update();
		}
		// if text is entered 
		if (e.getSource() == textField || cmd.equals("Graph")) {
			if (!textField.getText().equals("")) {
				if (dataBase.findEntry(textField.getText()) != null) {
					graph.addEntry(dataBase.findEntry(textField.getText()));
					graph.update();
					textField.setText("");
				} else {
					textField.setText("");
				}
			}else {
				textField.setText("");
			}
		}
	}

	// instance variable
	private JTextField textField;
	private NameSurferGraph graph;
	private NameSurferDataBase dataBase;
}
