package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.util.ArrayList;

import markmann.dennis.fileExtractor.objects.MediaType;

public class SettingHandler {

    private static GeneralSettings generalSettings = new GeneralSettings();
    private static ArrayList<TypeSettings> settingList = new ArrayList<>();
    private static File folder = new File("./Settings/");

    private static TypeSettings createAnimeSettings() {
        TypeSettings settings = new TypeSettings();
        settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
        settings.addException(new ExceptionPath("Monster Hunter Stories Ride On", "\\Other\\Monster Hunter Stories Ride On"));
        settings.addException(new ExceptionPath("Nanbaka", "\\Other\\Nanbaka"));
        return settings;
    }

    public static void createDefaultSettings() {
        generalSettings = createGeneralSettings();
        settingList.add(SettingHandler.createAnimeSettings());
        settingList.add(createSeriesSettings());
    }

    private static GeneralSettings createGeneralSettings() {
        GeneralSettings generalSettings = new GeneralSettings();
        return generalSettings;
    }

    private static TypeSettings createSeriesSettings() {
        TypeSettings settings = new TypeSettings();
        settings = new TypeSettings();
        settings.setName("Series");
        settings.setType(MediaType.Series);
        settings.setExtractionPath("M:\\Processing\\Completed\\Series");
        settings.setCompletionPath("M:\\Series");
        settings.setUseCurrentlyWatchingCheck(true);
        return settings;
    }

    public static GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    private static TypeSettings getMatchingTypeSettings(String name) {
        for (TypeSettings typeSettings : settingList) {
            if ((typeSettings.getType().toString() + ".xml").equals(name)) {
                return typeSettings;
            }
        }
        TypeSettings typeSettings = new TypeSettings();
        settingList.add(typeSettings);
        return typeSettings;
    }

    public static ArrayList<TypeSettings> getTypeSettings() {
        return SettingHandler.settingList;
    }

    public static void readSettingsFromXML(boolean initial) {
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String name = fileEntry.getName();
                if (name.equals("General.xml")) {
                    new XMLFileReader().readSettingsXML(name, generalSettings, initial);
                }
                else if (name.equals("Anime.xml") || name.equals("Series.xml")) {
                    TypeSettings typeSettings = getMatchingTypeSettings(name);
                    new XMLFileReader().readSettingsXML(name, typeSettings, initial);
                }

            }
        }
    }
}
