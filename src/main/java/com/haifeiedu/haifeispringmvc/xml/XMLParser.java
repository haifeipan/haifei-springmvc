package com.haifeiedu.haifeispringmvc.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;

/**
 * XMLParser is used to parse the configuration file of Spring
 */
public class XMLParser {
    public static String getBasePackage(String xmlFile) {
        SAXReader saxReader = new SAXReader();

        //get the classLoader of the class => find the resource stream of the spring configuration file.
        InputStream inputStream = XMLParser.class.getClassLoader().getResourceAsStream(xmlFile);

        try {
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Element componentScanElement = rootElement.element("component-scan");
            Attribute attribute = componentScanElement.attribute("base-package");
            return attribute.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
