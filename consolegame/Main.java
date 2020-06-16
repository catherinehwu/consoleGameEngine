package consolegame;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static GameEngine game;

    public static void main (String[] args) {
        try {
            String configFileName = args[0];
            Scanner config = new Scanner(new File(configFileName));

            // Trying alternative version with no Scanner
            File f = new File(configFileName);
            List<String> configText = Files.readAllLines(Paths.get(f.getPath()));
            initializeList(configText);

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
}
