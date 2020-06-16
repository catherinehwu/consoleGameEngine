package consolegame;

import java.util.ArrayList;

public class Board {
    private Square[][] grid;
    private Square[] ordering;
    private int Xrange;
    private int Yrange;
    private int totalSqNum;
    private Square start;
    private Square end;

    public Board(int XRange, int YRange, int endPosNum) {
        Xrange = XRange;
        Yrange = YRange;
        totalSqNum = endPosNum;
        grid = new Square[XRange][YRange];
        ordering = new Square[endPosNum + 1];
    }

    public void setSquare(int num, int sqX, int sqY,
                          String picture, String text, String sound,
                          ArrayList<String> listOfActions) {
        Square sq = new Square(num, sqX, sqY, picture, text, sound, listOfActions);
        grid[sqX][sqY] = sq;
        ordering[num] = sq;

        if (num == 0) {
            start = sq;
        } else if (num == totalSqNum - 1) {
            end = sq;
        }
    }

    public void display() {
        StringBuffer boardRep = new StringBuffer();
        for (int y = Yrange - 1; y >= 0; y -= 1) {
            for (int x = 0; x < Xrange; x += 1) {
                Square cur = grid[x][y];
                if (cur != null) {
                    boardRep.append(cur.getSeqNum());
                } else {
                    boardRep.append('X');
                }
                boardRep.append(" ");
            }
            boardRep.append('\n');
        }
        System.out.println();
        System.out.println("Current game board:");
        System.out.println(boardRep.toString());
    }

    public Square getSquare(int sqX, int sqY) {
        return grid[sqX][sqY];
    }

    public Square getSquare(int num) {
        return ordering[num];
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public int getTotalSqNum() {
        return totalSqNum;
    }
}
