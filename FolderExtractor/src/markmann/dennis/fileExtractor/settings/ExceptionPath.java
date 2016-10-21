package markmann.dennis.fileExtractor.settings;

public class ExceptionPath {

    private String name;

    private String path;

    public ExceptionPath(String name, String path) {
        this.setName(name);
        this.setPath(path);
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
