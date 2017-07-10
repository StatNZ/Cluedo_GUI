package GameControl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Card.Card;
import Card.Room;
import File_Readers.Parser;
import GUI.BoardCanvas;
import GameControl.Position;

/**
 * This class is where we control all the functionality that has to do with
 * the game "Cluedo" itself. Cluedo and all it's rules and player options will
 * be controlled through this class.
 * <p>
 * The Game class will contain collections of all the deck and players in the game
 * <p>
 * Created by Jack on 19/07/2016.
 */
public class Game extends GUI {

	 // Card class containing our collections of cards
    private Card card = new Card();
    
    // construct our board from a given text file, card must be initiated first
    private Board[][] board = Parser.parseFile(this);
    
    private Player suggestingPlayer;
    
   
    private List<Card> selectedSuggestCards = new ArrayList<>();

    /**
     * Here we set up our player with their initial position on where they will be
     * starting on the board, each player is assigned a character in which they will
     * play throughout the game. This does not necessarily mean that they will have
     * that character card in their deck.
     *
     * @param name the name the player will take on for the duration of the game
     * @param token the Character assigned token
     */
    @Override
    public Player createPlayer(Player.Token token, int playerNum) {
        switch (token) {
            case MissScarlett:
                return new Player(token, Parser.playersStartPositions.get(0), playerNum);
            case ProfessorPlum:
                return new Player(token, Parser.playersStartPositions.get(1), playerNum);
            case MrsWhite:
                return new Player(token, Parser.playersStartPositions.get(2), playerNum);
            case MrsPeacock:
                return new Player(token, Parser.playersStartPositions.get(3), playerNum);
            case MrGreen:
                return new Player(token, Parser.playersStartPositions.get(4), playerNum);
            case ColonelMustard:
                return new Player(token, Parser.playersStartPositions.get(5), playerNum);
            default:
                throw new IllegalArgumentException("Player selection was abnormally exited");
        }
    }

    /**
     * Checks if a door is adjacent to our Position then return
     * a door
     *
     * @param position the position to check whehter there is a door there or not
     * @return door object - if there is one near the specified position
     */
    public Door isDoor(Position position) {
        // check north
        if (board[position.x][position.y - 1] instanceof Door)
            return (Door) board[position.x][position.y - 1];
        // south
        if (board[position.x][position.y + 1] instanceof Door)
            return (Door) board[position.x][position.y + 1];
        // east
        if (board[position.x + 1][position.y] instanceof Door)
            return (Door) board[position.x + 1][position.y];
        // west
        if (board[position.x - 1][position.y] instanceof Door)
            return (Door) board[position.x - 1][position.y];
        return null;
    }

    /**
     * Returns the card object class
     *
     * @return Card class
     */
    public Card getCard() {
        return this.card;
    }

    /**
     * Return a character card that contains the string name
     */
    public Card getCharacter(String charName) {
        return card.getCharacter(charName);
    }

    /**
     * Return a weapon card that contains the string name
     *
     * @return Weapon card
     */
    public Card getWeapon(String weaponName) {
        return card.getWeapon(weaponName);
    }

    /**
     * Get the room by string name;
     *
     * @return Room
     */
    public Room getRoom(String roomName) {
        return card.getRoom(roomName);
    }

    /**
     * Checks if current position is a Room
     *
     * @return
     */
    private boolean isRoom(Position p) {
//    	System.out.println("x: "+p.x);
//		System.out.println("y: "+p.y);
        return !(board[p.x][p.y] instanceof Room);
    }

