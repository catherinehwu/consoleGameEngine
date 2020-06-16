package consolegame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    private String name;
    private String imageFileName;
    private Square location;
    private GameEngine game;
    private boolean skipTurn;

    public Player(String playerName, String imageFile, GameEngine curGame) {
        name = playerName;
        imageFileName = imageFile;
        game = curGame;
        location = game.getBoard().getStart();
        skipTurn = false;
    }

    public void turn() {
        int stepVal = roll();
        move(stepVal);
    }

    private int roll() {
        /* Human input dice roll
        String steps = game.readLine();
        int stepVal = Integer.valueOf(steps);
        return stepVal;
        */

        // Random dice role
        game.readLine();
        int nextVal = game.getDice().nextVal();
        System.out.println(name + " rolled a " + nextVal);
        return nextVal;
    }

    private void move(int num) {
        Board gameBoard = game.getBoard();
        int newLocationSeqNum = safeMove(num, gameBoard);

        Square futureLoc = gameBoard.getSquare(newLocationSeqNum);
        location = futureLoc;

        game.display();

        for (String action : location.getActions()) {
            boolean moved = this.act(action);
            if (moved) {
                return;
            }
        }
    }

    private void moveTo(int sqNum) {
        int curNum = location.getSeqNum();
        int difference = sqNum - curNum;
        move(difference);
    }

    private int safeMove(int num, Board gameBoard) {
        int locationSeqNum = location.getSeqNum();
        if (num < 0) {
            int counter = num;
            while (counter < 0 && locationSeqNum >= 0) {
                locationSeqNum -= 1;
                counter += 1;
            }
        } else {
            int counter = 0;
            while (counter < num && locationSeqNum < gameBoard.getTotalSqNum() - 1) {
                locationSeqNum += 1;
                counter += 1;
            }
        }
        return locationSeqNum;
    }

    private boolean act(String key) {
        char type = key.charAt(0);
        switch(type) {
            case 'a':
            case 'A':
                // roll again
                System.out.println("Roll Again!");
                turn();
                return true;
            case 'b':
            case 'B':
                // change square by certain number
                // positive is move forward
                int steps = Integer.valueOf(key.substring(1));
                System.out.println("Moving " + steps + " forward!");
                move(steps);
                return true;
            case 'c':
            case 'C':
                // change square by certain number
                // negative is move backward
                int backSteps = Integer.valueOf(key.substring(1));
                System.out.println("Moving " + backSteps + " backwards!");
                move(backSteps * -1);
                return true;
            case 'd':
            case 'D':
                // go to a certain square
                int sqNum = Integer.valueOf(key.substring(1));
                System.out.println("Moving to square #" + sqNum + "!");
                moveTo(sqNum);
                return true;
            case 'e':
            case 'E':
                // skip this player's next turn
                skipTurn = true;
                System.out.println("Next turn skipped!");
                break;
            case 'f':
            case 'F':
                // cycle reversed
                game.reverse();
                System.out.println("Turn order reversed!");
                break;
            case 'g':
            case 'G':
                // if roll certain number
                // do the following action
                System.out.println("Roll again to determine action!");
                int number = roll();
                String format = "[gG]([\\d]+)(.*)";
                Matcher match = Pattern.compile(format).matcher(key);
                int target = -1;
                if (match.matches()) {
                    target = Integer.valueOf(match.group(1));
                }
                if (number == target) {
                    act(match.group(2));
                } else {
                    System.out.println("No action occurred!");
                }
                break;
            default:
                System.out.println("Unknown command.");
                break;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Square getLocation() {
        return location;
    }

    public boolean getSkipTurn() {
        return skipTurn;
    }

    public void turnSkipped() {
        skipTurn = false;
    }
}
