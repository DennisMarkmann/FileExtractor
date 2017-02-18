package markmann.dennis.fileExtractor.settings;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import markmann.dennis.fileExtractor.logging.LogHandler;

class GeneralSettingsWriter {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private void createXmlFile(final GeneralSettings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        for (Field field : settings.getClass().getDeclaredFields()) {

            try {
                helper.createElement(doc, element, field.getName(), field.get(settings) + "");
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.error("Writing of 'General.xml' file failed.", e);
                e.printStackTrace();
            }
        }
        helper.writeFile("./Settings/", "General", doc);
    }

    final void initializeXMLPrint(final GeneralSettings settings) {
        this.createXmlFile(settings);
    }
}
