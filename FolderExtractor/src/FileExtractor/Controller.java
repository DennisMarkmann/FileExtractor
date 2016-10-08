package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

public class Controller {

    void startProcess(String extractionPath, String completionPath) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(new File(extractionPath));
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(new File(extractionPath), fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);

        new FileMover().moveFiles(fileList, new File(completionPath));
        new Cleaner().cleanFiles(folderList);
    }

}
