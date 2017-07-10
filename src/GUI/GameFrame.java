package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import GameControl.GUI;
import GameControl.Player;

public class GameFrame extends JFrame implements WindowListener {

	private MenuBar menuBar;
	private NotePad notePad;
	private Rooms rooms;
	private Characters characters;
	private BoardCanvas board;
	private Weapons weapons;
	private JPanel menuPane;
	private JPanel playerPane;
	private JPanel boardPane;
	private JPanel suggestPane1;
	private JPanel suggestPane2;
	
	// access to our GUI
	private GUI gui;
	
	public GameFrame(GUI gui){
		this.gui = gui;
		addWindowListener(this);
		setVisible(true);
		
		//setLocationRelativeTo(null);	
		menuBar = new MenuBar(this, gui.currentPlayer);
		//cards = new Cards(this, currentPlayer);
		
		rooms = new Rooms(this);
		rooms.setVisible(false);
		
		board = new BoardCanvas(this);
		board.setVisible(false);
		
		characters = new Characters(this);
		characters.setVisible(false);
		
		weapons = new Weapons(this);
		weapons.setVisible(false);
		
		boardPane = new JPanel();
		boardPane.setBounds(0, 100, 1100, 600);
		boardPane.setLayout(new BorderLayout());
		
		suggestPane1 = new JPanel();
		suggestPane1.setLayout(new BorderLayout());
		suggestPane2 = new JPanel();
		suggestPane2.setLayout(new BorderLayout());
		
		suggestPane1.add(rooms.displayRooms(), BorderLayout.NORTH);
		suggestPane2.add(characters.displayCharacters(), BorderLayout.NORTH);
		suggestPane2.add(weapons.displayWeapons(), BorderLayout.SOUTH);
		
		boardPane.add(suggestPane1, BorderLayout.WEST);
		boardPane.add(board, BorderLayout.CENTER);
		boardPane.add(suggestPane2, BorderLayout.EAST);
		
		getContentPane().add(boardPane, BorderLayout.CENTER);
		setSize(1050, 800);
	}
	
	public JPanel getBoardPane(){
		return this.boardPane;
	}
	
	public GUI getGUI(){
		return this.gui;
	}
	
	public BoardCanvas getBoard(){
		return this.board;
		//return null;
	}
	
	public Characters getCharacters(){
		return characters;
	}
	
	public Weapons getWeapons(){
		return weapons;
	}
	
	

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}


