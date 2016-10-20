package markmann.dennis.fileExtractor.settings;

public enum MediaType {

    Anime {

        @Override
        public String toString() {
            return "Anime";
        }
    },

    Series {

        @Override
        public String toString() {
            return "Series";
        }
    },

    Movie {

        @Override
        public String toString() {
            return "Movie";
        }
    }
}
