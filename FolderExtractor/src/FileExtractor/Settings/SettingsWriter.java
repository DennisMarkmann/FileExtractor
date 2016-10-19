package FileExtractor.Settings;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class SettingsWriter {

    private String path = "";

    private void createXmlFile(final TypeSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element groupsElement = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, groupsElement, "Name", settings.getName());
        helper.createElement(doc, groupsElement, "ExtractionPath", settings.getExtractionPath());
        helper.createElement(doc, groupsElement, "Path", settings.getCompletionPath());

        helper.writeFile(this.path, "Settings", doc);
        helper.writeFile("Settings//", settings.getName(), doc);

    }

    final void initializeXMLPrint(final ArrayList<TypeSettings> settingList) {

        for (final TypeSettings settings : settingList) {
            this.createXmlFile(settings);
        }
    }
}
