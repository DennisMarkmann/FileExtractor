package Main;

import java.io.File;
import java.util.ArrayList;

class FileLister {

    private ArrayList<File> listFilesForFolder(File folder, ArrayList<File> fileList, boolean includeSubfolder) {
        for (File file : folder.listFiles()) {
            if (includeSubfolder && file.isDirectory()) {
                this.listFilesForFolder(file, fileList, true);
            }
            else if (!file.isDirectory()) {
                final String fileName = file.getName();
                if (fileName.endsWith(".mkv") || fileName.endsWith(".mp4") || fileName.endsWith(".avi")) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    ArrayList<File> listFilesInFolderList(final ArrayList<File> folderList, boolean includeSubfolder) {

        final ArrayList<File> fileList = new ArrayList<>();
        for (final File folder : folderList) {
            this.listFilesForFolder(folder, fileList, includeSubfolder);
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
