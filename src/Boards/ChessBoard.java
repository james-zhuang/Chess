package Boards;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Pieces.*;
import WindowsAndMenu.ChessMain;


public class ChessBoard extends JPanel implements MouseListener{

	private Image background;
	private static ChessSquare[][] squares;
	private static ArrayList<Piece> alivePieces;
	private Piece selectedPiece;
	private boolean whiteTurn;
	private StringBuffer debugString;
	private JFrame container;
	private double RatioX;
	private double RatioY;
	private BoardListener expert = null;
	
	/**
	 * Creates a new empty, unsetup board
	 * @param j The JFrame that contains this ChessBoard
	 */
	public ChessBoard(JFrame j){
		super();
		container = j;
		background = (new ImageIcon("PiecesImages/600px-Chess_Board.png")).getImage();
		squares = new ChessSquare[8][8];
		alivePieces = new ArrayList<Piece>();
		selectedPiece = null;
		whiteTurn = true;
		debugString = new StringBuffer();
		clearBoard();
	}
	
	/**
	 * Clears the existing board, and then creates new Pieces in the standard positions
	 */
	
	public void setUpNormal(){
		
		clearBoard();
		
		whiteTurn = true;
		for(int i = 0; i < 8; i++){
			alivePieces.add(new Pawn(true, squares[6][i])); 
		}
		
		for(int i = 0; i < 8; i++){
			alivePieces.add(new Pawn(false, squares[1][i])); 
		}
		
		alivePieces.add(new Knight(true, squares[7][1]));
		alivePieces.add(new Knight(true, squares[7][6]));
		alivePieces.add(new Knight(false, squares[0][1]));
		alivePieces.add(new Knight(false, squares[0][6]));
		
		alivePieces.add(new Rook(true, squares[7][0]));
		alivePieces.add(new Rook(true, squares[7][7]));
		alivePieces.add(new Rook(false, squares[0][0]));
		alivePieces.add(new Rook(false, squares[0][7]));
		
		alivePieces.add(new Bishop(true, squares[7][5]));
		alivePieces.add(new Bishop(true, squares[7][2]));
		alivePieces.add(new Bishop(false, squares[0][5]));
		alivePieces.add(new Bishop(false, squares[0][2]));
		
		alivePieces.add(new Queen(true, squares[7][3]));
		alivePieces.add(new Queen(false, squares[0][3]));
		
		alivePieces.add(new King(true, squares[7][4]));
		alivePieces.add(new King(false, squares[0][4]));
		repaint();
	}
	
	/**
	 * Sets the Listener of this board to listener
	 * @param listener The listener of this board
	 */
	public void setBoardListener( BoardListener listener){
		expert = listener;
	}
	
	/**
	 * Gets the Squares of this board
	 */
	public static ChessSquare[][] getSquares(){
		return squares;
	}
	
	/**
	 * Gets the Pieces existing in this board
	 */
	public static ArrayList<Piece> getPieces(){
		return alivePieces;
	}

