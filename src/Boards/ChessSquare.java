package Boards;
import Pieces.Piece;


public class ChessSquare {

	private Piece occupant = null;
	private int row;
	private int col;
	
	/**
	 * 
	 * @param row the row in which the Piece is located
	 * @param col the col in which the Piece is located
	 */
	public ChessSquare(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getRow(){
		return row;
	}
	
	public void setOccupant(Piece incoming){
		 occupant = incoming;
	}
	
	public void removeOccupant(){
		ChessBoard.getPieces().remove(occupant);
		occupant = null;
		
	}
	
	public Piece getOccupant(){
		return occupant;
	}
	
	/**
	 * 
	 * @return The ChessSquare above this one
	 */
	public ChessSquare getNorth(){
		if(row == 0)
			return null;
		else{
			ChessSquare[][] copy = ChessBoard.getSquares();
			return copy[row - 1][col];
		}
	}
	
	/**
	 * 
	 * @return The ChessSquare below this one
	 */
	public ChessSquare getSouth(){
		if(row == 7)
			return null;
		else{
			ChessSquare[][] copy = ChessBoard.getSquares();
			return copy[row + 1][col];
		}
	}
	
	/**
	 * 
	 * @return The ChessSquare right this one
	 */
	public ChessSquare getEast(){
		if(col == 7)
			return null;
		else{
			ChessSquare[][] copy = ChessBoard.getSquares();
			return copy[row][col + 1];
		}
	}
	
	/**
	 * 
	 * @return The ChessSquare left this one
	 */
	public ChessSquare getWest(){
		if(col == 0)
			return null;
		else{
			ChessSquare[][] copy = ChessBoard.getSquares();
			return copy[row][col - 1];
		}
	}
	
	/**
	 * 
	 * @return Determines whether some coordinates are located in the boundaries of this Square
	 */
	public boolean isWithin(int x, int y, double ratioX, double ratioY){
		if((16 + (col + 1) * 111) * ratioX > x && ratioX * (16 + (col) * 111) < x)
			if((16 + (row + 1) * 111) * ratioY > y && (16 + (row) * 111) * ratioY  < y)
				return true;
		return false;
		
	}
	
	public String toString(){
		if(occupant == null)
			return "" + row + ", " + col + ", null";
		else{
			return "" + row + ", " + col + ", " + occupant;
		}
	}
	
	
}
