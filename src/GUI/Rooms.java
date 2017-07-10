package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GameControl.Player;

public class Rooms extends JPanel implements ActionListener {
	
	GameFrame frame;
	JPanel roomsPane;	
	private List<JButton> roomButtons = new ArrayList<>();
	
	public Rooms(GameFrame frame){
		this.frame = frame;
	}
	
	public JPanel displayRooms(){
		roomsPane = new JPanel();
		
		roomsPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton ballroom = new JButton(new ImageIcon("Cards/Ball Room.png"));
		ballroom.setActionCommand("ballroom");
		JButton billards = new JButton(new ImageIcon("Cards/Billards.png"));
		billards.setActionCommand("billards");
		JButton conservatory = new JButton(new ImageIcon("Cards/Conservatory.png"));
		conservatory.setActionCommand("conservatory");
		JButton diningroom = new JButton(new ImageIcon("Cards/Dining Room.png"));
		diningroom.setActionCommand("diningroom");
		JButton hall = new JButton(new ImageIcon("Cards/Hall.png"));
		hall.setActionCommand("hall");
		JButton kitchen = new JButton(new ImageIcon("Cards/Kitchen.png"));
		kitchen.setActionCommand("kitchen");
		JButton library = new JButton(new ImageIcon("Cards/Library.png"));
		library.setActionCommand("library");
		JButton lounge = new JButton(new ImageIcon("Cards/Lounge.png"));
		lounge.setActionCommand("lounge");
		JButton study = new JButton(new ImageIcon("Cards/Study.png"));
		study.setActionCommand("study");
		
		// Create a message
		JLabel message = new JLabel("	Rooms: ");
		message.setBorder(new EmptyBorder(7, 15, 7, 7));
		roomsPane.add(message, gbc);
		
		
		
		roomButtons.add(ballroom);
		roomButtons.add(billards);
		roomButtons.add(conservatory);
		roomButtons.add(diningroom);
		roomButtons.add(hall);
		roomButtons.add(kitchen);
		roomButtons.add(library);
		roomButtons.add(lounge);
		roomButtons.add(study);
		
		
		for (JButton r: roomButtons){
			roomsPane.add(r, gbc);
			r.setBorder(new EmptyBorder(5, 15, 5, 5));
			r.setEnabled(false);
			r.addActionListener(this);
		}
        
		return roomsPane;
		
	}
	
	/**
	 * Clears all our previous listeners and replaces them with this
	 */
	public void resetListeners(){
		for (JButton b: roomButtons)
			for (ActionListener act: b.getActionListeners())
				b.removeActionListener(act);		
		
		for (JButton b: roomButtons)
			b.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){
		case "ballroom": 
		case "billards":				
		case "conservatory":			
		case "diningroom":				
		case "hall":				
		case "kitchen":
		case "library":
		case "lounge":
		case "study":
			
		default:
		break;
		
		}
	
	}
}
