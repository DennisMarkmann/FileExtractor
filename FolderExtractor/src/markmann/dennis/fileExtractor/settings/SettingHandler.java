package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

import markmann.dennis.fileExtractor.objects.MediaType;

public class SettingHandler {

    private static GeneralSettings generalSettings = new GeneralSettings();
    private static ArrayList<TypeSettings> settingList = new ArrayList<>();

    private static TypeSettings createAnimeSettings() {
        TypeSettings settings = new TypeSettings();
        settings.setName("Anime");
        settings.setType(MediaType.Anime);
        settings.setExtractionPath("M:\\Processing\\Completed\\Anime");
        settings.setCompletionPath("M:\\MyAnime");
        settings.setUseCurrentlyWatchingCheck(true);
        settings.addException(new ExceptionPath("Naruto Shippuuden", "\\Other\\Naruto"));
        settings.addException(new ExceptionPath("Monster Hunter Stories Ride On", "\\Other\\Monster Hunter Stories Ride On"));
        settings.addException(new ExceptionPath("Nanbaka", "\\Other\\Nanbaka"));
        return settings;
    }

    public static void createDefaultSettings() {
        // generalSettings = createGeneralSettings();
        settingList.add(SettingHandler.createAnimeSettings());
        settingList.add(createSeriesSettings());
    }

    // private static GeneralSettings createGeneralSettings() {
    // GeneralSettings generalSettings = new GeneralSettings();
    // generalSettings.setTimerInterval(60);
    // generalSettings.setUseTimer(true);
    // generalSettings.setUseRenaming(true);
    // generalSettings.setUseFileMoving(true);
    // generalSettings.setUseCleanup(true);
    // generalSettings.setUseExtendedLogging(false);
    // generalSettings.setRemoveCorruptFiles(true);
    // generalSettings.setUseSystemTray(true);
    // return generalSettings;
    // }

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

    public static ArrayList<TypeSettings> getTypeSettings() {
        return SettingHandler.settingList;
    }

    public static void readSettingsFromXML() {
        generalSettings = new XMLFileReader().readSettingsXML("General.xml", generalSettings);
    }
}