    /**
     * Here we attempt to move the player n amount of steps (according to the dice roll),
     * In the future we will implement this move function with more logic. We will attempt
     * to move the player in a A* fashion towards a room/door of the players choosing.
     *
     * @param player - the current player who's turn it is
     * @param nmoves - the number of steps we can take (either determined by dice roll, or if player selects < dice roll)
     * @param room   - this is the destination we want to get to
     * @return true if player has entered a room
     */
    private void movePlayer(Player player, int nmoves, Position sink) {
    	
        // choose the best door to leave from
        if (player.getRoom() != null) {
            Door startingDoor = player.getRoom().selectBestDoorToLeaveFrom(sink);
            player.move(startingDoor.getPos());
        }
        
        // this lets a player select any tile within a room, and automatically,
        // finds its location and gets them to that room
        if (board[sink.x][sink.y] instanceof Room){
        	Room r = (Room)board[sink.x][sink.y];
        	sink = r.getDoor(player.getPosition()).getPos();
        }
        
        // setup A* instructions
//        Door closestDoor = room.getDoor(player.getPosition());
        Node start = new Node(null, player.getPosition());
        Node end = new Node(null, sink);
        start.setGoal(end);
        end.setGoal(end);

        // compute A* algorithm
        Node path = Astar(start, end);
        
        // check we have a valid path
        if (path == null)
        	return; // do nothing and make the user clic
        List<Position> pathToFollow = new ArrayList<>();
        addPath(pathToFollow, path);
        Collections.reverse(pathToFollow);

        // remove player from the current room as they have chosen to move elsewhere
        player.leaveRoom();
        
        // we must use a swing timer in order to delay the actions of our players moves
        int delay = 400;        
    	final Timer timer = new Timer(delay, new ActionListener(){    		
    		int movesLeft = nmoves;

			@Override
			public void actionPerformed(ActionEvent e) {
				//Position newPosition = pathToFollow.get(0);
				if (movesLeft == 0){

					((Timer)e.getSource()).stop();
					// we officially end our tur

				}
				
				if (!pathToFollow.isEmpty()){
					player.move(pathToFollow.get(0));
					pathToFollow.remove(0);
					movesLeft--;
				}
				
				getFrame().getBoard().repaint();
				
				// we must take into account a player entering a room
				Board playerPos = board[player.getPosition().x][player.getPosition().y];
		    	if (playerPos instanceof Door){
		    		Door door = (Door)playerPos;
		    		player.enterRoom(door.getRoom());
		    		
		    		// we must now point this spot to the right method for suggesting
		    		// a killer
		    		//JOptionPane.showMessageDialog(getFrame(), "player entered a room "+player.getRoom().getName());
		    		((Timer)e.getSource()).stop();
		    		
		    		// suggest a killer
		    		startSuggest(player);
		    		return;
		    	}
		    	
		    	// check to see if we have actually finished moving
				if (nmoves > 0 && pathToFollow.isEmpty()){
					((Timer)e.getSource()).stop();
					nextPlayersTurn();
					return;
				}
		
			}
    	});
		timer.start();

		if (!timer.isRunning()){
			nextPlayersTurn();

		}
    }

    /**
     * Helper method for adding our shortest path to the list in
     * which we will use to move our player
     */
    private void addPath(List<Position> positions, Node path) {
        while (path.parent != null) {
            positions.add(new Position(path.getPos().x, path.getPos().y));
            path = path.parent;
        }
    }

    /**
     * Algorithm for Astar search which will return a node containing
     * the shortest path to our destination. Should never return null.
     *
     * @param start Source node
     * @param end Sink node
     * @return Astar node containing our selected path
     */
    private Node Astar(Node start, Node end) {
        Queue<Node> order = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();

        order.add(start); // add our start node to the queue

        while (!order.isEmpty()) {
            // do Astar LOGIC
            Node n = order.poll();
            visited.add(n);
            if (n.equals(end)) return n; // found our path
            else {
                for (Position p : n.getPos().getNeighbours()) {
                    Node neighbour = new Node(n, p);
                    neighbour.setGoal(end);
                    if (!visited.contains(neighbour) && isRoom(p))
                        order.add(neighbour);
                }
            }
        }
        throw new IllegalArgumentException("Path finder has failed");
    }

    public String toString() {
        return board.toString();
    }
    
    @Override
    public void nextPlayersTurn(){
    	// clear our suggested cards
    	this.selectedSuggestCards.clear();
		this.suggestingPlayer = null;
    	
    	nextPlayer();
    	newDiceRoll();
    	// restart our mouse listener for the gui
    	getFrame().getBoard().startMouseListener();
    	JOptionPane.showMessageDialog(getFrame(),
    			"Player #"+currentPlayer.playerNumber+" It is your turn!!!\n"
    			+ "You rolled: "+getDiceRoll);
    }
	
    /**
     * Each player is dealt a set of cards. Each card dealt to a player
     * will come from a shuffled deck
     */
	@Override
	public void dealCards() {
		card.dealCards(getPlayers());
	}

	@Override
	protected void onMove(Move move, Player player) {
		int dice = getDiceRoll;
		// here we need to move the player in the correct direction. to do this
		// we need to change it's position. however we need to ensure they do not go
		// out of bounds
		
		getFrame().repaint();
		
	}

	@Override
	public void onClick(MouseEvent e, int widthOffset, int heightOffset, int offset) {
		getFrame().getBoard().stopMouseListener();
		Player player = getCurrentPlayer();
		int x = (e.getPoint().x-offset)/(widthOffset/25);
		int y = (e.getPoint().y-offset)/(heightOffset/24);
		Position endPosition = new Position(x,y);		
		movePlayer(player, getDiceRoll, endPosition);
	}

	@Override
	public void selectCharacter(String cardName, Player.Token selectedToken) {
		Card c = card.getCharacter(cardName);
		this.selectedSuggestCards.add(c);
		
		// disable our buttons so we cannot suggest the same card twice
		enableCharacterButtons(false);
		
		// now do the logic of moving this character into the specific room of the player
		
		// move the player who is the same token as c
		for (Player p: getPlayers()){
			if (p.getToken() == selectedToken){
				p.move(this.suggestingPlayer.getRoom().getDoors().iterator().next().getPos());
				p.enterRoom(this.suggestingPlayer.getRoom());
				p.setWasMoved();
				break;
			}
		}
		getFrame().repaint();
		
		// if we have chosen two cards we can now fulfill the rest our our suggest
		if (this.selectedSuggestCards.size() == 3)
			playerRevealsCard();		
	}

