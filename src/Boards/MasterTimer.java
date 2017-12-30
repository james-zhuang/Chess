package Boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MasterTimer extends JPanel implements ActionListener{

	private int time;
	private Timer clock;
	private SevenSegmentPanel[] panels;
	private JFrame j;
	private Box digits;
	private boolean isRunning;
	/**
	 * Creates a new 5 digit Timer used for Expert chess
	 * @param j The JFrame in which the Timer is located
	 */
	public MasterTimer(JFrame j){
		this.j = j;
		
		digits = new Box(BoxLayout.X_AXIS);
		panels = new SevenSegmentPanel[5];
		for (int i = 0; i < 5; i++) 
	 		  panels[i] = new SevenSegmentPanel(0);

		clock = new Timer(1000, this);
		clock.stop();
		
		for (int i = 4; i >= 0; i--){
			if(i%2 == 1){
				ColonPanel c = new ColonPanel();
				digits.add(c);
			}
			panels[i].initializeSegments();
			digits.add(panels[i]);
		}

		add(digits);

	}


	public void actionPerformed(ActionEvent e) {
		
		time++;
		if(time % 100 == 60)
			time += 40;
		if(time % 10000 == 6000)
			time += 4000;
		
		panels[0].setNumber(time % 10);
		panels[1].setNumber((time / 10) % 10);
		panels[2].setNumber((time / 100) % 10);
		panels[3].setNumber((time / 1000) % 10);
		panels[4].setNumber((time / 10000) % 10);
		
		for (int i = 0; i < 5; i++) 
	 		  panels[i].repaint();
		repaint();
	}
	
	/**
	 * Starts the clock in the Timer
	 */
	public void startClock(){
		clock.start();
		isRunning = true;
	}
	
	/**
	 * Stops the clock in the Timer
	 */
	public void stopClock(){
		clock.stop();
		isRunning = false;
	}
	
	/**
	 * Determines the state of this timer
	 * @return Whether or not the clock for this Timer is running
	 */
	public boolean isRunning(){
		return isRunning;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		digits.setPreferredSize(new Dimension((int) (j.getWidth()/2.5),(j.getHeight()/9)));
		
	}
	
	class ColonPanel extends JPanel{
		public void paintComponent(Graphics g){
			 super.paintComponent(g);
			 g.setColor(Color.RED);
			 int width = getWidth();
			 int height = getHeight();
				
			 g.fillRect((width - height/10) / 2, height/4, height/10, height/10);
			 g.fillRect((width - height/10) / 2, 13 * height/20, height/10, height/10);

			 setBackground(Color.BLACK);
		}
	}
	
	class SevenSegmentPanel extends JPanel{
		
		private int number;
		private boolean[] segments;
		private Double[] segments2;
		
		private SevenSegmentPanel(int number){
			this.number = number;
			segments = new boolean[7];
			segments2 = new Double[7];
			
			for (int i = 0; i < 7; i++) 
		   		  segments[i] = false;	
		     
		}
		
		public void initializeSegments(){
			
			 segments2[0] = new RoundRectangle2D.Double(0,0,0,0,0,0);
		     segments2[1] = new RoundRectangle2D.Double(0,0,0,0,0,0);
			 segments2[2] = new RoundRectangle2D.Double(0,0,0,0,0,0);
			 segments2[3] = new RoundRectangle2D.Double(0,0,0,0,0,0);
			 segments2[4] = new RoundRectangle2D.Double(0,0,0,0,0,0);
			 segments2[5] = new RoundRectangle2D.Double(0,0,0,0,0,0);
		     segments2[6] = new RoundRectangle2D.Double(0,0,0,0,0,0);
		}
		
		
		
		public void paintComponent(Graphics g){
			 super.paintComponent(g);
			
			segments[0] = (number != 1) && (number != 4);
		  	segments[1] = (number != 5) && (number != 6);
		  	segments[2] = (number != 2);
		  	segments[3] = (number%3 != 1);
		  	segments[4] = (number != 4) && (number%2 == 0);
			segments[5] = (number == 0) || (number > 3 && number != 7);
		  	segments[6] = (number > 1) && (number != 7);
		  	

			int width = getWidth();
			int height = getHeight();
			segments2[0].setRoundRect(width/4, height/20, width/2, height/10, width/40, height/40);
		     segments2[1].setRoundRect(3 * width/4, 3 * height/20, width/6, 3*height/10, width/40, height/40);
			 segments2[2].setRoundRect(3 * width/4, 11 * height/20, width/6, 3*height/10, width/40, height/40);
			 segments2[3].setRoundRect(width/4, 17 * height/20, width/2, height/10, width/40, height/40);
			 segments2[4].setRoundRect(width/12, 11 * height/20, width/6, 3*height/10, width/40, height/40);
			 segments2[5].setRoundRect(width/12, 3 * height/20, width/6, 3*height/10, width/40, height/40);
		     segments2[6].setRoundRect(width/4, 9 * height/20, width/2, height/10, width/40, height/40);
		  	
		  	Graphics2D g2 = (Graphics2D)g;
			
		    for (int i = 0; i < 7; i++) {
		    	
		    	if (segments[i]) 
		    		g2.setColor(Color.RED); 
		    	else 
		    		g2.setColor(new Color(20,20,20));
		    	g2.fill(segments2[i]);
		    	
		    	setBackground(Color.BLACK);
		    } 
		}
		
		public void setNumber(int number){
			this.number = number;
			
		}
	}
}
