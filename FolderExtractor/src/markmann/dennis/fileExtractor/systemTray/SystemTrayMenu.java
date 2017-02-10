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

import markmann.dennis.fileExtractor.logic.Controller;
import markmann.dennis.fileExtractor.settings.TypeSettings;

public class SystemTrayMenu {

    private Menu createSettingsSubmenu(Controller controller) {

        Menu settingsMenu = new Menu("Settings");
        MenuItem generalSettings = new MenuItem("General");
        generalSettings.addActionListener(e -> {
            controller.openFile("./Settings/General.xml");
        });
        settingsMenu.add(generalSettings);

        for (TypeSettings settings : controller.getSettingList()) {
            MenuItem subMenuItem = new MenuItem(settings.getName());
            subMenuItem.addActionListener(e -> {
                controller.openFile("./Settings/" + settings.getName() + ".xml");
            });
            settingsMenu.add(subMenuItem);
        }
        return settingsMenu;
    }

    public void createSystemTrayEntry(Controller controller) {

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
        MenuItem logItem = new MenuItem("Log");
        MenuItem exitItem = new MenuItem("Exit");
        Menu settingsMenu = this.createSettingsSubmenu(controller);

        scanItem.addActionListener(e -> {
            controller.startExtraction(true);
        });

        exitItem.addActionListener(e -> {
            controller.shutDownApplication();
        });

        logItem.addActionListener(e -> {
            controller.openFile("./Logs/FileExtractor.log");
        });

        popup.add(scanItem);
        popup.add(logItem);
        popup.add(settingsMenu);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        }
        catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}