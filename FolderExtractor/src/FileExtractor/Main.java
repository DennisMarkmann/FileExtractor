package FileExtractor;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.ExceptionPath;
import FileExtractor.Settings.FileWriteHelper;
import FileExtractor.Settings.GeneralSettings;
import FileExtractor.Settings.MediaType;
import FileExtractor.Settings.TypeSettings;

public class Main {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");

    public static void main(final String[] args) {

        LOGGER.info("Application starting.");

        boolean useTimer = true;
        ArrayList<TypeSettings> settingList = new ArrayList<>();
        TypeSettings settings = new TypeSettings();
        settings.setName("Anime");
        settings.setType(MediaType.Anime);
        settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
        settings.setCompletionPath("M:\\MyAnime");
        settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
        settings.addException(new ExceptionPath("Bubuki Buranki", "\\Bubuki Buranki"));
        settingList.add(settings);

        settings = new TypeSettings();
        settings.setName("Series");
        settings.setType(MediaType.Series);
        settings.setExtractionPath("M:\\Processing\\Completed\\Series");
        settings.setCompletionPath("M:\\Series");
        settings.addException(new ExceptionPath("Ash vs Evil Dead", "\\Later\\Ash vs Evil Dead"));
        settings.addException(new ExceptionPath("Once Upon A Time", "\\Later\\Once Upon A Time\\Season 6"));
        settingList.add(settings);

        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setTimerInterval(15);

        new FileWriteHelper().createXMLFiles(settingList);
        final Controller controller = new Controller();
        if (useTimer) {
            LOGGER.info("Timer activated. Interval: '" + generalSettings.getTimerInterval() + "' minutes.");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    for (final TypeSettings st : settingList) {
                        controller.startProcess(st);
                    }
                    LOGGER.info("-----------------------------------");
                }

            }, 1000, generalSettings.getTimerInterval() * 60000);
        }
        else {
            for (TypeSettings st : settingList) {
                controller.startProcess(st);
            }
            LOGGER.info("-----------------------------------");
        }
    }
}
