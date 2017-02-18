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
import markmann.dennis.fileExtractor.objects.MediaType;

public class XMLFileReader {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    private Object convertValueToType(Object fieldType, String value) {
        if (fieldType.equals(boolean.class)) {
            return Boolean.valueOf(value);
        }
        else if (fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        else if (fieldType.equals(MediaType.class)) {
            if (value.equals("Anime")) {
                return MediaType.Anime;
            }
            else if (value.equals("Series")) {
                return MediaType.Series;
            }
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

    public void readSettingsXML(String name, Settings settings, boolean initial) {

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
            if (settings instanceof TypeSettings) {
                ((TypeSettings) settings).clearExceptions();
            }
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;

                    for (Field field : settings.getClass().getDeclaredFields()) {

                        try {
                            String fieldName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                            if ((settings instanceof TypeSettings) && fieldName.equals("Exceptions")) {
                                NodeList nodeList = element.getChildNodes();
                                for (int i = 0; i < nodeList.getLength(); i++) {
                                    if (nodeList.item(i).getNodeName().equals("Exception")) {
                                        Element subElement = (Element) nodeList.item(i);
                                        String exceptionName = this.getValueByName(subElement, "ExceptionName");
                                        String exceptionPath = this.getValueByName(subElement, "ExceptionPath");
                                        ((TypeSettings) settings).addException(new ExceptionPath(exceptionName, exceptionPath));
                                    }
                                }
                                continue;
                            }
                            Object oldFieldValue = field.get(settings);
                            Object newFieldValue = this
                                    .convertValueToType(field.getType(), this.getValueByName(element, fieldName));
                            if ((oldFieldValue == null) || !oldFieldValue.equals(newFieldValue)) {
                                if (!initial) {
                                    LOGGER.info("Changed value of '" + fieldName + "' to '" + newFieldValue + "'.");
                                }
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
    }

}