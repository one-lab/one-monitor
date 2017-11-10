package org.onelab.monitor.agent.config;

import org.onelab.monitor.agent.config.xml.Node;
import org.onelab.monitor.agent.config.xml.NodeSelector;
import org.onelab.monitor.agent.config.xml.XmlUtil;
import org.onelab.monitor.agent.log.AgentLogger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chunliangh on 14-12-7.
 */
public class Config {

    private static final String TYPE = "type";

    private static final String TRACK = "track";
    private static final String TRACK_DURATION = "duration";

    private static final String METHOD = "method";
    private static final String METHOD_OWNER = "owner";
    private static final String METHOD_NAME = "name";
    private static final String METHOD_DESC = "desc";

    private static final String INCLUDEPATTERNS = "includepatterns";
    private static final String INCLUDEPATTERN = "includepattern";
    private static final String EXCLUDEPATTERNS = "excludepatterns";
    private static final String EXCLUDEPATTERN = "excludepattern";

    private static final String CODEINSERTERBUILDERS = "codeinserterbuilders";
    private static final String CODEINSERTERBUILDER = "codeinserterbuilder";

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

    public static int trackDuration ;

    public Set<String> typeIncludepatterns = new HashSet<String>();
    public Set<String> typeExcludepatterns = new HashSet<String>();

    public Set<MethodDesc> methodIncludepatterns = new HashSet<MethodDesc>();
    public Set<MethodDesc> methodExcludepatterns = new HashSet<MethodDesc>();

    public Set<String> codeinserterbuilders = new HashSet<String>();

    public static Config init(String path) {
        startUpdater(path);
        return update(path);
    }

    private static void startUpdater(final String path) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        update(path);
                    } catch (Throwable e) {
                        AgentLogger.sys.severe(e.toString());
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        AgentLogger.sys.info("Config Updater start success .");
    }

    private synchronized static Config update(String path) {
        Config config = new Config();
        Node node = XmlUtil.parseXml(path);
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
