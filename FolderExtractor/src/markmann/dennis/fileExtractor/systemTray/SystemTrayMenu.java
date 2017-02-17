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
import java.util.ArrayList;

import markmann.dennis.fileExtractor.logic.Controller;
import markmann.dennis.fileExtractor.settings.GeneralSettings;
import markmann.dennis.fileExtractor.settings.TypeSettings;

public class SystemTrayMenu {

    private void changeVisibility(Controller controller, MenuItem pauseItem, MenuItem resumeItem) {
        if (controller.isTimerIsActive()) {
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
        }
        else {
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
        }
    }

    private Menu createSettingsSubmenu(Controller controller, ArrayList<TypeSettings> typeSettings) {

        Menu settingsMenu = new Menu("Settings");
        MenuItem generalSettings = new MenuItem("General");
        generalSettings.addActionListener(e -> {
            controller.openFile("./Settings/General.xml");
        });
        settingsMenu.add(generalSettings);

        for (TypeSettings settings : typeSettings) {
            MenuItem subMenuItem = new MenuItem(settings.getName());
            subMenuItem.addActionListener(e -> {
                controller.openFile("./Settings/" + settings.getName() + ".xml");
            });
            settingsMenu.add(subMenuItem);
        }
        return settingsMenu;
    }

    public void createSystemTrayEntry(
            Controller controller,
            GeneralSettings generalSettings,
            ArrayList<TypeSettings> typeSettings) {

        if (!SystemTray.isSupported()) {
            return;
        }
        final SystemTray tray = SystemTray.getSystemTray();
        final PopupMenu popup = new PopupMenu();

        Dimension trayIconSize = tray.getTrayIconSize();
        Image image = Toolkit.getDefaultToolkit().getImage("./Icons/TrayIcon.png");
        final TrayIcon trayIcon = new TrayIcon(
                image.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH),
                "FileExtractor",
                popup);

        MenuItem scanItem = new MenuItem("Scan manually");
        MenuItem pauseItem = new MenuItem("Pause timer");
        MenuItem resumeItem = new MenuItem("Resume timer");
        MenuItem logItem = new MenuItem("Log");
        MenuItem exitItem = new MenuItem("Exit");
        Menu settingsMenu = this.createSettingsSubmenu(controller, typeSettings);

        scanItem.addActionListener(e -> {
            controller.initiateManualExtraction(generalSettings, typeSettings);
        });

        pauseItem.addActionListener(e -> {
            controller.stopTimer();
            this.changeVisibility(controller, pauseItem, resumeItem);
        });

        resumeItem.addActionListener(e -> {
            controller.startTimer(false, generalSettings, typeSettings);
            this.changeVisibility(controller, pauseItem, resumeItem);
        });

        exitItem.addActionListener(e -> {
            controller.shutDownApplication();
        });

        logItem.addActionListener(e -> {
            controller.openFile("./Logs/FileExtractor.log");
        });

        popup.add(scanItem);
        if (generalSettings.useTimer()) {
            popup.add(pauseItem);
            popup.add(resumeItem);
        }
        popup.add(logItem);
        popup.add(settingsMenu);
        popup.add(exitItem);

        this.changeVisibility(controller, pauseItem, resumeItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        }
        catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}