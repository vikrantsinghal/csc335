package model;

public class Hunter {

	// instance variables of the Hunter
	private int currentLocation;
	private int arrowsRemaining;

	// hunter begins with 3 arrows
	public Hunter(int location) {
		currentLocation = location;
		arrowsRemaining = 3;
	}

	// return the Hunter's current room
	public int getCurrentLocation() {
		return currentLocation;
	}

	// move the Hunter to new room
	public void moveToLocation(int location) {
		currentLocation = location;
	}

	// return the number of arrows remaining
	public int getRemainingArrows() {
		return arrowsRemaining;
	}

	// checks if there are arrows remaining
	private boolean canShoot() {
		if (arrowsRemaining > 0)
			return true;
		return false;
	}

	// increment shot, return false if unable to shoot
	public boolean shoot() {
		if (!canShoot())
			return false;
		arrowsRemaining--;
		return true;
	}
}
