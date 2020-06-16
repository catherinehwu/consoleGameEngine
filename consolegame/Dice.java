package consolegame;

import java.util.Random;

public class Dice {
    private int limitIncl;
    private Random rand;

    public Dice(int rangeUpper) {
        limitIncl = rangeUpper;
        rand = new Random();
    }

    public int nextVal() {
        int next = rand.nextInt(limitIncl) + 1;
        return next;
    }
}
