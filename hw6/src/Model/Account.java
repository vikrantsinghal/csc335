/**
 * Stores information about users of the MUD, which.
 * can be serialized and accessed between sessions.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */



package Model;

import java.io.Serializable;

import Characters.Warrior;

@SuppressWarnings("serial")
public class Account implements Serializable {

	private String userName;
	private String password;
	private Warrior myCharacter;

	public Account(String name, String pw) {
		userName = name;
		password = pw;
		myCharacter = new Warrior(userName);
	}

	public String getUserName() {
		return userName;
	}

	public String getPw() {
		return password;
	}

	public Warrior getCh() {
		return myCharacter;
	}
}
