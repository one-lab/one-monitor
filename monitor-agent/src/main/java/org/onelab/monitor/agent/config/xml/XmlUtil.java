package org.onelab.monitor.agent.config.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

/**
 * Created by chunliangh on 14-12-29.
 */
public class XmlUtil {
    public static Node parseXml(InputStream inputStream){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);//开启验证XML功能
        try {
            SAXParser parser = factory.newSAXParser();
            XmlHandler configHandler = new XmlHandler();
            parser.parse(inputStream,configHandler);
            return configHandler.getNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Node parseXml(String source){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);//开启验证XML功能
        try {
            SAXParser parser = factory.newSAXParser();
            XmlHandler configHandler = new XmlHandler();
            parser.parse(source,configHandler);
            return configHandler.getNode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
