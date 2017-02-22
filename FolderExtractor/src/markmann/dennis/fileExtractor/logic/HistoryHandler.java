package markmann.dennis.fileExtractor.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
import markmann.dennis.fileExtractor.mediaObjects.Medium;

public class HistoryHandler {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private String historyPath = "./Logs/History.txt";

    void addToHistory(ArrayList<Medium> mediaList) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.historyPath, true)))) {
            Date datum = new Date();
            String dateStringShort = new SimpleDateFormat("dd.MM.yyyy").format(datum);
            String dateStringComplete = new SimpleDateFormat("HH:mm:ss").format(datum);
            StringBuilder sb = new StringBuilder();
            sb = this.handleDayChange(dateStringShort, sb);
            for (Medium medium : mediaList) {
                sb.append(dateStringComplete);
                sb.append("  (");
                sb.append(medium.getClass().getSimpleName());
                sb.append(")  ");
                if (medium instanceof Anime) {
                    sb.append(" ");
                }
                sb.append(medium.getCompleteTitleNoExt());
                sb.append("\n");
            }
            out.print(sb.toString());
        }
        catch (IOException e) {
            LOGGER.error("Error while trying to access '" + this.historyPath + "'.", e);
            e.printStackTrace();
        }
    }

    private String getLastExtractionDate() {
        String lastExtractionDate = "";
        try {
            String history = Files.lines(Paths.get(this.historyPath)).collect(Collectors.joining("\n"));
            if (!history.isEmpty()) {
                try {
                    int index = history.lastIndexOf(") ***");
                    lastExtractionDate = history.substring(index - 10, index);
                }
                catch (StringIndexOutOfBoundsException e) {
                    // nothing to do here, returning the empty String is fine.
                }
            }
        }
        catch (IOException e) {
            LOGGER.error("Error while trying to access '" + this.historyPath + "'.", e);
            e.printStackTrace();
        }
        return lastExtractionDate;
    }

    private StringBuilder handleDayChange(String newDateString, StringBuilder sb) {
        if (!this.getLastExtractionDate().equals(newDateString)) {
            sb.append("----------------------");
            sb.append("\n");
            sb.append("*** ");
            sb.append(Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
            sb.append(" (");
            sb.append(newDateString);
            sb.append(") ***");
            sb.append("\n");
        }
        return sb;
    }
}
