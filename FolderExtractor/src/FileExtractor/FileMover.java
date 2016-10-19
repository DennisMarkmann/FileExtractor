package FileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.ExceptionPath;
import FileExtractor.Settings.TypeSettings;

class FileMover {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");

    private String checkForAdditionalFolder(String name, TypeSettings settings) {
        String additionalFolder = "";
        if (settings.useSeriesFolder()) {
        }
        if (settings.useSeasonFolder()) {
        }
        return additionalFolder;
    }

    private String checkForException(String fileName, ArrayList<ExceptionPath> exceptions) {
        for (ExceptionPath exceptionPath : exceptions) {
            if (fileName.toLowerCase().startsWith(exceptionPath.getName().toLowerCase())) {
                return exceptionPath.getPath();
            }
        }
        return "";
    }

    void moveFiles(final ArrayList<File> fileList, final File destinationDirectory, TypeSettings settings) {

        for (final File file : fileList) {
            try {
                Path sourcePath = file.toPath();
                String exceptionPath = this.checkForException(file.getName(), settings.getExceptions());
                String additionalFolder = this.checkForAdditionalFolder(file.getName(), settings);
                Path destinationPath = new File(
                        destinationDirectory.getPath() + exceptionPath + "\\" + additionalFolder + file.getName()).toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Moving '" + sourcePath + "' to '" + destinationPath + "'.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}