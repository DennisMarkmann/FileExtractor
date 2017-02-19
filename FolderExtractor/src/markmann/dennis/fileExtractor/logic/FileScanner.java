package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;
import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.settings.TypeSettings;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

public class FileScanner implements Runnable {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private boolean manually;

    public FileScanner(boolean manually) {
        this.manually = manually;
    }

    private boolean isPathValid(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        if (Controller.applyForWriteAccess()) {
            SettingHandler.readSettingsFromXML(false);
            for (final TypeSettings settings : SettingHandler.getTypeSettings()) {
                this.scan(settings, this.manually);
            }
        }
        LOGGER.info("-----------------------------------");
        Controller.returnWriteAccess();
    }

    void scan(TypeSettings settings, boolean manually) {
        if (manually) {
            LOGGER.info("Checking for " + settings.getType().toString() + " (manually):");
        }
        else {
            LOGGER.info("Checking for " + settings.getType().toString() + ":");
        }
        if (SettingHandler.getGeneralSettings().useExtendedLogging()) {
            LOGGER.info(
                    "Type: '" + settings.getType() + "', ExtractionPath: '" + settings.getExtractionPath()
                            + "', CompletionPath: '" + settings.getCompletionPath() + "', SeriesFolder: '"
                            + settings.useSeriesFolder() + "', SeasonFolder: '" + settings.useSeasonFolder()
                            + "', CurrentlyWatchingCheck: '" + settings.useCurrentlyWatchingCheck() + "'.");
        }
        File extractionFolder = new File(settings.getExtractionPath());
        File completionFolder = new File(settings.getCompletionPath());

        if (!this.isPathValid(extractionFolder)) {
            LOGGER.error("ExtractionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.");
            return;
        }
        if (!this.isPathValid(completionFolder)) {
            LOGGER.error("CompletionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.");
            return;
        }
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(extractionFolder);
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        ArrayList<Medium> mediaList = new ArrayList<>();

        fileList = fl.listFilesForFolder(extractionFolder, fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);
        LOGGER.info("Number of entries to process: '" + fileList.size() + "'.");
        Collections.sort(fileList);

        mediaList = new FileRenamer().scanFiles(fileList, settings.getType());

        if (SettingHandler.getGeneralSettings().useFileMoving()) {
            new FileMover().moveFiles(mediaList, completionFolder, settings);
        }
        if (SettingHandler.getGeneralSettings().useCleanup()) {
            new FileCleaner().cleanFiles(folderList);
        }
        if (SettingHandler.getGeneralSettings().useSystemTray() && SettingHandler.getGeneralSettings().usePopupNotification()
                && (mediaList.size() > 0)) {
            StringBuilder fileNames = new StringBuilder();
            for (Medium medium : mediaList) {
                fileNames.append("\n");
                fileNames.append(medium.getCompleteTitle());
            }
            SystemTrayMenu.sendInfoPopup("FileExtractor", "Extracted new files: " + fileNames);
        }
    }
}
