package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import FileExtractor.Settings.MediaType;

public class Renamer {

    private String handleAnimeRenaming(String fileName) {
        String checkString = "[HorribleSubs] ";
        if (fileName.startsWith(checkString)) {
            fileName = fileName.replace(checkString, "");
        }
        fileName = this.handleQualityInformation(fileName);
        return fileName;
    }

    private String handleMovieRenaming(String fileName) {
        fileName = this.handleQualityInformation(fileName);
        return fileName;
    }

    private String handleQualityInformation(String fileName) {
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add(" [1080p]");
        checkList.add(" [720p]");
        checkList.add(" 720p");
        checkList.add(" 1080p");
        for (String checkString : checkList) {
            if (fileName.contains(checkString)) {
                fileName = fileName.replace(checkString, "");
            }
        }
        return fileName;
    }

    private String handleSeriesRenaming(String fileName) {
        fileName = this.handleQualityInformation(fileName);
        return fileName;
    }

    public ArrayList<File> renameFiles(ArrayList<File> fileList, MediaType mediaType) {
        ArrayList<File> renamedFiles = new ArrayList<>();
        for (final File file : fileList) {
            String fileName = file.getName();
            if (mediaType == MediaType.Anime) {
                fileName = this.handleAnimeRenaming(fileName);
            }
            if (mediaType == MediaType.Series) {
                fileName = this.handleSeriesRenaming(fileName);
            }
            if (mediaType == MediaType.Movie) {
                fileName = this.handleMovieRenaming(fileName);
            }
            String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            File newFile = new File(filePath + "\\" + fileName);
            if (file.renameTo(newFile)) {
                renamedFiles.add(newFile);
            }
        }
        return renamedFiles;
    }
}
