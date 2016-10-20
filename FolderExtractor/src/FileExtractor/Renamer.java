package FileExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.MediaType;

class Renamer {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");

    private String handleAnimeRenaming(String fileName) {
        final Pattern pattern = Pattern.compile("(?i)(\\[.*\\])(.*)(S\\d*)?( *- (OVA|\\d*))(.*)");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String name = m.group(2).trim();
            String seasonNumber = m.group(3);
            String episodeNumber = m.group(4).trim();

            fileName = name + (seasonNumber != null ? " " + seasonNumber.toUpperCase() : "") + " " + episodeNumber;
        }
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
        checkList.add("H264");
        checkList.add("bluray");
        checkList.add("x264");
        checkList.add("X264");
        checkList.add("HDTV");
        for (String checkString : checkList) {
            fileName = this.replaceCheckString(fileName, checkString);
        }
        return fileName;
    }

    private String handleSeriesRenaming(String fileName) {
        fileName = this.handleQualityInformation(fileName);
        fileName = this.replaceDots(fileName);
        final Pattern pattern = Pattern.compile("(?i)(.*)(S\\d*E\\d*)(.*)(-.*)");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String name = m.group(1).trim();
            String episodeNumber = m.group(2).toUpperCase();
            String episodeTitle = m.group(3).trim();
            // TODO bugfix: keine "-" wenn schon vorhanden z.B. bereits verarbeitete Serien
            fileName = name + " - " + episodeNumber + (episodeTitle.length() > 0 ? " - " + episodeTitle : "");
        }
        return fileName;
    }

    ArrayList<File> renameFiles(ArrayList<File> fileList, MediaType mediaType) {
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
                LOGGER.info("Renaming '" + file.getName() + "' to '" + newFile.getName() + "'.");
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
