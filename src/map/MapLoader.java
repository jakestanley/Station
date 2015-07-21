package map;

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
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                boolean[] tileBools = new boolean[x * y];
                for (int i = 0; i < (strings.length - 2); i++) {
                    boolean isTraversible = (Integer.parseInt(strings[i + 2]) != 0);
                    tileBools[i] = isTraversible;
                }

            } else {
                System.err.println("Couldn't get map design from file"); // TODO better errors
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new MapTemplate(width, height, booleans);

    }

}
