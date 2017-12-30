package Pieces;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessSquare;
import Boards.ChessBoard;


public abstract class Piece {

	private ChessSquare currentLocation;
	private String color;
	private Image icon;
	private Image ready;
	private String type;
	private boolean hasMoved = false;
	private int turnCountdown;
	private boolean crazy;
	
	public abstract ArrayList<Integer> getMoves();
	public abstract void specialMove();
	
	/**
	 * Returns whether or not this Piece has moved or not
	 * @return A boolean that tells whether this Piece has been moved or not
	 */
	public boolean hasMoved(){
		return hasMoved;
	}
	
	/**
	 * Overwrites this Piece's hasMoved state to false
	 */
	public void overwriteHasMoved(){
		hasMoved = false;
	}
	
	/**
	 * Sets the type of this Piece: e.g. Rook
	 * @param input The desired type of this Piece
	 */
	public void setType(String input){
		type = input;
	}
	
	/**
	 * Returns the type of this Piece: e.g. Rook
	 * @return This Piece's type in a String
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Sets this Piece's Image representation to the parameter
	 * @param image The Image that will graphically represent the Piece
	 */
	public void setIcon(Image image){
		icon = image;
	}
	
	/**
	 * Returns the Image that represents this Piece currently
	 * @return The Image currently being used
	 */
	public Image getIcon(){
		if(crazy && (turnCountdown < 1))
			return ready;
		else
			return icon;
	}
	/**
	 * Returns this Piece's current ChessSquare
	 * @return This Piece's current ChessSquare
	 */
	public ChessSquare getSquare(){
		return currentLocation;
	}
	
	/**
	 * Changes the Icon of this Piece for use in CrazyChess
	 */
	public void setCrazy(){
		crazy = true;
		String imageName = "PiecesImages/";
		
		if(color.equals("White"))
			imageName += "w";
		else
			imageName += "b";
			
		imageName += type + "R.jpg";
		ready = (new ImageIcon(imageName)).getImage();
	}
	
	/**
	 * Moves the chess piece
	 * hasMoved() will now return true, unless overwritten
	 * @post ChessSquare other will now contain a piece / new piece
	 * @param other The ChessSquare being moved to
	 */
	public void moveTo(ChessSquare other){
		currentLocation.setOccupant(null);
		if(other.getOccupant() != null){
			ChessBoard.getPieces().remove(other.getOccupant());
		}
		setLocation(other);
		
		hasMoved = true;
		turnCountdown--;
		
	}
	
	/**
	 * Sets the piece to a new location - should not be used for moving
	 * @post input will now contain a piece/new piece
	 * @param input The ChessSquare to which this Piece is being set
	 */
	public void setLocation(ChessSquare input){
		currentLocation = input;
		input.setOccupant(this);
	}
	
	/**
	 * Returns the color of this Piece
	 * @return The color of this Piece
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * Sets the color of this Piece to color
	 * @param color The color being set to
	 */
	public void setColor(String color){
		this.color = color;
	}
	
	/**
	 * Returns the coordinates of this piece in an array consisting of Row and Column values
	 * @return An int array of length two, with Row then Column
	 */
	public int[] getCoordinates(){
		return new int[] {currentLocation.getRow(), currentLocation.getCol()};
	}
	
	/**
	 * @return A string representing this Piece's color and type
	 */
	public String toString(){
		if(getColor().equals("White"))
			return "White " + getType();
		else
			return "Black " + getType();
	}
	
	/**
	 * Sets the turn countdown of this piece
	 * @param countdown The countdown that this Piece will have before its next special move
	 */
	public void setCountdown(int countdown){
		turnCountdown = countdown;
	}
	

	/**
	 * @return The number of turns before the piece can make its special move
	 */
	public int getCountdown(){
		return turnCountdown;
	}
	
	/**
	 * @return Whether the piece can make its special move or not
	 */
	public boolean ready(){
		return turnCountdown < 1;
	}
	
}
