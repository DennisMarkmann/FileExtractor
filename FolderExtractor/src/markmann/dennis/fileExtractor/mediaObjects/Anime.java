package markmann.dennis.fileExtractor.mediaObjects;

public class Anime extends Medium {

    protected String episode = "";

    @Override
    public String getCompleteTitleNoExt() {
        if (this.isKeepOriginalName()) {
            return this.title;
        }
        else {
            return this.title + " - " + this.episode;
        }
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
