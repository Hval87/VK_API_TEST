package util;

import nl.flotsam.xeger.Xeger;

public class TextGenerator {
    public static String generateRandomString() {
        Xeger xeger = new Xeger(String.format("([:word:]){%d}", Math.toIntExact(MyParser.getTestValue("length_of_random_string"))));
        return xeger.generate();
    }
}