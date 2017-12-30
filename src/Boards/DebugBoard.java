package Boards;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DebugBoard extends ChessBoard{

	private JTextArea debugBox = new JTextArea(10,45);
	/**
	 * Creates a new DebugBoard. This board is not used in the final implementation of this program. This serves as a debugger for various algorithms used in ChessBoard and Piece
	 * @param j The JFrame to which this board belongs
	 */
	public DebugBoard(JFrame j){
		super(j);
		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(debugBox);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll,BorderLayout.EAST);
	}
	
	public void mouseClicked(MouseEvent e){
		super.mouseClicked(e);
		debugBox.append(getDebug().toString());
		clearDebug();
	}
	
	
	
	
}
