package markmann.dennis.fileExtractor.settings;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import markmann.dennis.fileExtractor.logging.LogHandler;

public class FileWriteHelper {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    final Document createDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    final Element createElement(final Document doc, final Element superiorElement, final String name, final String value) {
        final Element element = doc.createElement(name);
        if (value != null) {
            element.appendChild(doc.createTextNode(value));
        }
        superiorElement.appendChild(element);
        return element;
    }

    final Element createMainElement(final Document doc, final String name) {
        final Element element = doc.createElement(name);
        doc.appendChild(element);
        return element;
    }

    // write the content into xml file
    final void writeFile(final String path, final String fileName, final Document doc) {
        new File(path).mkdirs();
        final File file = new File(path + fileName);

        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(file));
        }
        catch (final TransformerException e) {
            LOGGER.error("Writing of '" + fileName + "' file failed.", e);
            e.printStackTrace();
        }

    }

}
