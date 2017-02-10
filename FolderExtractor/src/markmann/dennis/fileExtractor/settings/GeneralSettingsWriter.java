package markmann.dennis.fileExtractor.settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class GeneralSettingsWriter {

    private void createXmlFile(final GeneralSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, element, "UseGui", settings.useGui() + "");
        helper.createElement(doc, element, "UseSystemTray", settings.useSystemTray() + "");
        helper.createElement(doc, element, "UseTimer", settings.useTimer() + "");
        helper.createElement(doc, element, "TimerInterval", settings.getTimerInterval() + "");
        helper.createElement(doc, element, "UseRenaming", settings.useRenaming() + "");
        helper.createElement(doc, element, "UseFileMoving", settings.useFileMoving() + "");
        helper.createElement(doc, element, "UseCleanup", settings.useCleanup() + "");
        helper.createElement(doc, element, "UseExtendedLogging", settings.useExtendedLogging() + "");
        helper.createElement(doc, element, "RemoveCorruptFiles", settings.removeCorruptFiles() + "");
        helper.createElement(doc, element, "UseNotificationWhileWorking", settings.useNotificationWhileWorking() + "");

        helper.writeFile("Settings//", "General", doc);
    }

    final void initializeXMLPrint(final GeneralSettings settings) {
        this.createXmlFile(settings);
    }
}