	@Override
	public void selectWeapon(String cardName) {
		Card c = card.getWeapon(cardName);
		this.selectedSuggestCards.add(c);
		
		// disable our buttons so we cannot suggest the same card twice
		enableWeaponButtons(false);		
		
		// take into account that we have to move a weapon into the room as well
		
		// check if we have the right amount of cards
		if (this.selectedSuggestCards.size() == 3)
			playerRevealsCard();
		
	}
	
    /**
     * This is the logic behind suggesting a killer
     * @param player
     */
	private void startSuggest(Player player){
    	this.suggestingPlayer = player;
    	
    	// add our room that we are currently in to our suggesting cards
    	this.selectedSuggestCards.add(player.getRoom());
    	
    	enableCharacterButtons(true);    	
    	enableWeaponButtons(true);  
    	
    	
    	// show a message to the player to make a their selections.
    	JOptionPane.showMessageDialog(getFrame(), "Please select a Killer and a Weapon");    	
    }	
	
	/**
	 * In this method, we find the first player that has one of the suggest cards in their hands.
	 * We then ask that player to choose a card to reveal to the current player. Once they have
	 * revealed a card to the currentPlayer, we will update the current players inventory
	 */
	private void playerRevealsCard(){
		// find the player that has a card
		
		// checks players on our left and other left
		int start = getPlayers().indexOf(currentPlayer);
		Player p = findPlayerToReveal(start+1, getPlayers().size());
		if (p != null){
			enableSelectedButtons(p);
		}else if ((p = findPlayerToReveal(0, start)) != null){
			enableSelectedButtons(p);
		}else{
			// we don't have a player, therefore the card(s) are in our hand or in the solution
			// and we should simply exit this method informing the currentPlayer no cards to be revealed
			JOptionPane.showMessageDialog(getFrame(), "No cards were revealed");
			nextPlayersTurn();
		}
	}
	
	private Player findPlayerToReveal(int start, int end){
		
		for (int i=start; i<end; i++){
			Player leftPlayer = getPlayers().get(i);
			
			if (leftPlayer.checkCards(this.selectedSuggestCards) && leftPlayer != currentPlayer){
				JOptionPane.showMessageDialog(getFrame(), "Player #"+leftPlayer.playerNumber
						+" must select a card to reveal.\nPlease change positions now!");
				return leftPlayer;				
			}
		}
		return null;
	}
	
	/**
	 * enables only the selected buttons of the cards that are contained within our hand
	 */
	private void enableSelectedButtons(Player suggestedPlayer){
		
		List<JButton> allButtons = new ArrayList<JButton>(getFrame().getCharacters().getCharacterButtons());
		allButtons.addAll(getFrame().getWeapons().getWeaponButtons());		
		
		for (JButton b: allButtons){
			// remove all previous action listeners
			for (ActionListener act: b.getActionListeners())
				b.removeActionListener(act);
			
			for (Card c: suggestedPlayer.getHand()){
				
				// we will only enable the buttons that the player and the suggest cards
				// have in common
				if (b.getActionCommand().toLowerCase().contains(c.getName().toLowerCase())
						&& this.selectedSuggestCards.contains(c)){
					
					// set the cards in our hands to enabled
					b.setEnabled(true);
					//System.out.println(c.getName());
					// now we will set these buttons listeners to our own customized listener
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							currentPlayer.addCardToInventory(card.getCard(e.getActionCommand()));
							
							// now reset our button listeners back to their parents
							getFrame().getCharacters().resetListeners();
							getFrame().getWeapons().resetListeners();
							
							// disable all our buttons once again
							enableCharacterButtons(false);
							enableWeaponButtons(false);
							
							// and end our turn
							nextPlayersTurn();
							return;
						}						
					});
				}
			}
		}
	}
    
    /**
     * Helper method for enabling disabling character buttons
     * @param enable - true to enable otherwise false to disable
     */
    private void enableCharacterButtons(boolean enable){
    	// enable the buttons for selecting characters
    	for (JButton b: getFrame().getCharacters().getCharacterButtons()){
    		b.setEnabled(enable);
    	}
    }
    
    /**
     * Helper method for enabling disabling weapon buttons
     * @param enable - true to enable otherwise false to disable
     */
    private void enableWeaponButtons(boolean enable){
    	// enable buttons for selecting weapons
    	for (JButton b: getFrame().getWeapons().getWeaponButtons()){
    		b.setEnabled(enable);
    	}
    }

	
	 public static void main(String[] args){
	    	new Game();
	 }

	

}
