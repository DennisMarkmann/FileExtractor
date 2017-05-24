package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
import markmann.dennis.fileExtractor.mediaObjects.MediaType;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.mediaObjects.Series;
import markmann.dennis.fileExtractor.settings.SettingHandler;

public class FileRenamer {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    public Anime handleAnimeRenaming(String fileName, Anime anime) {
        final Pattern pattern = Pattern
                .compile("(\\[.{1,}])?([^<]*)\\ - (.{2,6})(\\[.{4,5}])?(\\[.{2,30}])?(\\(.{2,30}\\))?\\.(.{3})");
        // TODO: use sth like this? ("(\\[.{1,}])?([^<]*)\\ -
        // (.{2,6})(\\[.{4,5}])?([v{1,1])(\\[.{2,30}])?(\\(.{2,30}\\))?\\.(.{3})");
        // TODO: remember to increment the following group numbers after the change

        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String title = m.group(2).trim();
            String episode = m.group(3).trim();
            String extension = m.group(7).trim();
            // TODO: add this group for version numbers
            // m.group(4).trim();
            String versionNumber = "";

            anime.setTitle(title);
            this.setEpisode(anime, episode, versionNumber);
            anime.setExtension(extension);
            return anime;
        }
        return null;
    }

    private Series handleSeriesRenaming(String fileName, Series series) {
        final Pattern pattern = Pattern.compile("([^<]*)(\\ - |\\.{1})(?i)S(.{2,3})(?i)E(.{2,3})(\\.[^<]*)?\\.(.{3})");
        Matcher m = pattern.matcher(fileName);
        if (m.matches()) {
            String title = this.replaceDots(m.group(1).trim());
            String season = m.group(3).trim();
            String episode = m.group(4).trim();
            String extension = m.group(6).trim();

            series.setTitle(title);
            series.setSeason(season);
            series.setEpisode(episode);
            series.setExtension(extension);
            return series;
        }
        return null;
    }

    private String replaceDots(String fileName) {
        fileName = fileName.replaceAll("\\.", " ");
        return fileName;
    }

    ArrayList<Medium> scanFiles(ArrayList<File> fileList, MediaType mediaType) {

        ArrayList<Medium> mediaList = new ArrayList<>();
        for (final File file : fileList) {
            String originalFileName = file.getName();
            Medium medium = null;
            boolean useRenaming = SettingHandler.getGeneralSettings().useRenaming();
            if (mediaType == MediaType.Anime) {
                if (useRenaming) {
                    medium = this.handleAnimeRenaming(originalFileName, new Anime());
                } else {
                    medium = new Anime();
                }
            }

            if (mediaType == MediaType.Series) {
                if (useRenaming) {
                    medium = this.handleSeriesRenaming(originalFileName, new Series());
                } else {
                    medium = new Series();
                }
            }
            if (!useRenaming) {
                medium.setKeepOriginalName(true);
                medium.setTitle(originalFileName);
            }

            if ((medium == null) && useRenaming) {
                if (SettingHandler.getGeneralSettings().removeCorruptFiles()) {
                    LOGGER.info("Renaming of file:'" + originalFileName + "' not successful. File deleted.");
                    file.delete();
                } else {
                    LOGGER.info("Renaming of file:'" + originalFileName + "' not successful. Please try to fix it manually.");
                }
                continue;
            }

            String originPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            medium.setOriginPath(originPath);

            if (!(originalFileName.equals(medium.getCompleteTitle())) && useRenaming) {
                File newFile = new File(medium.getOriginPath() + "\\" + medium.getCompleteTitle());
                if (file.renameTo(newFile)) {
                    LOGGER.info("Renaming '" + originalFileName + "' to '" + medium.getCompleteTitle() + "'.");
                }
            }
            mediaList.add(medium);
        }
        return mediaList;
    }

    public void setEpisode(Anime anime, String episode, String versionNumber) {
        if (!SettingHandler.getGeneralSettings().removeVersionNumbers()) {
            episode = episode + versionNumber;
        }
        anime.setEpisode(episode);
    }
}
