package FileExtractor.Settings;

import java.util.ArrayList;

public class Settings {

    private String type;
    private String extractionPath;
    private String completionPath;
    private ArrayList<ExceptionPath> exceptions = new ArrayList<>();
    private boolean seriesFolder;
    private boolean seasonFolder;

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

    public String getType() {
        return this.type;
    }

    public boolean isSeasonFolder() {
        return this.seasonFolder;
    }

    public boolean isSeriesFolder() {
        return this.seriesFolder;
    }

    public void setCompletionPath(String path) {
        this.completionPath = path;
    }

    public void setExtractionPath(String extractionPath) {
        this.extractionPath = extractionPath;
    }

    public void setSeasonFolder(boolean seasonFolder) {
        this.seasonFolder = seasonFolder;
    }

    public void setSeriesFolder(boolean seriesFolder) {
        this.seriesFolder = seriesFolder;
    }

    public void setType(String type) {
        this.type = type;
    }

}