package Boards;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Pieces.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CrazyBoard extends ChessBoard{


	private static ArrayList<Piece> alivePieces; 
	private Piece selectedPiece;
	private ChessSquare selectedTile;
	private boolean bishopSpecial = false;
	private boolean knightSpecial = false;
	
	public CrazyBoard(JFrame j) {
		super(j);
		setUpNormal();
		alivePieces = super.getPieces();
		
		for(Piece p: alivePieces)
			p.setCrazy();
	}
	
	
public void mouseClicked(MouseEvent e) {
		
	
	int width = getWidth();
 	int height = getHeight();

	double RatioX = width / 970.0;
	double RatioY = height / 970.0;
   

	double smaller = Math.min(RatioX, RatioY);
	
		if(selectedPiece == null){
			for(ChessSquare[] s : getSquares()){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller))
						if(t.getOccupant() != null && (t.getOccupant().getColor().equals("White") == getTurn())){
							selectedPiece = t.getOccupant();
							selectedTile = selectedPiece.getSquare();
						}
				}
			}
		}
		else if(bishopSpecial == true){
			for(ChessSquare[] s : getSquares()){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller)){//finds square that was clicked on
						if(getBishopMoves().contains(t.getRow() * 10 + t.getCol())){
							selectedPiece.moveTo(t);
							setTurn(!getTurn());
							selectedPiece.setCountdown(3);
						}
						selectedPiece = null;
						selectedTile = null;	
					}
				}
			}
			bishopSpecial = false;
		}
		else if(knightSpecial == true){
			for(ChessSquare[] s : getSquares()){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller)){//finds square that was clicked on
						if(selectedPiece.getMoves().contains(t.getRow() * 10 + t.getCol())){
							selectedPiece.moveTo(t);
							selectedPiece.moveTo(selectedTile);
							setTurn(!getTurn());
							selectedPiece.setCountdown(3);
						}
						selectedPiece = null;
						selectedTile = null;	
					}
				}
			}
			knightSpecial = false;
		}
		else{
			for(ChessSquare[] s : getSquares()){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller)){//finds square that was clicked on
						if(t.getOccupant() != null && t.getOccupant().getColor().equals(selectedPiece.getColor()))
						{
							selectedPiece = t.getOccupant();
							selectedTile = selectedPiece.getSquare();
						}
						else
						{
							if(selectedPiece.getMoves().contains(t.getRow() * 10 + t.getCol())){
								
								selectedPiece.moveTo(t);
								setTurn(!getTurn());
							}
							selectedPiece = null;
							selectedTile = null;	
						}
					}
				}
			}
		}

		repaint();
		
		if(isBCheckMate() || isWCheckMate())
			finishGame("Game Over");
	}
	
	

	private boolean isBCheckMate(){
		
		for(Piece p: alivePieces)
			if(p.getType().equals("King") && p.getColor().equals("Black"))
				return false;
		return true;
	}
	
	
	private boolean isWCheckMate(){
		
		for(Piece p: alivePieces)
			if(p.getType().equals("King") && p.getColor().equals("White"))
				return false;
		return false;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if(selectedTile != null){
			int x = selectedTile.getCol();
			int y = selectedTile.getRow();
			g.setColor(Color.red);
			g.fillRect(x * 111 + 16, y * 111 + 16, 10, 111);
			g.fillRect(x * 111 + 16, y * 111 + 16, 111, 10);
			g.fillRect(x * 111 + 16, (y + 1) * 111 + 6, 111, 10);
			g.fillRect((x + 1) * 111 + 6, y * 111 + 16, 10, 111);
		}
		
	}
	
	public void specialMove(){
		selectedPiece.specialMove();
		if(!selectedPiece.getType().equals("Bishop") && !selectedPiece.getType().equals("Knight") ){
			setTurn(!getTurn());
			selectedPiece = null;
			selectedTile = null;
		}
		else{
			if(selectedPiece.getType().equals("Bishop"))
				bishopSpecial = true;
			else
				knightSpecial = true;
		}
			
		if(isBCheckMate() || isWCheckMate())
			finishGame("Game Over");
	}
	
	private  ArrayList<Integer> getBishopMoves(){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		ChessSquare selected = selectedTile.getNorth();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(selectedPiece.getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getNorth();
		}
		
		selected = selectedTile.getSouth();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(selectedPiece.getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getSouth();
		}
		
		selected = selectedTile.getWest();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(selectedPiece.getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getWest();
		}
		
		selected = selectedTile.getEast();
		while(selected != null){
			if(selected.getOccupant() != null){
				if(!selected.getOccupant().getColor().equals(selectedPiece.getColor()))
					moves.add(10 * selected.getRow() + selected.getCol());
				break;
			}
			moves.add(10 * selected.getRow() + selected.getCol());
			selected = selected.getEast();
		}

		
		return moves;
		
	}

	public boolean hasPiece()
	{
		if(selectedPiece == null)
			return false;
		return true;
	}
	public boolean isReady()
	{
		if(hasPiece() && selectedPiece.ready())
			return true;
		return false;
	}
	public int getCountdown()
	{
		if(hasPiece())
			return selectedPiece.getCountdown();
		return 9999;
	}
}
