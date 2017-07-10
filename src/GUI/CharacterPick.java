package GUI;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import Card.Character;
import GameControl.GUI;
import GameControl.Game;
import GameControl.Player;

public class CharacterPick extends JDialog implements ActionListener {
	
	private JPanel buttonPane;
	private JPanel messagePane;
	private JPanel answerPane;
	private int playerNo;
	private GameFrame frame;
	private GUI gui;
	
	private JLabel characterPicture;
	
	// first players default token
	private Player.Token chosenToken = Player.Token.MissScarlett;
	
	// list of all our radio buttons
	private List<AbstractButton> radioButtons = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;
	
	public CharacterPick(JFrame parent, int playerNo){
		 this.frame = (GameFrame)parent;
		 this.playerNo = playerNo;
		 this.gui = this.frame.getGUI();
		 
		 // set the position of the window
		 Point p = new Point(400, 400);
		 setLocation(p.x, p.y);
		 
		 // Create a message
		 messagePane = new JPanel();
		 messagePane.add(new JLabel("Player #"+playerNo+" - Select Token"));
		 
		 // get content pane, which is usually the
		 // Container of all the dialog's components.
		 getContentPane().add(messagePane, BorderLayout.PAGE_START);		 
		
		 // Creates RadioButton Panel
		 buttonPane = new JPanel();
		 displayRadioButtons();
		 getContentPane().add(buttonPane);
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 pack();
		 setVisible(true);
		 
		 // Creates close button
		 JPanel closeButton = new JPanel();
		 JButton button = new JButton("Quit");
		 JButton ok = new JButton("Ok");		 
		 closeButton.add(button);
		 closeButton.add(ok);
		 
		 ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkTokenIsFree(chosenToken)){
					// add our players to the players list in the GUI, as well as create a new player.
					gui.addPlayers(gui.createPlayer(chosenToken, playerNo));
				}else{
					showErrorOnCharacterSelection();
					return;
				}
				
				JOptionPane.showMessageDialog(frame, "You are "+chosenToken.toString());
				dispose();
				
				if (frame.getGUI().NUMBER_OF_PLAYERS != playerNo){
					JFrame parent = (JFrame)frame;
					new CharacterPick(parent, playerNo+1);
				}			
				// Now the game actually begins
				// after our above conditions are met, we can start playing the game.
				// so now we can call our BoardCanvas
				else {
					frame.getBoard().setVisible(true);
					// we also deal cards to all our players
					frame.getGUI().dealCards();
					frame.getGUI().nextPlayersTurn();
				}
			}			 
		 });
		 // set action listener on the button
		 button.addActionListener(new MyActionListener());
		 getContentPane().add(closeButton, BorderLayout.PAGE_END);
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 pack();
		 setVisible(true);
		 
		 
	}
	
	public void displayRadioButtons(){
		
		buttonPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JRadioButton scarlett = new JRadioButton("Miss Scarlett");
		scarlett.setActionCommand(Player.Token.MissScarlett.toString());
		scarlett.setSelected(true);
		JRadioButton mustard = new JRadioButton("Colonel Mustard");
		mustard.setActionCommand(Player.Token.ColonelMustard.toString());
		JRadioButton green = new JRadioButton("Mr Green");
		green.setActionCommand(Player.Token.MrGreen.toString());
		JRadioButton peacock = new JRadioButton("Mrs Peacock");
		peacock.setActionCommand(Player.Token.MrsPeacock.toString());
		JRadioButton white = new JRadioButton("Mrs White");
		white.setActionCommand(Player.Token.MrsWhite.toString());
		JRadioButton plum = new JRadioButton("Professor Plum");
		plum.setActionCommand(Player.Token.ProfessorPlum.toString());		
		
		radioButtons.add(scarlett);
		radioButtons.add(mustard);
		radioButtons.add(green);
		radioButtons.add(peacock);
		radioButtons.add(white);
		radioButtons.add(plum);
		
		
		// group buttons to handle them like radio
		ButtonGroup group = new ButtonGroup();
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		
		characterPicture = new JLabel(new ImageIcon("AllCardPics/MissScarlett.png"));
		characterPicture.setPreferredSize(new Dimension(160,250));	
		
		// enable and disable selected buttons, set up listeners etc
        for (AbstractButton r: radioButtons){
        	group.add(r);
        	radioPanel.add(r);
        	r.addActionListener(this);
        	
        	// disable selected buttons
        	 for (Player.Token token: Player.Token.values()){
             	if (!this.checkTokenIsFree(token))
             		if (r.getActionCommand().equals(token.toString())){
             			r.setEnabled(false);
             			break;
             		}             	
             }
        }
        
        radioPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));        
	    getContentPane().add(radioPanel, BorderLayout.WEST);
	    getContentPane().add(characterPicture, BorderLayout.EAST);
	}

	
	 public JRootPane createRootPane() {
		 JRootPane rootPane = new JRootPane();
		 KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
         Action action = new AbstractAction() {
        	private static final long serialVersionUID = 1L;
		    @Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("escaping..");
	            setVisible(false);
	             dispose();
			}
		 };
		    InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		    inputMap.put(stroke, "ESCAPE");
		    rootPane.getActionMap().put("ESCAPE", action);
		    return rootPane;
		     }
	 
	 class MyActionListener implements ActionListener {
		 //close and dispose of the window.
		public void actionPerformed(ActionEvent e) {
		    setVisible(false);
		    dispose();
		}
		 
	 }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (Player.Token token: Player.Token.values()){
			if (token.toString().equals(e.getActionCommand())){
				this.characterPicture.setIcon(new ImageIcon("AllCardPics/"+token.toString()+".png"));
				this.chosenToken = token;
			}
		}
		
//		switch(e.getActionCommand()){
//			case "scarlett": 
//
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/scarlett.png"));
//				this.chosenToken = Player.Token.MissScarlett;
//
//				break;
//			case "mustard":
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/mustard.png"));
//				this.chosenToken = Player.Token.ColonelMustard;
//
//				break;
//			case "green":
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/green.png"));
//				this.chosenToken = Player.Token.MrGreen;
//
//				break;
//			case "peacock":
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/peacock.png"));
//				this.chosenToken = Player.Token.MrsPeacock;
//
//				break;
//			case "white":
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/white.png"));
//				this.chosenToken = Player.Token.MrsWhite;
//
//				break;
//			case "plum":
//				this.characterPicture.setIcon(new ImageIcon("PlayerImages/plum.png"));
//				this.chosenToken = Player.Token.ProfessorPlum;
//
//				break;
//			default:
//				// there can be no default in this case
//		}

			
	}
	
	public void showErrorOnCharacterSelection(){
		JOptionPane.showMessageDialog(this, "This character has already been selected");
	}
	
	/**
	 * This method checks if a player can select the currently chosen token, and that
	 * no previous player has selected that token
	 */
	public boolean checkTokenIsFree(Player.Token token){
		for (Player p: this.frame.getGUI().getPlayers()){
			if (p.getToken() == token)
				return false;
		}
		return true;
	}
	
	
	public void sleep(){
		try{
			wait(1000);
		}catch (Exception e){
			
		}
	}

	 public static void main(String[] a) {
		 //CharacterPick dialog = new CharacterPick(new JFrame(), 2);
		 // set the size of the window
		// dialog.setSize(400,500);
	 }


}
