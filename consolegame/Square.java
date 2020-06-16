package consolegame;

import java.util.ArrayList;

public class Square {

    private int x;
    private int y;
    private int seqNum;
    private String squareImage;
    private String squareText;
    private String squareSound;
    private ArrayList<String> actions;

    public Square (int num, int sqX, int sqY,
                   String picture, String text, String sound,
                   ArrayList<String> listOfActions) {
        x = sqX;
        y = sqY;
        seqNum = num;
        squareImage = picture;
        squareText = text;
        squareSound = sound;
        actions = listOfActions; // copying reference of list

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

}
