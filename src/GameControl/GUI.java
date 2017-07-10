package GameControl;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.BoardCanvas;
import GUI.CharacterPick;
import GUI.GameFrame;
import GUI.StartGame;

/**
 * This class is the designer of our GUI. All classes wishing to be viewed upon the GUI must
 * inherit this class as its super. The GUI contains a huge chunk of the games logic. It is responsible
 * for ensuring the order of each player functions appropriately.
 * 
 * @author jack
 *
 */
public abstract class GUI {
	
	private GameFrame cluedoFrame;
	private StartGame startDialog;
	private CharacterPick charPickDialog;
	private static StartGame start;
	
	protected int getDiceRoll;
	
	public int NUMBER_OF_PLAYERS;
	
	private List<Player> players = new ArrayList<Player>();
	
	public Player currentPlayer;
	
	public enum Move {
		NORTH, SOUTH, EAST, WEST
	}
	
	
	
	/**
	 * This method will be called at the beginning of every players turn
	 */
	public void newDiceRoll(){
		Random ran = new Random();
		this.getDiceRoll = ran.nextInt(10)+2;		
	}
	
	public Graphics getGraphics(){
		return cluedoFrame.getGraphics();
	}
	
	public GameFrame getFrame(){
		return cluedoFrame;
	}
	
	public void addPlayers(Player p){
		this.players.add(p);
	}
	
	public List<Player> getPlayers(){
		return this.players;
	}
	
	public void nextPlayer(){
		int index = this.players.indexOf(currentPlayer);
		if (index == this.players.size()-1 || index == -1){
			this.currentPlayer = this.players.get(0);
			return;
		}
		this.currentPlayer = this.players.get(index+1);
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	/**
	 * Select a character or weapon from the given string
	 * @param cardName
	 */
	public abstract void selectCharacter(String cardName, Player.Token selectedToken);	
	public abstract void selectWeapon(String cardName);
	
	/**
	 * Deal each player in our a list set of cards each
	 */
	public abstract void dealCards();
	
	/**
	 * Deals with the logic of resetting variables and starting the turn for a next player
	 */
	public abstract void nextPlayersTurn();
	
	/**
	 * Create a player with the given information
	 */
	public abstract Player createPlayer(Player.Token token, int playerNo);
	
	/**
	 * Is called when a player uses either the keys to move or a JButton is pressed which is present
	 * on the frame. Moves the player in the corresponding direction of the parameter which is passed
	 * in.
	 * @param move - the direction to move a player
	 */
	protected abstract void onMove(Move move, Player player);
	
	/**
	 * Is called when the user presses the mouse button. It then starts by taking the co-ordinate of the
	 * mouse and determines the position in which a player should travel
	 * @param e - the mouse event listener
	 */
	public abstract void onClick(MouseEvent e, int widthOffset, int heightOffset, int offset);
	
	/**
	 * don't really need this method
	 */
	public void initialise(){
		cluedoFrame = new GameFrame(this);
	}
	
	public GUI(){
		initialise();
	}
	

}
