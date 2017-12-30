package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessSquare;


public class Queen extends Bishop{
	
	/**
	 * @param isWhite Whether or not this Piece is a White Piece
	 * @param location The ChessSquare where this piece is starting
	 */
	public Queen(boolean isWhite, ChessSquare location) {
		super(isWhite, location);
		setType("Queen");
		setCountdown(9);
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wQueen.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bQueen.jpg")).getImage());
		
	}

	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = super.getMoves();
		
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
	 * Makes this Piece do its special move, which removes all opposite-colored Pieces around it
	 * @post The squares around this Piece may have been modified, and may no longer have occupants
	 */
	
	public void specialMove() {
		ChessSquare initial = getSquare();
		ChessSquare next;
		
		if(initial.getNorth() != null){
			next = initial.getNorth();
			
			if((next.getOccupant() != null) && (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
			if(next.getEast() != null)
				if((next.getEast().getOccupant() != null) && (!next.getEast().getOccupant().getColor().equals(this.getColor())))
					next.getEast().removeOccupant();
			if(next.getWest() != null)
				if((next.getWest().getOccupant() != null) &&  (!next.getWest().getOccupant().getColor().equals(this.getColor())))
					next.getWest().removeOccupant();
		}
		
		if(initial.getSouth() != null){
			next = initial.getSouth();
			
			if((next.getOccupant() != null) && (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
			if(next.getEast() != null)
				if((next.getEast().getOccupant() != null) && (!next.getEast().getOccupant().getColor().equals(this.getColor())))
					next.getEast().removeOccupant();
			if(next.getWest() != null)
				if((next.getWest().getOccupant() != null) && (!next.getWest().getOccupant().getColor().equals(this.getColor())))
					next.getWest().removeOccupant();
			
		}
		
		next = initial.getEast();
		if(next != null)
			if((next.getOccupant() != null) &&  (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
		next = initial.getWest();
		if(next != null)
			if((next.getOccupant() != null) &&  (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
		
		setCountdown(9);
	}
	
	
}
