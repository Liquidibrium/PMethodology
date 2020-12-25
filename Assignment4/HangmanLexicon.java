
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;
import acmx.export.java.io.FileReader;

public class HangmanLexicon {

	private static ArrayList<String> strList = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public static int getWordCount() {
		return strList.size();
	}

	/** Returns the word at the specified index. */
	public static String getWord(int index) {
		return strList.get(index);
	};

// This is the HangmanLexicon that reads string form specified file and adds them to  arrayList 
	public void HangmanLexicon() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			String line = null;
			while ((line = rd.readLine()) != null) {
				strList.add(line);
			}
			rd.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
