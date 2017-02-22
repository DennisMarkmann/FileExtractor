package markmann.dennis.fileExtractor.mediaObjects;

public class Medium {

    protected String title = "";
    protected String extension = "";
    private String originPath = "";
    private boolean keepOriginalName = false;

    public String getCompletePath() {
        return this.originPath + "\\" + this.getCompleteTitle();
    }

    public String getCompleteTitle() {
        if (this.isKeepOriginalName()) {
            return this.getCompleteTitleNoExt();
        }
        else {
            return this.getCompleteTitleNoExt() + "." + this.extension;
        }
    }

    public String getCompleteTitleNoExt() {
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

    public boolean isKeepOriginalName() {
        return this.keepOriginalName;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setKeepOriginalName(boolean keepOriginalName) {
        this.keepOriginalName = keepOriginalName;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
