package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;

class FileCleaner {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    void cleanFiles(ArrayList<File> fileList) {
        for (File file : fileList) {
            this.deleteDir(file);
            LOGGER.info("Deleting folder: '" + file.getName() + "'.");
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                this.deleteDir(f);
            }
        }
        file.delete();
    }
}
