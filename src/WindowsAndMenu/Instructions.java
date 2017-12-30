package WindowsAndMenu;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Instructions extends JFrame{

	public Instructions()
	{
		super("Instructions");
		add(new instructionPane());
	}
	
	
	class instructionPane extends JPanel{
		public void paintComponent(Graphics g){
			g.drawImage(new ImageIcon("instructions.jpg").getImage(),0,0, this);
		}
	}
}
