package util;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log
public class ImageActions {
    private static String PATH_FOR_TMP_IMAGE = "src/main/resources/tmpIMG1.jpg";

    @SneakyThrows
    public static void downloadImage(String sourceUrl) {
        try (InputStream in = new URL(sourceUrl).openStream()) {
            Files.copy(in, Paths.get(PATH_FOR_TMP_IMAGE));
        }
    }

    public static boolean compareDownloadedImage(String expectedImg) {
        boolean result = false;
        File tmpFile = null;
        try {
            tmpFile = new File(PATH_FOR_TMP_IMAGE);
            BufferedImage actIMG = ImageIO.read(tmpFile);
            BufferedImage expIMG = ImageIO.read(new File(expectedImg));
            ImageDiff diff = new ImageDiffer().makeDiff(actIMG, expIMG);
            result = diff.hasDiff();
            tmpFile.delete();
        } catch (IOException e) {
            log.warning("photo is not founded");
        } finally {
            tmpFile.delete();
        }
        return result;
    }
}
