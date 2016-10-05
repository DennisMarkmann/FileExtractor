package Main;

import java.io.File;
import java.util.ArrayList;

class FileLister {

    ArrayList<File> listFilesInSubFolder(final File path, boolean isSubFolder) {

        final ArrayList<File> fileList = new ArrayList<>();
        for (final File file : path.listFiles()) {
            if (isSubFolder) {
                fileList.add(file);
            }
            if (file.isDirectory()) {
                fileList.addAll(this.listFilesInSubFolder(file, true));
            }
        }
        return fileList;
    }

    ArrayList<File> listFolderAtPath(final File path) {

        final ArrayList<File> fileList = new ArrayList<>();
        for (final File file : path.listFiles()) {
            if (file.isDirectory()) {
                fileList.add(file);
            }
        }
        return fileList;
    }
}
