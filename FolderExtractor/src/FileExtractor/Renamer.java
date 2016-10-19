package FileExtractor;

import java.io.File;
import java.util.ArrayList;

import FileExtractor.Settings.MediaType;

public class Renamer {

    private String addSeparators(String fileName) {
        // TODO
        return fileName;
    }

    private String handleAnimeHoster(String fileName) {
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add("HorribleSubs");
        checkList.add("Harunatsu");
        checkList.add("Coalgirls");
        checkList.add("Vivid");
        checkList.add("Hiryuu");
        checkList.add("DeadFish");
        checkList.add("InsaneSubs");
        checkList.add("Mori");
        checkList.add("Final8");
        checkList.add("tlacatlc6");
        checkList.add("Tsundere");
        checkList.add("Doki");
        checkList.add("FFF");
        checkList.add("CBM");
        checkList.add("AE");
        checkList.add("Anime-Koi");
        for (String checkString : checkList) {
            fileName = this.replaceCheckString(fileName, checkString);
        }
        return fileName;
    }

    private String handleAnimeRenaming(String fileName) {
        fileName = this.handleAnimeHoster(fileName);
        fileName = this.handleQualityInformation(fileName);
        return fileName;
    }

    private String handleMovieRenaming(String fileName) {
        fileName = this.handleQualityInformation(fileName);
        return fileName;
    }

    private String handleQualityInformation(String fileName) {
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add("1080p");
        checkList.add("720p");
        checkList.add("WEB-DL");
        checkList.add("DD5.1");
        checkList.add("H.264");

        for (String checkString : checkList) {
            fileName = this.replaceCheckString(fileName, checkString);
        }
        return fileName;
    }

    private String handleSeriesHoster(String fileName) {
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add("AG");
        checkList.add("DIMENSION");
        checkList.add("BATV");
        for (String checkString : checkList) {
            fileName = this.replaceCheckString(fileName, checkString);
        }
        return fileName;
    }

    private String handleSeriesRenaming(String fileName) {
        fileName = this.handleQualityInformation(fileName);
        fileName = this.handleSeriesHoster(fileName);
        fileName = this.replaceDots(fileName);
        fileName = this.addSeparators(fileName);
        return fileName;
    }

    public ArrayList<File> renameFiles(ArrayList<File> fileList, MediaType mediaType) {
        ArrayList<File> renamedFiles = new ArrayList<>();
        for (final File file : fileList) {
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            String extention = file.getName().substring(file.getName().lastIndexOf("."));

            if (mediaType == MediaType.Anime) {
                fileName = this.handleAnimeRenaming(fileName);
            }
            else if (mediaType == MediaType.Series) {
                fileName = this.handleSeriesRenaming(fileName);
            }
            else if (mediaType == MediaType.Movie) {
                fileName = this.handleMovieRenaming(fileName);
            }
            fileName = fileName.trim();
            String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            File newFile = new File(filePath + "\\" + fileName + extention);
            if (file.renameTo(newFile)) {
                renamedFiles.add(newFile);
            }
        }
        return renamedFiles;
    }

    private String replaceCheckString(String fileName, String checkString) {
        if (fileName.contains("[" + checkString + "]")) {
            fileName = fileName.replace("[" + checkString + "]", "");
        }
        if (fileName.contains("-" + checkString)) {
            fileName = fileName.replace("-" + checkString, "");
        }
        else if (fileName.contains(" " + checkString)) {
            fileName = fileName.replace(" " + checkString, "");
        }
        else if (fileName.contains(checkString)) {
            fileName = fileName.replace(checkString, "");
        }
        return fileName;
    }

    private String replaceDots(String fileName) {
        fileName = fileName.replaceAll("\\.", " ");
        return fileName;
    }
}
