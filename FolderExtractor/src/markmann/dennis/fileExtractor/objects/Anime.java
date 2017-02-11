package markmann.dennis.fileExtractor.objects;

public class Anime extends Medium {

    protected String episode = "";

    @Override
    public String getCompleteTitle() {
        if (this.isKeepOriginalName()) {
            return this.title;
        }
        else {
            return this.title + " - " + this.episode + "." + this.extension;
        }
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}