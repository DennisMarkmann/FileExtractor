package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

import markmann.dennis.fileExtractor.mediaObjects.MediaType;

public class TypeSettings implements Settings {

    MediaType type = MediaType.Anime;
    String extractionPath = "M:\\Processing\\Completed\\Anime";
    String completionPath = "M:\\MyAnime";
    boolean useSeriesFolder = false;
    boolean useSeasonFolder = false;
    boolean useCurrentlyWatchingCheck = true;
    ArrayList<ExceptionPath> exceptions = new ArrayList<>();

    public void addException(ExceptionPath newException) {
        for (ExceptionPath e : this.exceptions) {
            if (e.getName().equals(newException.getName())) {
                return;
            }
        }
        this.exceptions.add(newException);
    }

    public void clearExceptions() {
        this.exceptions = new ArrayList<>();
    }

    public String getCompletionPath() {
        return this.completionPath;
    }

    public ArrayList<ExceptionPath> getExceptions() {
        return this.exceptions;
    }

    public String getExtractionPath() {
        return this.extractionPath;
    }

    public MediaType getType() {
        return this.type;
    }

    public void setCompletionPath(String path) {
        this.completionPath = path;
    }

    public void setExtractionPath(String extractionPath) {
        this.extractionPath = extractionPath;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public void setUseCurrentlyWatchingCheck(boolean useCurrentlyWatchingCheck) {
        this.useCurrentlyWatchingCheck = useCurrentlyWatchingCheck;
    }

    public void setUseSeasonFolder(boolean useSeasonFolder) {
        this.useSeasonFolder = useSeasonFolder;
    }

    public void setUseSeriesFolder(boolean useSeriesFolder) {
        this.useSeriesFolder = useSeriesFolder;
    }

    public boolean useCurrentlyWatchingCheck() {
        return this.useCurrentlyWatchingCheck;
    }

    public boolean useSeasonFolder() {
        return this.useSeasonFolder;
    }

    public boolean useSeriesFolder() {
        return this.useSeriesFolder;
    }

}