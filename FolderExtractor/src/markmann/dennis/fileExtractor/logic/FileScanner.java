package markmann.dennis.fileExtractor.logic;

import java.awt.TrayIcon.MessageType;
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
            String errorMessage = "ExtractionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.";
            LOGGER.error(errorMessage);
            this.showErrorNotification(errorMessage);
            return;
        }
        if (!this.isPathValid(completionFolder)) {
            String errorMessage = "CompletionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.";
            LOGGER.error(errorMessage);
            this.showErrorNotification(errorMessage);
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
        if (SettingHandler.getGeneralSettings().useHistory() && !mediaList.isEmpty()) {
            new HistoryHandler().addToHistory(mediaList);
        }
        if (SettingHandler.getGeneralSettings().useSystemTray() && SettingHandler.getGeneralSettings().usePopupNotification()
                && (mediaList.size() > 0)) {
            this.showExtractionNotification(mediaList);
        }
    }

    private void showErrorNotification(String errorMessage) {
        if (SettingHandler.getGeneralSettings().useSystemTray() && SettingHandler.getGeneralSettings().usePopupNotification()) {
            SystemTrayMenu.sendTextPopup("FileExtractor", errorMessage, MessageType.ERROR);
        }
    }

    private void showExtractionNotification(ArrayList<Medium> mediaList) {
        StringBuilder fileNames = new StringBuilder();
        int i = 0;
        String infoString = "new file:";
        for (Medium medium : mediaList) {
            fileNames.append("\n");
            fileNames.append(medium.getCompleteTitle());
            i++;
            if (i == 2) {
                infoString = mediaList.size() + " new files:";
                break;
            }
        }
        SystemTrayMenu.sendTextPopup("FileExtractor", "Extracted " + infoString + fileNames.toString(), MessageType.INFO);
    }
}
