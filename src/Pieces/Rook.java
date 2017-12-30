package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessSquare;


public class Rook extends Piece{

	/**
	 * @param isWhite Whether or not this Piece is a White Piece
	 * @param location The ChessSquare where this piece is starting
	 */
	public Rook(boolean isWhite, ChessSquare location){
		setLocation(location);
		location.setOccupant(this);
		setType("Rook");
		setCountdown(5);
		
		if(isWhite)
			setColor("White");
		else
			setColor("Black");
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wRook.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bRook.jpg")).getImage());
	}
	
	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		ChessSquare selected = getSquare().getNorth();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getNorth();
		}
		
		selected = getSquare().getSouth();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getSouth();
		}
		
		selected = getSquare().getWest();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getWest();
		}
		
		selected = getSquare().getEast();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getEast();
		}

		
		return moves;
	}


	/**
	 *  Makes this Piece do its special move, which removes all opposite-colored Pieces in the squares in the four cardinal directions
	 *  @post Neighboring ChessSquares to this Piece's may have their occupants removed
	 */

	public void specialMove() {
		
		if(getSquare().getNorth() != null){
			ChessSquare selected = getSquare().getNorth();
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						selected.removeOccupant();
					
				}
		}
		
		if(getSquare().getSouth() != null){
			ChessSquare selected = getSquare().getSouth();
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						selected.removeOccupant();
					
				}
		}
		
		if(getSquare().getEast() != null ){
			ChessSquare selected = getSquare().getEast();
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						selected.removeOccupant();
					
				}
		}
		
		if(getSquare().getWest() != null ){
			ChessSquare selected = getSquare().getWest();
				if(selected.getOccupant() != null){
					if(!selected.getOccupant().getColor().equals(getColor()))
						selected.removeOccupant();
					
				}
		}
		setCountdown(5);
	}

}
