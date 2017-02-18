package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

import markmann.dennis.fileExtractor.objects.MediaType;

public class TypeSettings implements Settings {

    String name;
    MediaType type;
    String extractionPath;
    String completionPath;
    boolean useSeriesFolder;
    boolean useSeasonFolder;
    boolean useCurrentlyWatchingCheck;
    ArrayList<ExceptionPath> exceptions = new ArrayList<>();

    public void addException(ExceptionPath exception) {
        this.exceptions.add(exception);
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

    public String getName() {
        return this.name;
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

    public void setName(String type) {
        this.name = type;
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