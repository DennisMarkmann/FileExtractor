package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import FileExtractor.Settings.Settings;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

public class Controller {

    public void startProcess(Settings st) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(new File(st.getExtractionPath()));
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = fl.listFilesForFolder(new File(st.getExtractionPath()), fileList, false);
        fileList = new FileFilter().addMovies().filter(fileList);

        new FileMover().moveFiles(fileList, new File(st.getCompletionPath()), st.getExceptions());
        new Cleaner().cleanFiles(folderList);
    }
}
