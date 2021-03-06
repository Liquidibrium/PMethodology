
/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDataBaseEXT implements FacePamphletConstantsEXT {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */
	public FacePamphletDataBaseEXT() {
		// You fill this in
	}

	/**
	 * This method adds the given profile to the database. If the name associated
	 * with the profile is the same as an existing name in the database, the
	 * existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(FacePamphletProfileEXT profile) {
		if (dataBase.containsValue(profile)) {
			if (!dataBase.replace(profile.getName(), dataBase.get(profile.getName()), profile)) {
				dataBase.put(profile.getName(), profile);
			}
		} else {
			dataBase.put(profile.getName(), profile);
		}
	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public FacePamphletProfileEXT getProfile(String name) {
		if (dataBase.containsKey(name)) {
			return dataBase.get(name);
		} else {
			return null;
		}
	}

	/**
	 * This method removes the profile associated with the given name from the
	 * database. It also updates the list of friends of all other profiles in the
	 * database to make sure that this name is removed from the list of friends of
	 * any other profile.
	 * 
	 * If there is no profile in the database with the given name, then the database
	 * is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		dataBase.remove(name);
		Iterator<String> it = dataBase.keySet().iterator();
		while (it.hasNext()) {
			dataBase.get(it.next()).removeFriend(name);
		}
	}

	/**
	 * This method returns true if there is a profile in the database that has the
	 * given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return dataBase.containsKey(name);
	}

	private HashMap<String, FacePamphletProfileEXT> dataBase = new HashMap<>();
}
