package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.config.xml.Node;
import org.onelab.monitor.agent.config.xml.NodeSelector;
import org.onelab.monitor.agent.config.xml.XmlUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chunliangh on 14-12-7.
 */
public class Config {

    public static final String LOG = "log";
    public static final String LOG_LEVEL = "level";
    public static final String LOG_CONSOLE = "console";

    public static final String TYPE = "type";

    public static final String TRACK = "track";
    public static final String TRACK_DURATION = "duration";

    public static final String METHOD = "method";
    public static final String METHOD_OWNER = "owner";
    public static final String METHOD_NAME = "name";
    public static final String METHOD_DESC = "desc";

    public static final String INCLUDEPATTERNS = "includepatterns";
    public static final String INCLUDEPATTERN = "includepattern";
    public static final String EXCLUDEPATTERNS = "excludepatterns";
    public static final String EXCLUDEPATTERN = "excludepattern";

    public static final String CODEINSERTERBUILDERS = "codeinserterbuilders";
    public static final String CODEINSERTERBUILDER = "codeinserterbuilder";

    public static class MethodDesc {
        public String owner;
        public String name;
        public String desc;
        public MethodDesc(String owner,String name,String desc){
            this.owner = owner;
            this.name = name;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return this.owner+"#"+this.name+"#"+this.desc;
        }
    }

    public static String logLevel = "INFO";
    // todo 暂时不支持此参数
    public static boolean logConsole ;
    // todo 未来该值应该可以动态变更
    public static int trackDuration ;

    public Set<String> typeIncludepatterns = new HashSet<String>();
    public Set<String> typeExcludepatterns = new HashSet<String>();

    public Set<MethodDesc> methodIncludepatterns = new HashSet<MethodDesc>();
    public Set<MethodDesc> methodExcludepatterns = new HashSet<MethodDesc>();

    public Set<String> codeinserterbuilders = new HashSet<String>();

    public static Config init(String path) {
        Config config = new Config();
        Node node = XmlUtil.parseXml(path);
        Node nodeLog = NodeSelector.selectNode(node,LOG,0);
        if (nodeLog!=null && nodeLog.attributes!=null){
            config.logLevel = nodeLog.attributes.get(LOG_LEVEL);
            String isConsole = nodeLog.attributes.get(LOG_CONSOLE);
            if ("true".equals(isConsole)){
                config.logConsole = true;
            }
        }
        Node typeNode = NodeSelector.selectNode(node,TYPE,0);
        if (typeNode!=null){
            Node typeInclude = NodeSelector.selectNode(typeNode,INCLUDEPATTERNS,0);
            List<Node> subs = NodeSelector.selectNodes(typeInclude,INCLUDEPATTERN);
            if (subs!=null){
                for (Node iterm:subs){
                    config.typeIncludepatterns.add(iterm.value);
                }
            }
            Node typeExclude = NodeSelector.selectNode(typeNode,EXCLUDEPATTERNS,0);
            subs = NodeSelector.selectNodes(typeExclude,EXCLUDEPATTERN);
            if (subs!=null){
                for (Node iterm:subs){
                    config.typeExcludepatterns.add(iterm.value);
                }
            }
        }
        Node methodNode = NodeSelector.selectNode(node,METHOD,0);
        if (methodNode!=null){
            Node methodInclude = NodeSelector.selectNode(methodNode,INCLUDEPATTERNS,0);
            List<Node> subs = NodeSelector.selectNodes(methodInclude,INCLUDEPATTERN);
            if (subs!=null){
                for (Node iterm:subs){
                    Node ownerNode = NodeSelector.selectNode(iterm,METHOD_OWNER,0);
                    Node nameNode = NodeSelector.selectNode(iterm,METHOD_NAME,0);
                    Node descNode = NodeSelector.selectNode(iterm,METHOD_DESC,0);
                    if (ownerNode!=null && nameNode!=null && descNode!=null){
                        config.methodIncludepatterns.add(
                            new MethodDesc(ownerNode.value,nameNode.value,descNode.value)
                        );
                    }
                }
            }
            Node methodExclude = NodeSelector.selectNode(methodNode,EXCLUDEPATTERNS,0);
            subs = NodeSelector.selectNodes(methodExclude,EXCLUDEPATTERN);
            if (subs!=null){
                for (Node iterm:subs){
                    Node ownerNode = NodeSelector.selectNode(iterm,METHOD_OWNER,0);
                    Node nameNode = NodeSelector.selectNode(iterm,METHOD_NAME,0);
                    Node descNode = NodeSelector.selectNode(iterm,METHOD_DESC,0);
                    if (ownerNode!=null && nameNode!=null && descNode!=null){
                        config.methodExcludepatterns.add(
                            new MethodDesc(ownerNode.value,nameNode.value,descNode.value)
                        );
                    }
                }
            }
        }
        Node codeinserterbuildersNode = NodeSelector.selectNode(node,CODEINSERTERBUILDERS,0);
        List<Node> subs = NodeSelector.selectNodes(codeinserterbuildersNode,CODEINSERTERBUILDER);
        if (subs!=null){
            for (Node iterm:subs){
                config.codeinserterbuilders.add(iterm.value);
            }
        }
        Node track = NodeSelector.selectNode(node,TRACK,0);
        if (track != null){
            Node trackDurationNode = NodeSelector.selectNode(track, TRACK_DURATION, 0);
            if (trackDurationNode != null){
                if (trackDurationNode.value != null && trackDurationNode.value.length()>0){
                    config.trackDuration = Integer.parseInt(trackDurationNode.value);
                }
            }
        }
        return config;
    }

}
