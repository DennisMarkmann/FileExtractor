package markmann.dennis.fileExtractor.objects;

public class Medium {

    protected String title = "";
    protected String extension = "";
    protected String originPath = "";

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
        if ((extension != null) && !extension.trim().isEmpty()) {
            this.extension = extension;
        }
        else {
            // TODO error
        }
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public void setTitle(String title) {
        if ((title != null) && !title.trim().isEmpty()) {
            this.title = title;
        }
        else {
            // TODO error
        }
    }
}
