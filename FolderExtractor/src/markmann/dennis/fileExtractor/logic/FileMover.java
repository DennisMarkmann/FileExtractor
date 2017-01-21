package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.ExceptionPath;
import markmann.dennis.fileExtractor.settings.TypeSettings;

class FileMover {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private String checkForAdditionalFolder(String name, TypeSettings settings, String exceptionPath) {
        String additionalFolder = "";
        if (settings.useSeriesFolder()) {
        }
        if (settings.useSeasonFolder()) {
        }
        if (exceptionPath.equals("") && settings.useCurrentlyWatchingCheck()) {
            String mediaName = name.substring(0, name.indexOf("-") - 1);
            if (!new File(settings.getCompletionPath() + "\\" + mediaName).exists()) {
                additionalFolder = additionalFolder + "\\Later\\";
            }
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
                String additionalFolder = this.checkForAdditionalFolder(file.getName(), settings, exceptionPath);
                if (exceptionPath.equals("Delete")) {
                    LOGGER.info("Removing '" + file.getName() + "'.");
                    continue;
                }
                File destinationFolder = new File(destinationDirectory.getPath() + additionalFolder + "\\" + exceptionPath);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }
                Path destinationPath = new File(destinationFolder.toString() + "\\" + file.getName()).toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Moving '" + file.getName() + "' to '" + destinationPath + "'.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}