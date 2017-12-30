package Boards;

public interface BoardListener {
	
	/**
	 * Switches the turn of the BoardListener
	 */
	public void switchTurns();

	/**
	 * Set the points that the white side has
	 * @param points
	 */
	public void setWPoints(int points);
	
	/**
	 * Set the points that the black side has
	 * @param points
	 */
	public void setBPoints(int points);
	
	/**
	 * Resets whole board for a new game
	 */
	public void reset();
}
