package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessSquare;


public class Knight extends Piece{

	/**
	 * @param isWhite Whether or not this Piece is a White Piece
	 * @param location The ChessSquare where this piece is starting
	 */
	public Knight(boolean isWhite, ChessSquare location){
		setLocation(location);
		location.setOccupant(this);
		setType("Knight");
		setCountdown(3);
		if(isWhite)
			setColor("White");
		else
			setColor("Black");
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wKnight.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bKnight.jpg")).getImage());
	}

	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		ChessSquare north1 = null, north2 = null, west1 = null, west2 = null,
				south1 = null, south2 = null, east1 = null, east2 = null;
		
		if(getSquare().getNorth() != null && getSquare().getNorth().getNorth() != null){
			north1 = getSquare().getNorth().getNorth().getEast();
			north2 = getSquare().getNorth().getNorth().getWest();
		}
		if(getSquare().getWest() != null && getSquare().getWest().getWest() != null){
			west1 = getSquare().getWest().getWest().getNorth();
			west2 = getSquare().getWest().getWest().getSouth();
		}
		if(getSquare().getSouth() != null && getSquare().getSouth().getSouth() != null){
			south1 = getSquare().getSouth().getSouth().getWest();
			south2 = getSquare().getSouth().getSouth().getEast();
		}
		if(getSquare().getEast() != null && getSquare().getEast().getEast() != null){
			east1 = getSquare().getEast().getEast().getSouth();
			east2 = getSquare().getEast().getEast().getNorth();
		}
		
		
		if(north1 != null && (north1.getOccupant() == null || !north1.getOccupant().getColor().equals(getColor())))
			moves.add(10 * north1.getRow() + north1.getCol());
		if(north2 != null && (north2.getOccupant() == null || !north2.getOccupant().getColor().equals(getColor())))
			moves.add(10 * north2.getRow() + north2.getCol());
		if(south1 != null && (south1.getOccupant() == null || !south1.getOccupant().getColor().equals(getColor())))
			moves.add(10 * south1.getRow() + south1.getCol());
		if(south2 != null && (south2.getOccupant() == null || !south2.getOccupant().getColor().equals(getColor())))
			moves.add(10 * south2.getRow() + south2.getCol());
		if(west1 != null && (west1.getOccupant() == null || !west1.getOccupant().getColor().equals(getColor())))
			moves.add(10 * west1.getRow() + west1.getCol());
		if(west2 != null && (west2.getOccupant() == null || !west2.getOccupant().getColor().equals(getColor())))
			moves.add(10 * west2.getRow() + west2.getCol());
		if(east1 != null && (east1.getOccupant() == null || !east1.getOccupant().getColor().equals(getColor())))
			moves.add(10 * east1.getRow() + east1.getCol());
		if(east2 != null && (east2.getOccupant() == null || !east2.getOccupant().getColor().equals(getColor())))
			moves.add(10 * east2.getRow() + east2.getCol());
		
		return moves;
	}
	
	/**
	 * Empty method. Knights' special moves are handled in the CrazyBoard.
	 * The knight moves like a normal knight, but returns to its original spot.
	 * This method is empty because it does not have access to the square that the user must select after pressing the special move button.
	 */
	public void specialMove() {
		
		
	}
	
}
