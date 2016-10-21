package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.MediaType;

class FileRenamer {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

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
        return fileName;
    }

    private String handleSeriesRenaming(String fileName) {
        fileName = this.replaceDots(fileName);
        final Pattern pattern = Pattern.compile("(?i)(.*)(S\\d*E\\d*)(.*)");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String name = m.group(1).trim();
            String episodeNumber = m.group(2).toUpperCase();
            fileName = name + (episodeNumber != null ? " - " + episodeNumber : "");
        }
        return fileName;
    }

    ArrayList<File> renameFiles(ArrayList<File> fileList, MediaType mediaType) {
        ArrayList<File> renamedFiles = new ArrayList<>();
        for (final File file : fileList) {
            String originalFileName = file.getName();
            String newFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            String extention = file.getName().substring(file.getName().lastIndexOf("."));

            if (mediaType == MediaType.Anime) {
                newFileName = this.handleAnimeRenaming(newFileName);
            }
            else if (mediaType == MediaType.Series) {
                newFileName = this.handleSeriesRenaming(newFileName);
            }
            else if (mediaType == MediaType.Movie) {
                newFileName = this.handleMovieRenaming(newFileName);
            }
            String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            newFileName = newFileName.trim() + extention;
            if (newFileName.equals(originalFileName)) {
                renamedFiles.add(file);
            }
            else {
                File newFile = new File(filePath + "\\" + newFileName);
                if (file.renameTo(newFile)) {
                    renamedFiles.add(newFile);
                    LOGGER.info("Renaming '" + file.getName() + "' to '" + newFile.getName() + "'.");
                }
            }
        }
        return renamedFiles;
    }

    private String replaceDots(String fileName) {
        fileName = fileName.replaceAll("\\.", " ");
        return fileName;
    }
}
