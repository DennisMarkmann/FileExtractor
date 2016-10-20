package FileExtractor;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;

public class StartUp {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    public static void main(final String[] args) {
        LOGGER.info("Application starting.");
        new Controller().process();
    }
}
