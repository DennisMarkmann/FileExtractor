package markmann.dennis.fileExtractor.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.log4j.Logger;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;
import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
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

    private void addToHistory(ArrayList<Medium> mediaList) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./Logs/History.txt", true)))) {
            String dateString = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
            StringBuilder sb = new StringBuilder();
            for (Medium medium : mediaList) {
                sb.append(dateString);
                sb.append("  (");
                sb.append(medium.getClass().getSimpleName());
                sb.append(")  ");
                if (medium instanceof Anime) {
                    sb.append(" ");
                }
                sb.append(medium.getCompleteTitleNoExt());
                sb.append("\n");
            }
            out.print(sb.toString());
        }
        catch (IOException e) {
            LOGGER.error("Error while trying to access '/Logs/History.txt'.", e);
            e.printStackTrace();
        }

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
            this.showExtractionNotification(mediaList);
        }
        this.addToHistory(mediaList);
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
        SystemTrayMenu.sendInfoPopup("FileExtractor", "Extracted " + infoString + fileNames.toString());
    }
}
