package FileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import dennis.markmann.MyLibraries.DefaultJobs.File.FileFilter;
import dennis.markmann.MyLibraries.DefaultJobs.File.FileLister;

class Extractor {

    void extractFile(final ArrayList<File> fileList, final File path) {

        for (final File file : fileList) {
            try {
                Path sourcePath = file.toPath();
                Path destinationPath = new File(path.getPath() + "\\" + file.getName()).toPath();
                Files.move(sourcePath, destinationPath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void startExtraction(File path) {
        FileLister fl = new FileLister();
        ArrayList<File> folderList = fl.listFolderAtPath(path);
        ArrayList<File> fileList = fl.listFilesInFolderList(folderList, true);
        fileList = new FileFilter().addMovies().filter(fileList);
        this.extractFile(fileList, path);
        new Cleaner().cleanFiles(folderList);
    }
}