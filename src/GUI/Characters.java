package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Card.Card;
import GameControl.Game;
import GameControl.Player;


public class Characters extends JPanel implements ActionListener {
	
	GameFrame frame;
	JPanel characterPane;
	
	private List<JButton> characterButtons = new ArrayList<>();

	public Characters(GameFrame frame){
		this.frame = frame;
	}
	
	public JPanel displayCharacters(){
		characterPane = new JPanel();
		
		characterPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton scarlett = new JButton(new ImageIcon("Cards/Miss Scarlett.png"));
		scarlett.setActionCommand(Player.Token.MissScarlett.toString());
		JButton mustard = new JButton(new ImageIcon("Cards/Colonel Mustard.png"));
		mustard.setActionCommand(Player.Token.ColonelMustard.toString());
		JButton green = new JButton(new ImageIcon("Cards/The Reverend Green.png"));
		green.setActionCommand(Player.Token.MrGreen.toString());
		JButton peacock = new JButton(new ImageIcon("Cards/Mrs_Peacock.png"));
		peacock.setActionCommand(Player.Token.MrsPeacock.toString());
		JButton white = new JButton(new ImageIcon("Cards/Mrs_White.png"));
		white.setActionCommand(Player.Token.MrsWhite.toString());
		JButton plum = new JButton(new ImageIcon("Cards/Professor Plum.png"));
		plum.setActionCommand(Player.Token.ProfessorPlum.toString());
		
		// Create a message
		JLabel message = new JLabel("	Characters: ");
		message.setBorder(new EmptyBorder(7, 7, 7, 16));
		characterPane.add(message, gbc);
		
		characterButtons.add(scarlett);
		characterButtons.add(mustard);
		characterButtons.add(green);
		characterButtons.add(peacock);
		characterButtons.add(white);
		characterButtons.add(plum);
		
		for (JButton c: characterButtons){
			characterPane.add(c, gbc);
			c.setBorder(new EmptyBorder(5, 5, 5, 16));
			c.setEnabled(false);
		}
		resetListeners();
        
		return characterPane;
		
	}
	
	/**
	 * Clears all our previous listeners and replaces them with this
	 */
	public void resetListeners(){
		for (JButton b: characterButtons){
			// remove all our previous listeners
			for (ActionListener act: b.getActionListeners()){
				b.removeActionListener(act);
			}
		}
		
		for (JButton b: characterButtons){
			b.addActionListener(this);
		}
	}
	
	public List<JButton> getCharacterButtons(){
		return this.characterButtons;
	}
	
	private Player.Token getToken(String name){
		for (Player.Token t: Player.Token.values()){
			if (t.toString().equals(name))
				return t;
		}
		throw new IllegalArgumentException("Cannot locate token");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.getGUI().selectCharacter(e.getActionCommand(), getToken(e.getActionCommand()));
	}
}
