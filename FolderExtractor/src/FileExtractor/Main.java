package FileExtractor;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import FileExtractor.Logging.LogHandler;
import FileExtractor.Settings.ExceptionPath;
import FileExtractor.Settings.MediaType;
import FileExtractor.Settings.TypeSettings;

public class Main {

    private static final Logger LOGGER = LogHandler.getLogger("./logs/FileExtractor.log");

    public static void main(final String[] args) {

        LOGGER.info("Application starting.");

        boolean useGui = false;
        boolean useTimer = false;

        if (useGui) {
            MainFrame.getInstance();
        }
        else {
            ArrayList<TypeSettings> settingList = new ArrayList<>();
            TypeSettings settings = new TypeSettings();
            settings.setName("Anime");
            settings.setType(MediaType.Anime);
            settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
            settings.setCompletionPath("M:\\MyAnime");
            settings.addException(new ExceptionPath("naruto shippuuden", "\\Other\\Naruto"));
            settingList.add(settings);

            settings = new TypeSettings();
            settings.setName("Series");
            settings.setType(MediaType.Series);
            settings.setExtractionPath("M:\\Processing\\Completed\\Series");
            settings.setCompletionPath("M:\\Series");
            settings.addException(new ExceptionPath("Ash vs Evil Dead", "\\Later\\Ash vs Evil Dead"));
            settings.addException(new ExceptionPath("Once Upon A Time", "\\Later\\Once Upon A Time\\Season 6"));
            settingList.add(settings);

            // new FileWriteHelper().createXMLFiles(settingList);
            final Controller controller = new Controller();
            if (useTimer) {
                int intervalInMinutes = 30;
                Timer timer = new Timer();
                for (final TypeSettings st : settingList) {
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            controller.startProcess(st);
                        }

                    }, 1000, intervalInMinutes * 60000);
                }
            }
            else {
                for (TypeSettings st : settingList) {
                    controller.startProcess(st);
                }
            }
            LOGGER.info("-----------------------------");
        }
    }
}
