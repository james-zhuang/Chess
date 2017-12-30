package Boards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Pieces.*;

public class Editor extends ChessBoard{
	
	private boolean canEdit = true;
	private ChessSquare selectedTile;
	
	/**
	 * Creates a new Editor
	 * @param j The JFrame which contains this Editor
	 */
	public Editor(JFrame j){
		super(j);
	}
	
	/**
	 * If it can still be edited, selects Square for inserting/removing Piece
	 * Otherwise, acts like a normal ChessBoard
	 */
	public void mouseClicked(MouseEvent e) {
		
		int width = getWidth();
	 	int height = getHeight();

		double RatioX = width / 970.0;
		double RatioY = height / 970.0;
	   
		double smaller = Math.min(RatioX, RatioY);
		
		if(!canEdit){
			super.mouseClicked(e);
		}
		else{
			for(ChessSquare[] s : ChessBoard.getSquares()){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller)){
						selectedTile = t;
						repaint();
						if(t.getOccupant() == null){
							Piece p = insertPiece(t);
							if(p != null)
								ChessBoard.getPieces().add(p);
						}
						else
							removePiece(t);
						
						repaint();
					}
				}
			}
		}
	}
	
	
	/**
	 * Prevents the editing of the existing board
	 */
	
	public void stopEditing(){
		canEdit = false;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(canEdit && selectedTile != null){
			int x = selectedTile.getCol();
			int y = selectedTile.getRow();
			g.setColor(Color.red);
			g.fillRect(x * 111 + 16, y * 111 + 16, 10, 111);
			g.fillRect(x * 111 + 16, y * 111 + 16, 111, 10);
			g.fillRect(x * 111 + 16, (y + 1) * 111 + 6, 111, 10);
			g.fillRect((x + 1) * 111 + 6, y * 111 + 16, 10, 111);
	
		}
	}
	
	
	/**
	 * @post The ChessSquare which was selected now has an occupant Piece or Null
	 * @param s the square where an insert is about to take place
	 * @return The Piece created
	 */
			
	public Piece insertPiece(ChessSquare s){
		Piece insert = null;
		JOptionPane piece = new JOptionPane();
		Object[] options = {"Pawn","Bishop","Knight","Rook","Queen","King","Cancel"};
		int n = JOptionPane.showOptionDialog(piece, "Which Piece?", "New Piece", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[6]);
		
		if(n != 6){
			JOptionPane color = new JOptionPane();
			options = new String[]{"White","Black","Cancel"};
			int m = JOptionPane.showOptionDialog(color, "Which Color?", "New Piece", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[2]);
		
			if(m != 2){
				if(n == 0){
					insert = new Pawn(m == 0, s);
				}
				if(n == 1){
					insert = new Bishop(m == 0, s);
				}
				if(n == 2){
					insert = new Knight(m == 0, s);
				}
				if(n == 3){
					insert = new Rook(m == 0, s);
				}
				if(n == 4){
					insert = new Queen(m == 0, s);
				}
				if(n == 5){
					if(m == 1)
					{
						if(!hasBKing())
						{
							insert = new King(m == 0, s);
						}
						else
						{
							JOptionPane frame = new JOptionPane();
							JOptionPane.showMessageDialog(frame, "You may only have one king of each color.", "Error", JOptionPane.ERROR_MESSAGE, null);
						}
					}
					else
					{
						if(!hasWKing())
							insert = new King(m == 0, s);
						else
						{
							JOptionPane frame = new JOptionPane();
							JOptionPane.showMessageDialog(frame, "You may only have one king of each color.", "Error", JOptionPane.ERROR_MESSAGE, null);
						}
					}
						
				}
			}
		}
		return insert;
	}
	
	/**
	 * @post The ChessSquare which was selected may no longer have an occupant
	 * @param s the square where an insert is about to take place
	 * @return The Piece removed
	 */
	public Piece removePiece(ChessSquare s){
		
		Piece p = s.getOccupant();
		
		JOptionPane confirmation = new JOptionPane();
		int o = JOptionPane.showConfirmDialog(confirmation, "Are you sure?", "Confirm removal", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, 
				null);
		
		if(o == 0){
			s.setOccupant(null);
			ChessBoard.getPieces().remove(p);
		}
		
		return p;
	}
	
	private boolean hasBKing()
	{
		for(int i = 0; i < ChessBoard.getPieces().size(); i++){
			if(ChessBoard.getPieces().get(i).getType().equals("King") && ChessBoard.getPieces().get(i).getColor().equals("Black"))
				return true;
		}
		return false;
	}
	
	private boolean hasWKing()
	{
		for(int i = 0; i < ChessBoard.getPieces().size(); i++){
			if(ChessBoard.getPieces().get(i).getType().equals("King") && ChessBoard.getPieces().get(i).getColor().equals("White"))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return Whether or not this board has at least one king of each color
	 */
	public boolean isValidBoard(){
		if(hasBKing() && hasWKing())
			return true;
		return false;
	}

}
