package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

import markmann.dennis.fileExtractor.objects.MediaType;

public class SettingHandler {

    private ArrayList<TypeSettings> settingList;
    private GeneralSettings generalSettings;

    private TypeSettings createAnimeSettings() {
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

    public void createDefaultSettings() {
        this.settingList = new ArrayList<>();
        this.setGeneralSettings(this.createGeneralSettings());
        this.settingList.add(this.createAnimeSettings());
        this.settingList.add(this.createSeriesSettings());
    }

    private GeneralSettings createGeneralSettings() {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setTimerInterval(60);
        generalSettings.setUseTimer(true);
        generalSettings.setUseRenaming(true);
        generalSettings.setUseFileMoving(true);
        generalSettings.setUseCleanup(true);
        generalSettings.setUseExtendedLogging(false);
        generalSettings.setRemoveCorruptFiles(true);
        generalSettings.setUseSystemTray(true);
        return generalSettings;
    }

    private TypeSettings createSeriesSettings() {
        TypeSettings settings = new TypeSettings();
        settings = new TypeSettings();
        settings.setName("Series");
        settings.setType(MediaType.Series);
        settings.setExtractionPath("M:\\Processing\\Completed\\Series");
        settings.setCompletionPath("M:\\Series");
        settings.setUseCurrentlyWatchingCheck(true);
        return settings;
    }

    public GeneralSettings getGeneralSettings() {
        return this.generalSettings;
    }

    public ArrayList<TypeSettings> getSettingList() {
        return this.settingList;
    }

    public void setGeneralSettings(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
    }

    public void setSettingList(ArrayList<TypeSettings> settingList) {
        this.settingList = settingList;
    }

}
