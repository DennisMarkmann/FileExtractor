package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;
import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.settings.TypeSettings;

/**
 * Used to scan for new media to process.
 *
 * @author Dennis.Markmann
 */

public class FileScanner implements Runnable {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private boolean manually;

    /**
     * Constructor for the class remembering if the scan was caused automatically or manually.
     *
     * @param manually: constructor called manually or automatically?
     */

    public FileScanner(boolean manually) {
        this.manually = manually;
    }

    private boolean isPathValid(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * Locks the write access for itself and reads the current settings. Then carries out a scan for new media to process and
     * returns the write access afterwards.
     */

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

    /**
     * Scanning for new media to process, starting all configured follow up operations.
     *
     * @param settings: settings used for the currently processed media type.
     * @param manually: used for logging purposes only.
     */
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
            NotificationHelper.showErrorNotification(
                    "ExtractionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.",
                    true,
                    null);
            return;
        }
        if (!this.isPathValid(completionFolder)) {
            NotificationHelper.showErrorNotification(
                    "CompletionFolder '" + extractionFolder.getAbsolutePath() + "' is not valid.",
                    true,
                    null);
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
        GeneralSettings generalSettings = SettingHandler.getGeneralSettings();

        if (generalSettings.useFileMoving()) {
            new FileMover().moveFiles(mediaList, completionFolder, settings);
        }
        if (generalSettings.useCleanup()) {
            new FileCleaner().cleanFiles(folderList);
        }
        if (generalSettings.useHistory()) {
            new HistoryHandler().addToHistory(mediaList);
        }
        if ((mediaList.size() > 0)) {
            NotificationHelper.showExtractionNotification(mediaList);
        }
    }

}
