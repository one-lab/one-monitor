package org.onelab.monitor.agent.config.xml;

import java.util.List;
import java.util.Map;

/**
 * Created by chunliangh on 14-12-29.
 */
public class Node {
    public String name ;
    public String value ;
    public Node parent ;
    public List<Node> subs ;
    public Map<String,String> attributes ;
}
