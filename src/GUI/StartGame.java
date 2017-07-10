package GUI;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import GUI.CharacterPick.MyActionListener;
import GameControl.GUI;

/**
 * This object is called when a user wishes to play the game. It is instantiated by pressing the
 * New Button on the GUI. Once initiated it then collects all the information from each player wishing
 * to play the game.
 * 
 * @author Jack && T-Dawg
 *
 */
@SuppressWarnings("serial")
public class StartGame extends JDialog {
	
	JPanel textPanel;
	int numberOfPlayers;
	
	private GameFrame frame;
	
	public StartGame(JFrame parent){
		super(parent);
		this.frame = (GameFrame)parent;
		 // set the position of the window
		 Point p = new Point(400,400);
		 setLocation(p.x, p.y);
		 
		// Create a message
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel("Welcome to Cluedo"));
		// get content pane, which is usually the
		// Container of all the dialog's components.
		getContentPane().add(messagePane, BorderLayout.NORTH);
		
		// Creates RadioButton Panel
		textPanel = new JPanel();
		playerNumber();
		getContentPane().add(textPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
				 
		// Creates close button
		JPanel closeButton = new JPanel();
		JButton button = new JButton("Quit");
		closeButton.add(button);
		// set action listener on the button
		button.addActionListener(new MyActionListener());
		getContentPane().add(closeButton, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300,200);	// we don't want to use pack here
		setVisible(true);		
	}
	
	public void playerNumber(){
	
		textPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel promptLabel= new JLabel("How many players? (3-6)");
		final JTextField userText = new JTextField();
		
		textPanel.add(promptLabel, gbc);
		textPanel.add(new JLabel("     "), gbc);
		textPanel.add(userText, gbc);
		JButton okButton = new JButton("Ok");
		textPanel.add(okButton, gbc);	// made sure we could see the OK button
		
		// the user clicks the ok buton
	      okButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	readNumberInput(userText);
	        	dispose();
	         }
	      }); 		
	     // the user presses the enter button
	      userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readNumberInput(userText);
				dispose();
			}	    	  
	      });
	      
	}
	
	public void readNumberInput(JTextField input){
		String text = input.getText();	        	
    	try{
 	    	numberOfPlayers = Integer.parseInt(text);
 	    	if (numberOfPlayers < 3 || numberOfPlayers > 6)
 	    		throw new IllegalArgumentException("Incorrect number of players entered!"); 	    	
 	    	
 	    	// update our number of players to enter
 	    	this.frame.getGUI().NUMBER_OF_PLAYERS = numberOfPlayers;
 	    	
 	    	// now create our first player
 	    	new CharacterPick((JFrame)frame, 1);
    	}catch (Exception exception){
    		// try again
    		JOptionPane.showMessageDialog(frame, exception.getMessage());
    	}
	}
	
	public Integer getNumberOfPlayers(){
		return numberOfPlayers;
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
	
	public static void main(String[] a) {
		 StartGame dialog = new StartGame(new JFrame());
		 // set the size of the window
		 dialog.setSize(300, 300);
	 }
}
