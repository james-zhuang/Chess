package Boards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ExpertBoard extends JPanel implements BoardListener{
	
	private ChessBoard board;
	private MasterTimer whiteTimer;
	private MasterTimer blackTimer;
	private Box timerBox;
	private boolean paused;
	private MasterTimer pausedTimer;
	private boolean whiteTurn = true;
	private PointBox wPoints, bPoints;
	private JFrame j;
	
	public ExpertBoard(JFrame j){
		
		setLayout(new BorderLayout());
		this.j = j;
		whiteTimer = new MasterTimer(j);
		blackTimer = new MasterTimer(j);
		
		board = new ChessBoard(j);
		board.setUpNormal();
		board.repaint();
		board.setBoardListener(this);
		
		timerBox = new Box(BoxLayout.Y_AXIS);
		add(board, BorderLayout.CENTER);
		add(timerBox, BorderLayout.LINE_END);
		
		wPoints = new PointBox("White");
		bPoints = new PointBox("Black");
		addTimerBox();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	
	}
	
	private void addTimerBox(){
		
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(blackTimer);
		timerBox.add(bPoints);
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(500));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(500));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(500));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(500));
		timerBox.add(whiteTimer);
		timerBox.add(wPoints);
		timerBox.add(Box.createHorizontalStrut(200));
		timerBox.add(Box.createHorizontalStrut(500));
		timerBox.repaint();
		
		
	}
	
	
	
	private void switchTimers(){
	
		if(whiteTimer.isRunning()){
			whiteTimer.stopClock();
			blackTimer.startClock();
		}
		else{
			blackTimer.stopClock();
			whiteTimer.startClock();
		}

	}
	
	
	/**
	 * Starts the Timer, and allows or re-allows the editing of the board
	 */
	public void startGame(){
	
		if(paused){
			board.addMouseListener(board);
			pausedTimer.startClock();
			paused = false;
			
		}
		else{
			board.addMouseListener(board);
			board.setFocusable(true);
			switchTimers();
		}
		setVisible(true);
		repaint();
	}
	
	
	/**
	 * Pauses both timers in this game, and prevents editing of the board.
	 */
	public void pauseGame(){
		if(blackTimer.isRunning()){
			pausedTimer = blackTimer;
			blackTimer.stopClock();
		}
		else{
			pausedTimer = whiteTimer;
			whiteTimer.stopClock();
		}
		board.removeMouseListener(board);
		paused = true;
		setVisible(false);
		repaint();
		
	}
	
	/**
	 * Pauses the running timer, and unpauses the other paused one
	 */
	public void switchTurns() {
		whiteTurn = !whiteTurn;
		switchTimers();
	}

	/**
	 * Sets the points for the White tally
	 * @param points The points that White has earned
	 */
	public void setWPoints(int points) {
		wPoints.setPoints(points);
		repaint();
	}

	/**
	 * Sets the points for the Black tally
	 * @param points The points that Black has earned
	 */
	public void setBPoints(int points) {
		bPoints.setPoints(points);
		repaint();
	}
	/**
	 * resets timers and points
	 */
	public void reset()
	{
		
	}
	/**
	 * clears internal chess board
	 */
	public void clearBoard()
	{
		board.clearBoard();
	}
	
	class PointBox extends JPanel{
		 
		JLabel label;
		JTextArea area;
		public PointBox(String color){
			label = new JLabel(color + " points:");
			area = new JTextArea(1,2);
			area.setEditable(false);
			area.setText("" + 0);
			setLayout(new FlowLayout());
			add(label);
			add(area);
		}
		 
		public void setPoints(int points){
			area.setText("" + points);
		}
	}
}
