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

import markmann.dennis.fileExtractor.logic.Controller;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.settings.Settings;
import markmann.dennis.fileExtractor.settings.TypeSettings;

public class SystemTrayMenu {

    private static TrayIcon trayIcon;

    public static void sendInfoPopup(String title, String text) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, text, MessageType.INFO);
        }
    }

    private void changeVisibility(MenuItem pauseItem, MenuItem resumeItem) {
        if (Controller.isTimerIsActive()) {
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
        }
        else {
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
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

        if (!SystemTray.isSupported()) {
            return;
        }
        final SystemTray tray = SystemTray.getSystemTray();
        final PopupMenu popup = new PopupMenu();

        Dimension trayIconSize = tray.getTrayIconSize();
        Image image = Toolkit.getDefaultToolkit().getImage("./Icons/TrayIcon.png");
        SystemTrayMenu.trayIcon = new TrayIcon(
                image.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH),
                "FileExtractor",
                popup);

        MenuItem scanItem = new MenuItem("Scan manually");
        MenuItem pauseItem = new MenuItem("Pause timer");
        MenuItem resumeItem = new MenuItem("Resume timer");
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

        exitItem.addActionListener(e -> {
            Controller.shutDownApplication();
        });

        logItem.addActionListener(e -> {
            Controller.openFile("./Logs/FileExtractor.log");
        });

        popup.add(scanItem);
        if (SettingHandler.getGeneralSettings().useTimer()) {
            popup.add(pauseItem);
            popup.add(resumeItem);
        }
        popup.add(logItem);
        popup.add(settingsMenu);
        popup.add(exitItem);

        this.changeVisibility(pauseItem, resumeItem);

        SystemTrayMenu.trayIcon.setPopupMenu(popup);

        try {
            tray.add(SystemTrayMenu.trayIcon);
        }
        catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}