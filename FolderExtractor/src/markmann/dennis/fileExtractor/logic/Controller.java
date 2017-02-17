package markmann.dennis.fileExtractor.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.FileWriteHelper;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.settings.TypeSettings;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

public class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private Timer timer = null;
    private boolean timerIsActive = false;

    public void initiateManualExtraction(GeneralSettings generalSettings, ArrayList<TypeSettings> typeSettings) {
        new FileExtractor(generalSettings).startExtraction(typeSettings, true);
    }

    public boolean isTimerIsActive() {
        return this.timerIsActive;
    }

    public void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(fileName));
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void shutDownApplication() {
        LOGGER.info("Application stopped.");
        System.exit(0);
    }

    void startApplication() {
        SettingHandler settingHandler = new SettingHandler();
        settingHandler.createDefaultSettings();
        GeneralSettings generalSettings = settingHandler.getGeneralSettings();
        ArrayList<TypeSettings> typeSettings = settingHandler.getSettingList();

        new FileWriteHelper().createXMLFiles(typeSettings, generalSettings);

        if (generalSettings.useTimer()) {
            this.startTimer(true, generalSettings, typeSettings);
        }
        if (generalSettings.useSystemTray()) {
            new SystemTrayMenu().createSystemTrayEntry(this, generalSettings, typeSettings);
        }
    }

    public void startTimer(boolean initialStart, GeneralSettings generalSettings, ArrayList<TypeSettings> typeSettings) {
        if (initialStart) {
            LOGGER.info("Timer activated. Interval: '" + generalSettings.getTimerInterval() + "' minutes.");
        }
        else {
            LOGGER.info("Timer resumed.");
        }

        this.timer = new Timer();
        this.timerIsActive = true;
        this.timer.schedule(new TimerTask() {

            @Override
            public void run() {
                new FileExtractor(generalSettings).startExtraction(typeSettings, false);
            }

        }, 1000, generalSettings.getTimerInterval() * 60000);
    }

    public void stopTimer() {
        LOGGER.info("Timer stopped.");
        this.timer.cancel();
        this.timerIsActive = false;
    }
}
