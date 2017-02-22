package markmann.dennis.fileExtractor.mediaObjects;

public class Series extends Anime {

    private String season = "";

    @Override
    public String getCompleteTitleNoExt() {
        if (this.isKeepOriginalName()) {
            return this.title;
        }
        else {
            return this.title + " - " + "S" + this.season + "E" + this.episode;
        }
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}
