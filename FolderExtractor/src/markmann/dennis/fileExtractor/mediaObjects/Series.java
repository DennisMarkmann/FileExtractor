package markmann.dennis.fileExtractor.mediaObjects;

public class Series extends Medium {

    protected String episode = "";
    protected String season = "";

    @Override
    public String getCompleteTitleNoExt() {
        if (this.isKeepOriginalName()) {
            return this.title;
        }
        else {
            return this.title + " - " + "S" + this.season + "E" + this.episode;
        }
    }

    public String getEpisode() {
        return this.episode;
    }

    public String getSeason() {
        return this.season;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}
