package org.onelab.monitor.agent.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chunliangh on 14-12-7.
 */
public class ConfigHandler extends DefaultHandler {

    private Node node = new Node();
    private Config config = new Config();
    private Config.MethodDesc includePattern ;
    private Config.MethodDesc excludePattern ;

    private class Node {
        public String name;
        public Node parent;
        public List<Node> children = new LinkedList<Node>();
        public Node(){}
        public Node(String name){this.name = name;}
        public void addChild(Node n){n.parent = this;children.add(n);}
    }

    public final String CONFIG_LOG = "log";
    public final String CONFIG_LOG_LEVEL = "level";
    public final String CONFIG_LOG_CONSOLE = "console";
    public final String CONFIG_TYPE = "type";
    public final String CONFIG_METHOD = "method";
    public final String CONFIG_PRIVATE = "private";
    public final String CONFIG_INCLUDE_PATTERN = "includepattern";
    public final String CONFIG_EXCLUDE_PATTERN = "excludepattern";
    public final String CONFIG_FORCE_INCLUDE_PATTERN = "forceincludepattern";
    public final String CONFIG_CODEINSERTERBUILDER = "codeinserterbuilder";
    public final String CONFIG_OWNER = "owner";
    public final String CONFIG_NAME = "name";
    public final String CONFIG_DESC = "desc";

    public Config getConfig(){
        return config;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Node _this = new Node(qName);
        node.addChild(_this);
        node = _this;
        if (qName.equals(CONFIG_LOG)){
            config.log.level = attributes.getValue(CONFIG_LOG_LEVEL);
            String console = attributes.getValue(CONFIG_LOG_CONSOLE);
            if (Commons.TRUE.equals(console)){
                config.log.console = true;
            }
        } else if (qName.equals(CONFIG_TYPE)){
            String privateclass = attributes.getValue(CONFIG_PRIVATE);
            if (Commons.TRUE.equals(privateclass)){
                config.type.privateOn = true;
            }
        } else if (qName.equals(CONFIG_METHOD)){
            String privatemethod = attributes.getValue(CONFIG_PRIVATE);
            if (Commons.TRUE.equals(privatemethod)){
                config.method.privateOn = true;
            }
        } else if (node.name.equals(CONFIG_INCLUDE_PATTERN)
                && node.parent.parent.name.equals(CONFIG_METHOD)){
            includePattern = new Config.MethodDesc();
        } else if (node.name.equals(CONFIG_EXCLUDE_PATTERN)
                && node.parent.parent.name.equals(CONFIG_METHOD)){
            excludePattern = new Config.MethodDesc();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (node.name.equals(CONFIG_INCLUDE_PATTERN) && node.parent.parent.name.equals(CONFIG_METHOD)){
            config.method.includepatterns.add(includePattern);
        } else if (node.name.equals(CONFIG_EXCLUDE_PATTERN) && node.parent.parent.name.equals(CONFIG_METHOD)){
            config.method.excludepatterns.add(excludePattern);
        }
        node = node.parent;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (ch == null || ch.length==0) return;
        String value=new String(ch,start,length).trim();
        if (node.name.equals(CONFIG_INCLUDE_PATTERN)
                && node.parent.parent.name.equals(CONFIG_TYPE)){
            config.type.includepatterns.add(value);
        }
        else if(node.name.equals(CONFIG_EXCLUDE_PATTERN)
                && node.parent.parent.name.equals(CONFIG_TYPE)){
            config.type.excludepatterns.add(value);
        }
        else if (node.name.equals(CONFIG_OWNER)
                && node.parent.name.equals(CONFIG_INCLUDE_PATTERN)){
            includePattern.owner = value;
        }
        else if(node.name.equals(CONFIG_NAME)
                && node.parent.name.equals(CONFIG_INCLUDE_PATTERN)){
            includePattern.name = value;
        }
        else if(node.name.equals(CONFIG_DESC)
                && node.parent.name.equals(CONFIG_INCLUDE_PATTERN)){
            includePattern.desc = value;
        }
        else if (node.name.equals(CONFIG_OWNER)
                && node.parent.name.equals(CONFIG_EXCLUDE_PATTERN)){
            excludePattern.owner = value;
        }
        else if(node.name.equals(CONFIG_NAME)
                && node.parent.name.equals(CONFIG_EXCLUDE_PATTERN)){
            excludePattern.name = value;
        }
        else if(node.name.equals(CONFIG_DESC)
                && node.parent.name.equals(CONFIG_EXCLUDE_PATTERN)){
            excludePattern.desc = value;
        }
        else if(node.name.equals(CONFIG_FORCE_INCLUDE_PATTERN)){
            config.type.forceincludepatterns.add(value);
        }
        else if (node.name.equals(CONFIG_CODEINSERTERBUILDER)){
            config.codeinserterbuilders.add(value);
        }
    }
}
