package markmann.dennis.fileExtractor.objects;

public class Medium {

    // TODO change to interface
    protected String title = "";
    protected String extension = "";
    protected String originPath = "";
    private boolean keepOriginalName = false;

    public String getCompletePath() {
        return this.originPath + "\\" + this.getCompleteTitle();
    }

    public String getCompleteTitle() {
        return this.title;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getOriginPath() {
        return this.originPath;
    }

    public String getTitle() {
        return this.title;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isKeepOriginalName() {
        return keepOriginalName;
    }

    public void setKeepOriginalName(boolean keepOriginalName) {
        this.keepOriginalName = keepOriginalName;
    }
}
