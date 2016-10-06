package FileExtractor;

import java.io.File;
import java.util.ArrayList;

class Cleaner {

    void cleanFiles(ArrayList<File> fileList) {
        for (File file : fileList) {
            this.deleteDir(file);
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                this.deleteDir(f);
            }
        }
        file.delete();
    }
}
