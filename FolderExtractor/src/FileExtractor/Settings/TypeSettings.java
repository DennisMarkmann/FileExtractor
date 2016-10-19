package FileExtractor.Settings;

import java.util.ArrayList;

public class TypeSettings {

    private String name;
    private MediaType type;
    private String extractionPath;
    private String completionPath;
    private boolean seriesFolder;
    private boolean seasonFolder;
    private ArrayList<ExceptionPath> exceptions = new ArrayList<>();

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

    public void setName(String type) {
        this.name = type;
    }

    public void setSeasonFolder(boolean seasonFolder) {
        this.seasonFolder = seasonFolder;
    }

    public void setSeriesFolder(boolean seriesFolder) {
        this.seriesFolder = seriesFolder;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

}