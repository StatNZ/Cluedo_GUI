package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import Card.Card;
import GameControl.Player;

public class MenuBar implements ActionListener {
	
	private GameFrame frame;
	private JPanel menuPane;
	private JMenuBar menuBar;
	private JMenu mFile;
	private JToolBar toolBar;
	private JTextArea diceRoll;
	private JButton newGame, rules, quit, endTurn, cards, notePad;
	private ImageIcon iconNew;
	private ImageIcon iconRules;
	private ImageIcon iconQuit;
	
	
	public MenuBar(GameFrame frame, Player current){
		this.frame = frame;
		menuPane = new JPanel(new GridLayout(0, 1));
		//createDiceRoll();
		createMenuDrop();
		createMenuTool();
		 
		frame.add(menuPane, BorderLayout.NORTH);
	}
	
	public void createMenuDrop(){
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mFile = new JMenu("File");
		mFile.setMnemonic('f');
		
		Action actionNew = new AbstractAction("New", iconNew) {
			public void actionPerformed(ActionEvent e) {
				System.out.println("new action");
			}
		};
		JMenuItem item = mFile.add(actionNew);
		item.setMnemonic('n');
		mFile.add(item);
    
		Action actionRules = new AbstractAction("Rules", iconRules) {
			public void actionPerformed(ActionEvent e) {
				rules();
			}
		};
		item = mFile.add(actionRules);
		item.setMnemonic('r');
		mFile.add(item);
    
		Action actionQuit = new AbstractAction("Quit", iconQuit) {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		item = mFile.add(actionQuit);
		item.setMnemonic('q');
		mFile.add(item);
		mFile.addSeparator();
		menuBar.add(mFile);
		
		menuPane.add(menuBar, BorderLayout.NORTH);
	}
	
	public void createMenuTool(){
		
		toolBar = new JToolBar();
		
		ImageIcon iconNew = new ImageIcon("Buttons/button_new.png");
		newGame = new JButton(iconNew);
		newGame.addActionListener(this);
		newGame.setActionCommand("newGame");
		newGame.setMnemonic(KeyEvent.VK_N);
		newGame.setToolTipText("New Game");
		toolBar.add(newGame);
		
		ImageIcon iconRules = new ImageIcon("Buttons/button_rules.png");
		rules = new JButton(iconRules);
		rules.addActionListener(this);
		rules.setActionCommand("rules");
		rules.setMnemonic(KeyEvent.VK_R);
		newGame.setToolTipText("Show Rules");
		toolBar.add(rules);
		
		ImageIcon iconQuit = new ImageIcon("Buttons/button_quit.png");
		quit = new JButton(iconQuit);
		quit.addActionListener(this);
		quit.setActionCommand("quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setToolTipText("Quit Game");
		toolBar.add(quit);
		
		toolBar.add( Box.createHorizontalGlue() );
		
		//toolBar.add(diceRoll);
		
		toolBar.addSeparator();
		
		ImageIcon iconCards = new ImageIcon("Buttons/button_cards.png");
		cards = new JButton(iconCards);
		cards.addActionListener(this);
		cards.setActionCommand("cards");
		cards.setMnemonic(KeyEvent.VK_C);
		cards.setToolTipText("Show Cards");
		toolBar.add(cards);
		
		ImageIcon iconNotePad = new ImageIcon("Buttons/button_notePad.png");
		notePad = new JButton(iconNotePad);
		notePad.addActionListener(this);
		notePad.setActionCommand("notePad");
		notePad.setMnemonic(KeyEvent.VK_N);
		notePad.setToolTipText("NotePad");
		toolBar.add(notePad);
		
		ImageIcon iconEndTurn = new ImageIcon("Buttons/button_endTurn.png");
		endTurn = new JButton(iconEndTurn);
		endTurn.addActionListener(this);
		endTurn.setActionCommand("endTurn");
		endTurn.setMnemonic(KeyEvent.VK_E);
		endTurn.setToolTipText("End Turn");
		toolBar.add(endTurn);
		
		//toolBar.setBounds(0, 20, 1000, 100);
		menuPane.add(toolBar, BorderLayout.SOUTH);
	}
	
	public void createDiceRoll(){
		//String s = "You have " + roll + " moves";
		//diceRoll = new JTextArea(s);
	}
	
	public void rules(){
		JOptionPane.showMessageDialog(frame, "rules go here");
	}
	
	public JPanel displayCards(Player current){
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
		JLabel card;
		//JLabel title = new JLabel("Cards:");
		//cardsPanel.add(title);
	
		// loops through every card in players hand 
		for(Card c : current.getHand()){
			// creates a new JLabel for each card
			card = new JLabel(); 	
			
		    ImageIcon image = null;
	        try {
	        	image = new ImageIcon(ImageIO.read(new File("AllCardPics/"+c.getName()+".png")));
	        } catch(IOException ie) {
	            ie.printStackTrace();
	        } 

	        card.setIcon(image);
	        cardsPanel.add(card);
		
		}
        return cardsPanel;
	}
	
	public JPanel displayNotePad(Player current){
		JPanel notePadPanel = new JPanel();
		notePadPanel.setLayout(new BoxLayout(notePadPanel, BoxLayout.X_AXIS));
		
	
		// loops through every card in players hand 
		for(Card c : current.getNotepad()){
			// creates a new JLabel for each card
			JLabel card = new JLabel(); 	
			
		    ImageIcon image = null;
	        try {
	        	image = new ImageIcon(ImageIO.read(new File("AllCardPics/"+c.getName()+".png")));
	        } catch(IOException ie) {
	            ie.printStackTrace();
	        } 

	        card.setIcon(image);
	        notePadPanel.add(card);
		}
     
        return notePadPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		switch(e.getActionCommand()){
			case "newGame": 
				new StartGame(this.frame);
				break;
			case "rules":
				rules();
				break;
			case "quit":
				System.exit(0);
				
			case "endTurn":
				
			case "cards":
				System.out.println(frame.getGUI().currentPlayer.getToken().toString());
				JOptionPane.showConfirmDialog(frame,
                        displayCards(frame.getGUI().currentPlayer),
                        "Cards: ", 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
				
				break;
			case "notePad":
				JOptionPane.showConfirmDialog(frame,
                        displayNotePad(frame.getGUI().currentPlayer),
                        "NotePad: ", 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
				break;
			default:
				break;					
		}
		
	}
}
