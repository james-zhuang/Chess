package Pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Boards.ChessSquare;


public class Pawn extends Piece{

	private int direction;

	/**
	 * @param isWhite Whether or not this Piece is a White Piece
	 * @param location The ChessSquare where this piece is starting
	 */
	public Pawn(boolean isWhite, ChessSquare location){
		setLocation(location);
		location.setOccupant(this);
		setType("Pawn");
		setCountdown(1);
		
		if(isWhite)
		{
			setColor("White");
			direction = -1;
		}
		else
		{
			setColor("Black");
			direction = 1;
		}
		
		if(isWhite)
			setIcon((new ImageIcon("PiecesImages/wPawn.jpg")).getImage());
		else
			setIcon((new ImageIcon("PiecesImages/bPawn.jpg")).getImage());
	}

	/**
	 * @return An Integer ArrayList of possible moves, storing possible move locations with the Square's Row *10 + the Square's Column
	 */
	public ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if(direction == -1){
			ChessSquare north = getSquare().getNorth();
			if(north != null){
				if(north.getOccupant() == null)
					moves.add(10 * north.getRow() + north.getCol());
		
				ChessSquare northEast = north.getEast();
				if(northEast != null && northEast.getOccupant() != null && 
						!northEast.getOccupant().getColor().equals(this.getColor()))
					moves.add(10 * northEast.getRow() + northEast.getCol());
		
				ChessSquare northWest = north.getWest();
				if(northWest != null && northWest.getOccupant() != null && 
						!northWest.getOccupant().getColor().equals(this.getColor()))
					moves.add(10 * northWest.getRow() + northWest.getCol());
				
				if(getSquare().getRow() == 6 && north.getOccupant() == null && north.getNorth().getOccupant() == null)
					moves.add(40 + getSquare().getCol());
				}
			
			
		}
		else{
			ChessSquare south = getSquare().getSouth();
			if(south != null){
				if(south.getOccupant() == null)
					moves.add(10 * south.getRow() + south.getCol());
		
				ChessSquare southEast = south.getEast();
				if(southEast != null && southEast.getOccupant() != null && 
						!southEast.getOccupant().getColor().equals(this.getColor()))
					moves.add(10 * southEast.getRow() + southEast.getCol());
		
				ChessSquare southWest = south.getWest();
				if(southWest != null && southWest.getOccupant() != null && 
						!southWest.getOccupant().getColor().equals(this.getColor()))
					moves.add(10 * southWest.getRow() + southWest.getCol());
				
				if(getSquare().getRow() == 1 && south.getOccupant() == null && south.getSouth().getOccupant() == null)
					moves.add(30 + getSquare().getCol());	
			}
		}
		
		
		
		return moves;
	}
	
	/**
	 * When the pawn gets to the other side, allows the pawn to become any piece (except King, or Pawn).
	 * @return Promoted piece
	 */
	public Piece pawnPromotion(){
		Piece replacement = null;
		
		JOptionPane frame = new JOptionPane();
		Object[] options = {"Bishop","Knight","Rook","Queen"};
		int n = JOptionPane.showOptionDialog(frame, "Promote Pawn to:", "Pawn Promotion", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		
		if(n == 0){
			replacement = new Bishop(getColor().equals("White"), getSquare());
		}
		
		if(n == 1){
			replacement = new Knight(getColor().equals("White"), getSquare());
		}
		
		if(n == 2){
			replacement = new Rook(getColor().equals("White"), getSquare());
		}
		
		if(n == 3){
			replacement = new Queen(getColor().equals("White"), getSquare());
		}
		
		return replacement;
	}
	
	/**
	 * Returns a boolean stating whether or not this Piece can promote
	 * @return boolean stating whether or not the pawn can promote
	 */
	public boolean canPromote(){
		if(direction == -1){
			ChessSquare north = getSquare().getNorth();
			if(north.getRow() == 0){
				return true;
			}
		}
		else{
			ChessSquare south = getSquare().getSouth();
			if(south.getRow() == 7){
				return true;
			}
		}
		return false;
	}

	/**
	 * Attacks forward for 1 turn
	 */
	public void specialMove() {
		
		if(getColor().equals("White")){
			ChessSquare north = getSquare().getNorth();
			if(north != null)
				if(north.getOccupant() != null && !north.getOccupant().getColor().equals(getColor())){
					moveTo(north);
				}
		}
		else{
			ChessSquare south = getSquare().getSouth();
			if(south != null)
				if(south.getOccupant() != null && !south.getOccupant().getColor().equals(getColor())){
					moveTo(south);
				}
		}
		
		setCountdown(1);
	}
	
}
