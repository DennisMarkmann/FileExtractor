package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

class FileReaderHelper {

    final Document createDocument(final File file) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        }
        catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (final SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        return doc;

    }

    // final String getElementValue(final Element element, final String name) {
    // return element.getElementsByTagName(name).item(0).getTextContent();
    // }

    // public final void readXMLFiles(String path) {
    // new SettingsReader().readXmlFile(path);
    // }
}
