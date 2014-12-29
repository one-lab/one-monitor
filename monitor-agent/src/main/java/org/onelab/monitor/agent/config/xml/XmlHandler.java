package org.onelab.monitor.agent.config.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chunliangh on 14-12-7.
 */
public class XmlHandler extends DefaultHandler {

    private Node root = new Node();
    private Node index = root;

    public Node getNode(){
        return root;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Node _this = new Node();
        _this.parent = index;
        if (index.subs == null){
            index.subs = new ArrayList<Node>();
        }
        index.subs.add(_this);
        index = _this;
        index.name = qName;
        int len = attributes.getLength();
        if (len>0 && index.attributes==null){
            index.attributes = new HashMap<String, String>(len);
        }
        for (int i=0;i<len;i++){
            String qn = attributes.getQName(i);
            index.attributes.put(qn,attributes.getValue(i).trim());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        index = index.parent;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (ch == null || ch.length==0) return;
        String value=new String(ch,start,length).trim();
        index.value = value;
    }
}
