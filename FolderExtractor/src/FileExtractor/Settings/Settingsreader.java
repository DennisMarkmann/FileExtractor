package FileExtractor.Settings;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Settingsreader {

    final void readXmlFile(String path) {

        final FileReaderHelper helper = new FileReaderHelper();

        final Document doc = helper.createDocument(new File(path));
        if (doc == null) {
            return;
        }

        final NodeList nList = doc.getElementsByTagName("Settings");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            final Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) nNode;

                final String type = helper.getElementValue(element, "Type");
                final String extractionPath = helper.getElementValue(element, "Extractionpath");
                final String Path = helper.getElementValue(element, "Path");

                // creator.createGroup(groupName, description, fixSize);

            }
        }
    }
}
