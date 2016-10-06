package Main;

import java.io.File;
import java.util.ArrayList;

class FileLister {

    ArrayList<File> listFilesInFolderList(final ArrayList<File> folderList) {

        final ArrayList<File> fileList = new ArrayList<>();
        for (final File folder : folderList) {
            for (File file : folder.listFiles()) {
                final String fileName = file.getName();
                if (!file.isDirectory()
                        && (fileName.endsWith(".mkv") || fileName.endsWith(".mp4") || fileName.endsWith(".avi"))) {
                    fileList.add(file);
                }
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
