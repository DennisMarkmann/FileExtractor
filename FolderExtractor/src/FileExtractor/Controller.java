package FileExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.ExceptionPath;
import FileExtractor.Settings.FileWriteHelper;
import FileExtractor.Settings.GeneralSettings;
import FileExtractor.Settings.MediaType;
import FileExtractor.Settings.TypeSettings;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");
    private ArrayList<TypeSettings> settingList = new ArrayList<>();
    private GeneralSettings generalSettings = new GeneralSettings();

    private void createDefaultSettings() {
        TypeSettings settings = new TypeSettings();
        settings.setName("Anime");
        settings.setType(MediaType.Anime);
        settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
        settings.setCompletionPath("M:\\MyAnime");
        settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
        settings.addException(new ExceptionPath("Bubuki Buranki", "\\Other\\Bubuki Buranki"));
        this.settingList.add(settings);

        settings = new TypeSettings();
        settings.setName("Series");
        settings.setType(MediaType.Series);
        settings.setExtractionPath("M:\\Processing\\Completed\\Series");
        settings.setCompletionPath("M:\\Series");
        settings.addException(new ExceptionPath("Ash vs Evil Dead", "\\Later\\Ash vs Evil Dead"));
        settings.addException(new ExceptionPath("Once Upon A Time", "\\Later\\Once Upon A Time\\Season 6"));
        this.settingList.add(settings);

        this.generalSettings.setTimerInterval(15);
        this.generalSettings.setUseTimer(true);
        this.generalSettings.setUseRenaming(true);
        this.generalSettings.setUseFileMoving(true);
        this.generalSettings.setUseCleanup(true);
    }

    private void extract(TypeSettings settings) {
        LOGGER.info(
                "Checking for " + settings.getName() + " Settings: " + "' Type: '" + settings.getType() + "', ExtractionPath: '"
                        + settings.getExtractionPath() + "', CompletionPath: '" + settings.getCompletionPath() + "'.");

        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(new File(settings.getExtractionPath()));
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(new File(settings.getExtractionPath()), fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);
        LOGGER.info("Number of entries to process: '" + fileList.size() + "'.");

        if (this.generalSettings.useRenaming()) {
            fileList = new Renamer().renameFiles(fileList, settings.getType());
        }
        if (this.generalSettings.useFileMoving()) {
            new FileMover().moveFiles(fileList, new File(settings.getCompletionPath()), settings);
        }
        if (this.generalSettings.useCleanup()) {
            new Cleaner().cleanFiles(folderList);
        }
    }

    void process() {
        this.createDefaultSettings();
        new FileWriteHelper().createXMLFiles(this.settingList, this.generalSettings);
        
        if (this.generalSettings.useTimer()) {
            LOGGER.info("Timer activated. Interval: '" + this.generalSettings.getTimerInterval() + "' minutes.");

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    Controller.this.startExtraction();
                }

            }, 1000, this.generalSettings.getTimerInterval() * 60000);
        }
        else {
            this.startExtraction();
        }
    }

    private void startExtraction() {
        for (final TypeSettings st : Controller.this.settingList) {
            Controller.this.extract(st);
        }
        LOGGER.info("-----------------------------------");
    }
}
