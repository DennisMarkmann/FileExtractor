package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import markmann.dennis.fileExtractor.logging.LogHandler;

public class XMLFileReader {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private Object convertValueToType(Object fieldType, String value) {
        if (fieldType.equals(boolean.class)) {
            return Boolean.valueOf(value);
        }
        else if (fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        else if (fieldType.equals(String.class)) {
            return value;
        }
        String errorMessage = "No handling implemented for reading given datatype '" + fieldType + "'.";
        LOGGER.error(errorMessage);
        System.out.println(errorMessage);
        return null;
    }

    private String getValueByName(Element element, String name) {
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    public GeneralSettings readSettingsXML(String name, GeneralSettings settings) {

        try {

            if (SettingHandler.getGeneralSettings().useExtendedLogging()) {
                LOGGER.info("Reading '" + name + "' file.");
            }

            File fXmlFile = new File("./Settings/" + name);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Settings");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;

                    for (Field field : settings.getClass().getDeclaredFields()) {

                        try {
                            Object oldFieldValue = field.get(settings);
                            Object newFieldValue = this
                                    .convertValueToType(field.getType(), this.getValueByName(element, field.getName()));
                            if (!oldFieldValue.equals(newFieldValue)) {
                                // TODO print settings changed if not initial
                                field.set(settings, newFieldValue);
                            }
                        }
                        catch (IllegalArgumentException | IllegalAccessException e) {
                            LOGGER.error("Reading of '" + name + "' file failed.", e);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Reading of '" + name + "' file failed.", e);
            e.printStackTrace();
        }
        return settings;
    }

}