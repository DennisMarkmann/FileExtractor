package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class Extractor {

    void extractFile(final ArrayList<File> fileList, final File path) {

        for (final File file : fileList) {
            try {
                Path sourcePath = file.toPath();
                Path destinationPath = new File(path.getPath() + "\\" + file.getName()).toPath();
                Files.move(sourcePath, destinationPath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}