package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.objects.Anime;
import markmann.dennis.fileExtractor.objects.Medium;
import markmann.dennis.fileExtractor.objects.Series;
import markmann.dennis.fileExtractor.settings.MediaType;

class FileRenamer {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private Anime handleAnimeRenaming(String fileName) {
        final Pattern pattern = Pattern.compile("(\\[.{1,}])?([^<]*)\\ - (.{2,3})(\\[.{4,5}])?\\.(.{3})");
        Matcher m = pattern.matcher(fileName);
        Anime anime = new Anime();
        // TODO: Exception if not successful rather than returning an empty anime
        if (m.matches()) {
            String title = m.group(2).trim();
            String episode = m.group(3).trim();
            String extension = m.group(5).trim();
            anime.setTitle(title);
            anime.setEpisode(episode);
            anime.setExtension(extension);
        }
        return anime;
    }

    private Series handleSeriesRenaming(String fileName) {
        final Pattern pattern = Pattern.compile("([^<]*)(\\ - |\\.{1})S(.{2,3})E(.{2,3})(\\.[^<]*)?\\.(.{3})");
        Matcher m = pattern.matcher(fileName);
        Series series = new Series();
        // TODO: Exception if not successful rather than returning an empty series
        if (m.matches()) {
            String title = this.replaceDots(m.group(1).trim());
            String season = m.group(3).trim();
            String episode = m.group(4).trim();
            String extension = m.group(6).trim();
            series.setTitle(title);
            series.setSeason(season);
            series.setEpisode(episode);
            series.setExtension(extension);
        }
        return series;
    }

    ArrayList<File> renameFiles(ArrayList<File> fileList, MediaType mediaType) {
        ArrayList<File> renamedFiles = new ArrayList<>();
        for (final File file : fileList) {
            String originalFileName = file.getName();
            Medium medium = null;
            if (mediaType == MediaType.Anime) {
                medium = this.handleAnimeRenaming(originalFileName);
            }
            else if (mediaType == MediaType.Series) {
                medium = this.handleSeriesRenaming(originalFileName);
            }

            if (medium != null) {
                String originPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
                medium.setOriginPath(originPath);
            }

            if (originalFileName.equals(medium.getCompleteTitle())) {
                renamedFiles.add(file);
            }
            else {
                File newFile = new File(medium.getOriginPath() + "\\" + medium.getCompleteTitle());
                if (file.renameTo(newFile)) {
                    renamedFiles.add(newFile);
                    LOGGER.info("Renaming '" + originalFileName + "' to '" + medium.getCompleteTitle() + "'.");
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
