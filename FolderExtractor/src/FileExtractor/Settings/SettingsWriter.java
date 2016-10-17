package FileExtractor.Settings;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class SettingsWriter {

    private String path = "";

    private void createXmlFile(final Settings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element groupsElement = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, groupsElement, "Type", settings.getType());
        helper.createElement(doc, groupsElement, "ExtractionPath", settings.getExtractionPath());
        helper.createElement(doc, groupsElement, "Path", settings.getCompletionPath());

        helper.writeFile(this.path, "Settings", doc);
        helper.writeFile("Settings//", settings.getType(), doc);

    }

    final void initializeXMLPrint(final ArrayList<Settings> settingList) {

        for (final Settings settings : settingList) {
            this.createXmlFile(settings);
        }
    }
}
