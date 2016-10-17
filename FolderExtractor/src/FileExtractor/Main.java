package FileExtractor;

import java.util.ArrayList;

import FileExtractor.Settings.ExceptionPath;
import FileExtractor.Settings.Settings;

public class Main {

    public static void main(final String[] args) {

        boolean useGui = false;

        if (useGui) {
            MainFrame.getInstance();
        }
        else {
            ArrayList<Settings> settingList = new ArrayList<>();
            Settings settings = new Settings();
            settings.setType("Anime");
            settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
            settings.setCompletionPath("M:\\MyAnime");
            settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
            settingList.add(settings);

            settings = new Settings();
            settings.setType("Series");
            settings.setExtractionPath("M:\\Processing\\Completed\\Series");
            settings.setCompletionPath("M:\\Series");
            settings.addException(new ExceptionPath("Ash.vs.Evil.Dead.", "\\Later\\Ash vs Evil Dead"));
            settings.addException(new ExceptionPath("Once.Upon.a.Time.", "\\Later\\Once Upon A Time\\Season 6"));
            settingList.add(settings);

            // new FileWriteHelper().createXMLFiles(settingList);

            Controller controller = new Controller();
            for (Settings st : settingList) {
                controller.startProcess(st);
            }
        }
    }
}
