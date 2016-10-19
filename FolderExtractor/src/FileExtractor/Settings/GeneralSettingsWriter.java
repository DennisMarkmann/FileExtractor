package FileExtractor.Settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class GeneralSettingsWriter {

    private void createXmlFile(final GeneralSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, element, "TimerInterval", settings.getTimerInterval() + "");
        helper.createElement(doc, element, "UseRenaming", settings.useRenaming() + "");
        helper.createElement(doc, element, "UseLogging", settings.useLogging() + "");
        helper.createElement(doc, element, "UseUnzipping", settings.useUnzipping() + "");
        helper.createElement(doc, element, "UseGui", settings.useGui() + "");
        helper.createElement(doc, element, "UseTimer", settings.useTimer() + "");
        helper.createElement(doc, element, "UseTaskbarEntry", settings.useTaskbarEntry() + "");
        helper.createElement(doc, element, "UseNotificationWhileWorking", settings.useNotificationWhileWorking() + "");
        helper.createElement(doc, element, "Language", settings.getLanguage());
        helper.writeFile("Settings//", "General", doc);
    }

    final void initializeXMLPrint(final GeneralSettings settings) {
        this.createXmlFile(settings);
    }
}
