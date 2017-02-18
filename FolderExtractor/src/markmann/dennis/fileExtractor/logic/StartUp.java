package markmann.dennis.fileExtractor.logic;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;

public class StartUp {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    public static void main(final String[] args) {
        LOGGER.info("Application starting.");
        Controller.startApplication();
    }
}
