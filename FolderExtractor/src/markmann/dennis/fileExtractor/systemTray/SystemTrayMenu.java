package markmann.dennis.fileExtractor.systemTray;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.logic.Controller;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.settings.Settings;
import markmann.dennis.fileExtractor.settings.TypeSettings;

public class SystemTrayMenu {

    private static TrayIcon trayIcon;

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    public static void sendInfoPopup(String title, String text) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, text, MessageType.INFO);
        }
    }

    private Image activeIcon;
    private Image inActiveIcon;
    private SystemTray tray;

    public SystemTrayMenu() {

        if (!SystemTray.isSupported()) {
            LOGGER.error("TrayIcon is not supported.");
            System.out.println("TrayIcon is not supported.");
            return;
        }
        this.activeIcon = Toolkit.getDefaultToolkit().getImage("./Icons/TrayIcon_Active.png");
        this.inActiveIcon = Toolkit.getDefaultToolkit().getImage("./Icons/TrayIcon_Inactive.png");

        this.tray = SystemTray.getSystemTray();
        Dimension trayIconSize = this.tray.getTrayIconSize();

        this.activeIcon = this.activeIcon.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);
        this.inActiveIcon = this.inActiveIcon.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);
    }

    private void changeVisibility(MenuItem pauseItem, MenuItem resumeItem) {
        if (Controller.isTimerIsActive()) {
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
            trayIcon.setImage(this.activeIcon);
        }
        else {
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
            trayIcon.setImage(this.inActiveIcon);
        }
    }

    private Menu createSettingsSubmenu() {
        Menu settingsMenu = new Menu("Settings");
        for (Settings settings : SettingHandler.getAllSettings()) {
            String name;
            if (settings instanceof GeneralSettings) {
                name = "General";
            }
            else {
                name = ((TypeSettings) settings).getType().toString();
            }
            MenuItem subMenuItem = new MenuItem(name);
            subMenuItem.addActionListener(e -> {
                Controller.openFile("./Settings/" + name + ".xml");
            });
            settingsMenu.add(subMenuItem);
        }
        return settingsMenu;
    }

    public void createSystemTrayEntry() {

        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(this.activeIcon, "FileExtractor", popup);

        MenuItem scanItem = new MenuItem("Scan manually");
        MenuItem pauseItem = new MenuItem("Pause timer");
        MenuItem resumeItem = new MenuItem("Resume timer");
        MenuItem historyItem = new MenuItem("History");
        MenuItem logItem = new MenuItem("Log");
        MenuItem exitItem = new MenuItem("Exit");
        Menu settingsMenu = this.createSettingsSubmenu();

        scanItem.addActionListener(e -> {
            Controller.initiateManualScan();
        });

        pauseItem.addActionListener(e -> {
            Controller.stopTimer();
            this.changeVisibility(pauseItem, resumeItem);
        });

        resumeItem.addActionListener(e -> {
            Controller.startTimer(false);
            this.changeVisibility(pauseItem, resumeItem);
        });

        historyItem.addActionListener(e -> {
            Controller.openFile("./Logs/History.txt");
        });

        logItem.addActionListener(e -> {
            Controller.openFile("./Logs/FileExtractor.log");
        });

        exitItem.addActionListener(e -> {
            Controller.shutDownApplication();
        });

        popup.add(scanItem);
        if (SettingHandler.getGeneralSettings().useTimer()) {
            popup.add(pauseItem);
            popup.add(resumeItem);
        }
        popup.add(historyItem);
        popup.add(settingsMenu);
        popup.add(logItem);
        popup.add(exitItem);

        this.changeVisibility(pauseItem, resumeItem);

        SystemTrayMenu.trayIcon.setPopupMenu(popup);

        try {
            this.tray.add(SystemTrayMenu.trayIcon);
        }
        catch (AWTException e) {
            LOGGER.error("TrayIcon could not be added.", e);
            System.out.println("TrayIcon could not be added.");
        }
    }
}