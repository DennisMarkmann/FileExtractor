package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.TypeSettings;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

public class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");

    public void startProcess(TypeSettings settings) {
        LOGGER.info(
                "Checking for " + settings.getName() + "settings: " + "' Type: '" + settings.getType() + "', ExtractionPath: '"
                        + settings.getExtractionPath() + "', CompletionPath: '" + settings.getCompletionPath() + "'.");

        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(new File(settings.getExtractionPath()));
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(new File(settings.getExtractionPath()), fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);
        if (fileList.isEmpty()) {
            LOGGER.info("Nothing to process.");
        }
        else {
            LOGGER.info("Number of entries to process: '" + fileList.size() + "'.");
        }
        fileList = new Renamer().renameFiles(fileList, settings.getType());
        new FileMover().moveFiles(fileList, new File(settings.getCompletionPath()), settings.getExceptions());
        new Cleaner().cleanFiles(folderList);
    }
}
