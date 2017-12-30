package WindowsAndMenu;
import java.util.ArrayList;

import Boards.ChessSquare;
import Pieces.*;


public class GameTranslator {
	
	public GameTranslator(){
	
	}
	/**
	 * Converts board array to string format.
	 * @param pieces The ArrayList of Pieces being converted to a String.
	 * @param isWhiteTurn Whether or not it was the White side's turn before the save.
	 * @return A String with the Pieces' type, color and location
	 */
	public String arrayToString(ArrayList<Piece> pieces, boolean isWhiteTurn)
	{
		String result ="";
		for(Piece p: pieces)
		{
			result += p.getType() +"," + p.getColor() + "," +  p.getSquare().getRow() + "," +  p.getSquare().getCol() + "|";
		}
		result += isWhiteTurn;
		
		return result;
	}
	
	/**
	 * Reads String pieceList and converts to a new ArrayList of pieces.
	 * @pre The Pieces' have coordinates such that all Pieces are at row <= squares.length and col <= squares[0].length.
	 * @post The ChessSquares in squares may have new occupants.
	 * @param pieceList The String being converted to Pieces.
	 * @param squares The ChessSquares that the Pieces belong to. The Pieces require a location to be constructed.
	 * @return The Pieces that were constructed in an ArrayList.
	 */
	public ArrayList<Piece> stringToArray(String pieceList, ChessSquare[][] squares)
	{
		StringBuffer s = new StringBuffer(pieceList);
		ArrayList<Piece> result = new ArrayList<Piece>();
		
		while(s.length() > 0 && s.indexOf("|") != -1)
		{
			int end = s.indexOf("|");
			StringBuffer p = new StringBuffer(s.substring(0, end));
			s.delete(0, end+1);
			
			String type = p.substring(0,p.indexOf(","));
			
			p.delete(0, p.indexOf(",")+1);
			
			String color = p.substring(0,p.indexOf(","));
			
			boolean isWhite = true;
			if(color.equals("Black"))
				isWhite = false;
			p.delete(0, p.indexOf(",")+1);
			
			int row = Integer.parseInt(p.substring(0,p.indexOf(",")));
			p.delete(0, p.indexOf(",")+1);
			
			int col = Integer.parseInt(p.toString());
			
			if(type.equals("Pawn"))
				result.add(new Pawn(isWhite,squares[row][col]));
			else if(type.equals("Bishop"))
				result.add(new Bishop(isWhite,squares[row][col]));
			else if(type.equals("Rook"))
				result.add(new Rook(isWhite,squares[row][col]));
			else if(type.equals("Knight"))
				result.add(new Knight(isWhite,squares[row][col]));
			else if(type.equals("King"))
				result.add(new King(isWhite,squares[row][col]));
			else if(type.equals("Queen"))
				result.add(new Queen(isWhite,squares[row][col]));
			
		}
		return result;
	}
	
	/**
	 * Reads String pieceList, and determines whether or not it was White's move when the game was saved.
	 * @return A boolean equivalent to isWhiteTurn();
	 */
	
	public boolean getTurn(String pieceList)
	{
		int b = pieceList.indexOf("true");
		if(b != -1)
		{
			return true;
		}
		return false;
	}
	
	
}