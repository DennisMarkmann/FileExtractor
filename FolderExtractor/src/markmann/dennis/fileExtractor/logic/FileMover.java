package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.MediaType;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.mediaObjects.Series;
import markmann.dennis.fileExtractor.settings.ExceptionPath;
import markmann.dennis.fileExtractor.settings.TypeSettings;

class FileMover {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private String checkForAdditionalFolder(Medium medium, TypeSettings settings, String exceptionPath) {
        String additionalFolder = "";
        boolean addSeriesFolder = false;

        if (settings.useSeriesFolder()) {
            addSeriesFolder = true;
        }

        if (exceptionPath.equals("") && settings.useCurrentlyWatchingCheck()) {
            if (!new File(settings.getCompletionPath() + "\\" + medium.getTitle()).exists()) {
                additionalFolder = additionalFolder + "\\Later\\";
                addSeriesFolder = true;
            }
        }

        if (addSeriesFolder) {
            additionalFolder = additionalFolder + medium.getTitle() + "\\";
        }
        if (settings.useSeasonFolder() && settings.getType().equals(MediaType.Series)) {
            additionalFolder = additionalFolder + ((Series) medium).getSeason() + "\\";
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

    void moveFiles(final ArrayList<Medium> mediaList, final File destinationDirectory, TypeSettings settings) {

        for (final Medium medium : mediaList) {
            try {
                String title = medium.getCompleteTitle();
                String exceptionPath = this.checkForException(title, settings.getExceptions());
                String additionalFolder = this.checkForAdditionalFolder(medium, settings, exceptionPath);

                File destinationFolder = new File(destinationDirectory.getPath() + additionalFolder + "\\" + exceptionPath);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                Path destinationPath = new File(destinationFolder.toString() + "\\" + title).toPath();
                Path sourcePath = new File(medium.getCompletePath()).toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Moving '" + title + "' to '" + destinationPath + "'.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}