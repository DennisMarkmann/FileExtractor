package FileExtractor;

import java.io.File;

class Settings {

    private File seriesExtractionPath = new File("M:\\Processing\\Completed\\Series");
    private File seriesPath = new File("M:\\Series");
    private File animeExtractionPath = new File("M:\\Processing\\Completed\\Anime");
    private File animePath = new File("M:\\MyAnime\\ToTest");

    protected File getAnimeExtractionPath() {
        return this.animeExtractionPath;
    }

    protected File getAnimePath() {
        return this.animePath;
    }

    public File getSeriesExtractionPath() {
        return this.seriesExtractionPath;
    }

    protected File getSeriesPath() {
        return this.seriesPath;
    }
}
