package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Card.Card;
import GameControl.Player;

public class Cards {

	private JPanel cardsPanel;
	
	public Cards(Player p){
		displayCards(p);
		
		ImageIcon iconBallRoom = new ImageIcon("Card/Ball Room.png");
		ImageIcon iconBillards = new ImageIcon("Cards/Billards.png");
		ImageIcon iconCandleStick = new ImageIcon("Cards/CandleStick.png");
		ImageIcon iconColonelMustard = new ImageIcon("Cards/ColonelMustard.png");
		ImageIcon iconConservatory = new ImageIcon("Cards/Conservatory.png");
		ImageIcon iconDagger = new ImageIcon("Cards/Dagger.png");
		ImageIcon iconDiningRoom = new ImageIcon("Cards/Dining Room.png");
		ImageIcon iconHall = new ImageIcon("Cards/Hall.png");
		ImageIcon iconKitchen = new ImageIcon("Cards/Kitchen.png");
		ImageIcon iconLeadPipe = new ImageIcon("Cards/Lead Pipe.png");
		ImageIcon iconLibrary = new ImageIcon("Cards/Library.png");
		ImageIcon iconLounge = new ImageIcon("Cards/Lounge.png");
		ImageIcon iconMissScarlett = new ImageIcon("Cards/Miss Scarlett.png");
		ImageIcon iconMrsPeacock = new ImageIcon("Cards/Mrs_Peacock.png");
		ImageIcon iconMrsWhite = new ImageIcon("Cards/Mrs_White.png");
		ImageIcon iconProfessorPlum = new ImageIcon("Cards/Professor Plum.png");
		ImageIcon iconRevolver = new ImageIcon("Cards/Revolver.png");
		ImageIcon iconRope = new ImageIcon("Cards/Rope.png");
		ImageIcon iconSpanner = new ImageIcon("Cards/Spanner.png");
		ImageIcon iconStudy = new ImageIcon("Cards/Study.png");
		ImageIcon iconTheReverendGreen = new ImageIcon("Cards/The Reverend Green.png");
	}
	
	public JPanel displayCards(Player current){
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
		
		//JLabel title = new JLabel("Cards:");
		//cardsPanel.add(title);
	
		// loops through every card in players hand 
		for(Card c : current.getHand()){
			JLabel card = new JLabel(c.getName()); 	// creates a new JLabel for each card
			
		    ImageIcon image = null;
	        try {
	        	image = new ImageIcon(ImageIO.read(new File("AllCardPics/"+current.getName()+".png")));
	        } catch(IOException ie) {
	            ie.printStackTrace();
	        } 

	        card.setIcon(image);
	        cardsPanel.add(card);
		}
     
     

        return cardsPanel;
	}
		
	
		
		
	
}