	/**
	 * Resets all of the squares in the board
	 */
	public void clearBoard(){
		alivePieces.clear();
		squares = new ChessSquare[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				squares[i][j] = new ChessSquare(i,j);
		
			}
		}
	}
	
	/**
	 * Adds the ArrayList input into the board 
	 * @param input The pieces being inserted into the board
	 */
	
	public void setBoard(ArrayList<Piece> input)
	{
		alivePieces = input;
		repaint();
	}

	/**
	 * Paints the pieces, background, and Rectangles
	 * @param g Graphic object
	 */
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		 	int width = getWidth();
		 	int height = getHeight();
		
		 	
			RatioX = width / 970.0;
			RatioY = height / 970.0;
		   
		   Graphics2D g2 = (Graphics2D)g;
		   AffineTransform at = g2.getTransform();
		   if(RatioX > RatioY)
			   g2.scale(RatioY, RatioY);
		   else
			   g2.scale(RatioX, RatioX);
		
		   g.drawImage(background, 10, 10, 900, 900, this);
		   
		   if(selectedPiece != null){
			   int[] coordinates = selectedPiece.getCoordinates();
			   g.setColor(Color.red);
			   g.fillRect(10 + 6 + (coordinates[1]) * 111, 10 + 6 + (coordinates[0]) * 111, 111, 111);
		   
		   }
		   
		   for (Piece p : alivePieces){
			   int[] coordinates = p.getCoordinates();
			
			   g.drawImage(p.getIcon(), 10 + 16 + (coordinates[1]) * 111, 
					 10 + 16 + (coordinates[0]) * 111, 91, 91, this);
			
			   g.setColor(Color.red);
			   g.fillRect(10, 10 + Boolean.compare(whiteTurn, false) * 894, 900, 6);
			
			   g.setColor(Color.red);
			   if(isWCheck() && !(this instanceof CrazyBoard)){
				   g.drawImage((new ImageIcon("check.png")).getImage(),925,810,79,92,this);
			   }
			   if(isBCheck() && !(this instanceof CrazyBoard)){
				   g.drawImage((new ImageIcon("check.png")).getImage(),925,35,79,92,this);
			   }
			
		   }
	}
	
	
	
	
	/**
	 * Figures out what piece was selected. 
	 * Then figures out if the next click is a valid move. 
	 * Then checks for Check/Checkmate/Stalemate
	 * @param e MouseEvent
	 */
	public void mouseClicked(MouseEvent e) {
		
		double smaller = Math.min(RatioX, RatioY);
		
		if(selectedPiece == null){
			for(ChessSquare[] s : squares){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller))
						if(t.getOccupant() != null && (t.getOccupant().getColor().equals("White") == whiteTurn)){
							selectedPiece = t.getOccupant();
						}
				}
			}
		}
		else{
			for(ChessSquare[] s : squares){
				for(ChessSquare t : s){
					if(t.isWithin(e.getX(), e.getY(), smaller, smaller)){//finds square that was clicked on

						int direction = -1;
						if(t.getCol() > selectedPiece.getSquare().getCol())
							direction = 1;
						
						if(t.getOccupant() != null && t.getOccupant().getColor().equals(selectedPiece.getColor()))
							selectedPiece = t.getOccupant();
						else{
							if(selectedPiece.getType().equals("King")){
								King king = (King)selectedPiece;
								if(king.canCastle(direction) && ((direction == 1 && t.getCol() == 6) 
									|| (direction == -1 && t.getCol() == 1) || (direction == -1 && t.getCol() == 2))){
								
									king.moveTo(t);
									Piece rook;
									if(direction > 0){
										rook = squares[t.getRow()][7].getOccupant();
										rook.moveTo(t.getWest());
									}
									else{
										rook = squares[t.getRow()][0].getOccupant();
										rook.moveTo(t.getEast());
									}
	
								whiteTurn = !whiteTurn;
								}
								else if(selectedPiece.getMoves().contains(t.getRow() * 10 + t.getCol())){
									
									debugString.append("\nMoving " + selectedPiece + " " + t);
									
									ChessSquare startingSquare = selectedPiece.getSquare();
									Piece temporary = t.getOccupant();//In case of check/checkmate/stalemate problems
									boolean hasMoved = selectedPiece.hasMoved();	
										
										selectedPiece.moveTo(t);
									
										
										if(isWCheck() && whiteTurn){ //If white in check after White's move
											selectedPiece.moveTo(startingSquare);//Moves selected piece back
											if(!hasMoved)
												selectedPiece.overwriteHasMoved();//Sets hasMoved back to false
											if(temporary != null){//If there was an occupant to square t
												temporary.moveTo(t); // sets temporary back to square t
												alivePieces.add(temporary);//adds it back to the array
												debugString.append("\nAdded " + temporary + " back to " + t);
											}
											
											if(isWCheck())
												if(isWCheckmate("check")){
													finishGame("Checkmate");
											}
										}
									else if(isBCheck() && !whiteTurn){
										selectedPiece.moveTo(startingSquare);//Moves selected piece back
										if(!hasMoved)
											selectedPiece.overwriteHasMoved();//Sets hasMoved back to false
											if(temporary != null){//If there was an occupant to square t
												temporary.moveTo(t); // sets temporary back to square t
												alivePieces.add(temporary);//adds it back to the array
												debugString.append("\nAdded " + temporary + " back to " + t);
											}
									
											if(isBCheck())
												if(isBCheckmate("check")){
													finishGame("Checkmate");
											}
										}
										else
											whiteTurn = !whiteTurn;
								}
								
								
								if(isWCheck())
									if(isWCheckmate("check")){
										finishGame("Checkmate");
								}
								if(isBCheck())
									if(isBCheckmate("check")){
										finishGame("Checkmate");
								}
								
							}
							else if(selectedPiece.getMoves().contains(t.getRow() * 10 + t.getCol())){
								
							debugString.append("\nMoving " + selectedPiece + " " + t);
							
							ChessSquare startingSquare = selectedPiece.getSquare();
							Piece temporary = t.getOccupant();//In case of check/checkmate/stalemate problems
							Piece p = null;
							boolean hasMoved = selectedPiece.hasMoved();	
								
								if(selectedPiece.getType().equals("Pawn")){
									Pawn pawn = (Pawn)selectedPiece;
									if(pawn.canPromote()){//If Piece is a Pawn, will move to other end
										p = pawn.pawnPromotion();
										if(p == null){
											break;
										}
										else{
											p.moveTo(t);//moves new Piece to next square
											alivePieces.add(p);
											alivePieces.remove(selectedPiece);
											debugString.append("\nCreated new " + p + " and removed " + selectedPiece);
										}
									}
									else
										selectedPiece.moveTo(t);
									
								}
								else
									selectedPiece.moveTo(t);
							
								
								if(isWCheck() && whiteTurn){ //If white in check after White's move
									selectedPiece.moveTo(startingSquare);//Moves selected piece back
									if(!hasMoved)
										selectedPiece.overwriteHasMoved();//Sets hasMoved back to false
									if(temporary != null){//If there was an occupant to square t
										temporary.moveTo(t); // sets temporary back to square t
										alivePieces.add(temporary);//adds it back to the array
										debugString.append("\nAdded " + temporary + " back to " + t);
									}
									if(p != null){
										alivePieces.add(selectedPiece);//Adds the old Pawn back in
										alivePieces.remove(p);//removes the promoted Piece
										debugString.append("\nAdded " + p + " back to " + t);
									}
									if(isWCheck())
										if(isWCheckmate("check")){
											finishGame("Checkmate");
									}
								}
							else if(isBCheck() && !whiteTurn){
								selectedPiece.moveTo(startingSquare);//Moves selected piece back
								if(!hasMoved)
									selectedPiece.overwriteHasMoved();//Sets hasMoved back to false
									if(temporary != null){//If there was an occupant to square t
										temporary.moveTo(t); // sets temporary back to square t
										alivePieces.add(temporary);//adds it back to the array
										debugString.append("\nAdded " + temporary + " back to " + t);
									}
									if(p != null){
										alivePieces.add(selectedPiece);//Adds the old Pawn back in
										alivePieces.remove(p);//removes the promoted Piece
										debugString.append("\nAdded " + p + " back to " + t);
									}
									if(isBCheck())
										if(isBCheckmate("check")){
											finishGame("Checkmate");
									}
								}
								else{
									whiteTurn = !whiteTurn;
									if(expert != null){
										expert.switchTurns();
										expert.setWPoints(getPoints("Black"));
										expert.setBPoints(getPoints("White"));
										
									}
							
								}
							
							}
							selectedPiece = null;
							if((isWCheckmate("stale") && !isWCheck()) && whiteTurn || (isBCheckmate("stale") && !isBCheck() && !whiteTurn))
								finishGame("Stalemate");
						}
					}
				}
			}
		}
		repaint();
		if(isWCheck())
			if(isWCheckmate("check")){
				finishGame("Checkmate");
		}
		if(isBCheck())
			if(isBCheckmate("check")){
				finishGame("Checkmate");
		}
		
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	private int getPoints(String Color){
		int points = 39;
		for(Piece p : alivePieces){
			if(p.getColor().equals(Color)){
				if(p instanceof Pawn)
					points = points - 1;
				else if(p instanceof Knight)
					points = points - 3;
				else if(p instanceof Queen)
					points = points - 9;
				else if(p instanceof Bishop)
					points = points - 3;
				else if(p instanceof Rook)
					points = points - 5;
			}
		}
		return points;
	}
	
	private boolean isBCheck(){
		
		int bKingIndex = 0;
		
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("Black") && alivePieces.get(i).getType().equals("King"))
				bKingIndex = i;
		}
		
		int kSquare = alivePieces.get(bKingIndex).getSquare().getRow() * 10 + alivePieces.get(bKingIndex).getSquare().getCol();
		
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("White"))
				for(int location: alivePieces.get(i).getMoves()){
					if(location == kSquare){
						debugString.append("\nBlack King in check by " + alivePieces.get(i));
						debugString.append("\n" + alivePieces.get(i) + " moves are:");
						
						for(Integer k : alivePieces.get(i).getMoves()){
							debugString.append(" " + k);
						}
						debugString.append("\n");
						
						return true;
					}
				}
		}
		
		return false;

	}
	
	private boolean isWCheck(){
		int wKingIndex = 0;
		
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("White") && alivePieces.get(i).getType().equals("King"))
				wKingIndex = i;
		}
		
		int kSquare = alivePieces.get(wKingIndex).getSquare().getRow() * 10 + alivePieces.get(wKingIndex).getSquare().getCol();
		
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("Black"))
				for(int location: alivePieces.get(i).getMoves()){
					if(location == kSquare){
						debugString.append("\nWhite King in check by " + alivePieces.get(i));
						debugString.append("\n" + alivePieces.get(i) + " moves are:");
						
						for(Integer k : alivePieces.get(i).getMoves()){
							debugString.append(" " + k);
						}
						//debugString.append("\n");
						
						return true;
					}
				}
		}
		
		return false;

	}
	
	private boolean isWCheckmate(String state){
		
		debugString.append("\nChecking for " + state + "mate\n");
		
		ArrayList<Integer> bMoves = new ArrayList<>();
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("Black"))
				bMoves.addAll(alivePieces.get(i).getMoves());
		}
		
		boolean hasMoved;
		for(int i = 0; i < alivePieces.size(); i++){//for all alive pieces
			
			Piece p = alivePieces.get(i);//gets the piece
			hasMoved = p.hasMoved();
			
			if(p.getColor().equals("White")){//only checks white pieces
				debugString.append("\nAttempting to move " + p + " at " + p.getSquare());
				
				int startingSquare = p.getSquare().getRow() * 10 + p.getSquare().getCol(); //gets piece's original square
				
				/*
				 * Gets all alive black pieces, and moves them to each of their possible move locations
				 * Then checks if in check or not
				 */
						
				for(int location: p.getMoves()){
					Piece temp = squares[location/10][location%10].getOccupant();// creates temporary piece in case it will be taken
					if(temp != null)
						debugString.append("\nSaving " + temp + " to temp");
					else
						debugString.append("\n" + location + " is not occupied");
					debugString.append("\nMoving piece to " + location + " to check for check");
					p.moveTo(squares[location/10][location%10]);
					
					if(isWCheck() == false){
						p.moveTo(squares[startingSquare/10][startingSquare%10]);//Moves back to starting
						if(!hasMoved)
							p.overwriteHasMoved();//Sets hasMoved back to false
						
						if(temp != null){//If the nextLocation was occupied
							temp.moveTo(squares[location/10][location%10]);
							debugString.append("\nAdded " + temp + " back to " + location);
							alivePieces.add(i,temp);
						}
						
						debugString.append("\nNot " + state + "mate!\n");
						return false;
					}
					
					else{
						p.moveTo(squares[startingSquare/10][startingSquare%10]);//Moves back to starting
						if(!hasMoved)
							p.overwriteHasMoved();//Sets hasMoved back to false
						
						if(temp != null){//If the nextLocation was occupied
							debugString.append("\nAdded " + temp + " back to " + location);
							temp.moveTo(squares[location/10][location%10]);
							alivePieces.add(i,temp);
						}
					}
				}
				debugString.append("\n" + p + " has failed at preventing check\n");
			}
		}
		return true;
	}
	
	private boolean isBCheckmate(String state){
		
		debugString.append("\nChecking for " + state + "mate\n");
		
		ArrayList<Integer> wMoves = new ArrayList<>();
		for(int i = 0; i < alivePieces.size(); i++){
			if(alivePieces.get(i).getColor().equals("White"))
				wMoves.addAll(alivePieces.get(i).getMoves());
		}
		
		boolean hasMoved;
		for(int i = 0; i < alivePieces.size(); i++){//for all alive pieces
			
			Piece p = alivePieces.get(i);//gets the piece
			hasMoved = p.hasMoved();
			
			if(p.getColor().equals("Black")){//only checks black pieces
				debugString.append("\nAttempting to move " + p + " at " + p.getSquare());
				int startingSquare = p.getSquare().getRow() * 10 + p.getSquare().getCol(); //gets piece's original square
				
				/*
				 * Gets all alive white pieces, and moves them to each of their possible move locations
				 * Then checks if in check or not
				 */
						
				for(int location: p.getMoves()){
					Piece temp = squares[location/10][location%10].getOccupant();// creates temporary piece in case it will be taken
					if(temp != null)
						debugString.append("\nSaving " + temp + " to temp");
					else
						debugString.append("\n" + location + " is not occupied");
					debugString.append("\nMoving piece to " + location);
					
					p.moveTo(squares[location/10][location%10]);
					
					if(isBCheck() == false){
						p.moveTo(squares[startingSquare/10][startingSquare%10]);//Moves back to starting
						if(!hasMoved)
							p.overwriteHasMoved();//Sets hasMoved back to false
						
						if(temp != null){//If the nextLocation was occupied
							debugString.append("\nAdded " + temp + " back to " + location);
							temp.moveTo(squares[location/10][location%10]);
							alivePieces.add(i,temp);
						}
						debugString.append("\nNot " + state + "mate!");
						return false;
					}
					
					else{
						p.moveTo(squares[startingSquare/10][startingSquare%10]);//Moves back to starting
						if(!hasMoved)
							p.overwriteHasMoved();//Sets hasMoved back to false
						
						if(temp != null){//If the nextLocation was occupied
							debugString.append("\nAdded " + temp + " back to " + location);
							temp.moveTo(squares[location/10][location%10]);
							alivePieces.add(i,temp);
						}
					}
				}
				debugString.append("\n" + p + " has failed at preventing check\n");
			}
		}
		return true;
		
	}
	
	/**
	 * Creates a new JOptionPane for the end of the game with gamestate as its title
	 * @param gamestate The state of the game, which may be checkmate or stalemate
	 */
	
	public void finishGame(String gamestate){
		Object[] options = {"Play Again","Exit to Main Menu","Quit"};
		JOptionPane frame = new JOptionPane(gamestate + "! What do you want to do?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, 
				null, options, options[0]);
		JDialog dialog = frame.createDialog(gamestate);
		dialog.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent evt){}});
		dialog.setContentPane(frame);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();

		dialog.setVisible(true);
		String n = (String) frame.getValue();
		//int n = JOptionPane.showOptionDialog(frame, gamestate + "! What do you want to do?", "Checkmate", 
		//		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
		//		null, options, options[0]);
		
		if(n.equals("Play Again")){
			
			alivePieces.clear();
			squares = new ChessSquare[8][8];
			expert.reset();
			this.setUpNormal();
			
		}
		if(n.equals("Exit to Main Menu")){
			
			clearBoard();
			container.dispose();
			ChessMain.main(null);
			
		}
		if(n.equals("Quit")){
			System.exit(0);
			
		}
		
	}
	
	/**
	 * @return The debug String, which lists actions that the program has tried to make. Used in the DebugBoard 
	 */
	
	public StringBuffer getDebug(){
		return debugString;
	}
	public boolean getTurn()
	{
		return whiteTurn;
	}
	public void setTurn(boolean b)
	{
		whiteTurn = b;
	}
	
	/**
	 * Clears the current debug String
	 */
	public void clearDebug(){
		debugString.setLength(0);
	}
	
	

}
