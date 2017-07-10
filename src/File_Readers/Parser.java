package File_Readers;

import GameControl.Board;
import GameControl.Game;
import GameControl.Position;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Card.Room;

/**
 * This class sole aim is to read our board from a file and assigns appropriate Rooms to the their corresponding
 * positions.
 *
 * Created by Jack on 1/08/2016.
 */
public class Parser {
    public static List<Position> playersStartPositions = new ArrayList<>();

    public static Board[][] parseFile(Game game) {

        List<String> readString = new ArrayList<>();

        try {
            Scanner scan = new Scanner(new File("cluedoBoardDraw.txt"));
            while (scan.hasNext()) {
                readString.add(scan.next());
            }
            scan.close();
        } catch (IOException e) {
            System.out.println("File failure " + e);
        }

        // create an appropriate size for our board
        int x = readString.size();
        int y = readString.get(0).length();
        Board[][] board = new Board[y][x];

        // create objects and add them to our board
        for (int row = 0; row < x; row++) {
            String input = readString.get(row);
            placeString(game, input, board, row);
        }

        return board;
    }

    private static void placeString(Game card, String input, Board[][] board, int row) {
    	

        int index = 0;
        int count = 0;
        while (count < input.length()) {
            char c = input.charAt(index++);
            
            switch (c) {
	            case '1':
	            	card.getRoom("kitchen").addStandingPosition(new Position(count, row));
                case 'k': //create room kitchen                   
                    addToBoard(card.getRoom("Kitchen"), board, count++, row);
                    break;
                case 'K': //create door kitchen
                    addToBoard(card.getRoom("Kitchen").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '2':
	            	card.getRoom("Ball Room").addStandingPosition(new Position(count, row));
                case 'b': // create room
                    addToBoard(card.getRoom("Ball Room"), board, count++, row);
                    break;
                case 'B': // create door
                    addToBoard(card.getRoom("Ball Room").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '3':
	            	card.getRoom("Conservatory").addStandingPosition(new Position(count, row));
                case 'c': // create room
                    addToBoard(card.getRoom("Conservatory"), board, count++, row);
                    break;
                case 'C': // create door
                    addToBoard(card.getRoom("Conservatory").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '4':
	            	card.getRoom("Dining Room").addStandingPosition(new Position(count, row));
                case 'd':
                    addToBoard(card.getRoom("Dining Room"), board, count++, row);
                    break;                
                case 'D':
                    addToBoard(card.getRoom("Dining Room").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '9':
	            	card.getRoom("Billiard Room").addStandingPosition(new Position(count, row));
                case 'r':
                    addToBoard(card.getRoom("Billiard Room"), board, count++, row);
                    break;
                case 'R':
                    addToBoard(card.getRoom("Billiard Room").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '8':
	            	card.getRoom("Library").addStandingPosition(new Position(count, row));
                case 'l':
                    addToBoard(card.getRoom("Library"), board, count++, row);
                    break;
                case 'L':
                    addToBoard(card.getRoom("Library").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '5':
	            	card.getRoom("Lounge").addStandingPosition(new Position(count, row));
                case 'o':
                    addToBoard(card.getRoom("Lounge"), board, count++, row);
                    break;
                case 'O':
                    addToBoard(card.getRoom("Lounge").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '6':
	            	card.getRoom("Hall").addStandingPosition(new Position(count, row));
                case 'h':
                    addToBoard(card.getRoom("Hall"), board, count++, row);
                    break;
                case 'H':
                    addToBoard(card.getRoom("Hall").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '7':
	            	card.getRoom("Study").addStandingPosition(new Position(count, row));
                case 't':
                    addToBoard(card.getRoom("Study"), board, count++, row);
                    break;
                case 'T':
                    addToBoard(card.getRoom("Study").createDoor(new Position(count, row)), board, count++, row);
                    break;
                case '#':
                    addToBoard(new Room("BLOCKED"), board, count++, row);
                    break;
                case 's':
                    addToBoard(new Room("Solution"), board, count++, row);
                    break;
                case '.': // these are the paths in which we can move
                    addToBoard(null, board, count++, row);
                    break;
                case '*': // NO go zones, they are liked blocked but not
                    addToBoard(new Room("INSIDE"), board, count++, row);
                    break;
                case 'P': // players start position
                    playersStartPositions.add(new Position(count++, row));
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect input read: " + c);
	
	            }
        }
    }

    private static void addToBoard(Board o, Board[][] boards, int row, int col) {
        boards[row][col] = o;
    }
}
