package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessSquare;


public class Bishop extends Piece{

	/**
	 * @param isWhite Whether or not this Piece is a White Piece
	 * @param location The ChessSquare where this piece is starting
	 */
	public Bishop(boolean isWhite, ChessSquare location){
		setLocation(location);
		location.setOccupant(this);
		setType("Bishop");
		setCountdown(3);
		
		if(isWhite)
			setColor("White");
		else
			setColor("Black");
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wBishop.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bBishop.jpg")).getImage());
	}

	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		if(getSquare().getNorth() != null){
			ChessSquare selected = getSquare().getNorth().getWest();
			while(selected != null){
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						moves.add(10 * selected.getRow() + selected.getCol());
					break;
				}
				moves.add(10 * selected.getRow() + selected.getCol());
				if(selected.getNorth() != null)
					selected = selected.getNorth().getWest();
				else
					break;
			}
		
			selected = getSquare().getNorth().getEast();
			while(selected != null){
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						moves.add(10 * selected.getRow() + selected.getCol());
					break;
				}
				moves.add(10 * selected.getRow() + selected.getCol());
				if(selected.getNorth() != null)
					selected = selected.getNorth().getEast();
				else
					break;
			}
		}
		
		if(getSquare().getSouth() != null){
			ChessSquare selected = getSquare().getSouth().getWest();
			while(selected != null){
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						moves.add(10 * selected.getRow() + selected.getCol());
					break;
				}
				moves.add(10 * selected.getRow() + selected.getCol());
				if(selected.getSouth() != null)
					selected = selected.getSouth().getWest();
				else
					break;
			}
		
			selected = getSquare().getSouth().getEast();
			while(selected != null){
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						moves.add(10 * selected.getRow() + selected.getCol());
					break;
				}
				moves.add(10 * selected.getRow() + selected.getCol());
				if(selected.getSouth() != null)
					selected = selected.getSouth().getEast();
				else
					break;
			}
		}
		
		
		return moves;
	}


	/**
	 * Empty method. Bishops' special moves are handled in the CrazyBoard.
	 * The bishop moves like a normal rook would for 1 move.
	 * This method is empty because it does not have access to the square that the user must select after pressing the special move button.
	 */
	public void specialMove() {
		
	}
}
