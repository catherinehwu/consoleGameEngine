package consolegame;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static GameEngine game;
    private static String[] headerSetup =
            {"seqNum", "x", "y", "image", "sound", "text",
                    "roll again", "move by", "move to", "skip"};

    public static void main (String[] args) {
        try {
            String configFileName = args[0];
            Scanner config = new Scanner(new File(configFileName));

            // Trying alternative version with no Scanner
            File f = new File(configFileName);
            List<String> configText = Files.readAllLines(Paths.get(f.getPath()));
            // initializeList(configText);
            initializeCSV(configText);

            // Old version
            // initialize(config);
            game.setUpPlayers();
            game.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("error");
        }
    }

    private static void initializeList(List<String> config) {
        String setUp = config.get(0);
        String[] setUpSettings = setUp.split(" ");

        int rowNum = Integer.valueOf(setUpSettings[0]);
        int colNum = Integer.valueOf(setUpSettings[1]);
        int endPosNum = Integer.valueOf(setUpSettings[2]);
        game = new GameEngine(rowNum, colNum, endPosNum);

        for (int i = 1; i < config.size(); i += 1) {
            setUpSquareList(config.get(i));
        }
    }

    private static void initialize(Scanner config) {
        Scanner setUp = new Scanner(config.nextLine());
        int rowNum = setUp.nextInt();
        int colNum = setUp.nextInt();
        int endPosNum = setUp.nextInt();
        game = new GameEngine(rowNum, colNum, endPosNum);

        while (config.hasNextLine()) {
            String nextSqSettings = config.nextLine();
            setUpSquare(nextSqSettings);
        }
    }

    private static void setUpSquareList(String settings) {
        String[] line = settings.split(" ");
        int seqNum = Integer.valueOf(line[0]);
        int xVal = Integer.valueOf(line[1]);
        int yVal = Integer.valueOf(line[2]);

        String[] attributes = new String[3];
        for (int i = 0; i < 3; i += 1) {
            attributes[i] = line[i + 3];
            if (attributes[i].equals("*")) {
                attributes[i] = null;
            }
        }

        ArrayList<String> listOfActions = new ArrayList<>();
        for (int i = 6; i < line.length; i += 1) {
            listOfActions.add(line[i]);
        }

        game.addSquare(seqNum, xVal, yVal,
                attributes[0], attributes[1], attributes[2], listOfActions);

    }

    private static void setUpSquare(String settings) {
        Scanner sqSettings = new Scanner(settings);
        int seqNum = sqSettings.nextInt();
        int xVal = sqSettings.nextInt();
        int yVal = sqSettings.nextInt();

        String[] attributes = new String[3];
        for (int i = 0; i < 3; i += 1) {
            attributes[i] = sqSettings.next();
            if (attributes[i].equals("*")) {
                attributes[i] = null;
            }
        }

        ArrayList<String> listOfActions = new ArrayList<>();
        while (sqSettings.hasNext()) {
            listOfActions.add(sqSettings.next());
        }

        game.addSquare(seqNum, xVal, yVal,
                attributes[0], attributes[1], attributes[2], listOfActions);

    }

    // Parsing csv file
    // Current structure:
    // [square ID, x pos, y pos, image, sound, text,
    // roll again, change square by (pos/ neg), go to sqr id, skip a turn,
    // roll to determine action, conditions to do this row]
    private static int headersNum = 2;
    private static void initializeCSV(List<String> config) {
        // Ignore the header rows (row 1 - 2)
        // Row 3 will be # of squares, x position range, y position range
        // Row 4 onward are square IDs
        // For each square, index 0-2 set for sqNum, x coord, ycoord

        //List<String> config is a list or array of strings separated by \n char
        String boardRep = config.get(headersNum);
        String[] boardData = boardRep.split(",");

        int squareTotal = Integer.valueOf(boardData[0]);
        int xRange = Integer.valueOf(boardData[1]);
        int yRange = Integer.valueOf(boardData[2]);

        game = new GameEngine(xRange, yRange, squareTotal);

        for (int i = headersNum + 1; i < config.size(); i += 1) {
            setUpSquareCSV(config.get(i));
        }
    }

    private static void setUpSquareCSV(String settings) {
        // Assume sqData has same number of columns as headerSetup
        String[] sqData = settings.split(",");

        // Defining Variables
        int seqNum = 0;
        int xVal = 0;
        int yVal = 0;
        String image = null;
        String sound = null;
        String text = null;
        ArrayList<String> listOfActions = new ArrayList<>();

        for (int i = 0; i < sqData.length; i += 1) {
            String columnH = headerSetup[i];
            if (columnH.equals("seqNum")) {
                seqNum = Integer.valueOf(sqData[i]);
            } else if (columnH.equals("x")) {
                xVal = Integer.valueOf(sqData[i]);
            } else if (columnH.equals("y")) {
                yVal = Integer.valueOf(sqData[i]);
            } else if (columnH.equals("image")) {
                if (sqData[i] != null && !sqData[i].isEmpty()) {
                    image = sqData[i];
                }
            } else if (columnH.equals("sound")) {
                if (sqData[i] != null && !sqData[i].isEmpty()) {
                    sound = sqData[i];
                }
            } else if (columnH.equals("text")) {
                if (sqData[i] != null && !sqData[i].isEmpty()) {
                    text = sqData[i];
                }
            } else if (sqData[i] != null && !sqData[i].isEmpty()) {
                String action = actionDetails(sqData[i], i);
                if (action != null) {
                    listOfActions.add(action);
                }
            }
        }

        game.addSquare(seqNum, xVal, yVal, image, text, sound, listOfActions);
    }

    private static String actionDetails(String value, int column) {
        String colType = headerSetup[column];
        String action;
        switch(colType) {
            case "roll again":
                action = "A";
                break;
            case "move by":
                int amount = Integer.valueOf(value);
                if (amount >= 0) {
                    action = "B" + amount;
                } else {
                    amount *= -1;
                    action = "C" + amount;
                }
                break;
            case "move to":
                action = "D" + Integer.valueOf(value);
                break;
            case "skip":
                action = "E";
                break;
            default:
                action = null;
                System.out.println("Invalid action");
        }
        return action;
    }
}
