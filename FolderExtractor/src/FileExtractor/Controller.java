package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

public class Controller {

    void startProcess(File extractionPath, File completionPath) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(extractionPath);
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(extractionPath, fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);

        new FileMover().moveFiles(fileList, completionPath);
        new Cleaner().cleanFiles(folderList);
    }

}
