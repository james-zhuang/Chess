package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Boards.ChessBoard;
import Boards.ChessSquare;


public class King extends Piece{
/**
 * 
 * @param isWhite Whether or not this Piece is a White Piece
 * @param location The ChessSquare where this piece is starting
 */
	public King(boolean isWhite, ChessSquare location){
		setLocation(location);
		location.setOccupant(this);
		setType("King");
		setCountdown(9);
		
		if(isWhite)
			setColor("White");
		else
			setColor("Black");
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wKing.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bKing.jpg")).getImage());
	}

	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		ChessSquare initial = getSquare();
		ChessSquare next;
		
		if(initial.getNorth() != null){
			next = initial.getNorth();
			
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol());
			if(next.getEast() != null)
				if((next.getEast().getOccupant() == null) || (!next.getEast().getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol() + 1);
			if(next.getWest() != null && next.getWest().getOccupant() == null)
				if((next.getWest().getOccupant() == null) || (!next.getWest().getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol() - 1);
		}
		
		if(initial.getSouth() != null){
			next = initial.getSouth();
			
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol());
			if(next.getEast() != null)
				if((next.getEast().getOccupant() == null) || (!next.getEast().getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol() + 1);
			if(next.getWest() != null)
				if((next.getWest().getOccupant() == null) || (!next.getWest().getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol() - 1);
			
		}
		
		next = initial.getEast();
		if(next != null)
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol());
		next = initial.getWest();
		if(next != null)
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				moves.add(10 * next.getRow() + next.getCol());
		
		
		return moves;
	}

	/**
	 * Determines whether the king can or cannot castle
	 * @pre direction must be 1 or -1
	 * @param direction The direction being castled in as an integer, with 1 being East and -1 being West 
	 * @return Whether or not this Piece can or cannot castle
	 */
	public boolean canCastle(int direction){
		Piece rook;
		
		if(!((getColor().equals("White") && getSquare().getCol() == 4 && getSquare().getRow() == 7 ) ||
				(getColor().equals("Black") && getSquare().getCol() == 4 && getSquare().getRow() == 0 )))
			return false;
		
		
		if(direction == 1){
			rook = getSquare().getEast().getEast().getEast().getOccupant();
			if(rook == null || !rook.getColor().equals(this.getColor()) || !rook.getType().equals("Rook") || rook.hasMoved() || this.hasMoved())
				return false;
			if(getSquare().getEast().getEast().getOccupant() != null)
				return false;
			if(getSquare().getEast().getOccupant() != null)
				return false;
			
			if(getColor().equals("White")){
				for(int i = 0; i < ChessBoard.getPieces().size(); i++){
					if(ChessBoard.getPieces().get(i).getColor().equals("Black"))
						for(int location: ChessBoard.getPieces().get(i).getMoves()){
							if(location == 75 || location == 76){
								return false;
							}
						}
				}
			}
			else{
				for(int i = 0; i < ChessBoard.getPieces().size(); i++){
					if(ChessBoard.getPieces().get(i).getColor().equals("White"))
						for(int location: ChessBoard.getPieces().get(i).getMoves()){
							if(location == 5 || location == 6){
								return false;
							}
						}
				}
			}
				
		}
		
		else if (direction == -1){//East
			rook = getSquare().getWest().getWest().getWest().getWest().getOccupant();
			if(rook == null || !rook.getColor().equals(this.getColor()) || !rook.getType().equals("Rook") || rook.hasMoved() || this.hasMoved())
				return false;
			if(getSquare().getWest().getWest().getWest().getOccupant() != null)
				return false;
			if(getSquare().getWest().getWest().getOccupant() != null)
				return false;
			if(getSquare().getWest().getOccupant() != null)
				return false;
			
			if(getColor().equals("White")){
				for(int i = 0; i < ChessBoard.getPieces().size(); i++){
					if(ChessBoard.getPieces().get(i).getColor().equals("Black"))
						for(int location: ChessBoard.getPieces().get(i).getMoves()){
							if(location == 73 || location == 72 || location == 71){
								return false;
							}
						}
				}
				
			}
			else{
				for(int i = 0; i < ChessBoard.getPieces().size(); i++){
					if(ChessBoard.getPieces().get(i).getColor().equals("White"))
						for(int location: ChessBoard.getPieces().get(i).getMoves()){
							if(location == 3 || location == 2 || location == 1){
								return false;
							}
						}
				}
			}
		}
		
		return true;
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
			
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
			if(next.getEast() != null)
				if((next.getEast().getOccupant() == null) || (!next.getEast().getOccupant().getColor().equals(this.getColor())))
					next.removeOccupant();
			if(next.getWest() != null && next.getWest().getOccupant() == null)
				if((next.getWest().getOccupant() == null) || (!next.getWest().getOccupant().getColor().equals(this.getColor())))
					next.removeOccupant();
		}
		
		if(initial.getSouth() != null){
			next = initial.getSouth();
			
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
			if(next.getEast() != null)
				if((next.getEast().getOccupant() == null) || (!next.getEast().getOccupant().getColor().equals(this.getColor())))
					next.removeOccupant();
			if(next.getWest() != null)
				if((next.getWest().getOccupant() == null) || (!next.getWest().getOccupant().getColor().equals(this.getColor())))
					next.removeOccupant();
			
		}
		
		next = initial.getEast();
		if(next != null)
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
		next = initial.getWest();
		if(next != null)
			if((next.getOccupant() == null) || (!next.getOccupant().getColor().equals(this.getColor())))
				next.removeOccupant();
		setCountdown(9);
	}
	
}
