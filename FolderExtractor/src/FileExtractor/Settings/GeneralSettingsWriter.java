package FileExtractor.Settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class GeneralSettingsWriter {

    private void createXmlFile(final GeneralSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, element, "TimerInterval", settings.getTimerInterval() + "");
        helper.createElement(doc, element, "UseRenaming", settings.isUseRenaming() + "");
        helper.createElement(doc, element, "UseLogging", settings.isUseLogging() + "");
        helper.createElement(doc, element, "UseUnzipping", settings.isUseUnzipping() + "");
        helper.createElement(doc, element, "UseGui", settings.isUseGui() + "");
        helper.createElement(doc, element, "UseTimer", settings.isUseTimer() + "");
        helper.createElement(doc, element, "UseTaskbarEntry", settings.isUseTaskbarEntry() + "");
        helper.createElement(doc, element, "UseNotificationWhileWorking", settings.isUseNotificationWhileWorking() + "");
        helper.createElement(doc, element, "Language", settings.getLanguage());
        helper.writeFile("Settings//", "General", doc);
    }

    final void initializeXMLPrint(final GeneralSettings settings) {
        this.createXmlFile(settings);
    }
}
