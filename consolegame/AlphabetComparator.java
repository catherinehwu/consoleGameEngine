package consolegame;

import java.util.Comparator;

public class AlphabetComparator implements Comparator<Player> {
    public int compare(Player p1, Player p2) {
        String p1Name = p1.getName().toLowerCase();
        String p2Name = p2.getName().toLowerCase();
        if (p1Name.compareTo(p2Name) > 0) {
            return 1;
        } else if (p1Name.equals(p2Name)) {
            return 0;
        } else {
            return -1;
        }
    }
}
