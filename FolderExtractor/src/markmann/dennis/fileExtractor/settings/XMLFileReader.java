package markmann.dennis.fileExtractor.settings;

import java.io.File;

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

    private String getValueByName(Element element, String name) {
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    public GeneralSettings readGeneralSettings() {

        GeneralSettings generalSettings = null;

        try {

            if (SettingHandler.getGeneralSettings().useExtendedLogging()) {
                LOGGER.info("Reading 'General.xml' file.");
            }

            File fXmlFile = new File("./Settings/General.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Settings");
            generalSettings = new GeneralSettings();

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;

                    generalSettings.setUseGui(Boolean.valueOf(this.getValueByName(element, "UseGui")));
                    generalSettings.setUseSystemTray(Boolean.valueOf(this.getValueByName(element, "UseSystemTray")));
                    generalSettings.setUseTimer(Boolean.valueOf(this.getValueByName(element, "UseTimer")));
                    generalSettings.setTimerInterval(Integer.parseInt(this.getValueByName(element, "TimerInterval")));
                    generalSettings.setUseNotificationWhileWorking(
                            Boolean.valueOf(this.getValueByName(element, "UseNotificationWhileWorking")));
                    generalSettings.setUseRenaming(Boolean.valueOf(this.getValueByName(element, "UseRenaming")));
                    generalSettings.setUseCleanup(Boolean.valueOf(this.getValueByName(element, "UseCleanup")));
                    generalSettings.setUseFileMoving(Boolean.valueOf(this.getValueByName(element, "UseFileMoving")));
                    generalSettings.setUseExtendedLogging(Boolean.valueOf(this.getValueByName(element, "UseExtendedLogging")));
                    generalSettings.setRemoveCorruptFiles(Boolean.valueOf(this.getValueByName(element, "RemoveCorruptFiles")));
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Reading of 'General.xml' file failed.", e);
        }
        return generalSettings;
    }

}