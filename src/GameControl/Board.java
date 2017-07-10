package GameControl;

import java.awt.Graphics;

import GUI.BoardCanvas;

/**
 * The GameControl.Board is a 25x25 playing field which has 9 rooms in certain
 * locations. Players and Rooms are dispersed upon the board in certain
 * positions and are able to freely move along the board.
 * <p>
 * The board will be drawn to a canvas so the players can physically
 * see their steps they take.
 * <p>
 * Created by Jack on 19/07/2016.
 */
public interface Board {

    /**
     * Prints the board to the std.out/std.err
     */
    char printArray();
    
    /**
     * Draws each object to the canvas with the passed in graphics object
     */
    void draw(Graphics g, BoardCanvas observ);

}
