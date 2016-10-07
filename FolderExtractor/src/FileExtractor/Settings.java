package FileExtractor;

import java.io.File;

class Settings {

    private File extractionPath = new File("M:\\Processing\\Completed\\Series");
    private File seriesPath = new File("M:\\Series");

    public File getExtractionPath() {
        return this.extractionPath;
    }

    protected File getSeriesPath() {
        return this.seriesPath;
    }
}
