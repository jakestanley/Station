package map;

import main.Game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loads maps from files into instances of MapTemplate
 */
public class MapLoader {

    private static final String MAPS_PATH = "res/maps/";

    public static MapTemplate loadMap(String mapFileName) {

        // Initialising
        int width = 0;
        int height = 0;
        boolean[] booleans = new boolean[0];

        String mapPath = MAPS_PATH + mapFileName;
        String cvsSplitBy = ",";
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(mapPath));
            String line = br.readLine();
            if (line != null) {
                String[] strings = line.split(cvsSplitBy);
                System.out.println("Strings.length: " + strings.length);
                width = Integer.parseInt(strings[0]);
                height = Integer.parseInt(strings[1]);
                booleans = new boolean[width * height];
                for (int i = 0; i < (strings.length - 2); i++) {
                    boolean isTraversible = (Integer.parseInt(strings[i + 2]) != 0);
                    booleans[i] = isTraversible;
                }

            } else {
                System.err.println("Couldn't get map design from file"); // TODO better errors
                System.exit(Game.EXIT_BAD);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new MapTemplate(width, height, booleans);

    }

}
