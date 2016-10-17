package FileExtractor.Settings;

import java.util.ArrayList;

public class Settings {

    private String Type;
    private String extractionPath;
    private String completionPath;
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

    public String getType() {
        return this.Type;
    }

    public void setCompletionPath(String path) {
        this.completionPath = path;
    }

    public void setExtractionPath(String extractionPath) {
        this.extractionPath = extractionPath;
    }

    public void setType(String type) {
        this.Type = type;
    }

}