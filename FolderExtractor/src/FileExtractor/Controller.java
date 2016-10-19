package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import FileExtractor.Settings.TypeSettings;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

public class Controller {

    public void startProcess(TypeSettings settings) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(new File(settings.getExtractionPath()));
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(new File(settings.getExtractionPath()), fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);
        fileList = new Renamer().renameFiles(fileList, settings.getType());
        new FileMover().moveFiles(fileList, new File(settings.getCompletionPath()), settings.getExceptions());
        new Cleaner().cleanFiles(folderList);
    }
}
