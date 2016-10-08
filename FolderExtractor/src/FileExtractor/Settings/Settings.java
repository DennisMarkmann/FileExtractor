package FileExtractor.Settings;

public class Settings {

    private String Type;
    private String extractionPath;
    private String path;

    public String getExtractionPath() {
        return this.extractionPath;
    }

    public String getPath() {
        return this.path;
    }

    public String getType() {
        return this.Type;
    }

    public void setExtractionPath(String extractionPath) {
        this.extractionPath = extractionPath;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.Type = type;
    }

}