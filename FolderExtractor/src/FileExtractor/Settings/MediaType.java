package FileExtractor.Settings;

public enum MediaType {

    Anime {

        @Override
        public String toString() {
            return "anime";
        }
    },

    Series {

        @Override
        public String toString() {
            return "series";
        }
    },

    Movie {

        @Override
        public String toString() {
            return "movie";
        }
    }
}
