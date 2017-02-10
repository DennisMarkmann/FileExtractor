package markmann.dennis.fileExtractor.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;
import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.objects.Medium;
import markmann.dennis.fileExtractor.settings.ExceptionPath;
import markmann.dennis.fileExtractor.settings.FileWriteHelper;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.MediaType;
import markmann.dennis.fileExtractor.settings.TypeSettings;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

public class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private ArrayList<TypeSettings> settingList = new ArrayList<>();

    private GeneralSettings generalSettings = new GeneralSettings();

    private void createDefaultSettings() {
        TypeSettings settings = new TypeSettings();
        settings.setName("Anime");
        settings.setType(MediaType.Anime);
        settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
        settings.setCompletionPath("M:\\MyAnime");
        settings.setUseCurrentlyWatchingCheck(true);
        settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
        settings.addException(new ExceptionPath("Monster Hunter Stories Ride On", "\\Other\\Monster Hunter Stories Ride On"));
        settings.addException(new ExceptionPath("Nanbaka", "\\Other\\Nanbaka"));
        this.settingList.add(settings);

        settings = new TypeSettings();
        settings.setName("Series");
        settings.setType(MediaType.Series);
        settings.setExtractionPath("M:\\Processing\\Completed\\Series");
        settings.setCompletionPath("M:\\Series");
        settings.setUseCurrentlyWatchingCheck(true);
        this.settingList.add(settings);

        this.generalSettings.setTimerInterval(60);
        this.generalSettings.setUseTimer(true);
        this.generalSettings.setUseRenaming(true);
        this.generalSettings.setUseFileMoving(true);
        this.generalSettings.setUseCleanup(true);
        this.generalSettings.setUseExtendedLogging(false);
        this.generalSettings.setRemoveCorruptFiles(true);
        this.generalSettings.setUseSystemTray(true);
    }

    private void extract(TypeSettings settings, boolean manually) {
        if (manually) {
            LOGGER.info("Checking for " + settings.getName() + " (manually):");
        }
        else {
            LOGGER.info("Checking for " + settings.getName() + ":");
        }
        if (this.generalSettings.useExtendedLogging()) {
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

        mediaList = new FileRenamer().scanFiles(
                fileList,
                settings.getType(),
                this.generalSettings.useRenaming(),
                this.generalSettings.removeCorruptFiles());

        if (this.generalSettings.useFileMoving()) {
            new FileMover().moveFiles(mediaList, completionFolder, settings);
        }
        if (this.generalSettings.useCleanup()) {
            new FileCleaner().cleanFiles(folderList);
        }
    }

    public ArrayList<TypeSettings> getSettingList() {
        return this.settingList;
    }

    private boolean isPathValid(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        return false;
    }

    public void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(fileName));
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    void process() {
        this.createDefaultSettings();
        new FileWriteHelper().createXMLFiles(this.settingList, this.generalSettings);
        new SystemTrayMenu().createSystemTrayEntry(this);

        if (this.generalSettings.useTimer()) {
            LOGGER.info("Timer activated. Interval: '" + this.generalSettings.getTimerInterval() + "' minutes.");

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    Controller.this.startExtraction(false);
                }

            }, 1000, this.generalSettings.getTimerInterval() * 60000);
        }
        else {
            this.startExtraction(true);
        }
    }

    public void shutDownApplication() {
        LOGGER.info("Application stopped.");
        System.exit(0);
    }

    public void startExtraction(boolean manually) {
        for (final TypeSettings st : Controller.this.settingList) {
            Controller.this.extract(st, manually);
        }
        LOGGER.info("-----------------------------------");
    }
}
