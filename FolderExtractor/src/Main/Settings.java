package Main;

import java.io.File;

class Settings {

    private File seriesPath = new File("M:\\Processing\\Completed\\Series");
    private File moviePath = new File("M:\\Processing\\Completed\\Movies");

    protected File getMoviePath() {
        return this.moviePath;
    }

    protected File getSeriesPath() {
        return this.seriesPath;
    }

}
