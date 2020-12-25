
/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfileEXT implements FacePamphletConstantsEXT {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * profile.
	 */
	public FacePamphletProfileEXT(String name) {
		messenger = new HashMap<String, ArrayList<String>>();
		this.name = name;
		status = "";
		school = "";
		friendList = new ArrayList<String>();
	}

	/** This method returns the name associated with the profile. */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the image associated with the profile. If there is no
	 * image associated with the profile, the method returns null.
	 */
	public GImage getImage() {
		return image;
	}

	/** This method sets the image associated with the profile. */
	public void setImage(GImage image) {
		this.image = image;
	}

	/**
	 * This method returns the status associated with the profile. If there is no
	 * status associated with the profile, the method returns the empty string ("").
	 */
	public String getStatus() {
		return status;
	}

	/** This method sets the status associated with the profile. */
	public void setStatus(String status) {
		this.status = status;
	}

	// This method returns the school associated with the profile

	public String getSchool() {
		return school;
	}

//	 This method sets the hobby associated with the profile. */ 
	public void setSchool(String school) {
		this.school = school;
	}

//	  This method returns the date of birth associated with the profile.

	public String getBirthDate() {
		return BDate;
	}

	// This method sets the date of birth associated with the profile
	public void setBDate(String BDate) {
		this.BDate = BDate;
	}

	/**
	 * This method adds the named friend to this profile's list of friends. It
	 * returns true if the friend's name was not already in the list of friends for
	 * this profile (and the name is added to the list). The method returns false if
	 * the given friend name was already in the list of friends for this profile (in
	 * which case, the given friend name is not added to the list of friends a
	 * second time.)
	 */
	public boolean addFriend(String friend) {
		if (friendList.contains(friend)) {
			return false;
		} else {
			friendList.add(friend);
			return true;
		}
	}

	/**
	 * This method removes the named friend from this profile's list of friends. It
	 * returns true if the friend's name was in the list of friends for this profile
	 * (and the name was removed from the list). The method returns false if the
	 * given friend name was not in the list of friends for this profile (in which
	 * case, the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (friendList.contains(friend)) {
			friendList.remove(friend);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 
	 * This method returns an iterator over the list of friends associated with the
	 * profile.
	 */
	public Iterator<String> getFriends() {
		return friendList.iterator();
	}

	/**
	 * This method returns a string representation of the profile. This string is of
	 * the form: "name (status): list of friends", where name and status are set
	 * accordingly and the list of friends is a comma separated list of the names of
	 * all of the friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is "coding" and who
	 * has friends Don, Chelsea, and Bob, this method would return the string:
	 * "Alice (coding): Don, Chelsea, Bob"
	 */
	public String toString() {
		Iterator<String> iter = friendList.iterator();
		String friends = "";
		if (iter.hasNext()) {
			friends = iter.next();
		}
		while (iter.hasNext()) {
			friends = friends + ", " + iter.next();
		}
		return name + " (" + status + "): " + friends;
	}

	// if chat is already existed adds new messages in arrayList of messages with
	// receiver
	// if not creates new chat and then add new message at index 0
	public void setMessage(String receiver, String message) {
		if (messenger.containsKey(receiver)) {
			messenger.get(receiver).add(0, message);
		} else {
			messenger.put(receiver, new ArrayList<String>());
			messenger.get(receiver).add(0, message);
		}
	}

	// returns arrayList with messages of receiver
	public ArrayList<String> getMessages(String receiver) {
		return messenger.get(receiver);
	}

	// hashMap with name of receiver of messages and arrayList with messages of
	// receiver
	private HashMap<String, ArrayList<String>> messenger;
	// Instance variables
	private String school;
	private String BDate;
	private String name;
	private String status;
	private GImage image;
	private ArrayList<String> friendList;
}
