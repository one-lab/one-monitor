package org.onelab.monitor.agent.config.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunliangh on 14-12-29.
 */
public class NodeSelector {

    /**
     * 根据层级索引选择node
     * @param src root node
     * @param indexes 层级索引 start from 0
     * @return
     */
    public static Node selectNode(Node src,int... indexes){
        if (src == null) return null;
        Node node = src;
        for (int i=0;i<indexes.length;i++){
            List<Node> subNodes = node.subs;
            int index = indexes[i];
            if (subNodes != null || subNodes.size()>index){
                node = node.subs.get(indexes[i]);
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * 根据节点名称、序号搜索节点
     * @param src root node
     * @param name 节点名称
     * @param index 节点位置 start from 0
     * @return
     */
    public static Node selectNode(Node src,String name,int index){
        if (src == null) return null;
        List<Node> res = selectNodes(src,name);
        if (res.size()>index){
            return res.get(index);
        }
        return null;
    }

    /**
     * 根据节点名称获取节点列表
     * @param src root node
     * @param name 节点名称
     * @return
     */
    public static List<Node> selectNodes(Node src,String name){
        if (src == null) return null;
        List<Node> res = new ArrayList<Node>();
        selectNode(res,src,name);
        return res;
    }

    private static void selectNode(List<Node> res,Node src,String name){
        if (src.subs!=null){
            for (Node node:src.subs){
                if (name.equals(node.name)){
                    res.add(node);
                }
                selectNode(res,node,name);
            }
        }
    }
}
