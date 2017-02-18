package markmann.dennis.fileExtractor.settings;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class TypeSettingsWriter {

    private void createXmlFile(final TypeSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        helper.createElement(doc, element, "Type", settings.getType().toString());
        helper.createElement(doc, element, "ExtractionPath", settings.getExtractionPath());
        helper.createElement(doc, element, "CompletionPath", settings.getCompletionPath());
        helper.createElement(doc, element, "UseSeriesFolder", settings.useSeriesFolder() + "");
        helper.createElement(doc, element, "UseSeasonFolder", settings.useSeasonFolder() + "");
        helper.createElement(doc, element, "UseCurrentlyWatchingCheck", settings.useCurrentlyWatchingCheck() + "");

        for (final ExceptionPath exceptionPath : settings.getExceptions()) {
            final Element exceptionElement = helper.createElement(doc, element, "Exception", null);
            helper.createElement(doc, exceptionElement, "ExceptionName", exceptionPath.getName());
            helper.createElement(doc, exceptionElement, "ExceptionPath", exceptionPath.getPath());
        }
        helper.writeFile("./Settings/", settings.getType().toString(), doc);
    }

    final void initializeXMLPrint(final ArrayList<TypeSettings> settingList) {

        for (final TypeSettings settings : settingList) {
            this.createXmlFile(settings);
        }
    }
}
