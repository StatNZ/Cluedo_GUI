package GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GameControl.Game;
import GameControl.Player;


@SuppressWarnings("serial")
public class BoardCanvas extends JPanel implements MouseListener{

	private GameFrame frame;
	private BufferedImage board;
	public Player player;
	
	public int OFFSET = 15;
	
	public BoardCanvas(GameFrame frame){
		this.frame = frame;
		startMouseListener();
		//this.player = Game.createPlayer("jack", Player.Token.MissScarlett, 1);
		// reads/loads board image
		try {
		    board = ImageIO.read(new File("Board/board.jpg"));
		} catch (IOException e) {
		}
	}
	
	public BufferedImage getImage(){
		return board;
	}
	
	/** We set these as method calls so we may easily call it from our Game Class **/
	public void startMouseListener(){
		
		addMouseListener(this);
	}	
	public void stopMouseListener(){
		removeMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// draws the board
		g.drawImage(board, OFFSET, OFFSET, this);
		
		// display players
		for (Player p: this.frame.getGUI().getPlayers()){
			p.draw(g, this);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e){		
		this.frame.getGUI().onClick(e, board.getHeight(),board.getWidth(),OFFSET);		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
