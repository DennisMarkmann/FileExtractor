package markmann.dennis.fileExtractor.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

public class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private static Timer timer = null;
    private static boolean timerIsActive = false;

    public static void initiateManualScan() {
        SettingHandler.readSettingsFromXML(false);
        new FileScanner().startScan(true);
    }

    public static boolean isTimerIsActive() {
        return timerIsActive;
    }

    public static void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(fileName));
        }
        catch (IOException e) {
            LOGGER.error("File can't be opened '" + fileName + "'.", e);
            e.printStackTrace();
        }
    }

    public static void shutDownApplication() {
        LOGGER.info("Application stopped.");
        System.exit(0);
    }

    static void startApplication() {
        boolean overwriteExistingSettings = false;
        if (overwriteExistingSettings) {
            SettingHandler.createDefaultSettings();
            SettingHandler.writeSettingsToXML();
        }
        else {
            SettingHandler.readSettingsFromXML(true);
            SettingHandler.writeSettingsToXML();
        }

        if (SettingHandler.getGeneralSettings().useTimer()) {
            startTimer(true);
        }
        if (SettingHandler.getGeneralSettings().useSystemTray()) {
            new SystemTrayMenu().createSystemTrayEntry();
        }
    }

    public static void startTimer(boolean initialStart) {
        int timerInterval = SettingHandler.getGeneralSettings().getTimerInterval();
        if (initialStart) {
            LOGGER.info("Timer activated. Interval: '" + timerInterval + "' minutes.");
        }
        else {
            LOGGER.info("Timer resumed.");
        }

        timer = new Timer();
        timerIsActive = true;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                SettingHandler.readSettingsFromXML(false);
                new FileScanner().startScan(false);
            }

        }, 1000, timerInterval * 60000);
    }

    public static void stopTimer() {
        LOGGER.info("Timer stopped.");
        timer.cancel();
        timerIsActive = false;
    }
}
