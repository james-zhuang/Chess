package WindowsAndMenu;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Wallace Gray and James Zhuang
 * @version 16 05/24/2015
 * 
 * Chess program designed as a final project for AP-Computer Science
 */

public class ChessMain {

	
	public static void main(String[] args){
	
		

		Object[] options = {"Vanilla Chess","Editor","Crazy Chess","Expert","Quit"};
		int n = JOptionPane.showOptionDialog(new ChessWindow("",""), "Welcome to Chess! \nWhich version do you want to play?", "New Game", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE , 
				new ImageIcon("Icon.jpg"), options, options[0]);
		
		ChessWindow w = null;
		if(n == 0){
			w = new ChessWindow("normal","Chess");
			w.setBackground(Color.WHITE);
			w.setBounds(0, 0, 1000, 1000);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			w.setVisible(true);
			
		}
		
		
		if(n == 1){
			w = new ChessWindow("editor","Editor");
			w.setBackground(Color.WHITE);
			w.setBounds(0, 0, 1100, 1000);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			w.setVisible(true);
			
		}
		
		if(n == 2){
			w = new ChessWindow("crazy","Crazy Chess");
			w.setBackground(Color.WHITE);
			w.setBounds(0, 0, 1100, 1000);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			w.setVisible(true);
			
		}
		
		if(n == 3){
			w = new ChessWindow("expert","Expert Chess");
			w.setBackground(Color.WHITE);
			w.setBounds(0, 0, 1500, 1000);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			w.setVisible(true);
			
		}
		
		if(n == 4){
			System.exit(0);
		}
		
		if(n == 3){
		
			w.setBounds(0, 0, 1500, 980);
		}
	}
	
}
