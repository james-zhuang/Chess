package WindowsAndMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Boards.CrazyBoard;
import Boards.Editor;
import Boards.ChessBoard;
import Boards.ExpertBoard;


public class ChessWindow extends JFrame implements ActionListener{

	JMenuBar buttons = new JMenuBar();
	JMenu game = new JMenu("Options");
	JMenuItem newGame = new JMenuItem("New Game");
	JMenuItem loadGame = new JMenuItem("Load Game");
	JMenuItem saveGame = new JMenuItem("Save Game");
	JMenuItem mainMenu = new JMenuItem("Main Menu");
	JButton instructions = new JButton("Instructions");
	JButton special = new JButton("Special Move");
	JButton startGame = new JButton("Start Game");
	JButton pauseGame = new JButton("Pause Game");
	ChessBoard board;
	ExpertBoard expert;
	
	/**
	 * Creates a new Window with the board selected
	 * @param gamemode the gamemode that the user requested
	 * @param frameLabel the header of the JFrame
	 */
	public ChessWindow(String gamemode, String frameLabel){
		super(frameLabel);
		
		UIManager.put("nimbusBase", new Color(0x592f09));
		UIManager.put("nimbusBlueGrey", new Color(0x5e3a14));
		UIManager.put("control", new Color(0x592f09));
		UIManager.put("textForeground", new Color(255,255,255));
		UIManager.put("Menu[Enabled].textForeground", Color.WHITE);
		UIManager.put("nimbusFocus", Color.WHITE);
		UIManager.put("TextArea.foreground", Color.BLACK);
		UIManager.put("TextArea[Selected].textForeground", Color.BLACK);
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		setIconImage(new ImageIcon("Icon.jpg").getImage());
		this.setLayout(new BorderLayout());
		
		
		newGame.addActionListener(this);
		loadGame.addActionListener(this);
		saveGame.addActionListener(this);
		mainMenu.addActionListener(this);
		instructions.addActionListener(this);

		game.add(newGame);
		game.add(loadGame);
		game.add(saveGame);
		game.add(mainMenu);
		
		buttons.add(game);
		buttons.add(instructions);
		add(buttons, BorderLayout.NORTH);
		
		
		if(gamemode.equals("normal")){
			
			board = new ChessBoard(this);
			board.setFocusable(true);
			board.addMouseListener(board);
			board.setUpNormal();
			board.repaint();
			add(board, BorderLayout.CENTER);
		}
	
		if(gamemode.equals("editor")){

			board = new Editor(this);
			board.setFocusable(true);
			board.addMouseListener(board);
			buttons.add(startGame);
			startGame.addActionListener(this);
			add(board, BorderLayout.CENTER);
		}
		
		if(gamemode.equals("crazy")){
			board = new CrazyBoard(this);
			board.setFocusable(true);
			board.addMouseListener(board);
			buttons.add(special);
			special.addActionListener(this);
			add(board, BorderLayout.CENTER);
		}
		
		if(gamemode.equals("expert")){
			buttons.add(startGame);
			startGame.addActionListener(this);
			buttons.add(pauseGame);
			pauseGame.addActionListener(this);
			game.remove(newGame);
			remove(newGame);
			revalidate();
			
			expert = new ExpertBoard(this);
			add(expert);
			expert.repaint();
		}
		
		
		
		
	
	}

	public void actionPerformed(ActionEvent e) {
		Object a = e.getSource();

			if(a == newGame){
				JOptionPane.showConfirmDialog(this, "Are you sure? This will set up the standard chess board.");
				board.setUpNormal();
			}
			
			if(a == loadGame){
				FileIO reader = new FileIO();
				File f = new File("Game1.chess");
				if(f.exists() && !f.isDirectory())
				{
					String pieces = reader.readFile("Game1.chess");
					GameTranslator g = new GameTranslator();
					board.clearBoard();
					board.setBoard(g.stringToArray(pieces, ChessBoard.getSquares()));
					board.setTurn(g.getTurn(pieces));
					
				}
				else
				{
					JOptionPane frame = new JOptionPane();
					JOptionPane.showMessageDialog(frame, "No saves have been made.", "Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}
			
			
			if( a == saveGame)
			{
				GameTranslator g = new GameTranslator();
				String pieces = g.arrayToString(ChessBoard.getPieces(),board.getTurn());
				FileIO f = new FileIO();
				f.writeFile("Game1.chess", pieces);
			}
			
			if(a == startGame){
				
				if(board instanceof Editor){
					Editor edit = (Editor)board;
					if(edit.isValidBoard())
					{
						int confirm = JOptionPane.showConfirmDialog(this, "Are you sure? (No more changes may be made)", "Confirm", JOptionPane.YES_NO_OPTION);
						if(confirm == 0)
						{
							edit.stopEditing();
							buttons.remove(startGame);
							remove(startGame);
							revalidate();
							repaint();
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this, "You must have a king of each color.", "Error", JOptionPane.ERROR_MESSAGE, null);
					}
				}
				
				else
					expert.startGame();
					
			}
			
			if( a == pauseGame)
			{
				expert.pauseGame();
			}
			
			if(a== mainMenu)
			{
				dispose();
				if(board != null)
					board.clearBoard();
				else
					expert.clearBoard();
				ChessMain.main(null);
			}
			
			if(a== instructions)
			{
				Instructions w = new Instructions();
				w.setBackground(Color.WHITE);
				w.setBounds(0, 0, 1000, 1000);
				w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				w.setVisible(true);
			}
			
			if(a == special){
				CrazyBoard crazy = (CrazyBoard)board;
				if(crazy.hasPiece())
				{
					if(crazy.isReady())
					{
						int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
						if(confirm == 0)
						{
							crazy.specialMove();
							crazy.repaint();
						}
					}
					else
						JOptionPane.showMessageDialog(this, "The piece is not yet ready for its special move! \n Turns remaining: " + crazy.getCountdown(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(this, "No piece selected.", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			
		}	
		
	}
	
	
	

