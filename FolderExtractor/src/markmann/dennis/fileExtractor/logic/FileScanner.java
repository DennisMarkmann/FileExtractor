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

    /**
     * Collecting a List containing all files to process.
     */
    private ArrayList<File> collectFilesToProcess(File extractionFolder, FileLister fl, ArrayList<File> folderList) {
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(extractionFolder, fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);
        LOGGER.info("Number of entries to process: '" + fileList.size() + "'.");
        Collections.sort(fileList);
        return fileList;
    }

    /**
     * Checks if the folder exists and is a directory. Creates a popup notification if it is not.
     */
    private boolean isPathValid(File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            NotificationHelper.showErrorNotification("Directory '" + folder.getAbsolutePath() + "' is not valid.", true, null);
            return false;
        }
        return true;
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
        LOGGER.info("Checking for " + settings.getType().toString() + ((manually) ? " (manually):" : ":"));
        if (SettingHandler.getGeneralSettings().useExtendedLogging()) {
            LOGGER.info(
                    "Type: '" + settings.getType() + "', ExtractionPath: '" + settings.getExtractionPath()
                            + "', CompletionPath: '" + settings.getCompletionPath() + "', SeriesFolder: '"
                            + settings.useSeriesFolder() + "', SeasonFolder: '" + settings.useSeasonFolder()
                            + "', CurrentlyWatchingCheck: '" + settings.useCurrentlyWatchingCheck() + "'.");
        }
        File extractionFolder = new File(settings.getExtractionPath());
        File completionFolder = new File(settings.getCompletionPath());

        if (!this.isPathValid(extractionFolder) || !this.isPathValid(completionFolder)) {
            return;
        }
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(extractionFolder);
        ArrayList<File> fileList = this.collectFilesToProcess(extractionFolder, fl, folderList);

        ArrayList<Medium> mediaList = new ArrayList<>();
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
