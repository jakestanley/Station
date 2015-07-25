package resources;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stanners on 25/07/2015.
 */
public class FontLoader {

    public static String FONT_DIR = "res/fonts/";

    public static TrueTypeFont loadFont(String fontName) {
        // InputStream inputStream	= ResourceLoader.getResourceAsStream("res/visitor2.ttf");
        InputStream inputStream	= ResourceLoader.getResourceAsStream(FONT_DIR + fontName);
        Font awtFont2 = null;
        try {
            awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        awtFont2 = awtFont2.deriveFont(16f); // set font size
        return new TrueTypeFont(awtFont2, false);

    }

}