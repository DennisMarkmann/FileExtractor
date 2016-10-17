package FileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import FileExtractor.Settings.ExceptionPath;

class FileMover {

    private String checkForException(Path sourcePath, ArrayList<ExceptionPath> exceptions) {
        for (ExceptionPath exceptionPath : exceptions) {
            if (sourcePath.toString().contains(exceptionPath.getName())) {
                return exceptionPath.getPath();
            }
        }
        return "";
    }

    void moveFiles(final ArrayList<File> fileList, final File destinationDirectory, ArrayList<ExceptionPath> exceptions) {

        for (final File file : fileList) {
            try {
                Path sourcePath = file.toPath();
                String exceptionPath = this.checkForException(sourcePath, exceptions);
                Path destinationPath = new File(destinationDirectory.getPath() + exceptionPath + "\\" + file.getName())
                        .toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}