package FileExtractor;

import java.util.ArrayList;

import FileExtractor.Settings.FileWriteHelper;
import FileExtractor.Settings.Settings;

public class Main {

    public static void main(final String[] args) {

        boolean useGui = false;

        if (useGui) {
            MainFrame.getInstance();
        }
        else {
            // final SettingsOld settings = new SettingsOld();
            ArrayList<Settings> settingList = new ArrayList<>();
            Settings settings = new Settings();
            settings.setType("Anime");
            settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
            settings.setPath("M:\\MyAnime\\ToTest");
            settingList.add(settings);

            settings = new Settings();
            settings.setType("Series");
            settings.setExtractionPath("M:\\Processing\\Completed\\Series");
            settings.setPath("M:\\Series");
            settingList.add(settings);

            new FileWriteHelper().createXMLFiles(settingList);

            Controller controller = new Controller();
            for (Settings st : settingList) {
                controller.startProcess(st.getExtractionPath(), st.getPath());
            }
        }
    }
}
