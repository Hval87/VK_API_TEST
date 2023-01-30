package util;

import java.nio.file.Paths;

public class FileProvider {

    private FileProvider() {
    }

    public static String getPath(String fileName) {
        return Paths.get("src","test","resources", fileName).toString();
    }
}
