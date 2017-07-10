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

import GameControl.Game;

@SuppressWarnings("serial")
public class Weapons extends JPanel implements ActionListener {

	GameFrame frame;
	JPanel weaponPane;
	
	private List<JButton> weaponButtons = new ArrayList<JButton>();
	
	public Weapons(GameFrame frame){
		this.frame = frame;
	}
	
	public JPanel displayWeapons(){
		weaponPane = new JPanel();
		
		weaponPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton candleStick = new JButton(new ImageIcon("Cards/Candlestick.png"));
		candleStick.setActionCommand("candle");
		JButton dagger = new JButton(new ImageIcon("Cards/Dagger.png"));
		dagger.setActionCommand("dagger");
		JButton leadPipe = new JButton(new ImageIcon("Cards/Lead Pipe.png"));
		leadPipe.setActionCommand("lead");
		JButton revolver = new JButton(new ImageIcon("Cards/Revolver.png"));
		revolver.setActionCommand("revolver");
		JButton rope = new JButton(new ImageIcon("Cards/Rope.png"));
		rope.setActionCommand("rope");
		JButton spanner = new JButton(new ImageIcon("Cards/Spanner.png"));
		spanner.setActionCommand("spanner");
		
		// Create a message
		JLabel message = new JLabel("Weapons: ");
		message.setBorder(new EmptyBorder(7, 7, 7, 16));
		weaponPane.add(message, gbc);
		
        
        weaponButtons.add(candleStick);
        weaponButtons.add(dagger);
        weaponButtons.add(leadPipe);
        weaponButtons.add(revolver);
        weaponButtons.add(rope);
        weaponButtons.add(spanner);
        
        for (JButton w: weaponButtons){
        	weaponPane.add(w, gbc);
        	w.setBorder(new EmptyBorder(5, 5, 5, 16));
        	w.setEnabled(false);
        	w.addActionListener(this);
        }
        
        
		return weaponPane;
	}
	
	/**
	 * Clears all our previous listeners and replaces them with this
	 */
	public void resetListeners(){
		for (JButton b: weaponButtons)
			for (ActionListener act: b.getActionListeners())
				b.removeActionListener(act);		
		
		for (JButton b: weaponButtons)
			b.addActionListener(this);
	}
	
	/**
	 * Returns all our buttons, used for setting inactive and active
	 * @return
	 */
	public List<JButton> getWeaponButtons(){
		return this.weaponButtons;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){
			case "candle": 
			case "dagger":				
			case "lead":			
			case "revolver":				
			case "rope":				
			case "spanner":
				this.frame.getGUI().selectWeapon(e.getActionCommand());
			default:
			break;
					
		}
	}
}
